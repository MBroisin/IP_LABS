//=========================================================================================//// Project: IP-LAB Developer Tools 2016 - Teaching Image-Processing Programming in Java//// Author : Daniel Sage, Biomedical Imaging Group (BIG), http://bigwww.epfl.ch///// Organization: Ecole Polytechnique Federale de Lausanne (EPFL), Lausanne, Switzerland//// Information: http://bigwww.epfl.ch/iplab///// Reference: D. Sage, M. Unser, "Teaching Image-Processing Programming in Java"// IEEE Signal Processing Magazine, vol. 20, pp. 43-52, November 2003.// http://bigwww.epfl.ch/publications/sage0303.html//// Conditions of use: You'll be free to use this software for research purposes, but you // should not redistribute it without our consent. In addition, we expect you to include a// citation or acknowledgment whenever you present or publish results that are based on it.////=========================================================================================public class HysteresisThreshold extends Object {	private static final double STRONG = 250.0;	private static final double WEAK = 170.0;	private static final double CANDIDATE = 128.0;	private static final double BACKGROUND = 0.0;	private static final double FOREGROUND = 255.0;	/**	 * Applies a hysteresis threshold to an image.	 * 	 * The method call a recursive method trackEdge to track the contour	 * according the thresholds.	 * 	 * @return output image	 */	static public ImageAccess run(ImageAccess input, double thresholdLow, double thresholdHigh) {		double tl = thresholdLow;		double th = thresholdHigh;		int nx = input.getWidth();		int ny = input.getHeight();		ImageAccess out = new ImageAccess(nx, ny);		double value;		for (int x = 0; x < nx; x++) {			for (int y = 0; y < ny; y++) {				value = input.getPixel(x, y);				if (value > th) {					out.putPixel(x, y, STRONG);				}				else {					if (value > tl) {						out.putPixel(x, y, CANDIDATE);					}					else {						out.putPixel(x, y, BACKGROUND);					}				}			}		}		for (int x = 1; x < nx - 1; x++) {			for (int y = 1; y < ny - 1; y++) {				value = out.getPixel(x, y);				if (value == STRONG) {					 double[][] neigh = out.getNeighborhood(x, y, 3, 3);					if (neigh[2][2] == CANDIDATE)						trackEdge(out, x + 1, y + 1);					if (neigh[2][1] == CANDIDATE)						trackEdge(out, x + 1, y);					if (neigh[2][0] == CANDIDATE)						trackEdge(out, x + 1, y - 1);					if (neigh[1][2] == CANDIDATE)						trackEdge(out, x, y + 1);					if (neigh[1][0] == CANDIDATE)						trackEdge(out, x, y - 1);					if (neigh[0][2] == CANDIDATE)						trackEdge(out, x - 1, y + 1);					if (neigh[0][1] == CANDIDATE)						trackEdge(out, x - 1, y);					if (neigh[0][0] == CANDIDATE)						trackEdge(out, x - 1, y - 1);				}			}		}		for (int x = 0; x < nx; x++) {			double col[] = out.getColumn(x);			for (int y = 0; y < ny; y++) {				if (col[y] >= WEAK)					col[y] = FOREGROUND;				else					col[y] = BACKGROUND;			}			out.putColumn(x, col);		}		return out;	}	/*	 * Tracks the edges in an image.	 * 	 * The edges are marked directly in the image.	 * 	 * @param image input image	 * 	 * @param x starting point to track	 * 	 * @param y starting point to track	 */	static private void trackEdge(ImageAccess image, int x, int y) {		int nx = image.getWidth();		int ny = image.getHeight();		if (x < 0)			return;		if (y < 0)			return;		if (x > nx - 1)			return;		if (y > ny - 1)			return;		if (image.getPixel(x, y) != CANDIDATE)			return;		image.putPixel(x, y, WEAK);		double[][] neigh = image.getNeighborhood(x, y, 3, 3);		if (neigh[2][2] == CANDIDATE)			trackEdge(image, x + 1, y + 1);		if (neigh[2][1] == CANDIDATE)			trackEdge(image, x + 1, y);		if (neigh[2][0] == CANDIDATE)			trackEdge(image, x + 1, y - 1);		if (neigh[1][2] == CANDIDATE)			trackEdge(image, x, y + 1);		if (neigh[1][0] == CANDIDATE)			trackEdge(image, x, y - 1);		if (neigh[0][2] == CANDIDATE)			trackEdge(image, x - 1, y + 1);		if (neigh[0][1] == CANDIDATE)			trackEdge(image, x - 1, y);		if (neigh[0][0] == CANDIDATE)			trackEdge(image, x - 1, y - 1);	}}