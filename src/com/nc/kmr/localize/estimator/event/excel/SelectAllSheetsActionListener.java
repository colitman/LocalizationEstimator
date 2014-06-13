package com.nc.kmr.localize.estimator.event.excel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

public class SelectAllSheetsActionListener implements ActionListener {

	private JList<String> list;

	public SelectAllSheetsActionListener(JList<String> sheetList) {
		this.list = sheetList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		list.setEnabled(!list.isEnabled());
		int sheetsCount = list.getModel().getSize();
		
		int[] indexes = new int[sheetsCount];
		
		for(int i = 0; i < indexes.length; i++) {
			indexes[i] = i;
		}
		
		list.setSelectedIndices(indexes);
	}

}
