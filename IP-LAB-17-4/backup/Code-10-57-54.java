import ij.IJ;

public class Code {

	private double[] gaussian(double sigma) {
		int size = (int)(Math.ceil(sigma*3.0)*2.0 + 1.0);
		double kernel[] = new double[size];
		// TODO: add your code here
		double T;
		for (int k=0; k<size; k++){
			T = k*6*sigma/size - 3*sigma;
			kernel[k] = (1/(Math.sqrt(2*Math.PI)*sigma)*Math.exp(-(T*T)/(2*sigma*sigma)));
			
		}
		return kernel;
	}
	
	private double[] gaussian1(double sigma) {
		int size = (int)(Math.ceil(sigma*3.0)*2.0 + 1.0);
		double kernel[] = new double[size];
		// TODO: add your code here
		double T;
		for (int k=0; k<size; k++){
			T = k*6*sigma/size - 3*sigma;
			kernel[k] = (1/(Math.sqrt(2*Math.PI)*sigma)*Math.exp(-(T*T)/(2*sigma*sigma)))*(-T/(sigma*sigma));
			
		}
		return kernel;
	}
	
	private double[] gaussian2(double sigma) {
		int size = (int)(Math.ceil(sigma*3.0)*2.0 + 1.0);
		double kernel[] = new double[size];
		// TODO: add your code here
		double T;
		for (int k=0; k<size; k++){
			T = k*6*sigma/size - 3*sigma;
			kernel[k] = (1/(Math.sqrt(2*Math.PI)*sigma)) * ( (Math.exp(-(T*T)/(2*sigma*sigma)))*(-T/(sigma*sigma))*(-T/(sigma*sigma)) -  (Math.exp(-(T*T)/(2*sigma*sigma)))/(sigma*sigma));
			
		}
		return kernel;
	}	

	public double[][] kernel(double sigmaX, int orderX, double sigmaY, int orderY) {
		double kernelX[];
		switch(orderX) {
			case 1 : kernelX = gaussian1(sigmaX); break;
			case 2 : kernelX = gaussian2(sigmaX); break;
			default : kernelX = gaussian(sigmaX); break;
		}
		double kernelY[];
		switch(orderY) {
			case 1 : kernelY = gaussian1(sigmaY); break;
			case 2 : kernelY = gaussian2(sigmaY); break;
			default : kernelY = gaussian(sigmaY); break;
		}
		
		double kernel[][] = new double[kernelX.length][kernelY.length];
		for(int j=0; j<kernelY.length; j++) 
		for(int i=0; i<kernelX.length; i++)
			kernel[i][j] = kernelX[i] * kernelY[j];
		return kernel;
	}

	public ImageAccess correlate(ImageAccess in, double sigma, int orderX, int orderY) {
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		double b[][];
		int size = (int)(Math.ceil(sigma*3.0)*2.0 + 1.0);
		double pixelvalue;
		double gaussiankernel[][];
		gaussiankernel = kernel(sigma, orderX, sigma, orderY);
		for (int i=0; i<in.nx; i++){
			for (int j=0; j<in.ny; j++){
				b = in.getNeighborhood(i, j, size, size);
				pixelvalue = 0;
				for (int ii=0; ii<size; ii++){
					for (int jj=0; jj<size; jj++){
						pixelvalue = pixelvalue + b[ii][jj]*gaussiankernel[ii][jj];
					}
				}
				out.putPixel(i, j, pixelvalue);
			}
		}
		return out; // TODO: change the outputs
	}

	
	public ImageAccess detectVerticalEdges(ImageAccess in, double sigma, double tL, double tH) {
		// TODO: add your code here
		ImageAccess out = new ImageAccess(in.nx, in.ny);
		double b[][];
		
		for (int i=0; i<in.nx; i++){
			for (int j=0; j<in.ny; j++){
				b = in.getNeighborhood(i, j, 3, 1);
				if (b[0][0] < 0 && b[2][0] > 0 || b[0][0] > 0 && b[2][0] < 0){
					out.putPixel(i, j, Math.abs(b[0][0]-b[2][0]));
				} else {
					out.putPixel(i, j, 0.0);
				}
			}
		}
		return out; // TODO: change the outputs
	}

	public ImageAccess detectSpots(ImageAccess in, double sigma, double threshold) {
		// TODO: add your code here
		return in; // TODO: change the outputs
	}

	public ImageAccess[] gradient(ImageAccess in, double sigma) {
		ImageAccess gradient[] = new ImageAccess[3];
		// TODO: add your code here
		return gradient;
	}	

	public ImageAccess edges(ImageAccess in, double sigma, double tL, double tH) {
		ImageAccess[] grad = gradient(in, sigma);
		ImageAccess nms = applyNonMaximumSuppression(grad);
		nms.show("NMS");
		ImageAccess out = HysteresisThreshold.run(nms, tL, tH);
		return out;
	}
	
	public ImageAccess[] hessian(ImageAccess in, double sigma) {
		ImageAccess gradient[] = new ImageAccess[3];
		// TODO: add your code here
		return gradient;
	}

	public ImageAccess ridges(ImageAccess in, double sigma, double tL, double tH) {
		ImageAccess[] hessian = hessian(in, sigma);
		ImageAccess nms = applyNonMaximumSuppression(hessian);
		nms.show("NMS");
		ImageAccess out = HysteresisThreshold.run(nms, tL, tH);
		return out;
	}
	
	private ImageAccess applyNonMaximumSuppression(ImageAccess in[]) {
		ImageAccess m = in[0];
		ImageAccess ux = in[1];
		ImageAccess uy = in[2];
		// TODO: add your code here
		return m; // TODO: change the outputs
	}
}
