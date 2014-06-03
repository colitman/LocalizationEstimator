package com.nc.kmr.localize.estimator.event;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;

public class PerformanceInputListener implements CaretListener {

	private AnalyzerBuilder builder;

	public PerformanceInputListener(AnalyzerBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		JTextField field = (JTextField)e.getSource();
		String input = field.getText();
		if(input.matches("\\d+")) {
			builder.setPerformance(input);
		}
	}
}
