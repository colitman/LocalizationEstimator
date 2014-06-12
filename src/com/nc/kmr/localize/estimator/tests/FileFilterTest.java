package com.nc.kmr.localize.estimator.tests;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.junit.Test;

import com.nc.kmr.localize.estimator.filefilter.AllSupportedFileFilter;
import com.nc.kmr.localize.estimator.filefilter.PropertiesFileFilter;
import com.nc.kmr.localize.estimator.filefilter.XLSFileFilter;
import com.nc.kmr.localize.estimator.filefilter.XLSXFileFilter;

import junit.framework.TestCase;

public class FileFilterTest extends TestCase {
	
	private FileFilter ff;
	private File folder = new File("tmp/folder");
	private File fXLS = new File("sample.xls");
	private File fXLSX = new File("sample.xlsx");
	private File fProp = new File("sample.properties");
	private File fINI = new File("sample.ini");
	private File fLNG = new File("sample.lng");
	
	@Test
	public void testAllSupported() {
		ff = new AllSupportedFileFilter();
		folder.mkdirs();
		assertTrue(".xls files are not accepted", ff.accept(fXLS));
		assertTrue(".xlsx files are not accepted", ff.accept(fXLSX));
		assertTrue(".properties files are not accepted", ff.accept(fProp));
		assertTrue(".ini files are not accepted", ff.accept(fINI));
		assertTrue(".lng files are not accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
		assertTrue("Temporary test folder has not been deleted: " + folder.getAbsolutePath(), folder.delete());
	}
	
	@Test
	public void testXLS() {
		ff = new XLSFileFilter();
		folder.mkdirs();
		assertTrue(".xls files are not accepted", ff.accept(fXLS));
		assertFalse(".xlsx files are accepted", ff.accept(fXLSX));
		assertFalse(".properties files are accepted", ff.accept(fProp));
		assertFalse(".ini files are accepted", ff.accept(fINI));
		assertFalse(".lng files are accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
		assertTrue("Temporary test folder has not been deleted: " + folder.getAbsolutePath(), folder.delete());
	}
	
	@Test
	public void testXLSX() {
		ff = new XLSXFileFilter();
		folder.mkdirs();
		assertFalse(".xls files are accepted", ff.accept(fXLS));
		assertTrue(".xlsx files are not accepted", ff.accept(fXLSX));
		assertFalse(".properties files are accepted", ff.accept(fProp));
		assertFalse(".ini files are accepted", ff.accept(fINI));
		assertFalse(".lng files are accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
		assertTrue("Temporary test folder has not been deleted: " + folder.getAbsolutePath(), folder.delete());
	}
	
	@Test
	public void testProperties() {
		ff = new PropertiesFileFilter();
		folder.mkdirs();
		assertFalse(".xls files are accepted", ff.accept(fXLS));
		assertFalse(".xlsx files are accepted", ff.accept(fXLSX));
		assertTrue(".properties files are not accepted", ff.accept(fProp));
		assertTrue(".ini files are not accepted", ff.accept(fINI));
		assertTrue(".lng files are not accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
		assertTrue("Temporary test folder has not been deleted: " + folder.getAbsolutePath(), folder.delete());
	}
}
