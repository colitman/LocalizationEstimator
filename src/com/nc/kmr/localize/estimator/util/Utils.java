package com.nc.kmr.localize.estimator.util;

import java.io.File;

public class Utils {
	public static String getFileType(File file) {
		String type = "";
		
		if(file == null || file.isDirectory()) {
			return type;
		}
		
		if(file.getName().indexOf(".") == -1) {
			return type;
		}
		
		String[] fileName = file.getName().split("\\.");
		
		if(fileName.length == 0) {
			return type;
		}
		
		type = fileName[fileName.length - 1];
		
		return type;
	}
}
