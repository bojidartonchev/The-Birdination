package com.example.bozhidar.thebirdination.Entity;

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[][] frames;
    private int currentFrame;
    private int currentAction;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[][] frames)
    {
        this.frames = frames;
        this.setFrame(4);
        currentAction=0;
        startTime = System.nanoTime();
    }
    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame= i;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;

        if(elapsed>delay)
        {
            currentFrame--;
            startTime = System.nanoTime();
        }
        if(currentFrame < 0){
            currentFrame = 4;
            currentAction++;
            playedOnce = true;
        }
        if(currentAction==4){
            currentAction=0;
        }
    }
    public Bitmap getImage(){
        return frames[currentAction][currentFrame];
    }
    public int getFrame(){return currentFrame;}
    public boolean playedOnce(){return playedOnce;}
}