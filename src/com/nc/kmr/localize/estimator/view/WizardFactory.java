package com.nc.kmr.localize.estimator.view;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.exception.FileReadingException;
import com.nc.kmr.localize.estimator.exception.UnsupportedFileTypeException;
import com.nc.kmr.localize.estimator.impl.IniFileProcessor;
import com.nc.kmr.localize.estimator.impl.PropertiesFileProcessor;
import com.nc.kmr.localize.estimator.impl.XLSFileProcessor;
import com.nc.kmr.localize.estimator.impl.XLSXFileProcessor;
import com.nc.kmr.localize.estimator.util.Utils;
import com.nc.kmr.localize.estimator.view.wizard.IniWizard;
import com.nc.kmr.localize.estimator.view.wizard.PropertiesWizard;
import com.nc.kmr.localize.estimator.view.wizard.XLSWizard;

public class WizardFactory {
	private static Map<String, Class<? extends FileProcessor>> processors = null;
	private static Map<Class<? extends FileProcessor>, Class<? extends Wizard>> wizards = null;
	
	public static void showAnalyzeWizard(File[] files) throws UnsupportedFileTypeException, 
															InstantiationException, 
															IllegalAccessException, 
															InvocationTargetException, 
															NoSuchMethodException, 
															FileReadingException {
		
		if(files.length == 0) {
			return;
		}
		
		String fileType = Utils.getFileType(files[0]);		
		FileProcessor processor = null;
		Wizard wizard = null;
		
		if(processors == null) {
			initProcessors();
		}
		
		if(wizards == null) {
			initWizards();
		}
		
		if(!processors.containsKey(fileType)) {
			throw new UnsupportedFileTypeException("Files of the selected type are not supported: ." + fileType);
		} else {
			processor = processors.get(fileType).getConstructor(File[].class).newInstance((Object)files);
		}
		
		if(!processor.isReady()) {
			throw new FileReadingException(processor.getNotReadyException());
		}
		
		if(!wizards.containsKey(processor.getClass())) {
			throw new UnsupportedFileTypeException("No wizard for the selected file type available: ." + fileType);
		} else {
			wizard = wizards.get(processor.getClass()).getConstructor(FileProcessor.class).newInstance(processor);
		}
		
		SwingUtilities.invokeLater(wizard);
		
	}

	private static void initProcessors() {
		processors = new Hashtable<String, Class<? extends FileProcessor>>();
		
		processors.put("xls", XLSFileProcessor.class);
		processors.put("xlsx", XLSXFileProcessor.class);
		processors.put("properties", PropertiesFileProcessor.class);
		processors.put("ini", IniFileProcessor.class);
		processors.put("lng", IniFileProcessor.class);
	}

	private static void initWizards() {
		wizards = new Hashtable<Class<? extends FileProcessor>, Class<? extends Wizard>>();
		
		wizards.put(XLSFileProcessor.class, XLSWizard.class);
		wizards.put(XLSXFileProcessor.class, XLSWizard.class);
		wizards.put(PropertiesFileProcessor.class, PropertiesWizard.class);
		wizards.put(IniFileProcessor.class, IniWizard.class);
	}
}
