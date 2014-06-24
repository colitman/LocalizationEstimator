package com.nc.kmr.localize.estimator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import com.nc.kmr.localize.estimator.event.common.BrowseButtonListener;
import com.nc.kmr.localize.estimator.event.common.CreditsPresenter;
import com.nc.kmr.localize.estimator.filefilter.AllSupportedFileFilter;
import com.nc.kmr.localize.estimator.filefilter.IniFileFilter;
import com.nc.kmr.localize.estimator.filefilter.PPTFileFilter;
import com.nc.kmr.localize.estimator.filefilter.PropertiesFileFilter;
import com.nc.kmr.localize.estimator.filefilter.XLSFileFilter;
import com.nc.kmr.localize.estimator.filefilter.XLSXFileFilter;

public class MainWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel = new JPanel();
	private JPanel descPanel = new JPanel();
	private JPanel excelFilesPanel = new JPanel();
	private JPanel ppFilesPanel = new JPanel();
	private JPanel keyValueFilesPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JButton browseButton = new JButton("Browse file...");
	private JFileChooser chooser = new JFileChooser();
	
	private JLabel line1 = new JLabel("Localization Estimator is intended to help translators estimate translation time for different pieces of text.");
	private JLabel line2 = new JLabel("Supported file formats are as follows:");
	private JLabel xls = new JLabel("- *.xls");
	private JLabel xlsx = new JLabel("- *.xlsx");
	private JLabel ppt = new JLabel("- *.ppt");
//	private JLabel pptx = new JLabel("- *.pptx");
	private JLabel properties = new JLabel("- *.properties");
	private JLabel ini = new JLabel("- *.ini");
	private JLabel lng = new JLabel("- *.lng");
	private JLabel lastLine = new JLabel("Developed in NetCracker Knowledge Management and Research department.");
	

	@Override
	public void run() {
		createGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("KMR Localization Estimator");
		setLocationRelativeTo(null);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		pack();
		setVisible(true);
		setMinimumSize(getPreferredSize());
	}

	private void createGUI() {
		JMenuBar menuBar = new JMenuBar();
		JMenu aboutMenu = new JMenu("About");
		JMenuItem credits = new JMenuItem("Credits");
		credits.addActionListener(new CreditsPresenter());
		
		FileFilter defFilter = new AllSupportedFileFilter();
		
		chooser.addChoosableFileFilter(defFilter);
		chooser.addChoosableFileFilter(new XLSFileFilter());
		chooser.addChoosableFileFilter(new XLSXFileFilter());
		chooser.addChoosableFileFilter(new PPTFileFilter());
		chooser.addChoosableFileFilter(new PropertiesFileFilter());
		chooser.addChoosableFileFilter(new IniFileFilter());
		chooser.setFileFilter(defFilter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(true);
		browseButton.addActionListener(new BrowseButtonListener(chooser));

		aboutMenu.add(credits);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);
		add(mainPanel, BorderLayout.CENTER);
		
		excelFilesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "MS Excel:"));
		ppFilesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "MS PowerPoint:"));
		keyValueFilesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Key-Value:"));

		layoutComponents();
	}

	private void layoutComponents() {
		
		GroupLayout excelFilesPanelLayout = new GroupLayout(excelFilesPanel);
		excelFilesPanel.setLayout(excelFilesPanelLayout);
		excelFilesPanelLayout.setAutoCreateContainerGaps(true);
		excelFilesPanelLayout.setAutoCreateGaps(true);
		
		excelFilesPanelLayout.setHorizontalGroup(
			excelFilesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(xls)
				.addComponent(xlsx)
		);
		
		excelFilesPanelLayout.setVerticalGroup(
			excelFilesPanelLayout.createSequentialGroup()
				.addComponent(xls)
				.addGap(0)
				.addComponent(xlsx)
		);
		
		
		
		
		GroupLayout ppFilesPanelLayout = new GroupLayout(ppFilesPanel);
		ppFilesPanel.setLayout(ppFilesPanelLayout);
		ppFilesPanelLayout.setAutoCreateContainerGaps(true);
		ppFilesPanelLayout.setAutoCreateGaps(true);
		
		ppFilesPanelLayout.setHorizontalGroup(
				ppFilesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(ppt)
//				.addComponent(pptx)
		);
		
		ppFilesPanelLayout.setVerticalGroup(
			ppFilesPanelLayout.createSequentialGroup()
				.addComponent(ppt)
//				.addGap(0)
//				.addComponent(pptx)
		);
		
		
		
		GroupLayout keyValueFilesPanelLayout = new GroupLayout(keyValueFilesPanel);
		keyValueFilesPanel.setLayout(keyValueFilesPanelLayout);
		keyValueFilesPanelLayout.setAutoCreateContainerGaps(true);
		keyValueFilesPanelLayout.setAutoCreateGaps(true);
		
		keyValueFilesPanelLayout.setHorizontalGroup(
				keyValueFilesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(properties)
				.addComponent(ini)
				.addComponent(lng)
		);
		
		keyValueFilesPanelLayout.setVerticalGroup(
				keyValueFilesPanelLayout.createSequentialGroup()
				.addComponent(properties)
				.addGap(0)
				.addComponent(ini)
				.addGap(0)
				.addComponent(lng)
		);
		
		
		
		GroupLayout descLayout = new GroupLayout(descPanel);
		descPanel.setLayout(descLayout);
		descLayout.setAutoCreateContainerGaps(true);
		descLayout.setAutoCreateGaps(true);
		
		descLayout.setHorizontalGroup(
			descLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(line1)
				.addComponent(line2)
				.addGroup(descLayout.createSequentialGroup()
					.addComponent(excelFilesPanel)
					.addComponent(ppFilesPanel)
					.addComponent(keyValueFilesPanel)
				)
				.addComponent(lastLine)
		);
		
		descLayout.setVerticalGroup(
			descLayout.createSequentialGroup()
				.addComponent(line1)
				.addComponent(line2)
				.addGroup(descLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(excelFilesPanel)
					.addComponent(ppFilesPanel)
					.addComponent(keyValueFilesPanel)
				)
				.addComponent(lastLine)
		);
		
		descLayout.linkSize(SwingConstants.VERTICAL, excelFilesPanel, ppFilesPanel, keyValueFilesPanel);
		descLayout.linkSize(SwingConstants.HORIZONTAL, excelFilesPanel, ppFilesPanel, keyValueFilesPanel);
		
		
		GroupLayout buttonLayout = new GroupLayout(buttonPanel);
		buttonPanel.setLayout(buttonLayout);
		buttonLayout.setAutoCreateContainerGaps(true);
		buttonLayout.setAutoCreateGaps(true);
		
		buttonLayout.setHorizontalGroup(
			buttonLayout.createSequentialGroup()
				.addComponent(browseButton)
		);
		
		buttonLayout.setVerticalGroup(
			buttonLayout.createSequentialGroup()
				.addComponent(browseButton)
		);
		
		
		GroupLayout mainLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainLayout);
		mainLayout.setAutoCreateContainerGaps(true);
		mainLayout.setAutoCreateGaps(true);
		
		mainLayout.setHorizontalGroup(
			mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(descPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(buttonPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		
		mainLayout.setVerticalGroup(
			mainLayout.createSequentialGroup()
				.addComponent(descPanel)
				.addComponent(buttonPanel)
		);
		
	}
}
