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

import ij.ImagePlus;
import ij.gui.ImageRoi;
import ij.gui.Overlay;
import ij.process.ColorProcessor;

public class OverlayDetection {

	public static void show(ImagePlus imp, ImageAccess overlay) {
		
		ColorProcessor cp = new ColorProcessor(overlay.nx, overlay.ny);
		byte r[] = (byte[])overlay.createByteProcessor().getPixels();
		byte m = (byte)255;
		for(int k=0; k<r.length; k++)
			r[k] = (r[k] == 0 ? 0 : m);
		byte g[] = new byte[overlay.nx * overlay.ny];
		byte b[] = new byte[overlay.nx * overlay.ny];
		cp.setRGB(r, g, b);
		ImageRoi roi = new ImageRoi(0, 0, cp);
		roi.setZeroTransparent(true);
		roi.setOpacity(1);
		Overlay o = new Overlay();
		o.add(roi);
		imp.setOverlay(o);
		
	}
}
