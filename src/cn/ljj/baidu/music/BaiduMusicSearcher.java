package cn.ljj.baidu.music;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.ljj.common.Logger;

public class BaiduMusicSearcher {
	private static final String TAG = "BaiduMusicSearcher";
	public static final String BAIDU_QUERY_BASE = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.common&format=json&from=ios&version=4.1.1";
	public static final String BAIDU_SONG_DETAIL_BASE = "http://ting.baidu.com/data/music/links?";
	public static final String KEY_QUERY = "query";
	public static final String KEY_PAGE_SIZE = "page_size";
	public static final String KEY_PAGE_NO = "page_no";

	private SearchCallback mCallback = null;
	private String mUrl = null;
	private SearchThread mSearchThread = null;
	private boolean isSearching = false;

	public interface SearchCallback {
		public void onSearchResult(BaiduMusicSearchResult result);
	}

	public void setCallBack(SearchCallback callbakc) {
		mCallback = callbakc;
	}

	private void onSearchResult(BaiduMusicSearchResult result) {
		isSearching = false;
		if (mCallback != null && !mSearchThread.mCancel) {
			mCallback.onSearchResult(result);
		}
	}

	public void search(String keys, int pageSize, int pageNo) {
		if(isSearching){
			return;
		}
		isSearching = true;
		Logger.v(TAG, "search keys=" + keys + ", pageSize=" + pageSize + ", pageNo=" + pageNo);
		try {
			mUrl = BAIDU_QUERY_BASE + "&page_size=" + pageSize + "&page_no=" + pageNo + "&query=";
			mUrl += URLEncoder.encode(keys, "UTF-8");
			Logger.i(TAG, "search mUrl=" + mUrl);
			if (mSearchThread != null) {
				mSearchThread.mCancel = true;
			}
			mSearchThread = new SearchThread();
			mSearchThread.start();
		} catch (UnsupportedEncodingException e) {
			onSearchResult(null);
			Logger.e(TAG, "search failed", e);
		}
	}

	public void fetchMusicDetail(BaiduMusicInfo musicInfo, String rate) {
		String addr = BAIDU_SONG_DETAIL_BASE + "songIds=" + musicInfo.songId;
		if (rate != null) {
			addr += "&rate=" + rate;
		}
		try {
			URL url = new URL(addr);
			InputStream ips = url.openConnection().getInputStream();
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(new InputStreamReader(ips));
			JsonObject object = element.getAsJsonObject();
			int errorCode = object.get("errorCode").getAsInt();
			if (errorCode == 22000) {
				JsonObject data = object.get("data").getAsJsonObject();
				JsonArray songList = data.get("songList").getAsJsonArray();
				JsonObject item = songList.get(0).getAsJsonObject();
				musicInfo.setDetailInfos(item);
				Logger.v(TAG, "fetchMusicDetail success musicInfo=" + musicInfo);
			} else {
				Logger.e(TAG, "fetchMusicDetail error errorCode=" + errorCode);
			}
		} catch (Exception e) {
			Logger.e(TAG, "fetchMusicDetail catch exception", e);
		}
	}

	public void cancel() {
		Logger.v(TAG, "search cancel");
		if (mSearchThread != null) {
			mSearchThread.mCancel = true;
		}
	}

	class SearchThread extends Thread {
		boolean mCancel = false;

		@Override
		public void run() {
			InputStream inStream = null;
			try {
				URL url = new URL(mUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5 * 1000);
				inStream = conn.getInputStream();
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(new InputStreamReader(inStream));
				JsonObject object = element.getAsJsonObject();
				BaiduMusicSearchResult result = BaiduMusicSearchResult.fromJson(object);
				Logger.v(TAG, "search success result=" + result);
				onSearchResult(result);
			} catch (Exception e) {
				onSearchResult(null);
				Logger.e(TAG, "search query failed", e);
			} finally {
				try {
					if (inStream != null) {
						inStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
