package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.nc.kmr.localize.estimator.FileProcessor;

public abstract class AbstractExcelFileProcessor implements FileProcessor {
	
	protected boolean ready = false;
	protected Exception fileException;
	protected File file;
	protected String[] sheets;
	protected String range;
	protected List<String> content;
	
	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public Exception getNotReadyException() {
		return fileException;
	}

	@Override
	public String[] getTargets() {
		return sheets;
	}
	
	@Override
	public boolean setScope(String scope) {
		
		if(scope == null) {
			range = null;
			return false;
		}
		
		if(!scope.matches("([a-zA-Z]+[0-9]+:[a-zA-Z]+[0-9]+;?)+")) {
			range = null;
			return false;
		}
		
		range = scope;
		
		return true;
	}

	@Override
	public String getScope() {
		return range;
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

	abstract protected void init();

}
