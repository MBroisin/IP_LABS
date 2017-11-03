//=========================================================================
//
// Project: IP-LAB
// 
// Date   : 22 November 2013
//
// Author : Daniel Sage
// Biomedical Imaging Group (BIG)
// Ecole Polytechnique Federale de Lausanne (EPFL)
// Lausanne, Switzerland
//
// Information: http://bigwww.epfl.ch/iplab/
//
// Reference: D. Sage, M. Unser, 
// "Teaching Image-Processing Programming in Java"
// IEEE Signal Processing Magazine, vol. 20, pp. 43-52, November 2003.
// no. 2, pp. 415-427, February 2010.
//
// Paper available: http://bigwww.epfl.ch/publications/sage0303.html
//
// Conditions of use:
// You'll be free to use this software for research purposes, but you
// should not redistribute it without our consent. In addition, we 
// expect you to include a citation or acknowledgment whenever 
// you present or publish results that are based on it.
//
//==========================================================================


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ij.IJ;
import ij.gui.GUI;

public class Wavelets_Daubechies extends JDialog implements ActionListener {

	/**
	* Constructor.
	*/
	public Wavelets_Daubechies() {
		super(new JFrame(), "Wavelets");
		
		if (IJ.versionLessThan("1.21a"))
			return;
			
		doDialog();
	}

	
	/**
	* Build the dialog box.
	*/
	private GridBagLayout 		layout				= new GridBagLayout();
	private GridBagConstraints 	constraint			= new GridBagConstraints();
	private JComboBox			choiceScale			= new JComboBox(new String[]{"1", "2", "3", "4", "5", "6", "7", "8"});
	private JRadioButton 		rb0					= new JRadioButton("Daubechies 0 (Identity)", false);
	private JRadioButton 		rb1					= new JRadioButton("Daubechies 1 (Haar)", true);
	private JRadioButton 		rb2					= new JRadioButton("Daubechies 2 (db2)", false);
	private JButton 			bnClose				= new JButton("Close");
	private JButton 			bnRun				= new JButton(" Run All ");
	private JButton 			bnAnalysis			= new JButton(" Analysis ");
	private JButton 			bnSynthesis			= new JButton(" Synthesis ");
	private JButton 			bnProcess			= new JButton(" Process ");
	private JTextField			txtThreshold		= new JTextField("10.0", 5);
	private JRadioButton		chkSoft				= new JRadioButton("Soft Threshold");
	private JRadioButton		chkLow				= new JRadioButton("Keep Lowpass Band");
	private JRadioButton		chkNone				= new JRadioButton("Identity");
	
	private void doDialog() {

		choiceScale.setSelectedIndex(2);
		chkNone.setSelected(true);
		ButtonGroup group1 = new ButtonGroup();
		group1.add(rb0);
		group1.add(rb1);
		group1.add(rb2);
		
		ButtonGroup group = new ButtonGroup();
		group.add(chkNone);
		group.add(chkSoft);
		group.add(chkLow);
		
		// Panel parameters	
		JPanel pnParameters = new JPanel(layout);
		pnParameters.setBorder(BorderFactory.createTitledBorder("Operator"));
	 	addComponent(pnParameters, 0, 0, 2, 1, 3, rb0);
	 	addComponent(pnParameters, 1, 0, 2, 1, 3, rb1);
	 	addComponent(pnParameters, 2, 0, 2, 1, 3, rb2);
		
	 	addComponent(pnParameters, 3, 0, 1, 1, 3, new JLabel("Scale (n)"));
	 	addComponent(pnParameters, 3, 1, 1, 1, 3, choiceScale);
		
		JPanel pnCoefficients = new JPanel(layout);
		pnCoefficients.setBorder(BorderFactory.createTitledBorder("Coefficients processing"));
		addComponent(pnCoefficients, 2, 1, 2, 1, 3, chkNone);
		addComponent(pnCoefficients, 3, 1, 1, 1, 3, chkSoft);
		addComponent(pnCoefficients, 4, 1, 2, 1, 3, chkLow);
		addComponent(pnCoefficients, 3, 2, 1, 1, 3, txtThreshold);
				
		JPanel pn = new JPanel(layout);
		addComponent(pn, 0, 0, 1, 5, 3, pnParameters);	
	 	addComponent(pn, 0, 1, 1, 1, 3, bnClose);
	 	addComponent(pn, 2, 1, 1, 1, 3, bnAnalysis);
	 	addComponent(pn, 3, 1, 1, 1, 3, bnProcess);
	 	addComponent(pn, 4, 1, 1, 1, 3, bnSynthesis);
		
		addComponent(pn, 5, 0, 1, 5, 3, pnCoefficients);	
	 	addComponent(pn, 7, 1, 1, 1, 3, new JLabel(""));
		addComponent(pn, 8, 1, 1, 1, 3, bnRun);	

		JPanel pn1 = new JPanel(layout);
		addComponent(pn1, 1, 0, 1, 5, 12, pn);	
		
		// Add Listeners
		bnClose.addActionListener(this);
		bnRun.addActionListener(this);
		bnAnalysis.addActionListener(this);
		bnSynthesis.addActionListener(this);
		bnProcess.addActionListener(this);
		
		// Building the main panel
		add(pn1);
		pack();
		setResizable(false);
		GUI.center(this);
		setVisible(true);

	}

