package cn.ljj.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public class HttpManager implements Runnable {
	private static final String TAG = HttpManager.class.getSimpleName();
	public static final int ERROR_OTHER = 0;
	public static final int ERROR_MALFORMEDURL = 1;
	public static final int ERROR_PROTOCOL = 2;
	public static final int ERROR_UNSUPPORTEDENCODING = 3;
	public static final int ERROR_FILENOTFOUND = 4;
	public static final int ERROR_IOEXCEPTION = 5;
	public static final int ERROR_PARAMETER = 6;

	private static HttpManager sInstance = null;
	private List<HttpDownloadTask> pendingTask = new ArrayList<HttpDownloadTask>();
	private boolean isWaiting = false;

	public static HttpManager getInstance() {
		if (sInstance == null) {
			sInstance = new HttpManager();
		}
		return sInstance;
	}

	private HttpManager() {
		new Thread(this).start();
	}

	public void addTask(HttpDownloadTask tasak) {
		synchronized (this) {
			pendingTask.add(tasak);
			if (isWaiting) {
				notify();
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				HttpDownloadTask task = null;
				synchronized (this) {
					if (pendingTask.size() <= 0) {
						Logger.d(TAG, "No more DownloadTask, waiting....");
						isWaiting = true;
						wait();
						Logger.d(TAG, "New DownloadTask came, resume!");
					}
					task = pendingTask.remove(0);
				}
				if (task != null) {
					task.downloadSync();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public interface DownloadCallback {
		public void onProgressChange(int length, int finished);

		public void onFinished(String filePath);

		public void onFaild(int errorCode);
	}

	public static class HttpDownloadTask {
		private String mUrl = null;
		private String mSavePath = null;
		private boolean mCancel = false;
		private DownloadCallback mCallback = null;

		public HttpDownloadTask(String url, String savePath) {
			mUrl = url;
			mSavePath = savePath;
		}

		public void setCallBack(DownloadCallback callback) {
			mCallback = callback;
		}

		public void cancel() {
			Logger.d(TAG, "cancel:" + mUrl);
			mCancel = true;
		}

		private void onProgressChange(int length, int finished) {
//			Logger.d(TAG, "onProgressChange length:" + length + ", finished:" + finished);
			if(length < 0){
				length = finished;
			}
			final int finalLength = length;
			if (mCallback != null) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						mCallback.onProgressChange(finalLength, finished);
					}
				});
			}
		}

		private void onFinished(String filePath) {
			Logger.d(TAG, "onFinished:" + filePath);
			if (mCallback != null) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						mCallback.onFinished(filePath);
					}
				});
			}
		}

		private void onFaild(int errorCode) {
			Logger.e(TAG, "onFaild:" + errorCode);
			if (mCallback != null) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						mCallback.onFaild(errorCode);
					}
				});
			}
		}

		public void downloadSync() {
			Logger.d(TAG, "downloadSync:" + mUrl);
			if (mCancel) {
				return;
			}
			if ((mSavePath == null) || (mUrl == null)) {
				Logger.e(TAG, "run mSavePath=" + mSavePath + "; mUrl=" + mUrl);
				onFaild(ERROR_PARAMETER);
				return;
			}
			boolean isComplete = false;
			File file = new File(mSavePath);
			FileOutputStream fileOutputStream = null;
			InputStream inStream = null;
			try {
				URL url = new URL(mUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5 * 1000);
				inStream = conn.getInputStream();
				int length = conn.getContentLength();
				int finished = 0;
				file.createNewFile();
				fileOutputStream = new FileOutputStream(file);
				byte[] buffer = new byte[102400];
				int len = inStream.read(buffer);
				while ((len != -1) && (!mCancel)) {
					finished += len;
					fileOutputStream.write(buffer, 0, len);
					len = inStream.read(buffer);
					onProgressChange(length, finished);
				}
				if (!mCancel) {
					onFinished(mSavePath);
					isComplete = true;
				}
			} catch (MalformedURLException e) {
				onFaild(ERROR_MALFORMEDURL);
				e.printStackTrace();
			} catch (ProtocolException e) {
				onFaild(ERROR_PROTOCOL);
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				onFaild(ERROR_FILENOTFOUND);
				e.printStackTrace();
			} catch (IOException e) {
				onFaild(ERROR_IOEXCEPTION);
				e.printStackTrace();
			} finally {
				try {
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					if (inStream != null) {
						inStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!isComplete) {
				file.delete();
			}
		}
	}
}
