package com.nc.kmr.localize.estimator.event.pp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import com.nc.kmr.localize.estimator.FileProcessor;

public class PPTargetListener implements ActionListener {

	private JCheckBox[] options;
	private FileProcessor processor;

	public PPTargetListener(FileProcessor processor, JCheckBox... options) {
		this.processor = processor;
		this.options = options;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<JCheckBox> selected = new ArrayList<JCheckBox>();
		
		for(JCheckBox c:options) {
			if(c.isSelected()) {
				selected.add(c);
			}
		}
		
		String[] actions = new String[selected.size()];
		for (int i  = 0; i < actions.length; i++) {
			actions[i] = selected.get(i).getActionCommand();
		}
		
		processor.setTarget(actions);
	}

}
