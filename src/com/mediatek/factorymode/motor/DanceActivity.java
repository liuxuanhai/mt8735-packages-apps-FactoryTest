package com.mediatek.factorymode.motor;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.view.View;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.yongyida.robot.motorcontrol.MotorController;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class DanceActivity extends Activity{
    private MediaPlayer mMediaPlayer = null;
    private MotorController mMotorController;
    private MotorThread motorThread;
    private HeadMotorThread headMotorThread;
    //获取MotorService实例
    private ServiceConnection motorServiceConnection=new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mMotorController=MotorController.Stub.asInterface(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mMotorController=null;
        }
    };
    private void bindMotorService(){
        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_motor_dance);
        bindMotorService();
        startAllAction();
    }
    private void startAllAction(){
        mMediaPlayer = MediaPlayer.create(DanceActivity.this,R.raw.music);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                motorThread = new MotorThread();
                headMotorThread = new HeadMotorThread();
                motorThread.start();
                headMotorThread.start();
            }
        },2000);
    }

    class HeadMotorThread extends Thread {
        private boolean mStop = false;
        private void stopRun(){
            this.mStop = true;
        }
        @Override
        public void run() {
            super.run();
            while(!mStop){
                try {
                    if(!mStop)
                        headUp();
                    Thread.sleep(2100);
                    if(!mStop)
                        headDown();
                    Thread.sleep(2100);
                    if(!mStop)
                        headLeft();
                    Thread.sleep(2100);
                    if(!mStop)
                        headRight();
                    Thread.sleep(2100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void headUp(){
            try {
                mMotorController.setDrvType(0);
                mMotorController.setSpeed(30);
                mMotorController.headUp(2000);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        private void headDown(){
            try {
                mMotorController.setDrvType(0);
                mMotorController.setSpeed(30);
                mMotorController.headDown(2000);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        private void headLeft(){
            try {
                mMotorController.setDrvType(0);
                mMotorController.setSpeed(40);
                mMotorController.headLeftEnd();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        private void headRight(){
            try {
                mMotorController.setDrvType(0);
                mMotorController.setSpeed(40);
                mMotorController.headRightEnd();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    class MotorThread extends Thread {
        private boolean mStop = false;
        private void stopRun(){
            this.mStop = true;
        }
        @Override
        public void run() {
            super.run();
            while (!mStop){
                try {
                    if(!mStop)
                        forward();
                    Thread.sleep(2100);
                    if(!mStop)
                        back();
                    Thread.sleep(2100);
                    if(!mStop)
                        left();
                    Thread.sleep(2100);
                    if(!mStop)
                        right();
                    Thread.sleep(2100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        private void forward(){
            try {
                mMotorController.setDrvType(0);
                mMotorController.setSpeed(80);
                mMotorController.forward(2000);
                PowerManager pm = (PowerManager) DanceActivity.this.getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
                wl.acquire();
                wl.release();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        private void back(){
            try {
                mMotorController.setDrvType(0);
                mMotorController.setSpeed(80);
                mMotorController.back(2000);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        private void left(){
            try {
                mMotorController.setDrvType(0);
                mMotorController.setSpeed(80);
                mMotorController.left(2000);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        private void right(){
            try {
                mMotorController.setDrvType(0);
                mMotorController.setSpeed(80);
                mMotorController.right(2000);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopAllAction(){
        if(motorThread != null && headMotorThread != null){
            motorThread.stopRun();
            headMotorThread.stopRun();
            motorThread = null;
            headMotorThread = null;
        }
        try {
            mMotorController.stop();
            mMotorController.headLeftTurnMid();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(mMediaPlayer != null){
            mMediaPlayer.setLooping(false);
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
        unbindService(motorServiceConnection);
    }
    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.dance, "success");
        stopAllAction();
        finish();
    }
    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.dance, "failed");
        stopAllAction();
        finish();
    }
}
