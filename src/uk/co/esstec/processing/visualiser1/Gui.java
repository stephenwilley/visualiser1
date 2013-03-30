
package uk.co.esstec.processing.visualiser1;

import processing.core.*;

public class Gui extends PApplet {
    /**
     * Basic GUI functionality of the applet
     */
    private static final long serialVersionUID = 1L;
    private static final String version = "1";
    private static final String imagePath = "/Users/stephen/Desktop/visualiser1/"
            + Integer.parseInt(version) + "/";

    @Override
    public void setup() {
        size(1280, 720, P3D);
        frameRate(25);
    }

    @Override
    public void draw() {
        background(0);
        noStroke();

        saveFrame(imagePath + "pic-#####.png");
    }
}
