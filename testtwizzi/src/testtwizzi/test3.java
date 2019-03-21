package testtwizzi;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class test3 {
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      Mat m= Highgui.imread("hsv.png");
	      Mat output = Mat.zeros(m.size(),m.type());
	      Mat outputf = Mat.zeros(m.size(),m.type());
	      Imgproc.cvtColor(m, output, Imgproc.COLOR_BGR2HSV);
	      fonction.ImShow("hsv", output);

	      fonction.AffTabDou(output.get(1, 1));
	      double[] inter;
	      for(int i=0;i<output.height();i++) {
	    	  for(int j=0;j<output.width();j++) {
	    		  inter = output.get(i, i);
	    		  inter[1]=0;
	    		  output;
	    	  }
	      }
	      Imgproc.cvtColor(output, outputf, Imgproc.COLOR_HSV2BGR);
	      fonction.ImShow("gris", outputf);
	}
}
