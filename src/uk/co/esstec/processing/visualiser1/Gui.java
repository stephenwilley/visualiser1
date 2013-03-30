
package uk.co.esstec.processing.visualiser1;

import processing.core.*;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Gui extends PApplet {
    /**
     * Basic GUI functionality of the applet
     */
    private static final long serialVersionUID = 1L;
    private static final String version = "2";
    private static final String imagePath = "/Users/stephen/Desktop/visualiser1/"
            + Integer.parseInt(version) + "/";

    Minim minim;
    AudioPlayer player;

    @Override
    public void setup() {
        size(1280, 720, P3D);
        frameRate(25);

        // Load minim and create the player
        minim = new Minim(this);
        player = minim.loadFile("/Users/stephen/Downloads/lights-remix.mp3", 2048);
        player.loop();
    }

    @Override
    public void draw() {
        background(0);
        noStroke();

        saveFrame(imagePath + "pic-#####.png");
    }
}
