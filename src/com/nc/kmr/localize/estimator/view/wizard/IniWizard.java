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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.nc.kmr.localize.estimator.FileProcessor;
import com.nc.kmr.localize.estimator.event.common.AnalyzeButtonListener;
import com.nc.kmr.localize.estimator.event.common.LOECalculationOptionListener;
import com.nc.kmr.localize.estimator.event.common.PerformanceInputListener;
import com.nc.kmr.localize.estimator.event.common.PrintValuesToConsoleOptionListener;
import com.nc.kmr.localize.estimator.event.common.SelectAllTargetsActionListener;
import com.nc.kmr.localize.estimator.event.common.TargetsListSelectionListener;
import com.nc.kmr.localize.estimator.event.common.UniquesOptionActionListener;
import com.nc.kmr.localize.estimator.event.common.WizardWindowListener;
import com.nc.kmr.localize.estimator.event.prop.PropertiesScopeInputCaretListener;
import com.nc.kmr.localize.estimator.event.prop.PropertiesScopeOptionListener;
import com.nc.kmr.localize.estimator.impl.analyze.AnalyzerBuilder;
import com.nc.kmr.localize.estimator.view.Wizard;

public class IniWizard extends JFrame implements Wizard {

private static final long serialVersionUID = 1L;
	
	private FileProcessor processor;
	private AnalyzerBuilder builder;
	
	private JRadioButton noScope;
	private JRadioButton includeScope;
	private JRadioButton excludeScope;
	private ButtonGroup scopeGroup;
	private JList<String> categoryList;
	private JScrollPane catScroll;
	private JCheckBox allCategories;
	private JTextField scopeInput;
	private JCheckBox showUniques;
	private JCheckBox calculateLOE;
	private JCheckBox printToConsole;
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
	
	private JLabel targetChoiceLabel;
	private JLabel scopeChoiceLabel;
	private JLabel performanceUnits;


	public IniWizard(FileProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void run() {
		builder = new AnalyzerBuilder();
		createGUI();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Key-Value (ini, lng) file estimator wizard");
		setLocationRelativeTo(null);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		pack();
		setVisible(true);
		setMinimumSize(getPreferredSize());
	}

	private void createGUI() {
		
		targetChoicePanel = new JPanel();
		targetChoicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Categories"));
		targetChoiceLabel = new JLabel("Select a category(-ies) for analyzing:");
		categoryList = new JList<String>(processor.getTargets());
		catScroll = new JScrollPane(categoryList);
		categoryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		categoryList.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
		categoryList.getSelectionModel().addListSelectionListener(new TargetsListSelectionListener(processor, categoryList));
		categoryList.setSelectedIndex(0);
		allCategories = new JCheckBox("Select all categories");
		allCategories.addActionListener(new SelectAllTargetsActionListener(categoryList));
		
		scopeChoicePanel = new JPanel();
		scopeChoicePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Scope"));
		scopeGroup = new ButtonGroup();
		noScope = new JRadioButton("Process all entries");
		includeScope = new JRadioButton("Include only");
		excludeScope = new JRadioButton("Exclude");
		noScope.setActionCommand("NO_SCOPE");
		includeScope.setActionCommand("INCLUDE");
		excludeScope.setActionCommand("EXCLUDE");
		scopeGroup.add(noScope);
		scopeGroup.add(includeScope);
		scopeGroup.add(excludeScope);
		scopeInput = new JTextField();
		scopeChoiceLabel = new JLabel("Specify sample for selecting keys (case-sensitive):");
		scopeInput.addCaretListener(new PropertiesScopeInputCaretListener(processor));
		ActionListener scopeListener = new PropertiesScopeOptionListener(processor, scopeInput, scopeChoiceLabel);
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
						.addComponent(scopeChoiceLabel)
						.addComponent(scopeInput, 130, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				)
		);
		
		scopePanelLayout.setVerticalGroup(
				scopePanelLayout.createSequentialGroup()
					.addComponent(noScope)
					.addComponent(includeScope)
					.addComponent(excludeScope)
					.addComponent(scopeChoiceLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(scopeInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		
		
		
		targetPanelLayout = new GroupLayout(targetChoicePanel);
		targetChoicePanel.setLayout(targetPanelLayout);
		targetPanelLayout.setAutoCreateContainerGaps(true);
		targetPanelLayout.setAutoCreateGaps(true);
		
		targetPanelLayout.setHorizontalGroup(
			targetPanelLayout.createSequentialGroup()
				.addGroup(targetPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(targetChoiceLabel)
						.addComponent(catScroll, 130, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(allCategories)
				)
		);
		
		targetPanelLayout.setVerticalGroup(
				targetPanelLayout.createSequentialGroup()
					.addComponent(targetChoiceLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(catScroll)
					.addComponent(allCategories)
		);
		
		
		globalLayout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(globalLayout);
		globalLayout.setAutoCreateGaps(true);
		globalLayout.setAutoCreateContainerGaps(true);
		
		globalLayout.setHorizontalGroup(
			globalLayout.createSequentialGroup()
				.addGroup(globalLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addGroup(globalLayout.createSequentialGroup()
						.addComponent(targetChoicePanel)
						.addComponent(scopeChoicePanel)
						.addComponent(optionsChoicePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
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
