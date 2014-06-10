package com.nc.kmr.localize.estimator.impl.analyze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Analyzer {
	
	private boolean uniques;
	private boolean LOE;
	private int perfromance;
	private boolean printToConsole;
	
	private StringBuilder allContent;
	private StringBuilder uniqueContent;
	
	private Set<String> uniqueCells;
	private List<String> duplicateCells;
	
	private int allCharacters;
	private int allCharactersNoSpaces;
	private int allLatinCharacters;
	private int allLatinCharactersNoSpaces;
	private int allCyrillicCharacters;
	private int allCyrillicCharactersNoSpaces;
	private int allDigits;
	
	private int uniqueCharacters;
	private int uniqueCharactersNoSpaces;
	private int uniqueLatinCharacters;
	private int uniqueLatinCharactersNoSpaces;
	private int uniqueCyrillicCharacters;
	private int uniqueCyrillicCharactersNoSpaces;
	private int uniqueDigits;
	
	private int allEntriesCount;
	private int uniqueEntriesCount;
	private int allLatinEntriesCount;
	private int allCyrillicEntriesCount;
	private int uniqueLatinEntriesCount;
	private int uniqueCyrillicEntriesCount;
	private int duplicateEntriesCount;
	
	Analyzer() {
		uniques = false;
		LOE = false;
		perfromance = 0;
	}

	public boolean isCalculateLOE() {
		return LOE;
	}
	
	public boolean isShowUniquesStatistic() {
		return uniques;
	}

	public int getPerfromance() {
		return (perfromance == 0)? -1:perfromance;
	}
	
	public boolean isPrintToConsole() {
		return printToConsole;
	}

	public List<String> getDuplicateCells() {
		return (duplicateCells == null)? new ArrayList<String>():duplicateCells;
	}

	public int getAllCharacters() {
		return allCharacters;
	}

	public int getAllCharactersNoSpaces() {
		return allCharactersNoSpaces;
	}

	public int getAllLatinCharacters() {
		return allLatinCharacters;
	}

	public int getAllLatinCharactersNoSpaces() {
		return allLatinCharactersNoSpaces;
	}

	public int getAllCyrillicCharacters() {
		return allCyrillicCharacters;
	}

	public int getAllCyrillicCharactersNoSpaces() {
		return allCyrillicCharactersNoSpaces;
	}

	public int getAllDigits() {
		return allDigits;
	}

	public int getUniqueCharacters() {
		return uniqueCharacters;
	}

	public int getUniqueCharactersNoSpaces() {
		return uniqueCharactersNoSpaces;
	}

	public int getUniqueLatinCharacters() {
		return uniqueLatinCharacters;
	}

	public int getUniqueLatinCharactersNoSpaces() {
		return uniqueLatinCharactersNoSpaces;
	}

	public int getUniqueCyrillicCharacters() {
		return uniqueCyrillicCharacters;
	}

	public int getUniqueCyrillicCharactersNoSpaces() {
		return uniqueCyrillicCharactersNoSpaces;
	}

	public int getUniqueDigits() {
		return uniqueDigits;
	}

	public int getAllEntriesCount() {
		return allEntriesCount;
	}

	public int getUniqueEntriesCount() {
		return uniqueEntriesCount;
	}

	public int getAllLatinEntriesCount() {
		return allLatinEntriesCount;
	}

	public int getAllCyrillicEntriesCount() {
		return allCyrillicEntriesCount;
	}

	public int getUniqueLatinEntriesCount() {
		return uniqueLatinEntriesCount;
	}

	public int getUniqueCyrillicEntriesCount() {
		return uniqueCyrillicEntriesCount;
	}

	public int getDuplicateEntriesCount() {
		return duplicateEntriesCount;
	}

	void setShowUniquesStatistic(boolean b) {
		this.uniques = b;
	}

	void setCalculateLOE(boolean b) {
		this.LOE = b;
	}

	void setPerformance(int performance) {
		this.perfromance = performance;
	}
	
	void setPrintToConsole(boolean printToConsole) {
		this.printToConsole = printToConsole;
	}

	void analyze(List<String> content) {
		if(content == null) {
			return;
		}
		
		allContent = new StringBuilder();
		if(uniques) {
			uniqueContent = new StringBuilder();
			uniqueCells = new HashSet<String>();
			duplicateCells = new ArrayList<String>();
		}
		
		allEntriesCount = content.size();
		
		for(String s:content) {
			if(printToConsole) {
				System.out.println(s);
			}
			
			allContent.append(s);
			
			if(s.matches(".*[à-ÿÀ-ß]+.*")) {
				System.out.println("rus");
				allCyrillicEntriesCount++;
			}
			
			if(s.matches(".*[a-zA-Z]+.*")) {
				System.out.println("lat");
				allLatinEntriesCount++;
			}
			
			if(uniques) {
				if(uniqueCells.add(s)) {
					uniqueContent.append(s);
					
					if(s.matches(".*[à-ÿÀ-ß]+.*")) {
						uniqueCyrillicEntriesCount++;
					}
					
					if(s.matches(".*[a-zA-Z]+.*")) {
						uniqueLatinEntriesCount++;
					}
					
				} else {
					duplicateCells.add(s);
				}
				
				uniqueEntriesCount = uniqueCells.size();
				duplicateEntriesCount = duplicateCells.size();
			}
		}
		
		calculate();
	}

	private void calculate() {
		
		String all = allContent.toString();
		
		allCharacters = all.length();
		allCharactersNoSpaces = all.replaceAll(" ", "").length();
		allLatinCharacters = all.replaceAll("[^a-zA-Z ]", "").length();
		allLatinCharactersNoSpaces = all.replaceAll("[^a-zA-Z]| ", "").length();
		allCyrillicCharacters = all.replaceAll("[^à-ÿÀ-ß ]", "").length();
		allCyrillicCharactersNoSpaces = all.replaceAll("[^à-ÿÀ-ß]| ", "").length();
		allDigits = all.replaceAll("[^0-9]", "").length();
		
		if(uniques) {
			String unique = uniqueContent.toString();
			
			uniqueCharacters = unique.length();
			uniqueCharactersNoSpaces = unique.replaceAll(" ", "").length();
			uniqueLatinCharacters = unique.replaceAll("[^a-zA-Z ]", "").length();
			uniqueLatinCharactersNoSpaces = unique.replaceAll("[^a-zA-Z]| ", "").length();
			uniqueCyrillicCharacters = unique.replaceAll("[^à-ÿÀ-ß ]", "").length();
			uniqueCyrillicCharactersNoSpaces = unique.replaceAll("[^à-ÿÀ-ß]| ", "").length();
			uniqueDigits = unique.replaceAll("[^0-9]", "").length();
		}
	}
}
