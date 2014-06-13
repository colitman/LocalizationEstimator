package com.nc.kmr.localize.estimator.event.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.exception.InvalidInputException;
import com.nc.kmr.localize.estimator.impl.analyze.Analyzer;
import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;
import com.nc.kmr.localize.estimator.view.ResultsWindow;

public class AnalyzeButtonListener implements ActionListener {

	private AnalyzerBuilder builder;
	private FileProcessor processor;
	private Analyzer analyzer;

	public AnalyzeButtonListener(AnalyzerBuilder builder, FileProcessor processor) {
		this.builder = builder;
		this.processor = processor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			analyzer = builder.parse(processor.process());
			SwingUtilities.invokeLater(new ResultsWindow(analyzer, processor));
		} catch (InvalidInputException e1) {
			// TODO add logging
			//e1.printStackTrace();
		}
	}

}
