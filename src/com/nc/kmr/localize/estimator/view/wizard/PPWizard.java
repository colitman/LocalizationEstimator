package com.nc.kmr.localize.estimator.view.wizard;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.event.common.AnalyzeButtonListener;
import com.nc.kmr.localize.estimator.event.common.LOECalculationOptionListener;
import com.nc.kmr.localize.estimator.event.common.PerformanceInputListener;
import com.nc.kmr.localize.estimator.event.common.UniquesOptionActionListener;
import com.nc.kmr.localize.estimator.event.common.WizardWindowListener;
import com.nc.kmr.localize.estimator.event.excel.RangeInputCaretListener;
import com.nc.kmr.localize.estimator.event.pp.PPTargetListener;
import com.nc.kmr.localize.estimator.event.prop.PropertiesScopeOptionListener;
import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;
import com.nc.kmr.localize.estimator.view.Wizard;

public class PPWizard extends JFrame implements Wizard {

private static final long serialVersionUID = 1L;
	
	private FileProcessor processor;
	private AnalyzerBuilder builder;
	private JCheckBox slideText;
	private JCheckBox slideTitles;
	private JCheckBox slideNotes;
	private JRadioButton noScope;
	private JRadioButton includeScope;
	private JRadioButton excludeScope;
	private ButtonGroup scopeGroup;
	private JTextField rangeInput;
	private JCheckBox showUniques;
	private JCheckBox calculateLOE;
	private JTextField performanceInput;
	private JButton analyzeButton;
	
	
	private GroupLayout globalLayout;
	private GroupLayout targetPanelLayout;
	private GroupLayout scopePanelLayout;
	private GroupLayout optionsPanelLayout;
	private GroupLayout buttonsPanelLayout;
	
	private JPanel targetChoicePanel;
	private JPanel scopeChoicePanel;
	private JPanel optionsChoicePanel;
	private JPanel buttonsPanel;
	
	private JLabel performanceUnits;

	public PPWizard(FileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void run() {
		builder = new AnalyzerBuilder();
		createGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("MS PowerPoint file estimator wizard");
		setLocationRelativeTo(null);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		pack();
		setVisible(true);
		setMinimumSize(getPreferredSize());
	}
	
	private void createGUI() {
		targetChoicePanel = new JPanel();
		targetChoicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Targets"));
		slideText = new JCheckBox("Slide text");
		slideTitles = new JCheckBox("Slide titles");
		slideNotes = new JCheckBox("Slide notes");
		slideText.setActionCommand("TEXT");
		slideTitles.setActionCommand("TITLES");
		slideNotes.setActionCommand("NOTES");
		ActionListener targetListener = new PPTargetListener(processor, slideText, slideTitles, slideNotes);
		slideText.addActionListener(targetListener);
		slideTitles.addActionListener(targetListener);
		slideNotes.addActionListener(targetListener);
		slideText.doClick();
		slideTitles.doClick();
		slideNotes.doClick();
		
		scopeChoicePanel = new JPanel();
		scopeChoicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Pages"));
		scopeGroup = new ButtonGroup();
		noScope = new JRadioButton("All");
		includeScope = new JRadioButton("Include");
		excludeScope = new JRadioButton("Exclude");
		noScope.setActionCommand("NO_SCOPE");
		includeScope.setActionCommand("INCLUDE");
		excludeScope.setActionCommand("EXCLUDE");
		scopeGroup.add(noScope);
		scopeGroup.add(includeScope);
		scopeGroup.add(excludeScope);
		rangeInput = new JTextField();
		rangeInput.addCaretListener(new RangeInputCaretListener(processor));
		ActionListener scopeListener = new PropertiesScopeOptionListener(processor, rangeInput);
		noScope.addActionListener(scopeListener);
		includeScope.addActionListener(scopeListener);
		excludeScope.addActionListener(scopeListener);
		noScope.doClick();
		
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
		
		
		scopePanelLayout = new GroupLayout(scopeChoicePanel);
		scopeChoicePanel.setLayout(scopePanelLayout);
		scopePanelLayout.setAutoCreateContainerGaps(true);
		scopePanelLayout.setAutoCreateGaps(true);
		
		scopePanelLayout.setHorizontalGroup(
				scopePanelLayout.createSequentialGroup()
				.addGroup(scopePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(noScope)
						.addComponent(includeScope)
						.addComponent(excludeScope)
						.addComponent(rangeInput, 130, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				)
		);
		
		scopePanelLayout.setVerticalGroup(
				scopePanelLayout.createSequentialGroup()
					.addComponent(noScope)
					.addComponent(includeScope)
					.addComponent(excludeScope)
					.addComponent(rangeInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		
		
		targetPanelLayout = new GroupLayout(targetChoicePanel);
		targetChoicePanel.setLayout(targetPanelLayout);
		targetPanelLayout.setAutoCreateContainerGaps(true);
		targetPanelLayout.setAutoCreateGaps(true);
		
		targetPanelLayout.setHorizontalGroup(
			targetPanelLayout.createSequentialGroup()
				.addGroup(targetPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(slideText)
						.addComponent(slideTitles)
						.addComponent(slideNotes)
				)
		);
		
		targetPanelLayout.setVerticalGroup(
				targetPanelLayout.createSequentialGroup()
					.addComponent(slideText)
					.addComponent(slideTitles)
					.addComponent(slideNotes)
		);
		
		
		
		globalLayout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(globalLayout);
		globalLayout.setAutoCreateGaps(true);
		globalLayout.setAutoCreateContainerGaps(true);
		
		globalLayout.setHorizontalGroup(
			globalLayout.createSequentialGroup()
				.addGroup(globalLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addGroup(globalLayout.createSequentialGroup()
						.addComponent(targetChoicePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(scopeChoicePanel)
						.addComponent(optionsChoicePanel)
					)
					.addComponent(buttonsPanel)
				)
		);
		
		globalLayout.setVerticalGroup(
			globalLayout.createSequentialGroup()
				.addGroup(globalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE, false)
					.addComponent(targetChoicePanel)
					.addComponent(scopeChoicePanel)
					.addComponent(optionsChoicePanel)
				)
				.addComponent(buttonsPanel)
		);
		
		optionsPanelLayout.linkSize(SwingConstants.HORIZONTAL, showUniques, performanceInput);
		globalLayout.linkSize(SwingConstants.VERTICAL, targetChoicePanel, scopeChoicePanel, optionsChoicePanel);
	}

}
