package com.nc.kmr.localize.estimator.filefilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.nc.kmr.localize.estimator.util.Utils;

public class XLSFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) {
			return true;
		}
		
		String fileType = Utils.getFileType(f);
		
		if("xls".equals(fileType)) {
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "Excel 97-2003 Workbook (*.xls)";
	}

}
