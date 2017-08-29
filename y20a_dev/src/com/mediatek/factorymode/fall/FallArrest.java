package com.mediatek.factorymode.fall;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.SharedPreferences;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.content.ComponentName;

import com.yongyida.robot.motorcontrol.MotorController;
import com.mediatek.factorymode.Utils;
import com.mediatek.factorymode.R;

import java.util.Locale;

/**
 * Created by sunyibin on 2016/5/30 0030.
 */
public class FallArrest extends Activity{
    private ImageView leftLamp,rightLamp,backLamp;
	private MotorController mMotorController;
    public final static String TAG="FallArrest";
    public static final int BACK_UPDATE=2;
    public static final int LEFT_BACK_UPDATE=6;
    public static final int LEFT_UPDATE=4;
    public static final int RIGHT_BACK_UPDATE=3;
    public static final int RIGHT_UPDATE=1;
    public static final int LEFT_RIGHT_UPDATE=5;
    public static final int LEFT_RIGHT_BACK_UPDATE=7;
    private Button resetButton;
	private ServiceConnection motorServiceConnection=new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
			try {
				mMotorController=MotorController.Stub.asInterface(service);
				//mMotorController.setFallOn(false);
				//Log.i(TAG, "onServiceConnected = setFallOn false");
			} catch (Exception e) {
			}
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mMotorController=null;
        }
    };
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case BACK_UPDATE:
                    backLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    break;
                case LEFT_UPDATE:
                    leftLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    break;
                case RIGHT_UPDATE:
                    rightLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    break;
                case LEFT_BACK_UPDATE:
                    backLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    leftLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    break;
                case RIGHT_BACK_UPDATE:
                    backLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    rightLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    break;
                case LEFT_RIGHT_UPDATE:
                    leftLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    rightLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    break;
                case LEFT_RIGHT_BACK_UPDATE:
                    backLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    leftLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    rightLamp.setBackground(getResources().getDrawable(R.drawable.light_red));
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall);
        init();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { 
                setGreen();
            }
        });
    }
    private void init(){ 
        leftLamp=(ImageView)findViewById(R.id.left_lamp);
        rightLamp=(ImageView)findViewById(R.id.right_lamp);
        backLamp=(ImageView)findViewById(R.id.back_lamp);
        resetButton=(Button)findViewById(R.id.btn_reset);
		IntentFilter intentFilter=new IntentFilter("com.yongyida.robot.motor.callBackData");
        registerReceiver(broadcastReceiver,intentFilter);
		Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection,Context.BIND_AUTO_CREATE);
    }

    private static String getHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase(Locale.getDefault()));
        }
        return sb.toString();
    }
    private void setGreen(){ 
        rightLamp.setBackground(getResources().getDrawable(R.drawable.light_green));
        leftLamp.setBackground(getResources().getDrawable(R.drawable.light_green));
        backLamp.setBackground(getResources().getDrawable(R.drawable.light_green));
    }
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] data=intent.getByteArrayExtra("data");
            int cmdType=intent.getIntExtra("cmdType",0);
			if(cmdType==2 && data.length >= 2){
				//Log.i(TAG, "broadcastReceiver data[1] = " + Integer.toBinaryString((data[1] & 0xff) + 0x100).substring(1));
				Message message=new Message();
                message.what=(int)data[1];
                handler.sendMessage(message);
			}
        }
    };
	
	public void button001(View view) { 
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.fall_arrest, "success");
		unbindService(motorServiceConnection);
		finish();
	}

	public void button002(View view) { 
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.fall_arrest, "failed");
		unbindService(motorServiceConnection);
		finish();
	}
	@Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
