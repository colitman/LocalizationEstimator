package com.nc.kmr.localize.estimator.event.common;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import com.nc.kmr.localize.estimator.exception.FileReadingException;
import com.nc.kmr.localize.estimator.exception.UnsupportedFileTypeException;
import com.nc.kmr.localize.estimator.view.WizardFactory;

public class BrowseButtonListener implements ActionListener {

	private JFileChooser chooser;

	public BrowseButtonListener(JFileChooser chooser) {
		this.chooser = chooser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File[] files = new File[0];
		
		int chooserVal = chooser.showOpenDialog((Component) e.getSource());
		
		if(chooserVal == JFileChooser.APPROVE_OPTION) {
			files = chooser.getSelectedFiles();
			
			try {
				WizardFactory.showAnalyzeWizard(files);
			} catch (ReflectiveOperationException ex) {
				// TODO Add logging
				//ex.printStackTrace();
				Throwable cause = ex.getCause();
				if(cause.getClass() == OfficeXmlFileException.class) {
					JOptionPane.showMessageDialog(null, "<html>Actual type of file does not correspond to the file extention.<br>"
							+ "Perhaps you are trying to open MS Office 2007 file (or newer).<br>"
							+ "Change the file extention to a proper one (.docx, .xlsx, .pptx, etc.) and try again."
							+ "</html>", "Error while file read", JOptionPane.ERROR_MESSAGE);
				}
			} catch (UnsupportedFileTypeException e1) {
				// TODO Add logging
			} catch (FileReadingException e1) {
				// TODO Add logging
			} 
		}
	}

}
