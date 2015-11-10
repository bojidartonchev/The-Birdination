package com.example.bozhidar.thebirdination.Entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.bozhidar.thebirdination.GamePanel;

public class Background {

    private Bitmap image;

    public Background(Bitmap res)
    {
        image = res;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,0,0, null);
    }
}