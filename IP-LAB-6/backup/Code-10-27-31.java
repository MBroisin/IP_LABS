import ij.*;

public class Code {

	private ImageAccess convolve(ImageAccess in, double wx[], double wy[]) {
		// TODO add you code here
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		double sum;
		for(int i = 0; i<in.nx; i++){
			for (int j = 0; j<in.ny; j++){
				sum = 0;
				for (int ii = -(wx.length-1)/2; ii<(wx.length-1)/2; ii++){
					sum = sum + wx[ii+(wx.length-1)/2]*in.getPixel((i-ii)%in.nx, j);
				}
				out.putPixel(i, j, sum);
			}
		}
		return out;
	}
	
	public void testConvolution(ImageAccess in) {
		double[] wx = {-2, -1, 0, 1, 2}; 
		double[] wy = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; 
		ImageAccess s1 = convolve(in, wx, wy);
		s1.show("Test Convolution of " + in.getTitle());
	}
	
	public double[] getScalingFunction(int order) {
		if (order == 0) {
			return new double[] {1};
		}
		// TODO complete for order 1 and 2
		else 
			throw new ArithmeticException("Unvalid order"); 
	}
	
	public double[] getWaveletFunction(int order) {
		if (order == 0) {
			return new double[] {1};
		}
		// TODO complete for order 1 and 2
		else 
			throw new ArithmeticException("Unvalid order"); 
	}
	
	private double[] reflect(double arr[]) {
		int n = arr.length;
		double[] out = new double[n];
		// TODO add you code here
		return out;
	}
	
	public void testScalingFilter(ImageAccess in, int order) {
		// TODO add you code here
		in.show("Scaling db" + order);
	}
	
	public void testWaveletFilter(ImageAccess in, int order) {
		// TODO add you code here
		in.show("Scaling db" + order);
	}
	

	public ImageAccess analyze(ImageAccess image, int n, int order) {
		int nx = image.getWidth();
		int ny = image.getHeight();
		ImageAccess output = image.duplicate();
		for (int i = 0; i < n; i++) {
			ImageAccess sub = output.getSubImage(0, 0, nx, ny);
			sub = analyze1(sub, order);
			output.putSubImage(0, 0, sub);
			nx = nx / 2;
			ny = ny / 2;
		}
		return output;
	}

	private ImageAccess analyze1(ImageAccess in, int order) {
		// TODO add you code here
		return in;
	}

	public ImageAccess synthesize(ImageAccess in, int n, int order) {
		// TODO add you code here
		return in;
	}

	private ImageAccess synthesize1(ImageAccess coef, int order) {
		// TODO add you code here
		return coef;
	}
	
	public ImageAccess downsample(ImageAccess in) {
		// TODO add you code here
		return in;
	}
	
	public ImageAccess upsample(ImageAccess in) {
		// TODO add you code here
		return in;
	}
	public ImageAccess identity(ImageAccess in, int n) {
		// TODO add you code here
		return in;
	}

	public ImageAccess keepLowpass(ImageAccess in, int n) {
		// TODO add you code here
		return in;
	}
		
	public ImageAccess doSoftThreshold(ImageAccess in, double threshold) {
		// TODO add you code here
		return in;
	}

	public ImageAccess denoiseAuto(ImageAccess in) {
		// TODO add you code here
		// IJ.log("Threshold " + threshold);
		return in;
	}
}
