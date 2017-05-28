package cn.ljj.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
import cn.ljj.common.HttpManager;
import cn.ljj.common.HttpManager.DownloadCallback;
import cn.ljj.common.HttpManager.HttpDownloadTask;
import cn.ljj.ui.ProgressDialog.IProgressDialogCacnelListener;

public class MainUI extends JFrame implements SearchCallback, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	public static final String TAG = MainUI.class.getSimpleName();
	private JTextField searchTextField;
	private JButton searchButton;
	private SearchResultListModel resultListModel;
	private JList<BaiduMusicInfo> resultList;
	private BaiduMusicSearcher searcher;
	private ImagePanel singerImage;
	private JTextArea resultDetailText;
	private BaiduMusicInfo selectedMusic;
	private ProgressDialog progressDialog = null;

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
		p.setBounds(50, 370, 100, 35);
		p.add(downloadButton);
		downloadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doDownloadMusicData(selectedMusic);
			}
		});
		detailPanel.add(p);
		add(detailPanel);
		// 设置标题
		setTitle("百度音乐搜索");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(750, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

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
									selectedMusic = music;
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
			selectedMusic = music;
			doFetchMusicDetails(music, null);
		} else {
			singerImage.setImageURL(music.songPicRadio);
			resultDetailText.setText(getMusicString(music));
		}
	}

	public String getMusicString(BaiduMusicInfo music) {
		return "title=" + music.title + "\nauthor=" + music.author + "\nalbumTitle=" + music.albumTitle + "\n\nlrcLink="
				+ music.lrcLink + "\n\ncopyType=" + music.copyType + "\n\nallRate=" + music.allRate
				+ "\n\nsongPicSmall=" + music.songPicSmall + "\n\nsongPicBig=" + music.songPicBig + "\n\nsongPicRadio="
				+ music.songPicRadio + "\n\nsongLink=" + music.songLink + "\n\nformat=" + music.format + "\n\nrate="
				+ music.rate + "\n\nsize=" + music.size;
	}

	private void doDownloadMusicData(BaiduMusicInfo music) {
		File file = new File(music.author + " - " + music.title);
		file.mkdirs();
		String json = BaiduMusicInfo.toJson(music);
		try {
			File jsonFile = new File(file, music.title + ".json");
			if (!jsonFile.exists()) {
				jsonFile.createNewFile();
			}
			FileWriter fw = new FileWriter(jsonFile);
			fw.write(json);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
		}
		progressDialog.setVisible(true);
		progressDialog.setMessage("正在下载封面");
		progressDialog.setTitle("下载");
		HttpDownloadTask picTask = new HttpDownloadTask(music.songPicRadio,
				file.getAbsolutePath() + File.separator + music.title + ".jpg");
		picTask.setCallBack(new DownloadCallback() {

			@Override
			public void onProgressChange(int length, int finished) {
				progressDialog.setProgress(finished * 100 / length);
			}

			@Override
			public void onFinished(String filePath) {
				progressDialog.setMessage("正在下载歌词");
			}

			@Override
			public void onFaild(int errorCode) {
				progressDialog.setMessage("正在下载歌词");
			}
		});
		HttpManager.getInstance().addTask(picTask);
		HttpDownloadTask lrcTask = new HttpDownloadTask(music.lrcLink,
				file.getAbsolutePath() + File.separator + music.title + ".lrc");
		lrcTask.setCallBack(new DownloadCallback() {

			@Override
			public void onProgressChange(int length, int finished) {
				progressDialog.setProgress(finished * 100 / length);
			}

			@Override
			public void onFinished(String filePath) {
				progressDialog.setMessage("正在下载mp3");
			}

			@Override
			public void onFaild(int errorCode) {
				progressDialog.setMessage("正在下载mp3");
			}
		});
		HttpManager.getInstance().addTask(lrcTask);
		HttpDownloadTask songTask = new HttpDownloadTask(music.songLink,
				file.getAbsolutePath() + File.separator + music.title + "." + music.format);
		songTask.setCallBack(new DownloadCallback() {

			@Override
			public void onProgressChange(int length, int finished) {
				progressDialog.setProgress(finished * 100 / length);
			}

			@Override
			public void onFinished(String filePath) {
				progressDialog.setVisible(false);
			}

			@Override
			public void onFaild(int errorCode) {
				progressDialog.setVisible(false);
			}
		});
		HttpManager.getInstance().addTask(songTask);
		progressDialog.setCancelListener(new IProgressDialogCacnelListener() {

			@Override
			public void onCancel() {
				songTask.cancel();
				lrcTask.cancel();
				picTask.cancel();
			}
		});
	}
}
