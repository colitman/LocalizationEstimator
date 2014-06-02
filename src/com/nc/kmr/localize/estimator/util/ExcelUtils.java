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

	private static int letter2Int(String col) {
		String abc = "abcdefghijklmnopqrstuvwxyz";
		
		return abc.indexOf(col);
	}
}
