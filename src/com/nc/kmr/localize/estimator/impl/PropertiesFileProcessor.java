package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.swing.text.AbstractDocument.Content;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.exception.InvalidInputException;

public class PropertiesFileProcessor implements FileProcessor {
	
	private File file;
	
	private boolean ready = false;
	private Exception fileNotReadyException;
	private Properties props;
	private List<String> content;

	private String scope = "";

	public PropertiesFileProcessor(File file) {
		this.file = file;
		init();
	}

	private void init() {
		props = new Properties();
		try {
			props.load(new FileReader(file));
			ready = true;
		} catch (IOException e) {
			// TODO Add logging
			fileNotReadyException = e;
		}
//		try {
//			process();
//		} catch (InvalidInputException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public Exception getNotReadyException() {
		return fileNotReadyException;
	}

	@Override
	public String[] getTargets() {
		//throw new UnsupportedOperationException("No targets for this type of files");
		return new String[0];
	}

	@Override
	public boolean setTarget(String target) {
		//throw new UnsupportedOperationException("No targets for this type of files");
		return true;
	}

	@Override
	public String getTarget() {
		//throw new UnsupportedOperationException("No targets for this type of files");
		return "";
	}

	@Override
	public boolean setScope(String scope) {
		
		//	^(\w+\.?)+=    Regexp for keys filtration
		
		if(scope == null) {
			this.scope = null;
			return false;
		}
		
		if(scope.isEmpty()) {
			this.scope = "";
			return true;
		}
		
		if(!scope.matches("(\\w+\\.?)+")) {
			this.scope = null;
			return false;
		}
		
		this.scope = scope;
		
		return true;
	}

	@Override
	public String getScope() {
		return scope;
	}

	@Override
	public String getFileName() {
		String path = null;
		
		try {
			path = file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Add logging
			e.printStackTrace();
		}
		
		return path;
	}

	@Override
	public String getSimpleFileName() {
		return file.getName();
	}

	@Override
	public List<String> process() throws InvalidInputException {		
		content = new ArrayList<String>();
		if(!ready || scope == null) {
			return content;
		}
		
		Enumeration<Object> values;
		for(values = props.elements(); values.hasMoreElements();) {
			String value = (String) values.nextElement();
			if(value != null && !value.isEmpty()) {
				content.add(value);
			}
		}
		
		return content;
	}

	@Override
	public void releaseResource() {
		file = null;
	}

}
