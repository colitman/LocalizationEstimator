package com.nc.kmr.localize.estimator.event.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

public class SelectAllTargetsActionListener implements ActionListener {

	private JList<String> list;

	public SelectAllTargetsActionListener(JList<String> list) {
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//list.setEnabled(!list.isEnabled());
		int sheetsCount = list.getModel().getSize();
		
		int[] indexes = new int[sheetsCount];
		
		for(int i = 0; i < indexes.length; i++) {
			indexes[i] = i;
		}
		
		list.setSelectedIndices(indexes);
	}

}
