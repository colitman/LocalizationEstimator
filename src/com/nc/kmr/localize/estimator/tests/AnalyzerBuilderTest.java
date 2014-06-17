package com.nc.kmr.localize.estimator.tests;

import java.util.ArrayList;

import org.junit.Test;

import com.nc.kmr.localize.estimator.impl.analyze.Analyzer;
import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;

import junit.framework.TestCase;

public class AnalyzerBuilderTest extends TestCase {
	
	AnalyzerBuilder b;
	Analyzer a;
	
	@Test
	public void testInstantiation() {
		b = new AnalyzerBuilder();
		assertFalse("Show uniques statistic should be disabled by default", b.getShowUniquesStatistic());
		assertFalse("LOE calculating should be disabled by default", b.isCalculateLOE());
		assertFalse("Printing to console should be disabled by default", b.isPrintToConsole());
		b = null;
	}
	
	@Test
	public void testDelegation() {
		b = new AnalyzerBuilder();
		b.setShowUniquesStatistic(true);
		b.setCalculateLOE(true);
		b.setPerformance("100");
		b.setPrintToConsole(true);
		
		assertTrue("Builder does not save its own settings [Show uniques statistic]", b.getShowUniquesStatistic());
		assertTrue("Builder does not save its own settings [Calculate LOE]", b.isCalculateLOE());
		assertTrue("Builder does not save its own settings [Print to console]", b.isPrintToConsole());
		assertEquals("Incorrect performance saving;", "100", b.getRequestedPerformance());
		
		a = b.parse(new ArrayList<String>());
		
		assertEquals("Builder does not delegate settings to analyzer [Show uniques statistic]", a.isShowUniquesStatistic(), b.getShowUniquesStatistic());
		assertEquals("Builder does not delegate settings to analyzer [Calculate LOE]", a.isCalculateLOE(), b.isCalculateLOE());
		assertEquals("Builder does not delegate settings to analyzer [Print to console]", a.isPrintToConsole(), b.isPrintToConsole());
		assertEquals("Builder does not delegate settings to analyzer [Performance rate]", a.getPerfromance(), Integer.valueOf(b.getRequestedPerformance()).intValue());
		
		a = null;
		b = null;
	}
}
