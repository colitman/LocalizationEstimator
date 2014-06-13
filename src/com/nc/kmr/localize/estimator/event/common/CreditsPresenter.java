package com.nc.kmr.localize.estimator.event.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class CreditsPresenter implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "<html><p>Created by Dmitry Romenskiy (KMR Sumy)</p>"
				+ "<p>GitHub Repo: https://github.com/colitman/LocalizationEstimator</p>"
				+ "<p>Feel free to send bug reports and improvement requests.</p>", 
				"KMR Localization Estimator", 
				JOptionPane.INFORMATION_MESSAGE);
	}

}
