package com.nc.kmr.localize.estimator.event;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import com.nc.kmr.localize.estimator.FileProcessor;

public class CopyResultsToClipboardButtonActionListener implements
		ActionListener {

	private FileProcessor processor;
	private JTabbedPane tabs;
	private String NEW_LINE = System.getProperty("line.separator");

	public CopyResultsToClipboardButtonActionListener(FileProcessor processor, JTabbedPane tabs) {
		this.processor = processor;
		this.tabs = tabs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component[] comp = ((JComponent)tabs.getSelectedComponent()).getComponents();
		
		String title = tabs.getTitleAt(tabs.getSelectedIndex());
		
		String header = "Information from KMR Localization Estimator" + NEW_LINE
				+ "==================================================" + NEW_LINE
				+ "==================================================" + NEW_LINE
				+ "Analyzed file: " + processor.getSimpleFileName() + NEW_LINE
				+ "File path: " + processor.getFileName() + NEW_LINE
				+ "Analyzed target: " + processor.getTarget() + NEW_LINE
				+ "Analyzed scope: " + processor.getScope() + NEW_LINE
				+ "Statistic includes duplicate entries?: " + ("Unique".equals(title)? "No": "Yes") + NEW_LINE
				+ "==================================================" + NEW_LINE;
		
		for(Component c:comp) {
			if("JTextArea".equals(c.getClass().getSimpleName())) {
				String text = header + ((JTextArea)c).getText();
				StringSelection sel = new StringSelection(text);
				Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
				cp.setContents(sel, null);
			} else if("JScrollPane".equals(c.getClass().getSimpleName())) {
				Component[] comp0 = ((JScrollPane)c).getComponents();
				
				for(Component c0:comp0) {
					if("JViewport".equals(c0.getClass().getSimpleName())) {
						Component[] comp1 = ((JViewport)c0).getComponents();
						
						for(Component c1:comp1) {
							if("JTextArea".equals(c1.getClass().getSimpleName())) {
								String text2 = header + ((JTextArea)c1).getText();
								StringSelection sel2 = new StringSelection(text2);
								Clipboard cp2 = Toolkit.getDefaultToolkit().getSystemClipboard();
								cp2.setContents(sel2, null);
							}
						}
					}
				}
			}
		}
	}

}
