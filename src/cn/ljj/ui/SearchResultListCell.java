package cn.ljj.ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.ljj.baidu.music.BaiduMusicInfo;

public class SearchResultListCell extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = null;
	private JLabel authorLabel = null;
	private JLabel albumLabel = null;
	
	public SearchResultListCell(int listWidth){
		setLayout(null);
		int width = listWidth / 3;
		titleLabel = new JLabel();
		titleLabel.setBounds(0, 0, width, 30);
		add(titleLabel);
		authorLabel = new JLabel();
		authorLabel.setBounds(width, 0, width, 30);
		add(authorLabel);
		albumLabel = new JLabel();
		albumLabel.setBounds(2*width, 0, width, 30);
		add(albumLabel);
	}

	public void setMusicInfo(BaiduMusicInfo musicInfo){
		titleLabel.setText(musicInfo.title);
		authorLabel.setText(musicInfo.author);
		albumLabel.setText(musicInfo.albumTitle);
	}

	public void setStatus(boolean isSelected, boolean cellHasFocus){
		if(isSelected || cellHasFocus){
			setBackground(new Color(0x00ddcc));
		}else{
			setBackground(new Color(0xffffff));
		}
	}
}
