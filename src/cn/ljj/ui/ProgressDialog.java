package cn.ljj.ui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ProgressDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 150;

	private JLabel titleLabel = null;
	private JLabel messageLabel = null;
	private JLabel progressLabel = null;
	private JProgressBar progressBar = null;
	private JButton cancelButton = null;
	private IProgressDialogCacnelListener listener;

	public ProgressDialog(Frame owner) {
		super(owner, false);
		setLayout(null);
		Rectangle parentBounds = owner.getBounds();
		Rectangle bounds = new Rectangle();
		bounds.width = WIDTH;
		bounds.height = HEIGHT;
		bounds.x = parentBounds.x + (parentBounds.width - WIDTH) / 2;
		bounds.y = parentBounds.y + (parentBounds.height - HEIGHT) / 2;
		setBounds(bounds);
		setUndecorated(true);
		titleLabel = new JLabel();
		titleLabel.setFont(new Font("Dialog", 1, 16));
		titleLabel.setBounds(20, 0, WIDTH - 40, 30);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setVerticalAlignment(JLabel.BOTTOM);
		add(titleLabel);
		//
		messageLabel = new JLabel();
		messageLabel.setBounds(10, 30, WIDTH - 20, 30);
		messageLabel.setHorizontalAlignment(JLabel.CENTER);
		messageLabel.setVerticalAlignment(JLabel.CENTER);
		add(messageLabel);
		//
		progressLabel = new JLabel();
		progressLabel.setBounds(0, 70, WIDTH, 20);
		progressLabel.setHorizontalAlignment(JLabel.CENTER);
		progressLabel.setVerticalAlignment(JLabel.CENTER);
		add(progressLabel);
		//
		progressBar = new JProgressBar();
		progressBar.setBounds(20, 95, WIDTH - 40, 10);
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		add(progressBar);
		//
		cancelButton = new JButton();
		cancelButton.setBounds((WIDTH - 100) / 2, 110, 100, 30);
		add(cancelButton);
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (listener != null) {
					listener.onCancel();
					listener = null;
				}
			}
		});
	}

	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	public void setMessage(String message) {
		messageLabel.setText(message);
	}

	public void setProgress(int progress) {
		progressLabel.setText(progress + "%");
		progressBar.setValue(progress);
	}

	public void setCancelListener(IProgressDialogCacnelListener l) {
		listener = l;
	}

	public static interface IProgressDialogCacnelListener {
		void onCancel();
	}
}
