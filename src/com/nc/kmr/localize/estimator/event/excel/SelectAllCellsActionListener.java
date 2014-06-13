package com.nc.kmr.localize.estimator.event.excel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class SelectAllCellsActionListener implements ActionListener {

	private JTextField input;

	public SelectAllCellsActionListener(JTextField rangeInput) {
		this.input = rangeInput;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		input.setEnabled(!input.isEnabled());
		if(!input.isEnabled()) {
			input.setText("A1:ZZZ1048576");
		} else {
			input.setText("");
		}
	}

}
