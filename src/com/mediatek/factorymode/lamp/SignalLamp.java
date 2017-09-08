package com.mediatek.factorymode.lamp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.yongyida.robot.breathled.aidl.BreathledController;
import com.yongyida.robot.motorcontrol.MotorController;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class SignalLamp extends Activity {
    private Button btnArmLampOpen,btnArmLampClose,btnChestLampOpen;
    private Button btnChestLampClose,btnEarLampOpen,btnEarLampClose;
    private TextView txtContent;
    private BreathledController mBreathledController;

    //获取BreathLed实例
    private ServiceConnection mBreathledServiceConnect=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBreathledController= BreathledController.Stub.asInterface(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBreathledController=null;
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
        setContentView(R.layout.act_lamp_signallamp);
        initView();
        bindBreathledService();
        btnArmLampOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnArmLampOpen.setEnabled(false);
                btnArmLampClose.setEnabled(true);


            }
        });

        btnArmLampClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnArmLampOpen.setEnabled(true);
                btnArmLampClose.setEnabled(false);

            }
        });

        btnChestLampOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChestLampOpen.setEnabled(false);
                btnChestLampClose.setEnabled(true);
                try {
                    mBreathledController.setOnoff("chest","on");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        btnChestLampClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnChestLampOpen.setEnabled(true);
                btnChestLampClose.setEnabled(false);
                try {
                    mBreathledController.setOnoff("chest","off");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        btnEarLampOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEarLampOpen.setEnabled(false);
                btnEarLampClose.setEnabled(true);
                try {
                    mBreathledController.setOnoff("ear","on");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        btnEarLampClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEarLampOpen.setEnabled(true);
                btnEarLampClose.setEnabled(false);
                try {
                    mBreathledController.setOnoff("ear","off");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void initView(){
        btnArmLampOpen=(Button)findViewById(R.id.btn_arm_open);
        btnArmLampClose=(Button)findViewById(R.id.btn_arm_close);
        btnChestLampOpen=(Button)findViewById(R.id.btn_chest_open);
        btnChestLampClose=(Button)findViewById(R.id.btn_chest_close);
        btnEarLampOpen=(Button)findViewById(R.id.btn_ear_open);
        btnEarLampClose=(Button)findViewById(R.id.btn_ear_close);
        txtContent=(TextView)findViewById(R.id.txt_description);
    }
	
	public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.signal_lamp_test, "success");
        unbindService(mBreathledServiceConnect);
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.signal_lamp_test, "failed");
        unbindService(mBreathledServiceConnect);
        finish();
    }

}
