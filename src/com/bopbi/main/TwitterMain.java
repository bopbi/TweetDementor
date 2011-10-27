package com.bopbi.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.bopbi.dementor.TweetDementor;

public class TwitterMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("Tweet Dementor");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new DementorButton());
		f.pack();
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}

class DementorButton extends JPanel implements ActionListener {

	private JButton jButton;
	private Boolean dementor_running = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2307897381993059048L;

	public DementorButton() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					makeGUI();
				}
			});
		} catch (Exception exc) {
			System.out.println("Can't create because of " + exc);
		}
	}

	private void makeGUI() {
		setLayout(new FlowLayout());

		jButton = new JButton("Start Dementor");
		jButton.addActionListener(this);
		add(jButton);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		DementorThread dementorThread = new DementorThread();
		if (jButton.getText().equals("Start Dementor")) {
			dementor_running = true;
			new Thread(dementorThread).start();
			jButton.setText("Stop Dementor");
		} else {
			jButton.setText("Start Dementor");
			dementor_running = false;

		}

	}

	class DementorThread implements Runnable {
		public void run() {
			TweetDementor tweetDementor = new TweetDementor();
			try {

				while (dementor_running) {
					tweetDementor.test();
				}
			} catch (Exception e) {
			}

		}
	}

}
