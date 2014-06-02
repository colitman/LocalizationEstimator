package com.nc.kmr.localize.estimator.exception;

import javax.swing.JOptionPane;

public class FileReadingException extends Exception {

	public FileReadingException(Exception notReadyException) {
		JOptionPane.showMessageDialog(null, notReadyException.getMessage(), "Error while file read", JOptionPane.ERROR_MESSAGE);
	}

	private static final long serialVersionUID = 1L;

}
