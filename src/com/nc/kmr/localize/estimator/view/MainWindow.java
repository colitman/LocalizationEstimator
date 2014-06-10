package com.nc.kmr.localize.estimator.view;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.nc.kmr.localize.estimator.event.BrowseButtonListener;
import com.nc.kmr.localize.estimator.event.CreditsPresenter;
import com.nc.kmr.localize.estimator.filefilter.PropertiesFileFilter;
import com.nc.kmr.localize.estimator.filefilter.XLSFileFilter;
import com.nc.kmr.localize.estimator.filefilter.XLSXFileFilter;

public class MainWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel = new JPanel();
	private JPanel descPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JButton browseButton = new JButton("Browse file...");
	private JFileChooser chooser = new JFileChooser();
	
	private JLabel line1 = new JLabel("Localization Estimator is intended to help translators estimate translation time for different pieces of text");
	private JLabel line2 = new JLabel("Supported file formats are as follows:");
	private JLabel excel = new JLabel("MS Excel");
	private JLabel xls = new JLabel("- .xls");
	private JLabel xlsx = new JLabel("- .xlsx");
	private JLabel lastLine = new JLabel("Developed in NetCracker Knowledge Management and Research department");
	

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
		
		chooser.addChoosableFileFilter(new XLSFileFilter());
		chooser.addChoosableFileFilter(new XLSXFileFilter());
		chooser.addChoosableFileFilter(new PropertiesFileFilter());
		browseButton.addActionListener(new BrowseButtonListener(chooser));

		aboutMenu.add(credits);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);
		add(mainPanel, BorderLayout.CENTER);

		layoutComponents();
	}

	private void layoutComponents() {
		
		GroupLayout descLayout = new GroupLayout(descPanel);
		descPanel.setLayout(descLayout);
		descLayout.setAutoCreateContainerGaps(true);
		descLayout.setAutoCreateGaps(true);
		
		descLayout.setHorizontalGroup(
			descLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(line1)
				.addComponent(line2)
				.addGroup(descLayout.createSequentialGroup()
					.addPreferredGap(line2, excel, LayoutStyle.ComponentPlacement.INDENT)
					.addComponent(excel)
				)
				.addGroup(descLayout.createSequentialGroup()
					.addPreferredGap(excel, xls, LayoutStyle.ComponentPlacement.INDENT)
					.addPreferredGap(excel, xls, LayoutStyle.ComponentPlacement.INDENT)
					.addComponent(xls)
				)
				.addGroup(descLayout.createSequentialGroup()
					.addPreferredGap(xls, xlsx, LayoutStyle.ComponentPlacement.INDENT)
					.addPreferredGap(xls, xlsx, LayoutStyle.ComponentPlacement.INDENT)
					.addComponent(xlsx)
				)
				.addComponent(lastLine)
		);
		
		descLayout.setVerticalGroup(
			descLayout.createSequentialGroup()
				.addComponent(line1)
				.addComponent(line2)
				.addComponent(excel)
				.addComponent(xls)
				.addGap(0)
				.addComponent(xlsx)
				.addComponent(lastLine)
		);
		
		
		
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
