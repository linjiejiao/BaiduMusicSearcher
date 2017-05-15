package cn.ljj.baidu.music;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import cn.ljj.baidu.music.BaiduMusicInfo.AlbumInfo;
import cn.ljj.baidu.music.BaiduMusicInfo.ArtistInfo;

public class BaiduMusicSearchResult {
	public String query;
	public int isArtist;
	public int isAlbum;
	public String rsWords;
	public int total;
	public int rnNum;
	public ArrayList<BaiduMusicInfo> songList;
	public ArtistInfo artist;
	public AlbumInfo album;
	
	public static BaiduMusicSearchResult fromJson(JsonObject object) {
		BaiduMusicSearchResult result = new BaiduMusicSearchResult();
		result.query = object.get("query").getAsString();
		result.isArtist = object.get("is_artist").getAsInt();
		result.isAlbum = object.get("is_album").getAsInt();
		result.rsWords = object.get("rs_words").getAsString();
		JsonObject pages = object.get("pages").getAsJsonObject();
		result.total = pages.get("total").getAsInt();
		result.rnNum = pages.get("rn_num").getAsInt();
		JsonArray songList = object.get("song_list").getAsJsonArray();
		result.songList = new ArrayList<BaiduMusicInfo>();
		for(int i=0; i<songList.size(); i++){
			BaiduMusicInfo musicInfo = BaiduMusicInfo.fromJson(songList.get(i).getAsJsonObject());
			result.songList.add(musicInfo);
		}

		if(result.isArtist != 0){
			JsonObject artistInfo = object.get("artist").getAsJsonObject();
			result.artist = ArtistInfo.fromJson(artistInfo);
		}
		if(result.isAlbum != 0){
			JsonObject albumInfo = object.get("album").getAsJsonObject();
			result.album = AlbumInfo.fromJson(albumInfo);
		}
		return result;
	}

	@Override
	public String toString() {
		return "BaiduMusicSearchResult [query=" + query + ", isArtist=" + isArtist + ", isAlbum=" + isAlbum
				+ ", rsWords=" + rsWords + ", total=" + total + ", rnNum=" + rnNum + ", songList=" + songList
				+ ", artist=" + artist + ", album=" + album + "]";
	}
	
}
