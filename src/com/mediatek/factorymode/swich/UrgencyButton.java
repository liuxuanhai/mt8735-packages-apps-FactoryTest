package com.mediatek.factorymode.swich;

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
 * Created by Administrator on 2016/10/25 0025.
 */
public class UrgencyButton extends Activity {
    private MotorController mMotorController;
    private Button startButton;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_switch_urgencybutton);
        bindMotorService();
        startButton=(Button)findViewById(R.id.btn_start_test);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    mMotorController.setDrvType(0);
                    mMotorController.setSpeed(50);
                    mMotorController.right(0xffff);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void bindMotorService(){
        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.urgency_button_test, "success");
        unbindService(motorServiceConnection);
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.urgency_button_test, "failed");
        unbindService(motorServiceConnection);
        finish();
    }
}
