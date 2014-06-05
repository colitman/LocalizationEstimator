package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.docx4j.openpackaging.parts.JaxbXmlPart;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorkbookPart;
import org.xlsx4j.exceptions.Xlsx4jException;
import org.xlsx4j.sml.Cell;
import org.xlsx4j.sml.Row;
import org.xlsx4j.sml.STCellType;
import org.xlsx4j.sml.Sheet;
import org.xlsx4j.sml.Workbook;

import com.nc.kmr.localize.estimator.exception.InvalidInputException;

public class XLSXFileProcessor extends AbstractExcelFileProcessor {
	
	private SpreadsheetMLPackage pg;
	private WorkbookPart bookPart;
	private Workbook book;
	private Sheet sheet;
	private List<Sheet> sheetList;

	public XLSXFileProcessor(File file) {
		this.file = file;
		init();
	}

	protected void init() {
		try {
			pg = SpreadsheetMLPackage.load(file);
			bookPart = pg.getWorkbookPart();
			book = bookPart.getJaxbElement();
			ready  = true;
			sheetList = book.getSheets().getSheet();
			
			sheets = new String[sheetList.size()];
			
			for(Sheet s:sheetList) {
				sheets[sheetList.indexOf(s)] = s.getName();
			}
			
		} catch (Docx4JException e) {
			fileException = e;
		}
	}

	@Override
	public boolean setTarget(String target) {
		for(Sheet s:sheetList) {
			if(s.getName().equals(target)) {
				sheet = s;
				return true;
			}
		}
		
		sheet = null;
		return false;
	}

	@Override
	public String getTarget() {
		String sheetName = null;
		
		if(sheet != null) {
			sheetName = sheet.getName();
		}
		
		return sheetName;
	}

	@Override
	public List<String> process() throws InvalidInputException {
		// TODO Implement
		try {
			List<Row> rows = bookPart.getWorksheet(sheetList.indexOf(sheet)).getContents().getSheetData().getRow();
			for(Row r:rows) {
				List<Cell> cells = r.getC();
				for(Cell c:cells) {
					if(c.getT().equals(STCellType.S)) {
						Part part = (Part)pg.getRelationshipsPart();
						System.out.println(part.getClass().getName());
						SharedStrings sharedStrings = (SharedStrings) pg.getParts().get(new PartName("/xl/sharedStrings.xml"));
							System.out.println( " " + c.getR() + " contains " +
									sharedStrings.getJaxbElement().getSi().get(Integer.parseInt(c.getV())).getT().getValue()
									);
					}
				}
				
			}
		} catch (Xlsx4jException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void releaseResource() {
		book = null;
		sheet = null;
		sheetList = null;
	}
}
