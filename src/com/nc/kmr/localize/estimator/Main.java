package com.nc.kmr.localize.estimator;

import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.nc.kmr.localize.estimator.impl.XLSFileProcessor;
import com.nc.kmr.localize.estimator.view.MainWindow;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		SwingUtilities.invokeLater(new MainWindow());
	}

}
