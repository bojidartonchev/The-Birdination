package com.example.bozhidar.thebirdination.Entity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bozhidar.thebirdination.GamePanel;
import com.example.bozhidar.thebirdination.R;

public class HUD {
    // coordinates
    private final int X_SCORE = 20;
    private final int Y_SCORE  = 80;

    private int score;
    private int textSize;

    // data
    public HUD(){
        this.setScore(0);
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        if(score%5==0){
            GamePanel.BIRD_SPEED+=7;
        }
        this.score = score;
        this.textSize = GamePanel.WIDTH*GamePanel.HEIGHT/69200;
        this.textSize = this.textSize+30;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.rgb(122, 83, 40));
        paint.setTextSize(textSize - 10);
        System.out.println(textSize);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("Score: "+this.getScore(),X_SCORE,Y_SCORE,paint);
    }
    public void drawLable(Bitmap img,Canvas canvas,int highestScore){
        canvas.drawBitmap(img,
                GamePanel.WIDTH  - img.getWidth() ,
                GamePanel.HEIGHT  - img.getHeight(),
                null);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("Highest Score: " + Integer.toString(highestScore),
                GamePanel.WIDTH - img.getWidth()+this.textSize*1.5f,
                GamePanel.HEIGHT - img.getHeight()+this.textSize*2,
                paint);
        canvas.drawText("Current Score: " + Integer.toString(this.score),
                GamePanel.WIDTH  - img.getWidth()+this.textSize*1.5f,
                GamePanel.HEIGHT  - img.getHeight()+this.textSize*3.5f,
                paint);
    }
}
