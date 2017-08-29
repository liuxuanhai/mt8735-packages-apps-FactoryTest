package com.mediatek.factorymode.lamp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.yongyida.robot.breathled.aidl.BreathledController;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class SignalLamp extends Activity{
    private final String TAG="SignalLamp";
    private Button led_test,led_close;
    private TextView earText,chestText;
    private BreathledController breathLed;
    private char ear,chest,earColor,chestColor;
    //获取BreathLed实例
    private ServiceConnection mBreathledServiceConnect=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            breathLed= BreathledController.Stub.asInterface(service);
            try {
                if(breathLed==null){
                    Log.d(TAG,"getStatus() breathLed="+breathLed);
                   return;
                }
                getStatus();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            breathLed=null;
        }
    };
    private void bindBreathledService(){
        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.BreathledService");
        intent.setPackage("com.yongyida.robot.breathledservice");
        bindService(intent,mBreathledServiceConnect, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signal_lamp);
        bindBreathledService();
        initView();
        led_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (breathLed == null) {
                    Log.d(TAG, "chest breathLed=" + breathLed);
                    return;
                }
                try {
                    ledTest();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        led_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breathLed == null) {
                    Log.d(TAG, "chest breathLed=" + breathLed);
                    return;
                }
                try {
                    if(breathLed.setOnoff("ear","off") == 5){
                        earText.setText("双耳灯成功关闭");
                    }else {
                        earText.setText("双耳灯关闭失败");
                    }
                    if(breathLed.setOnoff("chest","off") == 5){
                        chestText.setText("胸前灯成功关闭");
                    }else {
                        chestText.setText("胸前灯关闭失败");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void initView() {
        led_test=(Button)findViewById(R.id.led_test_btn);
        led_close=(Button)findViewById(R.id.led_close);
        earText=(TextView)findViewById(R.id.ear_tv);
        chestText=(TextView)findViewById(R.id.chest_tv);
    }
    private void ledTest() throws RemoteException {
        char earColorStatus=breathLed.getColor("ear");
        char chestColorStatus=breathLed.getColor("chest");
        if(earColorStatus == 'R'){
            if(breathLed.setColor("ear","Green") == 5){
                earText.setText("双耳灯成功切换绿色");
            }else {
                earText.setText("双耳灯切换绿色失败");
            }
        }else if(earColorStatus == 'G'){
            if(breathLed.setColor("ear","Blue") == 5){
                earText.setText("双耳灯成功切换蓝色");
            }else {
                earText.setText("双耳灯切换蓝色失败");
            }
        }else if(earColorStatus == 'B'){
            if(breathLed.setColor("ear","Red") == 5){
                earText.setText("双耳灯成功切换红色");
            }else {
                earText.setText("双耳灯切换红色失败");
            }
        }else {
            if(breathLed.setColor("ear","Green") == 5){
                earText.setText("双耳灯成功切换绿色");
            }else {
                earText.setText("双耳灯切换绿色失败");
            }
        }

        if(chestColorStatus == 'R'){
            if(breathLed.setColor("chest","Green") == 5){
                chestText.setText("胸前灯成功切换绿色");
            }else {
                chestText.setText("胸前灯切换绿色失败");
            }
        }else if(chestColorStatus == 'G'){
            if(breathLed.setColor("chest","Blue") == 5){
                chestText.setText("胸前灯成功切换蓝色");
            }else {
                chestText.setText("胸前灯切换蓝色失败");
            }
        }else if(chestColorStatus == 'B'){
            if(breathLed.setColor("chest","Red") == 5){
                chestText.setText("胸前灯成功切换红色");
            }else {
                chestText.setText("胸前灯切换红色失败");
            }
        }else {
            if(breathLed.setColor("chest","Green") == 5){
                chestText.setText("胸前灯成功切换绿色");
            }else {
                chestText.setText("胸前灯切换绿色失败");
            }
        }
    }
    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.signal_lamp, "success");
        try {
            setStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(mBreathledServiceConnect);
        finish();
    }

    public void button003(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.signal_lamp, "failed");
        try {
            setStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(mBreathledServiceConnect);
        finish();
    }
    private void getStatus() throws RemoteException {
        ear=breathLed.getOnoff("ear");
        chest=breathLed.getOnoff("chest");
        earColor=breathLed.getColor("ear");
        chestColor=breathLed.getColor("chest");
        Log.d(TAG,"ear = "+ear+",chest = "+chest+",earColor = "+earColor+",chestColor = "+chestColor);
    }
    private void setStatus() throws RemoteException {
        switch (earColor){
            case 'R':
                breathLed.setColor("ear","Red");
                break;
            case 'B':
                breathLed.setColor("ear","Blue");
                break;
            case 'G':
                breathLed.setColor("ear","Green");
                break;
            default:
                Log.d(TAG,"退出工厂模式，双耳颜色恢复初始状态错误");
                break;
        }
        switch (chestColor){
            case 'R':
                breathLed.setColor("chest","Red");
                break;
            case 'B':
                breathLed.setColor("chest","Blue");
                break;
            case 'G':
                breathLed.setColor("chest","Green");
                break;
            default:
                Log.d(TAG,"退出工厂模式，胸前颜色恢复初始状态错误");
                break;
        }
        if(ear == '0'){
            breathLed.setOnoff("ear","off");
        }else if(ear == '1'){
            breathLed.setOnoff("ear","on");
        }else {
            Log.d(TAG,"退出工厂模式，双耳恢复初始状态错误");
        }
        if(chest == '0'){
            breathLed.setOnoff("chest","off");
        }else if(chest == '1'){
            breathLed.setOnoff("chest","on");
        }else {
            Log.d(TAG,"退出工厂模式，胸前恢复初始状态错误");
        }
    }
}
