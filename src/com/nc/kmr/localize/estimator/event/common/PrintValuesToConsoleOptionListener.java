package com.nc.kmr.localize.estimator.event.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;

public class PrintValuesToConsoleOptionListener implements ActionListener {

	private AnalyzerBuilder builder;

	public PrintValuesToConsoleOptionListener(AnalyzerBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		builder.setPrintToConsole(!builder.isPrintToConsole());
	}

}
