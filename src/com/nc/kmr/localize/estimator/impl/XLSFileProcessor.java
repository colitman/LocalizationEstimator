package com.nc.kmr.localize.estimator.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.exception.InvalidInputException;
import com.nc.kmr.localize.estimator.util.ExcelUtils;

public class XLSFileProcessor implements FileProcessor {
	
	private boolean ready = false;
	private Exception fileException;
	private File file;
	private Workbook book;
	private String[] sheets;
	private Sheet sheet;
	private String range;
	private List<String> content;
	
	private List<Point[]> sectors = new ArrayList<Point[]>();

	public XLSFileProcessor(File file) {
		this.file = file;
		init();
	}

	private void init() {
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
	public boolean setTarget(String target) {		
		sheet = book.getSheet(target);
		
		if(sheet != null) {
			return true;
		}
		
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
	public boolean setScope(String scope) {
		
		if(scope == null) {
			return false;
		}
		
		if(!scope.matches("([a-zA-Z]+[0-9]+:[a-zA-Z]+[0-9]+;?)+")) {
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

	@Override
	public List<String> process() throws InvalidInputException {
		if(ready && sheet != null && range != null) {
			processRange();
		}
		
		Cell cell = null;
		String cellContent = null;
		
		for(Point[] sector:sectors) {
			for(int i = sector[0].col; i <= sector[1].col; i++) {
				for(int j = sector[0].row; j <= sector[1].row; j++) {
					cell = sheet.getCell(i, j);
					cellContent = cell.getContents();
					if(!cellContent.isEmpty() && cellContent != null) {
						content.add(cellContent);
					}
				}
			}
		}
		
		return content;
	}

	private void processRange() throws InvalidInputException {
		int cols = sheet.getColumns();
		int rows = sheet.getRows();
		
		String[] ranges = range.split(";");
		
		// TODO check for overlapping
		
		for(String range:ranges) {
			createSector(range, cols, rows);
		}
	}

	private void createSector(String range, int cols, int rows) throws InvalidInputException {
		String upLeftCell = range.split(":")[0];
		String downRightCell = range.split(":")[1];
		
		Point upLeft = new Point(ExcelUtils.convertCellAddressToIntArray(upLeftCell));
		Point downRight = new Point(ExcelUtils.convertCellAddressToIntArray(downRightCell));
		
		if(upLeft.col >= cols || upLeft.row >= rows) {
			throw new InvalidInputException("There is no data in the specified range: " + range);
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
