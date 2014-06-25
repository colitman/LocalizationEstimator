package com.nc.kmr.localize.estimator.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nc.kmr.localize.estimator.impl.PPTFileProcessor;

public class PPTFileProcessorTest extends Assert {
	
	private PPTFileProcessor p;
	private File[] f_n;
	private File[] f_nf;
	private File[] f_if;
	private File[] f_txt;
	
	@Before
	public void prepareFiles() throws IOException {
		f_n = new File[]{new File("testData/test.ppt")};
		f_nf = new File[]{new File("test.ppt")};
		f_if = new File[]{new File("testData/test_if.ppt")};
		f_txt = new File[]{new File("testData/test_txt.ppt")};
	}
	
	@Test
	public void testCorrectInstantiation() {
		p = new PPTFileProcessor(f_n);
		assertTrue("Processor for *.ppt files is not ready", p.isReady());
		p = null;
	}
	
	@Test
	public void testFailingInstantiationFileNotFound() {
		p = new PPTFileProcessor(f_nf);
		assertFalse("Processor for *.ppt files turned to be ready", p.isReady());
		assertEquals("Failing cause is not saved", FileNotFoundException.class, p.getNotReadyException().getClass());
		p = null;
	}
	
	@Test(expected=OfficeXmlFileException.class)
	public void testFailingInstantiationInvalidFormat2007() {
		p = new PPTFileProcessor(f_if);
		assertFalse("Processor for *.ppt files turned to be ready", p.isReady());
		assertEquals("Failing cause is not saved", OfficeXmlFileException.class, p.getNotReadyException().getClass());
		p = null;
	}
	
	@Test
	public void testFailingInstantiationInvalidFormat() {
		p = new PPTFileProcessor(f_txt);
		assertFalse("Processor for *.ppt files turned to be ready", p.isReady());
		assertEquals("Failing cause is not saved", IOException.class, p.getNotReadyException().getClass());
		p = null;
	}
	
	public void testSetTarget() {
		//TODO implement
	}

	public void testSetScope() {
		//TODO implement
	}
	
	public void testSetScopeProcessingMode() {
		//TODO implement
	}
	
	public void testProcessing() {
		//TODO implement
	}

}
