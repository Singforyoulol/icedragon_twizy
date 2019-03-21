package testtwizzi;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class test7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat sroadSign = Highgui.imread("Ball_13.png");
		Mat object = Highgui.imread("bgr.png");
		Mat sObject = new Mat();
		Imgproc.resize(object, sObject, sroadSign.size());
		//mise en forme pour extraction
		Mat grayObject = new Mat(sObject.rows(),sObject.cols(),sObject.type());
		Imgproc.cvtColor(sObject, grayObject, Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(grayObject, grayObject, 0, 255, Core.NORM_MINMAX);
		
		Mat graySign = new Mat(sroadSign.rows(),sroadSign.cols(),sroadSign.type());
		Imgproc.cvtColor(sroadSign, graySign, Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(graySign, graySign, 0, 255, Core.NORM_MINMAX);
		
		//extraction des caractéristiques
		FeatureDetector orbDetector =FeatureDetector.create(FeatureDetector.ORB);
		DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		
		MatOfKeyPoint objectKeypoints = new MatOfKeyPoint();
		orbDetector.detect(grayObject, objectKeypoints);
		
		MatOfKeyPoint signKeypoints = new MatOfKeyPoint();
		orbDetector.detect(graySign, signKeypoints);
		
		Mat objectDescriptor = new Mat(object.rows(),object.cols(),object.type());
		orbExtractor.compute(grayObject, objectKeypoints, objectDescriptor);
		
		Mat signDescriptor = new Mat(sroadSign.rows(),sroadSign.cols(),sroadSign.type());
		orbExtractor.compute(graySign, signKeypoints, signDescriptor);
		
		// Faire le matching
		MatOfDMatch matchs = new MatOfDMatch();
		DescriptorMatcher matcher =DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		matcher.match(objectDescriptor, signDescriptor, matchs);
		System.out.println(matchs.dump());
		Mat matchedImage = new Mat(sroadSign.rows(),sroadSign.cols()*2,sroadSign.type());
		Features2d.drawMatches(sObject, objectKeypoints, sroadSign, signKeypoints, matchs, matchedImage);
		
		
	}

}
