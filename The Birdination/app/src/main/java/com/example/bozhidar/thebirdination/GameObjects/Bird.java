package com.example.bozhidar.thebirdination.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.bozhidar.thebirdination.Entity.Animation;
import com.example.bozhidar.thebirdination.GamePanel;


public class Bird extends GameObject {
    private Bitmap spritesheet;
    private double dya;
    private boolean dead;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Bird(Bitmap res, int w, int h, int numFrames) {

        x = -400;
        y = GamePanel.HEIGHT / 3;
        dy = 0;
        height = h;
        width = w;
        this.setPlaying(true);

        Bitmap[][] image = new Bitmap[5][numFrames];
        spritesheet = res;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < image.length; i++) {
                image[j][i] = Bitmap.createBitmap(spritesheet, i * width, j*height, width, height);
            }
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }

    public void setDead(boolean b){dead = b;}

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
        x+=10;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDYA(){dya = 0;}

}