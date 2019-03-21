package testtwizzi;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      Mat m= Highgui.imread("opencv.png");
	      for(int i=0;i<m.height();i++) {
	    	  for(int j=0;j<m.width();j++) {
	    		  double[] RGB=m.get(i, j);
	    		  if(RGB[0]==255 && RGB[1]==255 && RGB[2]==255) {
	    			  System.out.print(".");
	    		  }
	    		  else {
	    			  System.out.print("+");
	    		  }
	    	  }
	    	  System.out.println();
	      }

	}

}
