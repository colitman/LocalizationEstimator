package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.ini4j.Profile.Section;
import org.ini4j.Wini;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.util.Utils;

public class IniFileProcessor implements FileProcessor {

	private boolean ready = false;
	private Exception notReadyException;
	private int processingMode = -10000;
	private String scope = "";
	private File[] files;
	private Wini[] inis;
	private String[] targets;
	private String[] categories;
	private List<String> content;
	
	public IniFileProcessor(File[] files) {
		this.files = files;
		init();
	}

	private void init() {
		inis = new Wini[files.length];
		
		try {
			for(int i = 0; i < files.length; i++) {
				inis[i] = new Wini();
				inis[i].load(files[i]);
			}
			
			ready = true;
		} catch(IOException e) {
			// TODO add logging
			notReadyException = e;
		}
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public Exception getNotReadyException() {
		return notReadyException;
	}

	@Override
	public String[] getTargets() {
		if(categories != null) {
			return categories;
		}
		
		Set<String> cats = new LinkedHashSet<String>();
		
		for(Wini wini:inis) {
			for(Section sec:wini.values()) {
				String name = sec.getName();
				cats.add(("?".equals(name))? name + " [Without category]": name);
			}
		}
		
		categories = new String[cats.size()];
		
		cats.toArray(categories);
		
		return categories;
	}

	@Override
	public boolean setTarget(String... target) {
		if(target == null) {
			targets = null;
			return false;
		}
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] == null) {
				targets = null;
				return false;
			}
		}
		
		targets = target;
		
		return true;
	}

	@Override
	public String getTarget() {
		String cats = "";
		
		if(targets != null) {
			for(String s:targets) {
				cats = (cats.isEmpty())? s: cats + ", " + s;
			}
		}
		return cats;
	}

	@Override
	public boolean setScope(String scope) {
		if(scope == null) {
			this.scope = null;
			return false;
		}
		
		if(scope.isEmpty()) {
			this.scope = "";
			return true;
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
		return processingMode ;
	}

	@Override
	public List<String> process() {
		content = new ArrayList<String>();
		if(!ready || scope == null || targets == null) {
			return content;
		}
		
		String value = "";
		boolean flag = false;
		
		for(Wini wini:inis) {
			Collection<Section> sections = wini.values();
			for(Section sec:sections) {
				String secName = sec.getName();
				for(String target:targets) {
					if(secName.equals(target)) {
						flag = true;
						break;
					} else if("?".equals(secName) && ("? [Without category]".equals(target))) {
						flag = true;
						break;
					} else {
						flag = false;
					}
				}
				
				if(flag) {
					Set<String> keys = sec.keySet();
					for(String key:keys) {
						
						if(processingMode == Utils.NO_SCOPE_DEFINED || scope.isEmpty()) {
							value = sec.get(key);
						} else if(processingMode == Utils.INCLUDE_SCOPE) {
							if(key.contains(scope)) {
								value = sec.get(key);
							}
						} else if (processingMode == Utils.EXCLUDE_SCOPE) {
							if(!key.contains(scope)) {
								value = sec.get(key);
							}
						}
						
						if(value != null && !value.isEmpty()) {
							content.add(value);
						}
						
						value = "";
					}
				}
			}
			
		}
		return content;
	}

	@Override
	public void releaseResource() {
		files = null;
		inis = null;

	}

}
