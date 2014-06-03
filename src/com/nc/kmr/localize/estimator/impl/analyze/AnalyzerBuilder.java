package com.nc.kmr.localize.estimator.impl.analyze;

import java.util.List;

public class AnalyzerBuilder {
	
	private Analyzer analyzer;
	private boolean showUniquesStatistic;
	private boolean calculateLOE;
	private int performance;
	
	public AnalyzerBuilder() {
		analyzer = new Analyzer();
		showUniquesStatistic = false;
		calculateLOE = false;
		performance = 0;
	}

	public boolean getShowUniquesStatistic() {
		return showUniquesStatistic;
	}

	public void setShowUniquesStatistic(boolean b) {
		showUniquesStatistic = b;
		analyzer.setShowUniquesStatistic(b);
	}

	public boolean isCalculateLOE() {
		return calculateLOE;
	}

	public void setCalculateLOE(boolean b) {
		calculateLOE = b;
		analyzer.setCalculateLOE(b);
	}

	public void setPerformance(String performance) {
		this.performance = Integer.valueOf(performance);
		analyzer.setPerformance(this.performance);
	}
	
	public Analyzer parse(List<String> content) {
		analyzer.analyze(content);
		
		return analyzer;
	}

}
