package com.nc.kmr.localize.estimator.event;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.nc.kmr.localize.estimator.FileProcessor;

public class SheetListSelectionListener implements ListSelectionListener {

	private FileProcessor processor;
	private JList<String> sheetList;

	public SheetListSelectionListener(FileProcessor processor, JList<String> sheetList) {
		this.processor = processor;
		this.sheetList = sheetList;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			processor.setTarget(sheetList.getSelectedValue());
			//TODO add verifying
		}
	}

}
