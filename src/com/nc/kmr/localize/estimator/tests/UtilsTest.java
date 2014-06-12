package com.nc.kmr.localize.estimator.tests;

import java.io.File;

import org.junit.Test;

import com.nc.kmr.localize.estimator.util.ExcelUtils;
import com.nc.kmr.localize.estimator.util.Utils;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {
	
	@Test
	public void testGetFileType() {
		File txt = new File("sample.txt");
		File noExt = new File("sample");
		File noName = new File(".txt");
		File sevDots = new File("sample.doc.txt");
		File nullFile = null;
		File folder = new File("tmp/folder");
		
		String txtActual = Utils.getFileType(txt);
		String noExtActual = Utils.getFileType(noExt);
		String noNameActual = Utils.getFileType(noName);
		String sevDotsActual = Utils.getFileType(sevDots);
		String nullFileActual = Utils.getFileType(nullFile);
		folder.mkdirs();
		String folderActual = Utils.getFileType(folder);
		
		assertEquals("Wrong filetype defined for 'sample.txt';", "txt" , txtActual);
		assertEquals("Wrong filetype defined for 'sample';", "" , noExtActual);
		assertEquals("Wrong filetype defined for '.txt';", "txt" , noNameActual);
		assertEquals("Wrong filetype defined for 'sample.doc.txt';", "txt" , sevDotsActual);
		assertEquals("Wrong filetype defined for null file;", "" , nullFileActual);
		assertEquals("Wrong filetype defined for folder;", "" , folderActual);
		assertTrue("Temporary test folder has not been deleted: " + folder.getAbsolutePath(), folder.delete());
		
	}
	
	@Test
	public void testLetterTo0BasedInt() {
		String abc = "abcdefghijklmnopqrstuvwxyz";
		String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		int i = -1;
		int ii = -1;
		int iii = 0;
		int index = 0;
		
		for(int k = i; k < 26; k++) {
			String col1 = "";
			String COL1 = "";
			if(k != -1) {
				col1 = String.valueOf(abc.charAt(k));
				COL1 = String.valueOf(ABC.charAt(k));
				if(ii >= 26) {
					ii = 0;
				}
			}
			i++;
			for (int j = ii; j < 26; j++) {
				String col2 = "";
				String COL2 = "";
				if(j != -1) {
					col2 = String.valueOf(abc.charAt(j));
					COL2 = String.valueOf(ABC.charAt(j));
				}
				ii++;
				for (int h = iii; h < 26; h++) {
					String col3 = String.valueOf(abc.charAt(h));
					String COL3 = String.valueOf(ABC.charAt(h));
					String col = col1 + col2 + col3;
					String COL = COL1 + COL2 + COL3;
					assertEquals("Wrong column index received for lowercase Excel cell address - '" + col + "';", index, ExcelUtils.getIndexOfColumn(col));
					assertEquals("Wrong column index received for uppercase Excel cell address - '" + COL + "';", index, ExcelUtils.getIndexOfColumn(COL));
					index++;
				}
			}
		}
	}
}
