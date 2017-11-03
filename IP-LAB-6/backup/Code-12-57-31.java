import ij.*;

public class Code {

	private ImageAccess convolve(ImageAccess in, double wx[], double wy[]) {
		// TODO add you code here
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		ImageAccess out1 = new ImageAccess(in.nx, in.ny);
		double sum;
		for(int i = 0; i<in.nx; i++){
			for (int j = 0; j<in.ny; j++){
				sum = 0;
				for (int ii = -(wx.length-1)/2; ii<=(wx.length-1)/2; ii++){
					sum = sum + wx[ii+(wx.length-1)/2]*in.getPixel((i+ii)%in.nx, j);
				}
				out1.putPixel(i, j, sum);
			}
		}
		
		for(int i = 0; i<in.nx; i++){
			for (int j = 0; j<in.ny; j++){
				sum = 0;
				for (int ii = -(wy.length-1)/2; ii<=(wy.length-1)/2; ii++){
					sum = sum + wy[ii+(wy.length-1)/2]*out1.getPixel(i, (j+ii)%in.ny);
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
		else if(order == 1){
			double L;
			L = Math.sqrt(2);
			return new double[] {1/L, 1/L, 0};
		} 
		else if(order == 2){
			double L;
			L = Math.sqrt(2)*4;
			double t;
			t = Math.sqrt(3);
			return new double[] {(1-t)/L, (3-t)/L, (3+t)/L, (1+t)/L, 0};
		}
		else 
			throw new ArithmeticException("Unvalid order"); 
	}
	
	public double[] getWaveletFunction(int order) {
		if (order == 0) {
			return new double[] {1};
		}
		// TODO complete for order 1 and 2
		else if(order == 1){
			double L;
			L = Math.sqrt(2);
			return new double[] {-1/L, 1/L, 0};
		} 
		else if(order == 2){
			double L;
			L = Math.sqrt(2)*4;
			double t;
			t = Math.sqrt(3);
			return new double[] {(-1-t)/L, (3+t)/L, (t-3)/L, (1-t)/L, 0};
		}
		else 
			throw new ArithmeticException("Unvalid order"); 
	}
	
	private double[] reflect(double arr[]) {
		int n = arr.length;
		double[] out = new double[n];
		// TODO add you code here
		for (int i = 0; i<n; i++){
			out[i] = arr[n-i-1];
		}
		return out;
	}
	
	public void testScalingFilter(ImageAccess in, int order) {
		// TODO add you code here
		double[] Ha = getScalingFunction(order);
		ImageAccess out = convolve(in, Ha, Ha);
		out.show("Scaling db" + order);
	}
	
	public void testWaveletFilter(ImageAccess in, int order) {
		// TODO add you code here
		double[] Ga = getWaveletFunction(order);
		ImageAccess out = convolve(in, Ga, Ga);
		out.show("Scaling db" + order);
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
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		double[] Ha = getScalingFunction(order);
		double[] Ga = getWaveletFunction(order);
		ImageAccess LL = downsample(convolve(in, Ha, Ha));
		ImageAccess LH = downsample(convolve(in, Ha, Ga));
		ImageAccess HL = downsample(convolve(in, Ga, Ha));
		ImageAccess HH = downsample(convolve(in, Ga, Ga));
		out.putSubImage(0, 0, LL);
		out.putSubImage(in.nx/2, 0, HL);
		out.putSubImage(0, in.ny/2, LH);
		out.putSubImage(in.nx/2, in.ny/2, HH);
		return out;
	}

	public ImageAccess synthesize(ImageAccess in, int n, int order) {
		// TODO add you code here
		int nx = in.getWidth()/((int)Math.pow(2, n-1));
		int ny = in.getHeight()/((int)Math.pow(2, n-1));
		ImageAccess output = in.duplicate();
		for (int i = 0; i < n; i++) {
			ImageAccess sub = output.getSubImage(0, 0, nx, ny);
			sub = synthesize1(sub, order);
			output.putSubImage(0, 0, sub);
			nx = nx * 2;
			ny = ny * 2;
		}
		return output;
	}

	private ImageAccess synthesize1(ImageAccess in, int order) {
		// TODO add you code here
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		double[] Hainv = reflect(getScalingFunction(order));
		double[] Gainv = reflect(getWaveletFunction(order));
		
		ImageAccess LL = convolve(upsample(in.getSubImage(0, 0, in.nx/2, in.ny/2)), Hainv, Hainv);
		ImageAccess HL = convolve(upsample(in.getSubImage(in.nx/2, 0, in.nx/2, in.ny/2)), Gainv, Hainv);
		ImageAccess LH = convolve(upsample(in.getSubImage(0, in.ny/2, in.nx/2, in.ny/2)), Hainv, Gainv);
		ImageAccess HH = convolve(upsample(in.getSubImage(in.nx/2, in.ny/2, in.nx/2, in.ny/2)), Gainv, Gainv);
		
		for (int i = 0; i<in.nx; i++){
			for (int j = 0; j<in.ny; j++){
				out.putPixel(i, j, LL.getPixel(i, j)+LH.getPixel(i, j)+HL.getPixel(i, j)+HH.getPixel(i, j));
			}
		}
		return out;
	}
	
	public ImageAccess downsample(ImageAccess in) {
		// TODO add you code here
		ImageAccess out = new ImageAccess((int)Math.floor(in.nx/2), (int)Math.floor(in.ny/2));
		for (int i=0; i<in.nx; i++){
			for (int j = 0; j<in.ny; j++){
				if (i/2.0 - Math.floor(i/2.0) == 0){
					if (j/2.0 - Math.floor(j/2.0) == 0){
						out.putPixel(i/2, j/2, in.getPixel(i, j));
					}
				}
			}
		}
		return out;
	}
	
	public ImageAccess upsample(ImageAccess in) {
		// TODO add you code here
		ImageAccess out = new ImageAccess(in.nx*2, in.ny*2);
		for (int i=0; i<in.nx; i++){
			for (int j = 0; j<in.ny; j++){
				out.putPixel(2*i, 2*j, in.getPixel(i, j));
			}
		}
		return out;
	}
	public ImageAccess identity(ImageAccess in, int n) {
		// TODO add you code here
		ImageAccess out = in.duplicate();
		return out;
	}

	public ImageAccess keepLowpass(ImageAccess in, int n) {
		// TODO add you code here
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		out.putSubImage(0, 0, in.getSubImage(0, 0, in.nx/((int)Math.pow(2, n)), in.ny/((int)Math.pow(2, n))));
		
		return out;
	}
		
	public ImageAccess doSoftThreshold(ImageAccess in, double threshold) {
		// TODO add you code here
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		for (int i=0; i<in.nx; i++){
			for (int j = 0; j<in.ny; j++){
				out.putPixel(i, j, Math.signum(in.getPixel(i, j))*Math.max(0, Math.abs(in.getPixel(i, j))-threshold));
			}
		}
		return out;
	}

	public ImageAccess denoiseAuto(ImageAccess in) {
		// TODO add you code here
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		out = analyze(in, 3, 2);
		ImageAccess HH = in.getSubImage(in.nx/2, in.ny/2, in.nx/2, in.ny/2);
		double sum;
		sum = 0;
		for (int i = 0; i<in.nx/2; i++){
			for (int j = 0; j<in.ny/2; j++){
				sum = sum + HH.getPixel(i, j);
			}
		}
		double mean;
		mean = sum/(in.nx*in.ny/4);
		
		sum = 0;
		for (int i = 0; i<in.nx/2; i++){
			for (int j = 0; j<in.ny/2; j++){
				sum = sum + (HH.getPixel(i, j)-mean)*(HH.getPixel(i, j)-mean);
			}
		}
		double std;
		std = Math.sqrt(sum/(in.nx*in.ny/4));
		IJ.log("Threshold " + std);
		
		out = doSoftThreshold(out, std);
		out = synthesize(out, 3, 2);
		
		return out;
	}
}
