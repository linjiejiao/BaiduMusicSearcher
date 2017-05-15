package cn.ljj.ui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import cn.ljj.baidu.music.BaiduMusicInfo;

public class SearchResultListModel extends AbstractListModel<BaiduMusicInfo>{
	private static final long serialVersionUID = 1L;

	private ArrayList<BaiduMusicInfo> data;
	
	public void setData(ArrayList<BaiduMusicInfo> data){
		this.data = data;
	}

	@Override
	public int getSize() {
		if(data == null){
			return 0;
		}
		return data.size();
	}

	@Override
	public BaiduMusicInfo getElementAt(int index) {
		if(data == null || index < 0 || index >= data.size()){
			return null;
		}
		return data.get(index);
	}

}
