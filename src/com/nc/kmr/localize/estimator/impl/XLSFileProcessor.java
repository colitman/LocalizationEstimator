package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.nc.kmr.localize.estimator.util.ExcelUtils;

public class XLSFileProcessor extends AbstractExcelFileProcessor {
	
	private Workbook book;
	private Sheet sheet;
	private List<Sheet> targets;
	
	private List<Point[]> sectors;

	public XLSFileProcessor(File[] files) {
		this.file = files[0];
		init();
	}
	
	@Override
	protected void init() {
		try {
			book = Workbook.getWorkbook(file);
			ready = true;
			sheets = book.getSheetNames();
		} catch (BiffException e) {
			// TODO Add logging
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Add logging
			fileException = e;
		}
	}


	@Override
	public boolean setTarget(String... target) {
		targets = new ArrayList<Sheet>();
		
		for(String sheet:target) {
			Sheet s = book.getSheet(sheet);
			
			if(s == null) {
				targets = null;
				return false;
			}
			
			targets.add(s);
		}
		
		return true;
	}

	@Override
	public String getTarget() {
		String sheetName = null;
		
		if(targets != null) {
			for(Sheet s:targets) {
				sheetName = (sheetName == null)? s.getName(): sheetName + ", " + s.getName();
			}
		}
		
		return sheetName;
	}

	@Override
	public List<String> process() {
		content = new ArrayList<String>();
		if(!ready || targets == null || range == null) {
			return content;
		}
		
		for(Sheet s:targets) {
			sheet = s;
			
			processRange();
			
			Cell cell = null;
			String cellContent = null;
			
			if(sectors != null) {
				Set<String> processed = new HashSet<String>();
				for(Point[] sector:sectors) {
					for(int i = sector[0].col; i <= sector[1].col; i++) {
						for(int j = sector[0].row; j <= sector[1].row; j++) {
							String cellCoord = String.valueOf(i) + "_" + String.valueOf(j);
							if(!processed.add(cellCoord)) {
								continue;
							}
							cell = sheet.getCell(i, j);
							cellContent = cell.getContents();
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
	
	@Override
	public void releaseResource() {
		book.close();
	}

	private void processRange() /*throws InvalidInputException */{
		int cols = sheet.getColumns();
		int rows = sheet.getRows();
		
		String[] ranges = range.split(";");

		sectors = new ArrayList<Point[]>();
		for(String range:ranges) {
			createSector(range, cols, rows);
		}
	}

	private void createSector(String range, int cols, int rows)/* throws InvalidInputException*/ {
		String upLeftCell = range.split(":")[0];
		String downRightCell = range.split(":")[1];
		
		Point upLeft = new Point(ExcelUtils.convertCellAddressToIntArray(upLeftCell));
		Point downRight = new Point(ExcelUtils.convertCellAddressToIntArray(downRightCell));
		
		if(upLeft.col >= cols) {
			upLeft.col = (cols == 0)? cols:cols - 1;
		}
		
		if(upLeft.row >= rows) {
			upLeft.row = (rows == 0)? rows:rows - 1;
		}
		
		if(downRight.col >= cols) {
			downRight.col = cols - 1;
		}
		
		if(downRight.row >= rows) {
			downRight.row = rows - 1;
		}
		
		Point[] points = new Point[2];
		points[0] = upLeft;
		points[1] = downRight;
		
		sectors.add(points);
	}
	
	private static class Point {
		public int col;
		public int row;
		
		public Point(int[] address) {
			col = address[0];
			row = address[1];
		}
	}
}
