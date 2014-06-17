package com.nc.kmr.localize.estimator.event.prop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.util.Utils;

public class PropertiesScopeOptionListener implements ActionListener {

	private JComponent[] comps;
	private FileProcessor processor;

	public PropertiesScopeOptionListener(FileProcessor processor, JComponent... comps) {
		this.comps = comps;
		this.processor = processor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		
		if("NO_SCOPE".equals(action)) {
			for(JComponent c:comps) {
				c.setEnabled(false);
			}
			processor.setScopeProcessingMode(Utils.NO_SCOPE_DEFINED);
		} else {
			for(JComponent c:comps) {
				c.setEnabled(true);
			}
			
			if("INCLUDE".equals(action)) {
				processor.setScopeProcessingMode(Utils.INCLUDE_SCOPE);
			} else if ("EXCLUDE".equals(action)) {
				processor.setScopeProcessingMode(Utils.EXCLUDE_SCOPE);
			}
		}
	}

}
