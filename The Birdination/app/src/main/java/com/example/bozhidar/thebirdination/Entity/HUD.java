package com.example.bozhidar.thebirdination.Entity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathEffect;

import com.example.bozhidar.thebirdination.R;

import java.io.IOException;

public class HUD {
    // coordinates
    private final int X = 0;
    private final int Y = 10;
    private final int X_HITS = 70;
    private final int Y_HITS  = 50;
    private final int X_MISS = 70;
    private final int Y_MISS  = 113;
    private int missed;
    private int hit;

    // data
    private Bitmap image;

    public HUD(Bitmap image){
        this.image = image;
        this.setMissed(0);
        this.setHit(0);

    }

    public int getMissed() {
        return missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public void draw(Canvas canvas){
        //canvas.drawBitmap(image,0,10,null);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(122,83,40));
        paint.setTextSize(32);
        paint.setStyle(Paint.Style.FILL);
        //paint.setPathEffect(new PathEffect());
        canvas.drawText("Hits: " + this.getHit(), X_HITS, Y_HITS, paint);
        canvas.drawText("Miss: "+this.getMissed(),X_MISS,Y_MISS,paint);
    }
}
