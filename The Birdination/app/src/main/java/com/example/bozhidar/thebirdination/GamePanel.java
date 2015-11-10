package com.example.bozhidar.thebirdination;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
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
    public static int WIDTH;
    public static int HEIGHT;
    public static int BIRD_SPEED = 25;

    private MainThread thread;
    private Background bg;
    private Bird bird;
    private ArrayList<Tree> trees;
    private HUD hud;
    private int highestScore;
    public boolean hasStarted;
    private boolean gameOver;
    private boolean higherScore;

    public GamePanel(Game context)
    {
        super(context);
        setProportions(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

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
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.backround));
        initialize();
        trees=new ArrayList<>();
        trees.add(new Tree(BitmapFactory.decodeResource(getResources(), R.drawable.tree3), (int)(this.WIDTH/5.3), 0));
        trees.add(new Tree(BitmapFactory.decodeResource(getResources(), R.drawable.tree2),(int)(this.WIDTH/2.3),0));
        trees.add(new Tree(BitmapFactory.decodeResource(getResources(), R.drawable.tree1), (int)(this.WIDTH/1.3), 0));

        //we can safely start the game loop
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!hasStarted){
                this.hasStarted=true;
                this.bird.setPlaying(true);

            }
            else if(gameOver){
                initialize();
                this.gameOver =false;
                this.higherScore=false;
                this.bird.setPlaying(true);
            }
            boolean onTree = false;
            for (Tree tree : trees) {
                if(tree.clickOver(event)){
                    onTree=true;
                }
            }
            if(this.bird.clickOver(event)&&!onTree){
                bird.setDead(true);
                bird=new Bird(BitmapFactory.decodeResource(getResources(), R.drawable.robin), 240, 314,5, 5, this.BIRD_SPEED);
                this.bird.setPlaying(true);
                this.hud.setScore(this.hud.getScore() + 1);
            }

            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update()
    {
        if(bird.getPlaying()&&!this.gameOver) {
            bird.update();
            if((bird.getX()>this.WIDTH+50||bird.getY()>this.HEIGHT+50)&&!this.bird.isDead()){
                this.gameOver=true;
                if(this.highestScore<this.hud.getScore()){
                    saveScore(this.hud.getScore());
                    this.higherScore=true;
                    this.highestScore=this.hud.getScore();
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            if(!this.gameOver) {
                final int savedState = canvas.save();
                canvas.scale(scaleFactorX, scaleFactorY);
                bg.draw(canvas);
                hud.draw(canvas);
                System.out.println(this.highestScore);
                bird.draw(canvas);

                for (Tree tree : trees) {
                    tree.draw(canvas);
                }

                if(!this.hasStarted){
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tap),
                            GamePanel.WIDTH / 2-(BitmapFactory.decodeResource(getResources(), R.drawable.tap).getWidth()/2),
                            GamePanel.HEIGHT / 2-(BitmapFactory.decodeResource(getResources(), R.drawable.tap).getHeight()/2),
                            null);
                }

                canvas.restoreToCount(savedState);
            }
            else{
                if(this.higherScore){
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.highscore),
                            GamePanel.WIDTH / 2 - (BitmapFactory.decodeResource(getResources(), R.drawable.highscore).getWidth() / 2),
                            GamePanel.HEIGHT / 3 - (BitmapFactory.decodeResource(getResources(), R.drawable.highscore).getHeight() / 2),
                            null);
                }
                else {
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gameover),
                            GamePanel.WIDTH / 2-(BitmapFactory.decodeResource(getResources(), R.drawable.gameover).getWidth()/2),
                            GamePanel.HEIGHT / 4-(BitmapFactory.decodeResource(getResources(), R.drawable.gameover).getHeight()/2),
                            null);
                }
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tap2),
                        GamePanel.WIDTH / 2-(BitmapFactory.decodeResource(getResources(), R.drawable.tap2).getWidth()/2),
                        GamePanel.HEIGHT / 2-(BitmapFactory.decodeResource(getResources(), R.drawable.tap2).getHeight()/2),
                        null);
                this.hud.drawLable(BitmapFactory.decodeResource(getResources(), R.drawable.sign),canvas,this.highestScore);

            }
        }
    }

    private void saveScore(int score){
        SharedPreferences keyValues = getContext().getSharedPreferences("highestScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValues.edit();
        editor.putInt("highscore", score);
        editor.commit();
    }

    private int readScore() {
        SharedPreferences prefs = getContext().getSharedPreferences("highestScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("highscore", 0); //0 is the default value
        return score;
    }

    private void setProportions(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        this.WIDTH = metrics.widthPixels;
        this.HEIGHT = metrics.heightPixels;
    }

    private void initialize() {
        this.hud=new HUD();
        this.BIRD_SPEED = 25;
        this.bird = new Bird(BitmapFactory.decodeResource(getResources(), R.drawable.robin), 240, 314,5, 5, this.BIRD_SPEED);
        this.highestScore=this.readScore();
    }

}