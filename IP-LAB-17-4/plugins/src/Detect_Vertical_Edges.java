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

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

public class Detect_Vertical_Edges implements PlugIn {

	public void run(String arg) {
		ImagePlus imp = WindowManager.getCurrentImage();
		int type = imp.getType();
		if (type != ImagePlus.GRAY8 && type != ImagePlus.GRAY32) {
			IJ.error("Only process the 8-bits or 32-bits image");
			return;
		}
		
		GenericDialog gd = new GenericDialog("Detect Vertical Edges");
		gd.addNumericField("Sigma", 3, 1);
		gd.addNumericField("Threshold Low", 1, 1);
		gd.addNumericField("Threshold High", 2, 1);
		gd.addCheckbox("Display as overlay on the original image", true);
		gd.addMessage("To hide/show the overlay use the ImageJ command");
		gd.addMessage("Menu: Images > Overlay > Hide Overlay or Show Overlay");

		gd.showDialog();
		if (gd.wasCanceled())
			return;

		imp.setOverlay(null);
		double sigma = gd.getNextNumber();	
		double tL = gd.getNextNumber();
		double tH = gd.getNextNumber();

		ImageAccess out = (new Code()).detectVerticalEdges(new ImageAccess(imp), sigma, tL, tH);
		if (gd.getNextBoolean())
			OverlayDetection.show(imp, out);
		else 
			out.show("Detect Vertical Edges (" + sigma + ")");
	}
}

