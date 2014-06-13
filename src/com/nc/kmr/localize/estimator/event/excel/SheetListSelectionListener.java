package com.nc.kmr.localize.estimator.event.excel;

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
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
			List<String> selectedList = sheetList.getSelectedValuesList();
			String[] selectedSheets = selectedList.toArray(new String[selectedList.size()]);
			
			if(!processor.setTarget(selectedSheets)) {
				sheetList.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, new Color(255,0,0), new Color(130,0,0)));
			} else {
				sheetList.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
			}
		}
	}
}
