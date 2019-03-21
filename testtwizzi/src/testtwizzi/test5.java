package testtwizzi;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class test5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat m=Highgui.imread("circles_rectangles.jpg");
		List<MatOfPoint> contours =fonction.DetecContou(m);
		MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
		float[] radius = new float[1];
		Point center =new Point();
		for (int c=0;c<contours.size();c++) {
			MatOfPoint contour = contours.get(c);
			double contourArea = Imgproc.contourArea(contour);
			matOfPoint2f.fromList(contour.toList());
			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius);
			if ((contourArea/(Math.PI*radius[0]*radius[0]))>=0.8) {
				Core.circle(m, center, (int)radius[0],new Scalar(0,255,0), 2);
			}
		}
		fonction.ImShow("detect", m);
		
	}

}
