package com.example.bozhidar.thebirdination.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.bozhidar.thebirdination.Entity.Animation;
import com.example.bozhidar.thebirdination.GamePanel;

import java.util.Random;


public class Bird extends GameObject {
    private Bitmap spritesheet;
    private double dya;
    private boolean dead;
    private int speed;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Bird(Bitmap res, int w, int h,int numRows, int numFrames,int speed) {

        x = -400;
        y = getRandomY();
        dy = 0;

        this.height = h;
        this.width = w;
        this.setSpeed(speed);
        this.setPlaying(false);

        Bitmap[][] image = new Bitmap[numRows][numFrames];
        spritesheet = res;
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numFrames; i++) {
                image[j][i] = Bitmap.createBitmap(spritesheet, i * width, j*height, width, height);
            }
        }

        animation.setFrames(image);
        animation.setDelay(1);
        startTime = System.nanoTime();

    }

    private int getRandomY() {
        Random rand = new Random();
        return rand.nextInt(GamePanel.HEIGHT-400) + 100;
    }
    public int getSpeed() {
        return speed;
    }

    public boolean isDead() {
        return dead;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDead(boolean b){
        dead = b;
    }

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            startTime = System.nanoTime();
        }
        animation.update();

        if(dead){
            dy = (int)(dya+=5);

        }
        else{
            dy = (int)(dya+=0);
        }

        y += dy*2;
        dy = 0;
        x+=this.getSpeed();
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDYA(){dya = 0;}

}