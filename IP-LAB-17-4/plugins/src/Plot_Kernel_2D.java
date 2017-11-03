//=========================================================================================
//
// Project: IP-LAB Developer Tools 2016 - Teaching Image-Processing Programming in Java
//
// Author : Daniel Sage, Biomedical Imaging Group (BIG), http://bigwww.epfl.ch/
//
// Organization: Ecole Polytechnique Federale de Lausanne (EPFL), Lausanne, Switzerland
//
// Information: http://bigwww.epfl.ch/iplab/
//
// Reference: D. Sage, M. Unser, "Teaching Image-Processing Programming in Java"
// IEEE Signal Processing Magazine, vol. 20, pp. 43-52, November 2003.
// http://bigwww.epfl.ch/publications/sage0303.html
//
// Conditions of use: You'll be free to use this software for research purposes, but you 
// should not redistribute it without our consent. In addition, we expect you to include a
// citation or acknowledgment whenever you present or publish results that are based on it.
//
//=========================================================================================

import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

public class Plot_Kernel_2D implements PlugIn {

	public void run(String arg) {
		GenericDialog gd = new GenericDialog("Test Kernel 2D");
		gd.addNumericField("Sigma in X", 3, 1);
		gd.addNumericField("Sigma in Y", 3, 1);
		gd.addChoice("Order of derivative in X", new String[] {"0", "1", "2"}, "0");
		gd.addChoice("Order of derivative in Y", new String[] {"0", "1", "2"}, "0");
		gd.showDialog();
		if (gd.wasCanceled()) {
			return;
		}
		double sigmaX = gd.getNextNumber();
		double sigmaY = gd.getNextNumber();
		int orderX = Integer.parseInt(gd.getNextChoice());
		int orderY = Integer.parseInt(gd.getNextChoice());
		
		double kernel[][] = (new Code()).kernel(sigmaX, orderX, sigmaY, orderY);
		ImageAccess k = new ImageAccess(kernel);
		k.show("Mask (" + orderX + ", " + orderY + ")");

	}
}
