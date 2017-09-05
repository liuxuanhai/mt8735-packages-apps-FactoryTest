package com.mediatek.factorymode.power;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.yongyida.robot.motorcontrol.MotorController;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class AutoChargePower extends Activity{
    private MotorController mMotorController;
    private Button btnAutoChargeOn,btnAutoChargeOff;
    private TextView powerStateValue,processStateValue;
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
                        mMotorController.stop();
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

        /*注册自动充电状态广播*/
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.yongyida.robot.r150.battery_change");
        registerReceiver(autoChargeReceiver,intentFilter);

        btnAutoChargeOn=(Button)findViewById(R.id.btn_auto_charge_on);
        btnAutoChargeOff=(Button)findViewById(R.id.btn_auto_charge_off);
        powerStateValue=(TextView)findViewById(R.id.txt_1);
        processStateValue=(TextView)findViewById(R.id.txt_2);
    }
    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.auto_charge, "success");
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.auto_charge, "failed");
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(motorServiceConnection);
        unregisterReceiver(autoChargeReceiver);
    }

    private BroadcastReceiver autoChargeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] data=intent.getByteArrayExtra("data");
            int type=intent.getIntExtra("cmdType",0);
            if(type==18){
                setStateValueText(data);
            }
        }
    };
    private void setStateValueText(byte[] data){
        if(data[0]==0){
            powerStateValue.setText("关");
        }else if(data[0]==1){
            powerStateValue.setText("开");
        }else {
            powerStateValue.setText("无法获取开关状态");
        }
        switch (data[1]){
            case 0:
                processStateValue.setText("断开连接");
                break;
            case 1:
                processStateValue.setText("寻找中");
                break;
            case 2:
                processStateValue.setText("连接超时");
                btnAutoChargeOn.setEnabled(true);
                break;
            case 3:
                processStateValue.setText("连接成功");
                btnAutoChargeOn.setEnabled(true);
                break;
            default:
                processStateValue.setText("无法获取过程状态");
                break;
        }
    }
}
