package com.nc.kmr.localize.estimator.exception;

import javax.swing.JOptionPane;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidInputException(String message) {
		JOptionPane.showMessageDialog(null, message, "Invalid input", JOptionPane.ERROR_MESSAGE);
	}

}
