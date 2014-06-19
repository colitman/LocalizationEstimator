package com.nc.kmr.localize.estimator.event.common;

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.nc.kmr.localize.estimator.FileProcessor;

public class TargetsListSelectionListener implements ListSelectionListener {

	private FileProcessor processor;
	private JList<String> list;

	public TargetsListSelectionListener(FileProcessor processor, JList<String> list) {
		this.processor = processor;
		this.list = list;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			List<String> selectedList = list.getSelectedValuesList();
			String[] selectedSheets = selectedList.toArray(new String[selectedList.size()]);
			
			if(!processor.setTarget(selectedSheets)) {
				list.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, new Color(255,0,0), new Color(130,0,0)));
			} else {
				list.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
			}
		}
	}
}
