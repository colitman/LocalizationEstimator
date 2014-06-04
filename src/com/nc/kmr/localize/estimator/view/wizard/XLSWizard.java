package com.nc.kmr.localize.estimator.view.wizard;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.event.AnalyzeButtonListener;
import com.nc.kmr.localize.estimator.event.LOECalculationOptionListener;
import com.nc.kmr.localize.estimator.event.PerformanceInputListener;
import com.nc.kmr.localize.estimator.event.RangeInputCaretListener;
import com.nc.kmr.localize.estimator.event.SheetListSelectionListener;
import com.nc.kmr.localize.estimator.event.UniquesOptionActionListener;
import com.nc.kmr.localize.estimator.event.WizardWindowListener;
import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;
import com.nc.kmr.localize.estimator.view.Wizard;

public class XLSWizard extends JFrame implements Wizard {
	
	private static final long serialVersionUID = 1L;
	
	private FileProcessor processor;
	private AnalyzerBuilder builder;
	private JList<String> sheetList;
	private JTextField rangeInput;
	private JCheckBox showUniques;
	private JCheckBox calculateLOE;
	private JTextField performanceInput;
	private JButton analyzeButton;
	
	
	private GroupLayout globalLayout;
	private GroupLayout sheetPanelLayout;
	private GroupLayout rangePanelLayout;
	private GroupLayout optionsPanelLayout;
	private GroupLayout buttonsPanelLayout;
	
	private JPanel sheetChoicePanel;
	private JPanel rangeChoicePanel;
	private JPanel optionsChoicePanel;
	private JPanel buttonsPanel;
	
	private JLabel sheetChoiceDesc;
	private JLabel rangeChoiceDesc;
	private JLabel performanceUnits;

	public XLSWizard(FileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void run() {
		builder = new AnalyzerBuilder();
		createGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("MS Excel file estimator wizard");
		setLocationRelativeTo(null);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		pack();
		setVisible(true);
		setMinimumSize(getPreferredSize());
	}
	
	private void createGUI() {
		sheetChoicePanel = new JPanel();
		sheetChoicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Sheet"));
		sheetChoiceDesc = new JLabel("Select a sheet for analyzing:");
		sheetList = new JList<String>(processor.getTargets());
		sheetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sheetList.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
		sheetList.getSelectionModel().addListSelectionListener(new SheetListSelectionListener(processor, sheetList));
		sheetList.setSelectedIndex(0);
		
		rangeChoicePanel = new JPanel();
		rangeChoicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Range"));
		rangeChoiceDesc = new JLabel("Select range for analyzing:");
		rangeInput = new JTextField();
		rangeInput.addCaretListener(new RangeInputCaretListener(processor));
		
		optionsChoicePanel = new JPanel();
		optionsChoicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Options"));
		showUniques = new JCheckBox("Show uniques statistic");
		showUniques.addActionListener(new UniquesOptionActionListener(builder));
		calculateLOE = new JCheckBox("Calculate LOE");
		performanceInput = new JTextField();
		performanceUnits = new JLabel("symbols per day");
		performanceInput.setEnabled(false);
		performanceUnits.setEnabled(false);
		performanceInput.addCaretListener(new PerformanceInputListener(builder));
		calculateLOE.addActionListener(new LOECalculationOptionListener(builder, performanceInput, performanceUnits));
		
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
		);
		
		
		rangePanelLayout = new GroupLayout(rangeChoicePanel);
		rangeChoicePanel.setLayout(rangePanelLayout);
		rangePanelLayout.setAutoCreateContainerGaps(true);
		rangePanelLayout.setAutoCreateGaps(true);
		
		rangePanelLayout.setHorizontalGroup(
				rangePanelLayout.createSequentialGroup()
				.addGroup(rangePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(rangeChoiceDesc)
						.addComponent(rangeInput, 130, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				)
		);
		
		rangePanelLayout.setVerticalGroup(
				rangePanelLayout.createSequentialGroup()
					.addComponent(rangeChoiceDesc)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(rangeInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		
		
		sheetPanelLayout = new GroupLayout(sheetChoicePanel);
		sheetChoicePanel.setLayout(sheetPanelLayout);
		sheetPanelLayout.setAutoCreateContainerGaps(true);
		sheetPanelLayout.setAutoCreateGaps(true);
		
		sheetPanelLayout.setHorizontalGroup(
			sheetPanelLayout.createSequentialGroup()
				.addGroup(sheetPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(sheetChoiceDesc)
						.addComponent(sheetList, 130, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				)
		);
		
		sheetPanelLayout.setVerticalGroup(
				sheetPanelLayout.createSequentialGroup()
					.addComponent(sheetChoiceDesc)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(sheetList)
		);
		
		
		
		globalLayout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(globalLayout);
		globalLayout.setAutoCreateGaps(true);
		globalLayout.setAutoCreateContainerGaps(true);
		
		globalLayout.setHorizontalGroup(
			globalLayout.createSequentialGroup()
				.addGroup(globalLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addGroup(globalLayout.createSequentialGroup()
						.addComponent(sheetChoicePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(rangeChoicePanel)
						.addComponent(optionsChoicePanel)
					)
					.addComponent(buttonsPanel)
				)
		);
		
		globalLayout.setVerticalGroup(
			globalLayout.createSequentialGroup()
				.addGroup(globalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
					.addComponent(sheetChoicePanel)
					.addComponent(rangeChoicePanel)
					.addComponent(optionsChoicePanel)
				)
				.addComponent(buttonsPanel)
		);
		
		optionsPanelLayout.linkSize(SwingConstants.HORIZONTAL, showUniques, performanceInput);
		globalLayout.linkSize(SwingConstants.VERTICAL, sheetChoicePanel, rangeChoicePanel, optionsChoicePanel);
	}

}
