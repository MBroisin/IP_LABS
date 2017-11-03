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

import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

public class Test_Scaling_Filter implements PlugIn {

	public void run(String args) {
		String labels[] = new String[] {"Daubechies 0 (Identity)", "Daubechies 1 (Haar)", "Daubechies 2 (db2)"};
		GenericDialog dlg = new GenericDialog("Test Perfect Reconstruction 1");
		dlg.addChoice("Order", labels, labels[1]);
		dlg.showDialog();
		String order = dlg.getNextChoice();
		for(int i=0; i<labels.length; i++) {
			if (order.equals(labels[i]))
				new Code().testScalingFilter(new ImageAccess(), i);
		}
		
	}
}