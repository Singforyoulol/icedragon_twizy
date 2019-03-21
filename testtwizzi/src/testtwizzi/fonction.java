package testtwizzi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class fonction {
	public static void ImShow(String title, Mat img) {
		MatOfByte matOfByte = new MatOfByte();
		Highgui.imencode(".png", img, matOfByte);
		byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			JFrame frame = new JFrame();
			frame.setTitle(title);
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void AffTabDou(double[] D) {
		System.out.print("[ "+D[0]);
		for(int i=1;i<D.length;i++) {
			System.out.print(", "+D[i]);
		}
		System.out.println(" ]");
	}
	
	public static List<MatOfPoint> DetecContou(Mat m){
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
		return contours;
	}
	
	public Mat Resize(Mat imgAResize,Mat imgRefSize) {
		Mat sObject = new Mat();
		Imgproc.resize(imgAResize, sObject, imgRefSize.size());
		return sObject;
	}
}
