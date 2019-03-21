package testtwizzi;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class test4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat m=Highgui.imread("circles.jpg");
		Mat hsv_image = Mat.zeros(m.size(), m.type());
		Imgproc.cvtColor(m, hsv_image, Imgproc.COLOR_BGR2HSV);
		Mat threshold_img = new Mat();
		Mat threshold_img1 = new Mat();
		Mat threshold_img2 = new Mat();
		Core.inRange(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255), threshold_img1);
		Core.inRange(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255), threshold_img2);
		Core.bitwise_or(threshold_img1, threshold_img2, threshold_img);
		//Imgproc.GaussianBlur(threshold_img, threshold_img, new Size(9,9), 2,2);
		fonction.ImShow("Cercles rouge",threshold_img);
		int thresh=100;
		Mat canny_output = new Mat();
		List<MatOfPoint> contours =new ArrayList<MatOfPoint>();
		MatOfInt4 hierarchy = new MatOfInt4();
		Imgproc.Canny(threshold_img, canny_output, thresh, thresh*2);
		Imgproc.findContours(canny_output, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		Mat drawing = Mat.zeros(canny_output.size(), CvType.CV_8UC3);
		Random rand = new Random();
		for(int i=0;i<contours.size();i++) {
			Scalar color = new Scalar(rand.nextInt(255-0+1),rand.nextInt(255-0+1),rand.nextInt(255-0+1));
			Imgproc.drawContours(drawing, contours, i, color,1,8,hierarchy,0,new Point());
		}
		fonction.ImShow("contours", drawing);
		
	}

}
