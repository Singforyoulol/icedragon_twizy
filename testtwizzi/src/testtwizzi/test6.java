package testtwizzi;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class test6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
		float[] radius =new float[1];
		Point center = new Point();
		Mat m=Highgui.imread("Billard_Balls.jpg");
		List<MatOfPoint> contours =fonction.DetecContou(m);
		List<Mat> ListImg = new ArrayList<Mat>() ;
		
		//extrait les cercles rouges
		for(int c=0;c<contours.size();c++) {
			MatOfPoint contour=contours.get(c);
			double contourArea = Imgproc.contourArea(contour);
			matOfPoint2f.fromList(contour.toList());
			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius);
			if ((contourArea/(Math.PI*radius[0]))>=8) {
				Core.circle(m, center, (int)radius[0], new Scalar(0,255,0), 2);
				Rect rect=Imgproc.boundingRect(contour);
				Core.rectangle(m, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(0,255,0),2);
				Mat tmp=m.submat(rect.y, rect.y+rect.height, rect.x, rect.x+rect.width);
				Mat Ball=Mat.zeros(tmp.size(), tmp.type());
				tmp.copyTo(Ball);
				fonction.ImShow("Ball", Ball);
				ListImg.add(Ball);
			}
		}
		
		//reconnaissance de l'objet
		Mat sroadSign = Highgui.imread("Ball_three.png");
		Mat graySign = new Mat(sroadSign.rows(),sroadSign.cols(),sroadSign.type());
		Imgproc.cvtColor(sroadSign, graySign, Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(graySign, graySign, 0, 255, Core.NORM_MINMAX);
		
		FeatureDetector orbDetector =FeatureDetector.create(FeatureDetector.ORB);
		DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		
		MatOfKeyPoint signKeypoints = new MatOfKeyPoint();
		orbDetector.detect(graySign, signKeypoints);
		
		Mat signDescriptor = new Mat(sroadSign.rows(),sroadSign.cols(),sroadSign.type());
		orbExtractor.compute(graySign, signKeypoints, signDescriptor);
		
		Mat object = new Mat();
		Mat sObject = new Mat();
		
		for(int i=0;i<ListImg.size();i++) {
			object = ListImg.get(i);
			Imgproc.resize(object, sObject, sroadSign.size());
			
			Mat grayObject = new Mat(sObject.rows(),sObject.cols(),sObject.type());
			Imgproc.cvtColor(sObject, grayObject, Imgproc.COLOR_BGRA2GRAY);
			Core.normalize(grayObject, grayObject, 0, 255, Core.NORM_MINMAX);
			
			MatOfKeyPoint objectKeypoints = new MatOfKeyPoint();
			orbDetector.detect(grayObject, objectKeypoints);
			
			Mat objectDescriptor = new Mat(object.rows(),object.cols(),object.type());
			orbExtractor.compute(grayObject, objectKeypoints, objectDescriptor);
			
			MatOfDMatch matchs = new MatOfDMatch();
			DescriptorMatcher matcher =DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
			matcher.match(objectDescriptor, signDescriptor, matchs);
			System.out.println(i);
			System.out.println(matchs.dump());
			Mat matchedImage = new Mat(sroadSign.rows(),sroadSign.cols()*2,sroadSign.type());
			Features2d.drawMatches(sObject, objectKeypoints, sroadSign, signKeypoints, matchs, matchedImage);
			fonction.ImShow("match "+i, matchedImage);
		}
	}

}
