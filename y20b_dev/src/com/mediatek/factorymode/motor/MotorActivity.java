package com.mediatek.factorymode.motor;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

import android.content.Intent;
import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Motor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.CompoundButton;
import java.util.Locale;
import android.widget.ToggleButton;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.content.ComponentName;
import android.content.Context;

import com.yongyida.robot.motorcontrol.MotorController;
public class MotorActivity extends Activity {

	private static final String TAG = MotorActivity.class.getSimpleName();
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMid;
    private RadioButton radioButtonHigh;
    private RadioButton radioButtonClick;
    private RadioButton radioButtonMove;
    private Button buttonForward;
    private Button buttonLeft;
    private Button buttonRight;
    private Button buttonStop;
    private Button buttonBack;
	private ToggleButton fallSwitch;
	private MotorController mMotorController;
	public static final int LOW=25;
	public static final int MID=60;
	public static final int HIGH=95;
	public static final int CLICK_TIME=2000;
	public static final int CONTINUS_TIME=0xffff;
	//获取MotorService实例
	private ServiceConnection motorServiceConnection=new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mMotorController=MotorController.Stub.asInterface(service);
			try{
                mMotorController.setFallOn(false);
				mMotorController.setDrvType(0);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);
		//初始化控件
        initWidget();
		
		//绑定MotorService
		Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection,Context.BIND_AUTO_CREATE);
		
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				
                switch (isSelect()){
                    case 1:
                        // 低速，点动，前进
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(LOW);
							mMotorController.forward(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 2:
                        //低速，连动，前进
						try{
                            
                            mMotorController.setDrvType(0);
                            mMotorController.setSpeed(LOW);
							mMotorController.forward(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 3:
                        //中速，点动，前进
						try{
                            mMotorController.setDrvType(0);
                            mMotorController.setSpeed(MID);
							mMotorController.forward(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 4:
                        //中速，连动，前进
						try{
                            
                            mMotorController.setDrvType(0);
                            mMotorController.setSpeed(MID);
							mMotorController.forward(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 5:
                        //高速，点动，前进
						try{
                            
                           mMotorController.setDrvType(0);
                            mMotorController.setSpeed(HIGH);
							mMotorController.forward(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 6:
                        //高速，连动，前进
						try{
                            
                            mMotorController.setDrvType(0);
                            mMotorController.setSpeed(HIGH);
							mMotorController.forward(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }

            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				
                switch (isSelect()){
                    case 1:
                        //低速，点动，左转
						try{
                            
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(LOW);
							mMotorController.left(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 2:
                        //低速，连动，左转
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(LOW);
							mMotorController.left(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 3:
                        //中速，点动，左转
						try{
                            mMotorController.setDrvType(0);
                            mMotorController.setSpeed(MID);
							mMotorController.left(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 4:
                        //中速，连动，左转
						try{
                            mMotorController.setDrvType(0);
                            mMotorController.setSpeed(MID);
							mMotorController.left(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        //高速，点动，左转
						try{
                            mMotorController.setDrvType(0);
                            mMotorController.setSpeed(HIGH);
							mMotorController.left(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 6:
                        //高速，连动，左转
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(HIGH);
							mMotorController.left(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
					try{ 
                            mMotorController.stop();
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
                
            }
        });
		
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (isSelect()){
                    case 1:
                        //低速，点动，右转
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(LOW);
							mMotorController.right(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 2:
                        //低速，连动，右转
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(LOW);
							mMotorController.right(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 3:
                        //中速，点动，右转
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(MID);
							mMotorController.right(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 4:
                        //中速，连动，右转
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(MID);
							mMotorController.right(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 5:
                        //高速，点动，右转
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(HIGH);
							mMotorController.right(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 6:
                        //高速，连动，右转
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(HIGH);
							mMotorController.right(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    default:
                        break;
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (isSelect()){
                    case 1:
                        //低速，点动，后退
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(LOW);
							mMotorController.back(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 2:
                        //低速，连动，后退
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(LOW);
							mMotorController.back(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 3:
                        //中速，点动，后退
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(MID);
							mMotorController.back(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 4:
                        //中速，连动，后退
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(MID);
							mMotorController.back(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 5:
                        //高速，点动，后退
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(HIGH);
							mMotorController.back(CLICK_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
						
                        break;
                    case 6:
                        //高速，连动，后退
						try{
							mMotorController.setDrvType(0);
                            mMotorController.setSpeed(HIGH);
							mMotorController.back(CONTINUS_TIME);
                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
		
		//打开和关闭防跌落测试
//		fallSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(isChecked){
//                    mRobotController.setFallOn(true);
//               }else {
//                    mRobotController.setFallOn(false);
//                }
//            }
//        });

    }
	/*
	@Override
    protected void onPause() {
        super.onPause();
		mRobotController.setFallOn(true);
        mRobotController.stop();
		mRobotController.close();
		finish();
    }*/


    public int isSelect(){
        if(radioButtonLow.isChecked()){
            if(radioButtonClick.isChecked()){
                return 1;
            }else if(radioButtonMove.isChecked()){
                return 2;
            }else {
                return 0;
            }
        }else if(radioButtonMid.isChecked()){
            if(radioButtonClick.isChecked()){
                return 3;
            }else if(radioButtonMove.isChecked()){
                return 4;
            }else {
                return 0;
            }
        }else if(radioButtonHigh.isChecked()){
            if(radioButtonClick.isChecked()){
                return 5;
            }else if(radioButtonMove.isChecked()){
                return 6;
            }else {
                return 0;
            }
        }else{
            return 0;
        }
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
	
	
    public void initWidget(){
        radioButtonLow=(RadioButton)findViewById(R.id.rbtn_low);
        radioButtonMid=(RadioButton)findViewById(R.id.rbtn_mid);
        radioButtonHigh=(RadioButton)findViewById(R.id.rbtn_high);
        radioButtonClick=(RadioButton)findViewById(R.id.rbtn_click_move);
        radioButtonMove=(RadioButton)findViewById(R.id.rbtn_continuous_move);
        buttonForward=(Button)findViewById(R.id.btn_forward);
        buttonLeft=(Button)findViewById(R.id.btn_left);
        buttonRight=(Button)findViewById(R.id.btn_right);
        buttonStop=(Button)findViewById(R.id.btn_stop);
        buttonBack=(Button)findViewById(R.id.btn_back);
//		fallSwitch=(ToggleButton)findViewById(R.id.fall_switch);
    }

	public void button009(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.motor, "success");
		try{
			mMotorController.stop();
			mMotorController.setFallOn(true);
            }catch (RemoteException e){
                e.printStackTrace();
            }
		unbindService(motorServiceConnection);
		finish();
	}

	public void button010(View view) {
		
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.motor, "failed");
		try{
			mMotorController.stop();
			mMotorController.setFallOn(true);
            }catch (RemoteException e){
                e.printStackTrace();
            }
		unbindService(motorServiceConnection);
		finish();
	}
	
}
