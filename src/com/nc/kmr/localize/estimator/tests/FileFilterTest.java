package com.nc.kmr.localize.estimator.tests;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.nc.kmr.localize.estimator.filefilter.AllSupportedFileFilter;
import com.nc.kmr.localize.estimator.filefilter.IniFileFilter;
import com.nc.kmr.localize.estimator.filefilter.PPTFileFilter;
import com.nc.kmr.localize.estimator.filefilter.PropertiesFileFilter;
import com.nc.kmr.localize.estimator.filefilter.XLSFileFilter;
import com.nc.kmr.localize.estimator.filefilter.XLSXFileFilter;

public class FileFilterTest extends Assert {
	
	@Rule
	public final TemporaryFolder tmpFolder = new TemporaryFolder();
	
	private FileFilter ff;
	
	private File folder;
	private File fXLS;
	private File fXLSX;
	private File fPPT;
	private File fProp;
	private File fINI;
	private File fLNG;
	
	@Before
	public void prepair() throws IOException {
		folder = tmpFolder.newFolder("folder");
		fXLS = tmpFolder.newFile("sample.xls");
		fXLSX = tmpFolder.newFile("sample.xlsx");
		fPPT = tmpFolder.newFile("sample.ppt");
		fProp = tmpFolder.newFile("sample.properties");
		fINI = tmpFolder.newFile("sample.ini");
		fLNG = tmpFolder.newFile("sample.lng");
	}
	
	@Test
	public void testAllSupported() {
		ff = new AllSupportedFileFilter();
		assertTrue(".xls files are not accepted", ff.accept(fXLS));
		assertTrue(".xlsx files are not accepted", ff.accept(fXLSX));
		assertTrue(".ppt files are not accepted", ff.accept(fPPT));
		assertTrue(".properties files are not accepted", ff.accept(fProp));
		assertTrue(".ini files are not accepted", ff.accept(fINI));
		assertTrue(".lng files are not accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
	}
	
	@Test
	public void testXLS() {
		ff = new XLSFileFilter();
		assertTrue(".xls files are not accepted", ff.accept(fXLS));
		assertFalse(".xlsx files are accepted", ff.accept(fXLSX));
		assertFalse(".ppt files are accepted", ff.accept(fPPT));
		assertFalse(".properties files are accepted", ff.accept(fProp));
		assertFalse(".ini files are accepted", ff.accept(fINI));
		assertFalse(".lng files are accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
	}
	
	@Test
	public void testXLSX() {
		ff = new XLSXFileFilter();
		assertFalse(".xls files are accepted", ff.accept(fXLS));
		assertTrue(".xlsx files are not accepted", ff.accept(fXLSX));
		assertFalse(".ppt files are accepted", ff.accept(fPPT));
		assertFalse(".properties files are accepted", ff.accept(fProp));
		assertFalse(".ini files are accepted", ff.accept(fINI));
		assertFalse(".lng files are accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
	}
	
	@Test
	public void testPPT() {
		ff = new PPTFileFilter();
		assertFalse(".xls files are accepted", ff.accept(fXLS));
		assertFalse(".xlsx files are accepted", ff.accept(fXLSX));
		assertTrue(".ppt files are not accepted", ff.accept(fPPT));
		assertFalse(".properties files are accepted", ff.accept(fProp));
		assertFalse(".ini files are accepted", ff.accept(fINI));
		assertFalse(".lng files are accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
	}
	
	@Test
	public void testProperties() {
		ff = new PropertiesFileFilter();
		assertFalse(".xls files are accepted", ff.accept(fXLS));
		assertFalse(".xlsx files are accepted", ff.accept(fXLSX));
		assertFalse(".ppt files are accepted", ff.accept(fPPT));
		assertTrue(".properties files are not accepted", ff.accept(fProp));
		assertFalse(".ini files are accepted", ff.accept(fINI));
		assertFalse(".lng files are accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
	}
	
	@Test
	public void testIni() {
		ff = new IniFileFilter();
		assertFalse(".xls files are accepted", ff.accept(fXLS));
		assertFalse(".xlsx files are accepted", ff.accept(fXLSX));
		assertFalse(".ppt files are accepted", ff.accept(fPPT));
		assertFalse(".properties files are accepted", ff.accept(fProp));
		assertTrue(".ini files are not accepted", ff.accept(fINI));
		assertTrue(".lng files are not accepted", ff.accept(fLNG));
		assertTrue("Folders are not accepted", ff.accept(folder));
	}
}
