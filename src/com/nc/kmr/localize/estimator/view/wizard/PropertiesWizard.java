package com.nc.kmr.localize.estimator.view.wizard;

import java.awt.Dialog.ModalExclusionType;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.event.AnalyzeButtonListener;
import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;
import com.nc.kmr.localize.estimator.view.Wizard;

public class PropertiesWizard extends JFrame implements Wizard {
	
	private static final long serialVersionUID = 1L;
	
	private FileProcessor processor;
	private AnalyzerBuilder builder;

	public PropertiesWizard(FileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void run() {
		builder = new AnalyzerBuilder();
		JButton b = new JButton("Analyze");
		builder.setCalculateLOE(true);
		builder.setPerformance("7000");
		builder.setShowUniquesStatistic(true);
		b.addActionListener(new AnalyzeButtonListener(builder, processor));
		add(b);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Key-Value file estimator wizard");
		setLocationRelativeTo(null);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		pack();
		setVisible(true);
	}

}
