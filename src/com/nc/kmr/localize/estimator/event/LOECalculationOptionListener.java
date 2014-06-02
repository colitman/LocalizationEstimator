package com.nc.kmr.localize.estimator.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LOECalculationOptionListener implements ActionListener {

	private JComponent[] components;

	public LOECalculationOptionListener(JComponent... components) {
		this.components = components;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(JComponent component:components){
			component.setEnabled(!component.isEnabled());
		}
	}

}
