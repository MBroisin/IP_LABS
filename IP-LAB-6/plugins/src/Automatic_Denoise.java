
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

import ij.plugin.PlugIn;

public class Automatic_Denoise implements PlugIn {

	public void run(String args) {
		ImageAccess in = new ImageAccess();
		ImageAccess out = new Code().denoiseAuto(in);
		out.show("Denoised image of " + in.getTitle());
	}
}
