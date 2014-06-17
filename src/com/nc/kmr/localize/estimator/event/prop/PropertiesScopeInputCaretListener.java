package com.nc.kmr.localize.estimator.event.prop;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.nc.kmr.localize.estimator.FileProcessor;

public class PropertiesScopeInputCaretListener implements CaretListener {

	private FileProcessor processor;

	public PropertiesScopeInputCaretListener(FileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		JTextField field = (JTextField)e.getSource();
		String input = field.getText();
		
		if(!processor.setScope(input)) {
			field.setBackground(new Color(230,90,90));
		} else {
			field.setBackground(Color.WHITE);
		}
	}

}
