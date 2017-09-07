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
public class AutoChargePower extends Activity{
    private MotorController mMotorController;
    private Button btnAutoChargeOn,btnAutoChargeOff;
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
        setContentView(R.layout.act_power_autochargepower);
        init();

        btnAutoChargeOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAutoChargeOn.setEnabled(false);
                btnAutoChargeOff.setEnabled(true);
                try {
                    if(mMotorController!=null){
                        mMotorController.autoCharge(ON);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAutoChargeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAutoChargeOn.setEnabled(true);
                btnAutoChargeOff.setEnabled(false);
                try {
                    if(mMotorController!=null){
                        mMotorController.autoCharge(OFF);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void init(){
        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection, Context.BIND_AUTO_CREATE);

        btnAutoChargeOn=(Button)findViewById(R.id.btn_auto_charge_on);
        btnAutoChargeOff=(Button)findViewById(R.id.btn_auto_charge_off);
    }
    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.auto_charge, "success");
        unbindService(motorServiceConnection);
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.auto_charge, "failed");
        unbindService(motorServiceConnection);
        finish();
    }

}
