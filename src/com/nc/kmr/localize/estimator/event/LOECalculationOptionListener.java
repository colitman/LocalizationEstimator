package com.nc.kmr.localize.estimator.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;

public class LOECalculationOptionListener implements ActionListener {

	private JComponent[] components;
	private AnalyzerBuilder builder;

	public LOECalculationOptionListener(AnalyzerBuilder builder, JComponent... components) {
		this.builder = builder;
		this.components = components;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(JComponent component:components){
			component.setEnabled(!component.isEnabled());
		}
		
		builder.setCalculateLOE(!builder.isCalculateLOE());
	}

}
