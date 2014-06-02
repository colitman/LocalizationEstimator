package com.nc.kmr.localize.estimator.exception;

import javax.swing.JOptionPane;

public class UnsupportedFileTypeException extends Exception {

	public UnsupportedFileTypeException(String message) {
		JOptionPane.showMessageDialog(null, message, "Unsupported file type", JOptionPane.ERROR_MESSAGE);
	}

	private static final long serialVersionUID = 1L;

}
