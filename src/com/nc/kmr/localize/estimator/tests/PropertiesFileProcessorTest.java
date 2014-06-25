package com.nc.kmr.localize.estimator.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nc.kmr.localize.estimator.impl.PropertiesFileProcessor;
import com.nc.kmr.localize.estimator.util.Utils;

public class PropertiesFileProcessorTest extends Assert {
	
	private PropertiesFileProcessor p;
	private File[] f_n;
	private File[] f_nf;
	
	private List<String> empty;
	private List<String> no_cat;
	private List<String> all;
	private List<String> exclude;
	private List<String> include;
	private List <String> actual;
	
	@Before
	public void prepareFiles() throws IOException {
		f_n = new File[]{new File("testData/test.properties")};
		f_nf = new File[] {new File("test.properties")};
	}
	
	@Before
	public void setData() {
		empty = new ArrayList<String>();
		no_cat = new ArrayList<String>();
		all = new ArrayList<String>();
		exclude = new ArrayList<String>();
		include = new ArrayList<String>();
		
		String string1 = "Author:";
		String string2 = "KMR Sumy";
		String string3 = "Version";
		String string4 = "Licence:";
		String string5 = "For internal NetCracker usage.";
		String string6 = "Web:";
		String string7 = "GitHub Repo:";
		String string8 = "https://github.com/colitman/LocalizationEstimator";
		String string9 = "UI translation by:";
		String string10 = "Dmitry Romenskiy";
		String string11 = "Feel free to send bug reports and improvement requests";
		
		no_cat.add("English");
		no_cat.add("24-06-2014");
		no_cat.add("0.1");
		
		all.add(string1);
		all.add(string2);
		all.add(string3);
		all.add(string4);
		all.add(string5);
		all.add(string6);
		all.add(string7);
		all.add(string8);
		all.add(string9);
		all.add(string10);
		all.add(string11);
		
		exclude.add(string1);
		exclude.add(string2);
		exclude.add(string3);
		exclude.add(string4);
		exclude.add(string6);
		exclude.add(string7);
		exclude.add(string11);
		
		include.add(string5);
		include.add(string8);
		include.add(string9);
		include.add(string10);
		
		Collections.sort(all);
		Collections.sort(exclude);
		Collections.sort(include);
	}

	@Test
	public void testCorrectInstantiation() {
		p = new PropertiesFileProcessor(f_n);
		assertTrue("Processor for *.properties files is not ready", p.isReady());
		p = null;
	}
	
	@Test
	public void testFailingInstantiationFileNotFound() {
		p = new PropertiesFileProcessor(f_nf);
		assertFalse("Processor for *.properties files turned to be ready", p.isReady());
		assertEquals("Failing cause is not saved", FileNotFoundException.class, p.getNotReadyException().getClass());
		p = null;
	}

	@Test
	public void testSetScope() {
		p = new PropertiesFileProcessor(f_n);
		assertNotNull("'scope' property of processor should be initialized by default", p.getScope());
		assertEquals("Initial scope should be an empty string", "", p.getScope());
		assertFalse("Scope cannot be set to NULL", p.setScope(null));
		assertNull("In case of incorrect scope setting, 'scope' property of processor should be set to NULL", p.getScope());
		assertTrue("It should be possible not to set any scope", p.setScope(""));
		assertEquals("Incorrect setting/getting of scope", "",  p.getScope());
		assertTrue("It should be possible to set scope", p.setScope("Scope"));
		assertEquals("Incorrect setting/getting of scope", "Scope",  p.getScope());
		p = null;
	}
	
