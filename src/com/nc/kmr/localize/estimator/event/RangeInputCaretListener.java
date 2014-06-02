package com.nc.kmr.localize.estimator.event;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.nc.kmr.localize.estimator.FileProcessor;

public class RangeInputCaretListener implements CaretListener {

	private FileProcessor processor;

	public RangeInputCaretListener(FileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		processor.setScope(((JTextField)e.getSource()).getText());
		// TODO add verifying
	}

}
