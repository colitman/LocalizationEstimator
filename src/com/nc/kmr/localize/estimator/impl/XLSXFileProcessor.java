package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorkbookPart;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart;
import org.xlsx4j.exceptions.Xlsx4jException;
import org.xlsx4j.sml.Cell;
import org.xlsx4j.sml.Row;
import org.xlsx4j.sml.STCellType;
import org.xlsx4j.sml.Sheet;
import org.xlsx4j.sml.Workbook;

import com.nc.kmr.localize.estimator.exception.InvalidInputException;
import com.nc.kmr.localize.estimator.util.ExcelUtils;

public class XLSXFileProcessor extends AbstractExcelFileProcessor {
	
	private SpreadsheetMLPackage pg;
	private WorkbookPart bookPart;
	private WorksheetPart sheetPart;
	private Workbook book;
	private Sheet sheet;
	private List<Sheet> sheetList;

	public XLSXFileProcessor(File[] files) {
		this.file = files[0];
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
	public void releaseResource() {
		book = null;
		sheet = null;
		sheetList = null;
		pg = null;
		bookPart = null;
		sheetPart = null;
	}

	@Override
	public List<String> process() throws InvalidInputException {
		content = new ArrayList<String>();
		
		if(!ready || sheet == null || range == null) {
			return content;
		}
		
		String[] ranges = range.split(";");
		
		SharedStrings sharedStrings = null;
		try {
			sharedStrings = (SharedStrings) pg.getParts().get(new PartName("/xl/sharedStrings.xml"));
		} catch (InvalidFormatException e) {
			// TODO add logging
			e.printStackTrace();
		}
		
		List<Row> rows;
		
		try {
			sheetPart = bookPart.getWorksheet(sheetList.indexOf(sheet));
		} catch (Xlsx4jException e) {
			// TODO Add logging
			e.printStackTrace();
		}
		rows = sheetPart.getContents().getSheetData().getRow();
		
		Set<String> processed = new HashSet<String>();
		String cellContent = "";
		
		for(String area:ranges) {
			String upLeftCell = area.split(":")[0];
			String downRightCell = area.split(":")[1];
			
			Long upRow = Long.valueOf(upLeftCell.replaceAll("[a-zA-Z]", ""));
			Long downRow = Long.valueOf(downRightCell.replaceAll("[a-zA-Z]", ""));
			int leftCol = ExcelUtils.getIndexOfColumn(upLeftCell.replaceAll("[0-9]", ""));
			int rightCol = ExcelUtils.getIndexOfColumn(downRightCell.replaceAll("[0-9]", ""));
				
			for(Row r:rows) {
				Long rowNum = r.getR();
				if(rowNum >= upRow && rowNum <= downRow) {
					List<Cell> cells = r.getC();
					for(Cell c:cells) {
						int colNum = ExcelUtils.getIndexOfColumn(c.getR().replaceAll("[0-9]", ""));
						if(colNum >= leftCol && colNum <= rightCol) {
							if(!processed.add(c.getR())) {
								continue;
							}
							
							if(c.getT().equals(STCellType.S)) {
								cellContent = sharedStrings.getJaxbElement().getSi().get(Integer.parseInt(c.getV())).getT().getValue();
							} else if(c.getT().equals(STCellType.N)) {
								cellContent = c.getV();
							}
							
							if(cellContent != null && !cellContent.isEmpty()) {
								content.add(cellContent);
							}
						}
					}
				}
			}
		}
		
		return content;
	}
}
