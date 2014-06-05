package com.nc.kmr.localize.estimator.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class CreditsPresenter implements ActionListener {
	
	private final String NEW_LINE = System.getProperty("line.separator");

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Created by Dmitry Romenskiy (KMR Sumy)" + NEW_LINE
				+ "GitHub Repo: https://github.com/colitman/LocalizationEstimator" + NEW_LINE
				+ "Feel free to send bug reports and improvement requests.", 
				"KMR Localization Estimator", 
				JOptionPane.INFORMATION_MESSAGE);
	}

}
