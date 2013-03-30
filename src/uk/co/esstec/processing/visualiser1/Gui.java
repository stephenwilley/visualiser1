
package uk.co.esstec.processing.visualiser1;

import processing.core.*;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class Gui extends PApplet {
    /**
     * Basic GUI functionality of the applet
     */
    private static final long serialVersionUID = 1L;
    private static final String version = "3";
    private static final String imagePath = "/Users/stephen/Desktop/visualiser1/"
            + Integer.parseInt(version) + "/";

    Minim minim;
    AudioPlayer player;
    FFT fftLog1;

    @Override
    public void setup() {
        size(1280, 720, P3D);
        frameRate(25);

        // Load minim and create the player
        minim = new Minim(this);
        player = minim.loadFile("/Users/stephen/Downloads/lights-remix.mp3", 2048);
        player.loop();

        // Create the FFT
        fftLog1 = new FFT(player.bufferSize(), player.sampleRate());
        fftLog1.logAverages(22, 3);

        rectMode(CORNERS);
    }

    @Override
    public void draw() {
        background(0);
        noStroke();
        fill(255);

        fftLog1.forward(player.mix);
        int w = width/fftLog1.avgSize();
        for(int i = 0; i < fftLog1.avgSize(); i++)
        {
            rect(i*w, height, i*w + w, height - fftLog1.getAvg(i));
        }

        saveFrame(imagePath + "pic-#####.png");
    }

    @Override
    public void stop()
    {
        player.close();
        minim.stop();

        super.stop();
    }
}
