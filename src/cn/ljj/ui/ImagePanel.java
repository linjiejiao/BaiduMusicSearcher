package cn.ljj.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cn.ljj.common.Logger;

public class ImagePanel extends JPanel {
	private static final String TAG = ImagePanel.class.getName();
	private static final long serialVersionUID = 1L;
	private Image image;
	private Image defaultImage;

	public void setImageURL(String imgURL) {
		image = null;
		InputStream inputStream = null;
		try {
			if(imgURL == null || imgURL.length() <= 0){
				updateUI();
				return;
			}
			if (imgURL.startsWith("http")) {
				URL url = new URL(imgURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5 * 1000);
				inputStream = conn.getInputStream();
			} else {
				inputStream = new FileInputStream(imgURL);
			}
			if (inputStream != null) {
				image = ImageIO.read(inputStream);
			}
			updateUI();
		} catch (IOException e) {
			Logger.e(TAG, "setImageURL failed imgURL=" + imgURL, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Image getDefaultImage(){
		if(defaultImage == null){
			try {
				defaultImage = ImageIO.read(getClass().getResourceAsStream("image_failed.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return defaultImage;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (null == image) {
			image = getDefaultImage();
		}
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}
}
