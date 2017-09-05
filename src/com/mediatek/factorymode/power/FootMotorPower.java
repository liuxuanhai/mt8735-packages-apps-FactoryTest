package com.mediatek.factorymode.power;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.yongyida.robot.motorcontrol.MotorController;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class FootMotorPower extends Activity {
    private Button btnMotorPowerOn,btnMotorPowerOff,btnForward,btnBack;
    private MotorController mMotorController;
    private static final byte ON=1;
    private static final byte OFF=0;

    //获取MotorService实例
    private ServiceConnection motorServiceConnection=new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mMotorController= MotorController.Stub.asInterface(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mMotorController=null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_power_footmotorpower);
        init();
        btnMotorPowerOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				btnMotorPowerOn.setEnabled(false);
                btnMotorPowerOff.setEnabled(true);
                if(mMotorController!=null){
                    try {
                        mMotorController.footMotorPower(ON);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnMotorPowerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				btnMotorPowerOn.setEnabled(true);
                btnMotorPowerOff.setEnabled(false);
                if(mMotorController!=null){
                    try {
                        mMotorController.footMotorPower(OFF);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMotorController!=null){
                    try {
                        mMotorController.forward(0.2f,3000);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMotorController!=null){
                    try {
                        mMotorController.back(0.2f,3000);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void init(){
        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection, Context.BIND_AUTO_CREATE);

        btnMotorPowerOn=(Button)findViewById(R.id.btn_motor_power_on);
        btnMotorPowerOff=(Button)findViewById(R.id.btn_motor_power_off);
        btnForward=(Button)findViewById(R.id.btn_forward);
        btnBack=(Button)findViewById(R.id.btn_back);
    }
    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.foot_motor, "success");
        unbindService(motorServiceConnection);
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.foot_motor, "failed");
        unbindService(motorServiceConnection);
        finish();
    }

}
