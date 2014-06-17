package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.exception.InvalidInputException;
import com.nc.kmr.localize.estimator.util.Utils;

public class PropertiesFileProcessor implements FileProcessor {
	
	private File[] files;
	
	private boolean ready = false;
	private Exception fileNotReadyException;
	private Properties[] props;
	private List<String> content;
	private int processingMode = -10000;
	
//	private boolean mode;

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
	public boolean setTarget(String... target) {
		return true;
	}

	@Override
	public String getTarget() {
		return "";
	}

	@Override
	public boolean setScope(String scope) {
		
		//	^(\w+\.?)+=    	Regexp for keys filtration
		// (\\w+\\.?)+		Regexp for scope validation
		
		if(scope == null) {
			this.scope = null;
			return false;
		}
		
		if(scope.isEmpty()) {
			this.scope = "";
			return true;
		}
		
//		if(!scope.matches("\\w{7}::\\w*")) {
//			this.scope = null;
//			return false;
//		}
//		
//		this.scope = scope.substring(9);
//		
//		mode = (scope.substring(0, 7).equals("INCLUDE"))? true:false;
//		
//		System.out.println(this.scope + "-" + mode);
		
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
	public boolean setScopeProcessingMode(int processingMode) {
		if(processingMode < -1 || processingMode > 1) {
			this.processingMode = -10000;
			return false;
		}
		
		this.processingMode = processingMode;
		return true;
	}
	
	@Override
	public int getScopeProcessingMode() {
		return processingMode;
	}

	@Override
	public List<String> process() throws InvalidInputException {		
		content = new ArrayList<String>();
		if(!ready || scope == null) {
			return content;
		}
		
		String value = "";
		Set<String> keys;
		
		for(Properties prop:props) {
			keys = prop.stringPropertyNames();
			for(String key:keys) {
				if(processingMode == Utils.NO_SCOPE_DEFINED || scope.isEmpty()) {
					value = prop.getProperty(key);
				} else if(processingMode == Utils.INCLUDE_SCOPE) {
					if(key.contains(scope)) {
						value = prop.getProperty(key);
					}
				} else if (processingMode == Utils.EXCLUDE_SCOPE) {
					if(!key.contains(scope)) {
						value = prop.getProperty(key);
					}
				}
				
				if(value != null && !value.isEmpty()) {
					content.add(value);
				}
				
				value = "";
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
