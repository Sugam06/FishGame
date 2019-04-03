package com.easytechh.flyingfish;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;
    private static int jumpSound;

    public SoundPlayer(Context context){

        //SoundPool(int maxStream, int streamType, int srcQuality)
        soundPool=new SoundPool(2,AudioManager.STREAM_MUSIC,0);

        hitSound = soundPool.load(context,R.raw.score,1);
        overSound = soundPool.load(context,R.raw.scoremin,1);
        jumpSound=soundPool.load(context,R.raw.jump,1);

    }
    public void playIncreaseSound(){
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }

    public void playDecreaseSound(){
        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }

    public void jumpSound(){
        soundPool.play(jumpSound,1.0f,1.0f,1,0,1.0f);
    }
}
