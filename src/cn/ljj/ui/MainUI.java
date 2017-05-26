package cn.ljj.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.ljj.baidu.music.BaiduMusicInfo;
import cn.ljj.baidu.music.BaiduMusicSearchResult;
import cn.ljj.baidu.music.BaiduMusicSearcher;
import cn.ljj.baidu.music.BaiduMusicSearcher.SearchCallback;

public class MainUI extends JFrame implements SearchCallback, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JTextField searchTextField;
	private JButton searchButton;
	private SearchResultListModel resultListModel;
	private JList<BaiduMusicInfo> resultList;
	private BaiduMusicSearcher searcher;
	private ImagePanel singerImage;
	private JTextArea resultDetailText;

	public MainUI() {
		setLayout(null);
		setLocationRelativeTo(null);
		// 搜索输入
		JPanel searchInputPanel = new JPanel();
		searchInputPanel.setBounds(0, 10, 750, 40);
		searchTextField = new JTextField(50);
		searchTextField.setBounds(0, 10, 600, 20);
		searchTextField.setText("勇气");
		searchButton = new JButton("搜索");
		searchButton.setBounds(0, 0, 40, 20);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSearch();
			}
		});
		searchTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ActionEvent.ACTION_PERFORMED == e.getID()) {
					doSearch();
				}
			}
		});
		searchInputPanel.add(searchTextField);
		searchInputPanel.add(searchButton);
		add(searchInputPanel);
		// 搜索结果
		setupSearchResultList();
		// 结果详情
		JPanel detailPanel = new JPanel();
		detailPanel.setLayout(null);
		detailPanel.setBounds(530, 60, 200, 400);
		singerImage = new ImagePanel();
		singerImage.setBounds(25, 0, 150, 150);
		detailPanel.add(singerImage);
		resultDetailText = new JTextArea();
		resultDetailText.setLineWrap(true);
		JScrollPane resultPanel = new JScrollPane(resultDetailText);
		resultPanel.setBounds(0, 160, 200, 210);
		detailPanel.add(resultPanel);
		JPanel p = new JPanel();
		JButton downloadButton = new JButton("下载");
		p.setBounds(50, 370, 100, 40);
		p.add(downloadButton);
		detailPanel.add(p);
		add(detailPanel);
		// 设置标题
		this.setTitle("百度音乐搜索");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(750, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);

		searcher = new BaiduMusicSearcher();
		searcher.setCallBack(this);
	}

	private void setupSearchResultList() {
		resultList = new JList<BaiduMusicInfo>();
		resultListModel = new SearchResultListModel();
		resultList.setModel(resultListModel);
		resultList.setCellRenderer(new SearchResultListCellRender());
		resultList.setFixedCellHeight(30);
		resultList.setFixedCellWidth(480);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.addListSelectionListener(this);
		JScrollPane searchResultPanel = new JScrollPane(resultList);
		searchResultPanel.setBounds(20, 60, 500, 400);
		add(searchResultPanel);
		resultList.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					final BaiduMusicInfo music = resultListModel.getElementAt(resultList.locationToIndex(e.getPoint()));
					String[] rates = (music.allRate == null) ? null : music.allRate.split(",");
					if (rates != null && rates.length > 0) {
						JPopupMenu popupMenu = new JPopupMenu();
						for (String rate : rates) {
							JMenuItem item = new JMenuItem(rate);
							popupMenu.add(item);
							item.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									doFetchMusicDetails(music, rate);
								}
							});
						}
						popupMenu.show(e.getComponent(), e.getX(), e.getY());
					} else {
						resultList.setSelectedIndex(resultList.locationToIndex(e.getPoint()));
					}
				}

			}
		});
	}

	private void doSearch() {
		searcher.search(searchTextField.getText(), 20, 0);
	}

	@Override
	public void onSearchResult(BaiduMusicSearchResult result) {
		if (result == null) {
			return;
		}
		resultListModel.setData(result.songList);
		resultList.updateUI();
	}

	private void doFetchMusicDetails(BaiduMusicInfo music, String rate) {
		searcher.fetchMusicDetail(music, rate);
		singerImage.setImageURL(music.songPicRadio);
		resultDetailText.setText(getMusicString(music));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		BaiduMusicInfo music = resultListModel.getElementAt(resultList.getSelectedIndex());
		if (!music.isDetailFetched) {
			doFetchMusicDetails(music, null);
		} else {
			singerImage.setImageURL(music.songPicRadio);
			resultDetailText.setText(getMusicString(music));
		}
	}

	public String getMusicString(BaiduMusicInfo music) {
		return "title=" + music.title + "\nsongId=" + music.songId + "\nauthor=" + music.author + "\nartistId="
				+ music.artistId + "\nallArtistId=" + music.allArtistId + "\nalbumTitle=" + music.albumTitle
				+ "\nappendix=" + music.appendix + "\nalbumId=" + music.albumId + "\nlrcLink=" + music.lrcLink
				+ "\nresourceType=" + music.resourceType + "\ncontent=" + music.content + "\nrelateStatus="
				+ music.relateStatus + "\nhaveHigh=" + music.haveHigh + "\ncopyType=" + music.copyType + "\ndelStatus="
				+ music.delStatus + "\nallRate=" + music.allRate + "\nhasMv=" + music.hasMv + "\nhasMvMobile="
				+ music.hasMvMobile + "\nmvProvider=" + music.mvProvider + "\ncharge=" + music.charge + "\ntoneId="
				+ music.toneId + "\ninfo=" + music.info + "\ndataSource=" + music.dataSource + "\nlearn=" + music.learn
				+ "\nisDetailFetched=" + music.isDetailFetched + "\nsongPicSmall=" + music.songPicSmall
				+ "\nsongPicBig=" + music.songPicBig + "\nsongPicRadio=" + music.songPicRadio + "\nsongLink="
				+ music.songLink + "\nformat=" + music.format + "\nrate=" + music.rate + "\nsize=" + music.size;
	}
}
