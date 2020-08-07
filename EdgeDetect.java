package cameraManipulation;

import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;

public class EdgeDetect extends PApplet {
	private Capture cam;

	private boolean newEditedFrameAvailable;
	private PImage editedFrame;

	public static void main(String[] args) {
		PApplet.main("camera.EdgeDetect");
	}

	public void settings() {
		size(1280, 720);
	}

	public void setup() {
		String[] cameras = Capture.list();

		System.out.println("Available cameras:");
		for (String camera : cameras)
			System.out.println(camera);

		cam = new Capture(this, cameras[0]);
		cam.start();
	}

	public void draw() {
		if (newEditedFrameAvailable) {
			image(editedFrame, 0, 0);
			newEditedFrameAvailable = false;
		}

		if (cam.available() && !newEditedFrameAvailable)
			readAndEditFrame();
	}

	private void readAndEditFrame()

	{

		cam.read();

		editedFrame = createImage(width, height, RGB);

		editedFrame.loadPixels();

		cam.loadPixels();

		for (int x = 1; x < width; x++) {

			for (int y = 0; y < height; y++) {
				int loc = x + y * cam.width;

				int pix = cam.pixels[loc];

				int leftLoc = (x - 1) + y * cam.width;

				int leftPix = cam.pixels[leftLoc];

				float diff = abs(brightness(pix) - brightness(leftPix));

				editedFrame.pixels[loc] = color(diff);

			}

		}

		editedFrame.updatePixels();

		newEditedFrameAvailable = true;

	}
  
}
