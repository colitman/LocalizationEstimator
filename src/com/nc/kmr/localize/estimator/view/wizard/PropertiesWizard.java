package com.nc.kmr.localize.estimator.view.wizard;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.event.common.AnalyzeButtonListener;
import com.nc.kmr.localize.estimator.event.common.LOECalculationOptionListener;
import com.nc.kmr.localize.estimator.event.common.PerformanceInputListener;
import com.nc.kmr.localize.estimator.event.common.PrintValuesToConsoleOptionListener;
import com.nc.kmr.localize.estimator.event.common.UniquesOptionActionListener;
import com.nc.kmr.localize.estimator.event.common.WizardWindowListener;
import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;
import com.nc.kmr.localize.estimator.view.Wizard;

public class PropertiesWizard extends JFrame implements Wizard {
	
	private static final long serialVersionUID = 1L;
	
	private FileProcessor processor;
	private AnalyzerBuilder builder;
	
	private JCheckBox showUniques;
	private JCheckBox calculateLOE;
	private JCheckBox printToConsole;
	private JTextField performanceInput;
	private JButton analyzeButton;
	
	private GroupLayout globalLayout;
	private GroupLayout optionsPanelLayout;
	private GroupLayout buttonsPanelLayout;
	
	private JPanel optionsChoicePanel;
	private JPanel buttonsPanel;
	
	private JLabel performanceUnits;

	public PropertiesWizard(FileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void run() {
		builder = new AnalyzerBuilder();
		createGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Key-Value file estimator wizard");
		setLocationRelativeTo(null);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		pack();
		setVisible(true);
		setMinimumSize(getPreferredSize());
	}

	private void createGUI() {
		
		optionsChoicePanel = new JPanel();
		optionsChoicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Options"));
		showUniques = new JCheckBox("Show uniques statistic");
		showUniques.addActionListener(new UniquesOptionActionListener(builder));
		calculateLOE = new JCheckBox("Calculate LOE");
		performanceInput = new JTextField();
		performanceUnits = new JLabel("symbols per day");
		performanceInput.setEnabled(false);
		performanceUnits.setEnabled(false);
		printToConsole = new JCheckBox("Print values to console");
		performanceInput.addCaretListener(new PerformanceInputListener(builder));
		calculateLOE.addActionListener(new LOECalculationOptionListener(builder, performanceInput, performanceUnits));
		printToConsole.addActionListener(new PrintValuesToConsoleOptionListener(builder));
		
		buttonsPanel = new JPanel();
		analyzeButton = new JButton("Analyze");
		analyzeButton.addActionListener(new AnalyzeButtonListener(builder, processor));
		
		addWindowListener(new WizardWindowListener(processor));
		
		layoutComponents();
	}

	private void layoutComponents() {		
		buttonsPanelLayout = new GroupLayout(buttonsPanel);
		buttonsPanel.setLayout(buttonsPanelLayout);
		buttonsPanelLayout.setAutoCreateContainerGaps(true);
		buttonsPanelLayout.setAutoCreateGaps(true);
		
		buttonsPanelLayout.setHorizontalGroup(
			buttonsPanelLayout.createSequentialGroup()
					.addComponent(analyzeButton)
		);
		
		buttonsPanelLayout.setVerticalGroup(
			buttonsPanelLayout.createSequentialGroup()
				.addComponent(analyzeButton)
		);
			
			
		
		
		optionsPanelLayout = new GroupLayout(optionsChoicePanel);
		optionsChoicePanel.setLayout(optionsPanelLayout);
		optionsPanelLayout.setAutoCreateContainerGaps(true);
		optionsPanelLayout.setAutoCreateGaps(true);
		
		optionsPanelLayout.setHorizontalGroup(
			optionsPanelLayout.createSequentialGroup()
				.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(showUniques)
					.addComponent(calculateLOE)
					.addGroup(optionsPanelLayout.createSequentialGroup()
						.addPreferredGap(calculateLOE, performanceInput, LayoutStyle.ComponentPlacement.INDENT)
						.addComponent(performanceInput)
						.addComponent(performanceUnits)
					)
					.addComponent(printToConsole)
				)
		);
		
		optionsPanelLayout.setVerticalGroup(
			optionsPanelLayout.createSequentialGroup()
				.addComponent(showUniques)
				.addComponent(calculateLOE)
				.addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(performanceInput)
					.addComponent(performanceUnits)
				)
				.addComponent(printToConsole)
		);
		
		
		
		globalLayout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(globalLayout);
		globalLayout.setAutoCreateGaps(true);
		globalLayout.setAutoCreateContainerGaps(true);
		
		globalLayout.setHorizontalGroup(
			globalLayout.createSequentialGroup()
				.addGroup(globalLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addGroup(globalLayout.createSequentialGroup()
						.addComponent(optionsChoicePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
					)
					.addComponent(buttonsPanel)
				)
		);
		
		globalLayout.setVerticalGroup(
			globalLayout.createSequentialGroup()
				.addGroup(globalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
					.addComponent(optionsChoicePanel)
				)
				.addComponent(buttonsPanel)
		);
		
		optionsPanelLayout.linkSize(SwingConstants.HORIZONTAL, showUniques, performanceInput);
		
	}

}
