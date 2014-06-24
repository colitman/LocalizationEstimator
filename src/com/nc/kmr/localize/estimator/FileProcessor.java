package com.nc.kmr.localize.estimator;

import java.util.List;

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
	public boolean setScopeProcessingMode(int processingMode);
	public int getScopeProcessingMode();
	public List<String> process();
	public void releaseResource();
}
