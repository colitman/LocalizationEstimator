package com.nc.kmr.localize.estimator.util;

public class ExcelUtils {
	
	public static int[] convertCellAddressToIntArray(String cellAddress) {
		int[] address = new int[2];
		
		String col = cellAddress.replaceAll("[0-9]", "");
		int row = Integer.valueOf(cellAddress.replaceAll("[a-zA-Z]", ""));
		
		address[0] = letter2Int(col);
		address[1] = row - 1;
		
		return address;
	}
	
	public static int getIndexOfColumn(String col) {
		return letter2Int(col);
	}

	private static int letter2Int(String col) {				
		String columnName = col.toUpperCase();
        int value = 0;
        for (int i = 0; i < columnName.length(); i++) {
            int delta = (columnName.charAt(i)) - 64;
            value = value * 26 + delta;
        }
        
        return value - 1;
	}
}
