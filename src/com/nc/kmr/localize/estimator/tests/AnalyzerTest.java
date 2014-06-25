package com.nc.kmr.localize.estimator.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nc.kmr.localize.estimator.impl.analyze.Analyzer;
import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;

public class AnalyzerTest extends Assert {
	
	private AnalyzerBuilder b;
	private Analyzer a;
	private List<String> data;
	
	@Before
	public void setData() {
		data = new ArrayList<String>();
		
		data.add("four 4");
		data.add("три 3");
		data.add("four 4 три 3");
		data.add("три 4 four 3");
		data.add("four 4");
		data.add("три 3");
		data.add("~!@#$%^&*()_+`-=/.?\\';:,<>№");
		data.add("1 2 3 4 5 6 7 8 9 0 12 34 56");
	}
	
	@Test
	public void testInstantiation() {
		b = new AnalyzerBuilder();
		a = b.parse(new ArrayList<String>());
		assertFalse("Show uniques statistic should be disabled by default", a.isShowUniquesStatistic());
		assertFalse("LOE calculating should be disabled by default", a.isCalculateLOE());
		assertEquals("Performance should not be defined by default", -1, a.getPerfromance());
		assertFalse("Printing to console should be disabled by default", a.isPrintToConsole());
		
		b = null;
		a = null;
	}
	
	@Test
	public void testDelegation() {
		b = new AnalyzerBuilder();
		b.setShowUniquesStatistic(true);
		b.setCalculateLOE(true);
		b.setPerformance("100");
		b.setPrintToConsole(true);
		
		a = b.parse(new ArrayList<String>());
		
		assertEquals("Builder does not delegate settings to analyzer [Show uniques statistic]", a.isShowUniquesStatistic(), b.getShowUniquesStatistic());
		assertEquals("Builder does not delegate settings to analyzer [Calculate LOE]", a.isCalculateLOE(), b.isCalculateLOE());
		assertEquals("Builder does not delegate settings to analyzer [Print to console]", a.isPrintToConsole(), b.isPrintToConsole());
		assertEquals("Builder does not delegate settings to analyzer [Performance rate]", a.getPerfromance(), Integer.valueOf(b.getRequestedPerformance()).intValue());
		
		a = null;
		b = null;
	}
	
	@Test
	public void testAnalyzing() {		
		b = new AnalyzerBuilder();
		b.setShowUniquesStatistic(true);
		
		a = b.parse(data);
		
		assertEquals("Incorrect calculation of all characters", 101, a.getAllCharacters());
		assertEquals("Incorrect calculation of all characters (no spaces)", 79, a.getAllCharactersNoSpaces());
		assertEquals("Incorrect calculation of a number of all entries", 8, a.getAllEntriesCount());
		
		assertEquals("Incorrect calculation of all latin characters", 38, a.getAllLatinCharacters());
		assertEquals("Incorrect calculation of all latin characters (no spaces)", 16, a.getAllLatinCharactersNoSpaces());
		assertEquals("Incorrect calculation of a number of all entries with latin characters", 4, a.getAllLatinEntriesCount());
		
		assertEquals("Incorrect calculation of all cyrillic characters", 34, a.getAllCyrillicCharacters());
		assertEquals("Incorrect calculation of all cyrillic characters (no spaces)", 12, a.getAllCyrillicCharactersNoSpaces());
		assertEquals("Incorrect calculation of a number of all entries with cyrillic characters", 4, a.getAllCyrillicEntriesCount());
		
		assertEquals("Incorrect calculation of all digits", 24, a.getAllDigits());
		


		
		
		assertEquals("Incorrect calculation of unique characters", 90, a.getUniqueCharacters());
		assertEquals("Incorrect calculation of unique characters (no spaces)", 70, a.getUniqueCharactersNoSpaces());
		assertEquals("Incorrect calculation of a number of unique entries", 6, a.getUniqueEntriesCount());
		
		assertEquals("Incorrect calculation of unique latin characters", 32, a.getUniqueLatinCharacters());
		assertEquals("Incorrect calculation of unique latin characters (no spaces)", 12, a.getUniqueLatinCharactersNoSpaces());
		assertEquals("Incorrect calculation of a number of unique entries with latin characters", 3, a.getUniqueLatinEntriesCount());
		
		assertEquals("Incorrect calculation of unique cyrillic characters", 29, a.getUniqueCyrillicCharacters());
		assertEquals("Incorrect calculation of unique cyrillic characters (no spaces)", 9, a.getUniqueCyrillicCharactersNoSpaces());
		assertEquals("Incorrect calculation of a number of unique entries with cyrillic characters", 3, a.getUniqueCyrillicEntriesCount());
		
		assertEquals("Incorrect calculation of unique digits", 22, a.getUniqueDigits());
		
		
		b = null;
		a = null;
	}
}
