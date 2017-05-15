package cn.ljj.ui;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cn.ljj.baidu.music.BaiduMusicInfo;

public class SearchResultListCellRender implements ListCellRenderer<BaiduMusicInfo>, Serializable {
	private static final long serialVersionUID = 1L;

    public Component getListCellRendererComponent(JList<? extends BaiduMusicInfo> list,  
    		BaiduMusicInfo value, int index, boolean isSelected, boolean cellHasFocus) {
    	SearchResultListCell cell = new SearchResultListCell(list.getWidth());
		cell.setMusicInfo((BaiduMusicInfo)value);
		cell.setStatus(isSelected, cellHasFocus);
        return cell;  
    } 
}