	/**
	* Add a component in a panel in the northeast of the cell.
	*/
	private void addComponent(JPanel pn, int row, int col, int width, int height, int space, JComponent comp) {
	    constraint.gridx = col;
	    constraint.gridy = row;
	    constraint.gridwidth = width;
	    constraint.gridheight = height;
	    constraint.anchor = GridBagConstraints.NORTHWEST;
	    constraint.insets = new Insets(space, space, space, space);
		constraint.fill = GridBagConstraints.HORIZONTAL;
	    layout.setConstraints(comp, constraint);
	    pn.add(comp);
	}

	/**
	* Implements the actionPerformed for the ActionListener.
	*/
	public synchronized  void actionPerformed(ActionEvent e) {
		if (e.getSource() == bnClose) {
			dispose();
			return;
		}
		ImageAccess source = new ImageAccess();
		// Get the parameters
		int transform = (rb0.isSelected() == true ? 0 : (rb1.isSelected() == true ? 1: 2));
		int scale = Integer.parseInt((String)choiceScale.getSelectedItem());
		double threshold = getDoubleValue(txtThreshold, Double.MIN_VALUE, 0.0, Double.MAX_VALUE);
			
		int nx = source.getWidth();
		int ny = source.getHeight();
		if (nx / Math.pow(2, scale) < 1.0) {
			IJ.log("Too many scales.");
			return;
		}
		if (nx != ny) {
			IJ.log("Only process square image.");
			return;
		}
		String title = "";
		if (e.getSource() == bnRun) {
			ImageAccess coef = new Code().analyze(source, scale, transform);
			ImageAccess output = null;
			if (chkSoft.isSelected()) {
				title = "T=" + threshold;
				output = new Code().doSoftThreshold(coef, threshold);
			}
			if (chkLow.isSelected()) {
				title = "L";
				output = new Code().keepLowpass(coef, scale);
			}
			if (chkNone.isSelected()) {
				output = new Code().identity(coef, scale);
			}
			ImageAccess reconst = new Code().synthesize(output, scale, transform);
			reconst.show("Reconstruction (" + scale + ") db" + transform + " " + title);
		}
		else if (e.getSource() == bnAnalysis) {
			ImageAccess coef = new Code().analyze(source, scale, transform);
			coef.show("Analysis (" + scale + ") db" + transform);
		}
		else if (e.getSource() == bnProcess) {
			ImageAccess output = null;
			if (chkSoft.isSelected()) {
				title = "T=" + threshold;
				output = new Code().doSoftThreshold(source, threshold);
			}
			if (chkLow.isSelected()) {
				title = "L";
				output = new Code().keepLowpass(source, scale);
			}
			if (chkNone.isSelected()) {
					output = new Code().identity(source, scale);
			}
			output.show("Processing " + title );
		}
		else if (e.getSource() == bnSynthesis) {
			ImageAccess reconst = new Code().synthesize(source, scale, transform);
			reconst.show("Synthesis (" + scale + ") db" + transform);
		}
		notify();
	}
		
		
	/**
	* Get a double value from a TextField between minimal and maximal values.
	*/
	private double getDoubleValue(JTextField text, double mini, double defaut, double maxi) {
		double d;
		try {
			String s = text.getText();
			if (s.charAt(0) == '-') { 
				s = s.substring(1);
			}	
			d = (new Double(s)).doubleValue();
			if (d < mini)  
				text.setText( "" + mini);
			if (d > maxi)  
				text.setText( "" + maxi);
		}
		
		catch (Exception e) {
			if (e instanceof NumberFormatException) 
				text.setText( "" + defaut);
		}
		d = (new Double(text.getText())).doubleValue();
		return d;
	}
}


