package com.nc.kmr.localize.estimator.view;

import java.awt.Dialog;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.impl.analyze.Analyzer;

public class ResultsWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private Analyzer analyzer;
	private FileProcessor processor;
	
	private JPanel infoPanel = new JPanel();
	private JLabel info = new JLabel();
	
	private JTabbedPane tabs = new JTabbedPane();
	
	private JPanel allPanel = new JPanel();
	private JTextArea allResults = new JTextArea();
	
	private JPanel uniquePanel;
	private JTextArea uniqueResults;
	
	private JPanel repeatsPanel;
	private JTextArea repeats;
	private JScrollPane scroll;
	
	private GroupLayout globalLayout;
	private GroupLayout infoPanelLayout;
	private GroupLayout allPanelLayout;
	private GroupLayout uniquePanelLayout;
	private GroupLayout repeatesPanelLayout;
	

	public ResultsWindow(Analyzer analyzer, FileProcessor processor) {
		this.analyzer = analyzer;
		this.processor = processor;
	}

	@Override
	public void run() {
		setTitle(processor.getSimpleFileName() + " - Estimation Results");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		createGUI();
		fillResults();
		setSize(600, 400);
		setLocationRelativeTo(null);
		setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		setVisible(true);		
	}

	private void createGUI() {
		
		allResults.setEditable(false);
		allResults.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
		tabs.addTab("All", allPanel);
		
		if(analyzer.isShowUniquesStatistic()) {
			uniqueResults = new JTextArea();
			uniqueResults.setEditable(false);
			uniqueResults.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
			uniquePanel = new JPanel();
			tabs.addTab("Unique", uniquePanel);
			
			repeats = new JTextArea();
			repeats.setLineWrap(true);
			repeats.setWrapStyleWord(true);
			repeats.setEditable(false);
			repeats.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
			repeatsPanel = new JPanel();
			scroll = new JScrollPane(repeatsPanel);
			tabs.addTab("Repetitions", scroll);
		}
		
		layoutComponents();
	}
	
	private void layoutComponents() {
		allPanelLayout = new GroupLayout(allPanel);
		allPanel.setLayout(allPanelLayout);
		
		allPanelLayout.setAutoCreateGaps(true);
		allPanelLayout.setAutoCreateContainerGaps(true);
		
		allPanelLayout.setHorizontalGroup(
			allPanelLayout.createSequentialGroup()
				.addComponent(allResults)
		);
		
		allPanelLayout.setVerticalGroup(
			allPanelLayout.createSequentialGroup()
				.addComponent(allResults)
		);
		
		
		
		if(analyzer.isShowUniquesStatistic()) {
			uniquePanelLayout = new GroupLayout(uniquePanel);
			uniquePanel.setLayout(uniquePanelLayout);
			
			uniquePanelLayout.setAutoCreateGaps(true);
			uniquePanelLayout.setAutoCreateContainerGaps(true);
			
			uniquePanelLayout.setHorizontalGroup(
				uniquePanelLayout.createSequentialGroup()
					.addComponent(uniqueResults)
			);
			
			uniquePanelLayout.setVerticalGroup(
				uniquePanelLayout.createSequentialGroup()
					.addComponent(uniqueResults)
			);
			
			repeatesPanelLayout = new GroupLayout(repeatsPanel);
			repeatsPanel.setLayout(repeatesPanelLayout);
			
			repeatesPanelLayout.setAutoCreateGaps(true);
			repeatesPanelLayout.setAutoCreateContainerGaps(true);
			
			repeatesPanelLayout.setHorizontalGroup(
				repeatesPanelLayout.createSequentialGroup()
					.addComponent(repeats)
			);
			
			repeatesPanelLayout.setVerticalGroup(
				repeatesPanelLayout.createSequentialGroup()
					.addComponent(repeats)
			);
		}
		
		
		
		infoPanelLayout = new GroupLayout(infoPanel);
		infoPanel.setLayout(infoPanelLayout);
		
		infoPanelLayout.setAutoCreateGaps(true);
		infoPanelLayout.setAutoCreateContainerGaps(true);
		
		infoPanelLayout.setHorizontalGroup(
			infoPanelLayout.createSequentialGroup()
				.addComponent(info)
		);
		
		infoPanelLayout.setVerticalGroup(
			infoPanelLayout.createSequentialGroup()
				.addComponent(info)
		);
		
		
		
		
		globalLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(globalLayout);
		
		globalLayout.setAutoCreateGaps(true);
		globalLayout.setAutoCreateContainerGaps(true);
		
		globalLayout.setHorizontalGroup(
			globalLayout.createSequentialGroup()
				.addGroup(globalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(infoPanel)
					.addComponent(tabs)
				)
		);
		
		globalLayout.setVerticalGroup(
			globalLayout.createSequentialGroup()
				.addComponent(infoPanel)
				.addComponent(tabs)
		);
		
	}

	private void fillResults() {
		info.setText("<html>Analyzed file: " + processor.getSimpleFileName() + NEW_LINE_HTML
				+ "Filepath: " + processor.getFileName() + NEW_LINE_HTML
				+ "Analyzed target: " + processor.getTarget() + NEW_LINE_HTML
				+ "Analyzed scope: " + processor.getScope() + "</html>");
		
		allResults.append(CHARS + analyzer.getAllCharacters() + NEW_LINE);
		allResults.append(CHARS_NO_SP + analyzer.getAllCharactersNoSpaces() + NEW_LINE);
		allResults.append(LAT_CHARS + analyzer.getAllLatinCharacters() + NEW_LINE);
		allResults.append(LAT_CHARS_NO_SP + analyzer.getAllLatinCharactersNoSpaces() + NEW_LINE);
		allResults.append(CYR_CHARS + analyzer.getAllCyrillicCharacters() + NEW_LINE);
		allResults.append(CYR_CHARS_NO_SP + analyzer.getAllCyrillicCharactersNoSpaces() + NEW_LINE);
		allResults.append(DIGITS + analyzer.getAllDigits() + NEW_LINE);
		
		if(analyzer.isShowUniquesStatistic()) {
			uniqueResults.append(CHARS + analyzer.getUniqueCharacters() + NEW_LINE);
			uniqueResults.append(CHARS_NO_SP + analyzer.getUniqueCharactersNoSpaces() + NEW_LINE);
			uniqueResults.append(LAT_CHARS + analyzer.getUniqueLatinCharacters() + NEW_LINE);
			uniqueResults.append(LAT_CHARS_NO_SP + analyzer.getUniqueLatinCharactersNoSpaces() + NEW_LINE);
			uniqueResults.append(CYR_CHARS + analyzer.getUniqueCyrillicCharacters() + NEW_LINE);
			uniqueResults.append(CYR_CHARS_NO_SP + analyzer.getUniqueCyrillicCharactersNoSpaces() + NEW_LINE);
			uniqueResults.append(DIGITS + analyzer.getUniqueDigits() + NEW_LINE);
			
			for(String entry:analyzer.getDuplicateCells()) {
				repeats.append(entry);
				repeats.append(NEW_LINE);
			}
		}
		
		if(analyzer.isCalculateLOE()) {
			allResults.append("==================================================" + NEW_LINE);
			allResults.append(LOE_FOR_TOTAL + analyzer.getAllCharactersNoSpaces()/analyzer.getPerfromance() + "m/d" + NEW_LINE);
			allResults.append(LOE_FOR_LAT + analyzer.getAllLatinCharactersNoSpaces()/analyzer.getPerfromance() + "m/d" + NEW_LINE);
			allResults.append(LOE_FOR_CYR + analyzer.getAllCyrillicCharactersNoSpaces()/analyzer.getPerfromance() + "m/d" + NEW_LINE);
			
			if(analyzer.isShowUniquesStatistic()) {
				uniqueResults.append("==================================================" + NEW_LINE);
				uniqueResults.append(LOE_FOR_TOTAL + analyzer.getUniqueCharactersNoSpaces()/analyzer.getPerfromance() + "m/d" + NEW_LINE);
				uniqueResults.append(LOE_FOR_LAT + analyzer.getUniqueLatinCharactersNoSpaces()/analyzer.getPerfromance() + "m/d" + NEW_LINE);
				uniqueResults.append(LOE_FOR_CYR + analyzer.getUniqueCyrillicCharactersNoSpaces()/analyzer.getPerfromance() + "m/d" + NEW_LINE);
			}
		}
		
	}
	
	private final String NEW_LINE_HTML = "<br>";
	private final String NEW_LINE = System.getProperty("line.separator");
	
	private final String CHARS = "Total characters: ";
	private final String CHARS_NO_SP = "Total alphabetical characters: ";
	private final String LAT_CHARS = "Total latin characters: ";
	private final String LAT_CHARS_NO_SP = "Total alphabetical latin characters: ";
	private final String CYR_CHARS = "Total cyrillic characters: ";
	private final String CYR_CHARS_NO_SP = "Total alphabetical cyrillic characters: ";
	private final String DIGITS = "Total digits: ";
	
	private final String LOE_FOR_TOTAL = "Total characters LOE: ";
	private final String LOE_FOR_LAT = "Latin characters LOE: ";
	private final String LOE_FOR_CYR = "Cyrillic characters LOE: ";
	
}
