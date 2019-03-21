package testtwizzi;

import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      Mat m= Highgui.imread("bgr.png");
	      Vector<Mat> mv = new Vector<Mat>();
		Core.split(m, mv);
		fonction.ImShow("test", m);
		Mat dst =Mat.zeros(m.size(), m.type());
		Vector<Mat> chans =new Vector<Mat>();
		Mat empty =Mat.zeros(m.size(), CvType.CV_8UC1);
		
	      for(int i=0;i<mv.size();i++) {
	    	  //fonction.ImShow(Integer.toString(i), mv.get(i));
	    	  chans.removeAllElements();
	    	  for(int j=0; j<mv.size();j++) {
	    		  if(j!=i) {
	    			  chans.add(empty);
	    		  }else {
	    			  chans.add(mv.get(i));
	    		  }
	    	  }
	    	  Core.merge(chans, dst);
	    	  fonction.ImShow(Integer.toString(i), dst);
	      }
	}

}