	@Test
	public void testSetScopeProcessingMode() {
		p = new PropertiesFileProcessor(f_n);
		int mode;
		assertEquals("Incorrect initialization of processing mode", -10000, p.getScopeProcessingMode());
		mode = -2;
		assertFalse("Forbidden value for scope processing mode: " + mode, p.setScopeProcessingMode(mode));
		assertEquals("Incorrect reaction after passing wrong value to set scope processing mode", -10000, p.getScopeProcessingMode());
		mode = 2;
		assertFalse("Forbidden value for scope processing mode: " + mode, p.setScopeProcessingMode(mode));
		assertEquals("Incorrect reaction after passing wrong value to set scope processing mode", -10000, p.getScopeProcessingMode());
		mode = -1;
		assertTrue("Allowed value for scope processing mode is recognized as forbidden: " + mode, p.setScopeProcessingMode(mode));
		assertEquals("Scope processing mode constant discrepancy (EXCLUDE_SCOPE)", Utils.EXCLUDE_SCOPE, p.getScopeProcessingMode());
		mode = 0;
		assertTrue("Allowed value for scope processing mode is recognized as forbidden: " + mode, p.setScopeProcessingMode(mode));
		assertEquals("Scope processing mode constant discrepancy (NO_SCOPE_DEFINED)", Utils.NO_SCOPE_DEFINED, p.getScopeProcessingMode());
		mode = 1;
		assertTrue("Allowed value for scope processing mode is recognized as forbidden: " + mode, p.setScopeProcessingMode(mode));
		assertEquals("Scope processing mode constant discrepancy (INCLUDE_SCOPE)", Utils.INCLUDE_SCOPE, p.getScopeProcessingMode());
		p = null;
	}
	
	@Test
	public void testProcessing() {		
		
		p = new PropertiesFileProcessor(f_nf);
		assertEquals("Empty list should be returned if processor is not ready", empty, p.process());
		
		p = new PropertiesFileProcessor(f_n);
		assertFalse(p.setScope(null));
		assertEquals("Empty list should be returned if scope is set to NULL", empty, p.process());		
		
		assertTrue(p.setScope("Scope"));
		assertFalse(p.setScopeProcessingMode(-3));
		assertEquals("Empty list should be returned if scope processing mode is not correctly set", empty, p.process());
		
		assertTrue(p.setScope(""));
		assertFalse(p.setScopeProcessingMode(-3));
		assertEquals("If scope is empty, scope processing mode should not be considered", 11, p.process().size());
		actual = p.process();
		Collections.sort(actual);
		assertEquals("Retrieved data discrepancy", all, actual);
		
		assertTrue(p.setScopeProcessingMode(-1));
		assertEquals("If scope is empty, scope processing mode should not be considered", 11, p.process().size());
		actual = p.process();
		Collections.sort(actual);
		assertEquals("Retrieved data discrepancy", all, actual);
		
		assertTrue(p.setScopeProcessingMode(0));
		assertEquals("If scope is empty, scope processing mode should not be considered", 11, p.process().size());
		actual = p.process();
		Collections.sort(actual);
		assertEquals("Retrieved data discrepancy", all, actual);
		
		assertTrue(p.setScopeProcessingMode(1));
		assertEquals("If scope is empty, scope processing mode should not be considered", 11, p.process().size());
		actual = p.process();
		Collections.sort(actual);
		assertEquals("Retrieved data discrepancy", all, actual);
		
		assertFalse(p.setScopeProcessingMode(2));
		assertEquals("If scope is empty, scope processing mode should not be considered", 11, p.process().size());
		actual = p.process();
		Collections.sort(actual);
		assertEquals("Retrieved data discrepancy", all, actual);
		
		assertTrue(p.setScope("_"));
		assertTrue(p.setScopeProcessingMode(0));
		assertEquals("If scope processing mode is NO_SCOPE, defined scope should not be considered", 11, p.process().size());
		actual = p.process();
		Collections.sort(actual);
		assertEquals("Retrieved data discrepancy", all, actual);
		
		assertTrue(p.setScope("_"));
		assertTrue(p.setScopeProcessingMode(-1));
		assertEquals("Scope processing mode constant discrepancy (EXCLUDE_SCOPE)", Utils.EXCLUDE_SCOPE, p.getScopeProcessingMode());
		assertEquals("Incorrect number of recognized entries", 7, p.process().size());
		actual = p.process();
		Collections.sort(actual);
		assertEquals("Retrieved data discrepancy", exclude, actual);
		
		assertTrue(p.setScope("_"));
		assertTrue(p.setScopeProcessingMode(1));
		assertEquals("Scope processing mode constant discrepancy (INCLUDE_SCOPE)", Utils.INCLUDE_SCOPE, p.getScopeProcessingMode());
		assertEquals("Incorrect number of recognized entries", 4, p.process().size());
		actual = p.process();
		Collections.sort(actual);
		assertEquals("Retrieved data discrepancy", include, actual);
		
	}
}
