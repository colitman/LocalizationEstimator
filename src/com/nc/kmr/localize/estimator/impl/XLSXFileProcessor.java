package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.xlsx4j.sml.Sheet;

import com.nc.kmr.localize.estimator.exception.InvalidInputException;

public class XLSXFileProcessor extends AbstractExcelFileProcessor {
	
	private SpreadsheetMLPackage book;

	public XLSXFileProcessor(File file) {
		this.file = file;
		init();
	}

	protected void init() {
		try {
			book = SpreadsheetMLPackage.load(file);
			ready  = true;
			List<Sheet> sheetList= book.getWorkbookPart().getJaxbElement().getSheets().getSheet();
			
			sheets = new String[sheetList.size()];
			
			for(Sheet s:sheetList) {
				sheets[sheetList.indexOf(s)] = s.getName();
			}
			
		} catch (Docx4JException e) {
			// TODO Auto-generated catch block
			fileException = e;
		}
	}

	@Override
	public boolean setTarget(String target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setScope(String scope) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> process() throws InvalidInputException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void releaseResource() {
		// TODO Auto-generated method stub

	}

}
