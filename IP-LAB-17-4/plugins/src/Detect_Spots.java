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

public class Detect_Spots implements PlugIn {

	public void run(String arg) {
		ImagePlus imp = WindowManager.getCurrentImage();
		int type = imp.getType();
		if (type != ImagePlus.GRAY8 && type != ImagePlus.GRAY32) {
			IJ.error("Only process the 8-bits or 32-bits image");
			return;
		}

		GenericDialog gd = new GenericDialog("Detect Spot");
		gd.addNumericField("Sigma", 3, 1);
		gd.addNumericField("Threshold", 100, 1);
		gd.addCheckbox("Display as overlay on the original image", true);

		gd.showDialog();
		if (gd.wasCanceled()) {
			return;
		}
		imp.setOverlay(null);
		double sigma = gd.getNextNumber();
		double threshold = gd.getNextNumber();
		
		ImageAccess input = new ImageAccess(imp.getProcessor());
		ImageAccess out = (new Code()).detectSpots(input, sigma, threshold);
		if (out == null)
			return;
		ImageAccess cross = new ImageAccess(out.nx, out.ny);
		for(int i=0; i<out.nx; i++)
		for(int j=0; j<out.ny; j++)
			if (out.getPixel(i, j) != 0) {
				for(int k=-4; k<=4; k++)
					cross.putPixel(i+k, j, 255);
				for(int k=-4; k<=4; k++)
					cross.putPixel(i, j+k, 255);
			}
		if (gd.getNextBoolean())
			OverlayDetection.show(imp, cross);
		else 
			cross.show("Detect Spots (" + sigma + ")");
	}
}
