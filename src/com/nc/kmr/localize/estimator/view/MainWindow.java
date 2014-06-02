package com.nc.kmr.localize.estimator.view;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.nc.kmr.localize.estimator.event.BrowseButtonListener;
import com.nc.kmr.localize.estimator.filefilter.XLSFileFilter;

public class MainWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	@Override
	public void run() {
		createGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Localization Estimator");
		setLocationRelativeTo(null);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		pack();
		setResizable(false);
		setVisible(true);
	}

	private void createGUI() {
		JMenuBar menuBar = new JMenuBar();
		JMenu aboutMenu = new JMenu("About");
		JMenuItem credits = new JMenuItem("Credits");
		//credits.addActionListener(new CreditsPresenter());	//TODO Credits Presenter
		
		JPanel descPanel = new JPanel(new BorderLayout());
		JLabel desc = new JLabel();
		setDescription(desc);
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		JButton browseButton = new JButton("Browse file...");
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new XLSFileFilter());
		browseButton.addActionListener(new BrowseButtonListener(chooser));
		
		buttonPanel.add(browseButton, BorderLayout.CENTER);
		descPanel.add(desc, BorderLayout.CENTER);
		aboutMenu.add(credits);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);
		add(descPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.CENTER);
	}

	private void setDescription(JLabel desc) {
		String description = "<html>" + 
								"<p>Localization Estimator is intended to help translators estimate translation time for different pieces of text</p>" + 
								"<p>" + 
									"Supported file formats are as follows:" + 
									"<ul>" + 
										"<li>" + 
											"MS Excel" + 
											"<ul>" + 
												"<li>.xls</li>" + 
											"</ul>" + 
										"</li>" + 
									"</ul>" + 
								"</p>" + 
								"<p>Developed in NetCracker Knowledge Management and Research department</p>" + 
							"</html>";
		
		desc.setText(description);
	}

}
