package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.exception.InvalidInputException;

public class PropertiesFileProcessor implements FileProcessor {
	
	private File[] files;
	
	private boolean ready = false;
	private Exception fileNotReadyException;
	private Properties[] props;
	private List<String> content;

	private String scope = "";

	public PropertiesFileProcessor(File[] files) {
		this.files = files;
		init();
	}

	private void init() {
		props = new Properties[files.length];
		try {
			for(int i = 0; i < files.length; i++) {
				props[i] = new Properties();
				props[i].load(new FileReader(files[i]));
			}
			ready = true;
		} catch (IOException e) {
			// TODO Add logging
			fileNotReadyException = e;
		}		
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
		return new String[0];
	}

	@Override
	public boolean setTarget(String target) {
		return true;
	}

	@Override
	public String getTarget() {
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
		String path = "";
		
		try {
			if(files.length == 1) {
				path = files[0].getCanonicalPath();
			} else {
				File parentFolder = files[0].getParentFile();
				path = "'" + parentFolder.getCanonicalPath() + "' folder";
			}
		} catch (IOException e) {
			// TODO Add logging
			e.printStackTrace();
		}
		
		return path;
	}

	@Override
	public String getSimpleFileName() {
		String name = "";
		
		if(files.length == 1) {
			name = files[0].getName();
		} else {
			File parentFolder = files[0].getParentFile();
			name = "'" + parentFolder.getName() + "' folder";
		}
		
		return name;
	}

	@Override
	public List<String> process() throws InvalidInputException {		
		content = new ArrayList<String>();
		if(!ready || scope == null) {
			return content;
		}
		
		String value = "";
		Enumeration<Object> values;
		
		for(Properties prop:props) {
			for(values = prop.elements(); values.hasMoreElements();) {
				value = (String) values.nextElement();
				if(value != null && !value.isEmpty()) {
					content.add(value);
				}
			}
		}
		
		return content;
	}

	@Override
	public void releaseResource() {
		files = null;
		props = null;
	}

}
