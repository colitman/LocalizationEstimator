package com.nc.kmr.localize.estimator.event.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;

public class UniquesOptionActionListener implements ActionListener {

	private AnalyzerBuilder builder;

	public UniquesOptionActionListener(AnalyzerBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		builder.setShowUniquesStatistic(!builder.getShowUniquesStatistic());
	}

}
