package com.nc.kmr.localize.estimator;

import java.util.List;

import com.nc.kmr.localize.estimator.exception.InvalidInputException;

public interface FileProcessor {
	
	public boolean isReady();
	public Exception getNotReadyException();
	public String[] getTargets();
	public boolean setTarget(String... target);
	public String getTarget();
	public boolean setScope(String scope);
	public String getScope();
	public String getFileName();
	public String getSimpleFileName();
	public List<String> process() throws InvalidInputException;
	public void releaseResource();
}
