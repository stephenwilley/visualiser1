
package uk.co.esstec.processing.visualiser1;

import processing.core.*;
import processing.opengl.PShader;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class Gui extends PApplet {
    /**
     * Basic GUI functionality of the applet
     */
    private static final long serialVersionUID = 1L;

    Minim minim;
    AudioPlayer player;
    FFT fftLog1, fftLog2;
    PShader blur1, blur2, bloom;
    PGraphics buffer1;
    PImage preblur, postblur;
    int blendMode = 0;

    @Override
    public void setup() {
        size(1280, 720, P3D);
        frameRate(25);

        // Load the shaders
        blur1 = loadShader("Blur1.glsl");
        blur2 = loadShader("Blur2.glsl");
        bloom = loadShader("Bloom.glsl");
        buffer1 = createGraphics(width, height, P3D);
        preblur = new PImage(width, height);
        postblur = new PImage(width, height);

        // Load minim and create the player
        minim = new Minim(this);
        player = minim.loadFile("lights-remix.mp3", 2048);
        player.loop();

        // Create the FFT
        fftLog1 = new FFT(player.bufferSize(), player.sampleRate());
        fftLog1.logAverages(11, 10);
        fftLog2 = new FFT(player.bufferSize(), player.sampleRate());
        fftLog2.logAverages(11, 10);

        rectMode(CORNERS);
    }

    @Override
    public void draw() {
        buffer1.beginDraw();
        buffer1.rectMode(CORNERS);
        // Clear the background
        buffer1.background(0);

        // Set up colours
        buffer1.noStroke();
        buffer1.fill(150,150,255);

        // Work out some useful variables
        int middle = height / 2;
        int margin = 64;
        int newWidth = width - (margin * 2);

        // Now draw the FFT spectrum
        fftLog1.forward(player.left);
        fftLog2.forward(player.right);
        int w = newWidth/fftLog1.avgSize();
        for(int i = 0; i < fftLog1.avgSize(); i++) {
            float size1 = fftLog1.getAvg(i) / 5;
            float size2 = fftLog2.getAvg(i) / 5;
            int xPos = i * w;
            buffer1.rect(xPos + margin, middle - 1, xPos + w/2 + margin, middle - 1 - size1 - 1);
            buffer1.rect(xPos + margin, middle, xPos + w/2 + margin, middle + size2 + 1);
        }
        buffer1.endDraw();
        preblur.copy(buffer1, 0, 0, width, height, 0, 0, width, height);

        buffer1.beginDraw();
        // Blur it with shaders
        //shader(blur1);
        buffer1.filter(blur1);
        //shader(blur2);
        buffer1.filter(blur2);
        buffer1.endDraw();
        postblur.copy(buffer1, 0, 0, width, height, 0, 0, width, height);

        // Now add the original back in
        buffer1.beginDraw();
        buffer1.shader(bloom);
        bloom.set("preblur", preblur);
        bloom.set("postblur", postblur);
        bloom.set("blendMode", blendMode);
        buffer1.image(new PImage(width, height), 0, 0);
        buffer1.resetShader();
        buffer1.endDraw();

        image(buffer1, 0, 0, width, height);
    }

    @Override
    public void mousePressed() {
        blendMode++;
        if (blendMode > 3) {
            blendMode = 0;
        }
    }

    @Override
    public void stop()
    {
        player.close();
        minim.stop();

        super.stop();
    }
}
