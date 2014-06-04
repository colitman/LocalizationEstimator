package com.nc.kmr.localize.estimator.event;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.nc.kmr.localize.estimator.FileProcessor;

public class WizardWindowListener extends WindowAdapter {

	private FileProcessor processor;

	public WizardWindowListener(FileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void windowClosed(WindowEvent e) {
		super.windowClosed(e);
		processor.releaseResource();
	}
	
}
