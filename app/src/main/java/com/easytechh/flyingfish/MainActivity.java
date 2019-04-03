package com.easytechh.flyingfish;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;

    private ImageView orange;
    private ImageView pink;
    private ImageView black;

    private ImageView image[]=new ImageView[3];
    private ImageView image1[]=new ImageView[3];

    //for sound
    private SoundPlayer sound;

    //sizes
    private int frameHeight;
    private int fishSize;
    private int screenHeight;
    private int screenWidth;

    //for life
    private int lifeCounter;

    //for score
    private int score;

    private ImageView fish;

    //position
    private int fishy;
    private int fishx;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;


       // Initialize Class
    private Handler handler=new Handler();
    private Timer timer=new Timer();


    //check status
    public boolean action_flag=false;
    public boolean start_flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        fish = (ImageView) findViewById(R.id.fish1);
        orange = (ImageView) findViewById(R.id.orange);
        pink = (ImageView) findViewById(R.id.pink);
        black = (ImageView) findViewById(R.id.black);
        sound=new SoundPlayer(this);

        //hearts
        image[0]=findViewById(R.id.heartgrey1);
        image[0].setVisibility(View.INVISIBLE);
        image[1]=findViewById(R.id.heartgrey2);
        image[1].setVisibility(View.INVISIBLE);
        image[2]=findViewById(R.id.heartgrey3);
        image[2].setVisibility(View.INVISIBLE);
        image1[0]=findViewById(R.id.heartRed1);
        image1[1]=findViewById(R.id.heartRed2);
        image1[2]=findViewById(R.id.heartRed3);


        // getting screen size
        WindowManager wm=getWindowManager();
        Display disp=wm.getDefaultDisplay();
        Point size=new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        // Move to out of screen.
        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        black.setX(-80);
        black.setY(-80);
       lifeCounter=3;
       score=0;

            }

            public boolean hitBallChecker(int x , int y){
        if(fishx < x && x < (fishx + fish.getWidth()) &&  fishy < y && y <(fishy + fish.getHeight())){
            return true;
        }
        return false;
            }



   public void changePos(){
        //move balls
       //orange

       orangeX -= 12;
       if(hitBallChecker(orangeX,orangeY)){

            score +=10;
            sound.playIncreaseSound();
           orangeX=-100;
       }
       if(orangeX < 0){
           orangeX = screenWidth + 20;
           orangeY = (int) Math.floor(Math.random()*(frameHeight - orange.getHeight()));
       }
       orange.setX(orangeX);
       orange.setY(orangeY);

       //pink
       pinkX -= 10;
       if(hitBallChecker(pinkX,pinkY)){

           score+=20;
           sound.playIncreaseSound();
           pinkX=-100;
       }
       if(pinkX < 0){
           pinkX = screenWidth + 20;
           pinkY = (int) Math.floor(Math.random()*(frameHeight - pink.getHeight()));
       }
       pink.setX(pinkX);
       pink.setY(pinkY);

       scoreLabel.setText("Score : "+score);

       //black
       blackX -= 9;

       if(hitBallChecker(blackX,blackY)){
           lifeCounter--;
           sound.playDecreaseSound();
           if(lifeCounter>=0) {
               image1[lifeCounter].setVisibility(View.INVISIBLE);
               image[lifeCounter].setVisibility(View.VISIBLE);
           }

           if(lifeCounter==0){
               timer.cancel();
               timer=null;
               Intent intent=new Intent(MainActivity.this,Result.class);
                      intent.putExtra("Score",score);
                      startActivity(intent);
           }

           blackX=-100;
       }
       if(blackX < 0){
           blackX = screenWidth + 20;
           blackY = (int) Math.floor(Math.random()*(frameHeight - black.getHeight()));
       }
       black.setX(blackX);
       black.setY(blackY);


        //move fish

        if(action_flag ==true){
            //touching
            fishy -=22;
        }else{
            //releasing
            fishy +=7;
        }

        if(fishy < 0) fishy = 0;
        if(fishy > frameHeight - fishSize) fishy=frameHeight - fishSize;
        fish.setY(fishy);


    }


    @Override
    public boolean onTouchEvent(MotionEvent me) {

        if(start_flag == false){

            start_flag = true;

            // getting frame and fish height
            FrameLayout frame=findViewById(R.id.frame);
            frameHeight=frame.getHeight();

            fishy= (int) fish.getY();

            fishSize=fish.getHeight();


            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);

        }else{
            if(me.getAction()== MotionEvent.ACTION_DOWN){
                action_flag=true;
                sound.jumpSound();

            }else if(me.getAction() == MotionEvent.ACTION_UP){
                action_flag=false;
            }
        }
        return true;
    }
    double backPress;
    @Override
    public void onBackPressed() {

        double currentTime=System.currentTimeMillis();
        if(backPress>currentTime){
           super.onBackPressed();

        }else{
            Toast.makeText(this, "Please Press again to exit", Toast.LENGTH_SHORT).show();
            backPress=currentTime+2000;
        }

    }
}
