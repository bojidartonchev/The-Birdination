package com.example.bozhidar.thebirdination.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Tree extends GameObject {
    private Bitmap image;

    public Tree(Bitmap image,int x , int y) {
        this.image = image;
        super.setX(x);
        super.setY(y);
        width=image.getWidth();
        height=image.getHeight();
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,x,y,null);
    }
}
