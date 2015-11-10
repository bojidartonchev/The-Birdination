package com.example.bozhidar.thebirdination.GameObjects;

import android.view.MotionEvent;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }

    public boolean clickOver(MotionEvent event){

        double clickedX = event.getX();
        double clickedY = event.getY();

        if(clickedX>=this.getX()&& clickedX<=this.getX() + this.getWidth()){
            if(clickedY>=this.getY()&&clickedY<=this.getY()+this.getHeight()){
                return true;
            }
        }
        return false;
    }

}