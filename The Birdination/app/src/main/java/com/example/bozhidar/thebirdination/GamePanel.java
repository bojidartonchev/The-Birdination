package com.example.bozhidar.thebirdination;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bozhidar.thebirdination.Entity.Background;
import com.example.bozhidar.thebirdination.Entity.HUD;
import com.example.bozhidar.thebirdination.GameObjects.Bird;
import com.example.bozhidar.thebirdination.GameObjects.Tree;

import java.util.ArrayList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 1920;

    public static final int HEIGHT = 1080;

    private MainThread thread;
    private Background bg;
    private Bird bird;
    private ArrayList<Tree> trees;
    private HUD hud;


    public GamePanel(Context context)
    {
        super(context);
        hud = new HUD(BitmapFactory.decodeResource(getResources(), R.drawable.hud));
        trees=new ArrayList<>();
        trees.add(new Tree(BitmapFactory.decodeResource(getResources(), R.drawable.tree3), 300, 0));
        trees.add(new Tree(BitmapFactory.decodeResource(getResources(), R.drawable.tree2),800,0));
        trees.add(new Tree(BitmapFactory.decodeResource(getResources(), R.drawable.tree1),1450,0));

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{
                thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.backround));
        bird = new Bird(BitmapFactory.decodeResource(getResources(), R.drawable.robin), 240, 314, 5);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();


    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){

            double clickedX = event.getX();
            double clickedY = event.getY();

            if(clickedX>=this.bird.getX()&&clickedX<=this.bird.getX()+this.bird.getWidth()){
                if(clickedY>=this.bird.getY()&&clickedY<=this.bird.getY()+this.bird.getHeight()){
                    bird.setDead(true);
                    bird=new Bird(BitmapFactory.decodeResource(getResources(), R.drawable.robin), 240, 314, 5);
                    this.hud.setHit(this.hud.getHit()+1);
                }
            }

            return true;
        }

        if(event.getAction()==MotionEvent.ACTION_UP)
        {

            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update()
    {
        if(bird.getPlaying()) {
            bird.update();
            if(bird.getX()>this.WIDTH||bird.getY()>this.HEIGHT){
                bird.setDead(true);
                bird=new Bird(BitmapFactory.decodeResource(getResources(), R.drawable.robin), 240, 314, 5);
                this.hud.setMissed(this.hud.getMissed()+1);
            }
        }
    }
    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);


        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            hud.draw(canvas);
            bird.draw(canvas);
            for (Tree tree : trees) {
                tree.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }


}