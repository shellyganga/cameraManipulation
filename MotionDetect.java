package cameraManipulation;

	import processing.core.PApplet;

	import processing.core.PImage;

	import processing.video.*;

	public class MotionDetect extends PApplet

	{

	    private Capture cam;

	    int numPixels;

	    float []previousFrame;

	    float pixAverage;

	    private boolean newEditedFrameAvailable;

	    private PImage editedFrame;

	    public static void main(String[] args)

	    {

	        PApplet.main("camera.MotionDetect");

	    }

	    
	    public void settings()

	    {

	        size(1280, 720);

	    }

	    public void setup()

	    {

	    	cam = new Capture(this, width, height);

	        cam.start();

	       

	        numPixels = cam.width * cam.height;

	        previousFrame = new float[numPixels];

	        loadPixels();

	        stroke(0, 255, 255);

	        strokeWeight(4);

	        frameRate(10);



	    }

	    

	    public void draw()

	    {

	    	 if (cam.available())

	    	  {

	    	    cam.read();

	    	    cam.loadPixels();

	    	    

	    	    int sum = 0;

	    	    for (int i = 0; i < numPixels; i++)

	    	    {

	    	      float currColor = red(cam.pixels[i]);
	    	      float prevColor = previousFrame[i];
	    	      float d = abs(prevColor-currColor);

	    	      if (d>50)

	    	      {
	    	        sum ++;
	    	        pixels[i] = color(currColor,0,0);

	    	      }

	    	      else

	    	        pixels[i] = color(currColor);
	    	      	previousFrame[i] = currColor;

	    	    }

	    	    if (sum>1000)

	    	    {

	    	      updatePixels();

	    	      

	    	    }

	    	  }

	   

	    }
 
	}

