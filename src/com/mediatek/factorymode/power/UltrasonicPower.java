package com.mediatek.factorymode.power;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.yongyida.robot.motorcontrol.MotorController;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class UltrasonicPower extends Activity {
    private TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11,txt12,txt13;
    private TextView txtForward,txtBack,txtLeft,txtRight,txtForwardLeft,txtForwardRight,txtBackLeft,txtBackRight;
    private TextView txtVersion;
    private Button ultrasonicOn,ultrasonicOff,getVersion;
    private MotorController mMotorController;
    private static final byte ON=1;
    private static final byte OFF=2;
    //获取MotorService实例
    private ServiceConnection motorServiceConnection=new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mMotorController= MotorController.Stub.asInterface(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_power_ultrasonicpower);
        initViews();
        ultrasonicOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ultrasonicOn.setEnabled(false);
                ultrasonicOff.setEnabled(true);
                try {
                    mMotorController.ultrasonicPower(ON);
                } catch (RemoteException e) {
                }
            }
        });
        ultrasonicOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ultrasonicOn.setEnabled(true);
                ultrasonicOff.setEnabled(false);
                try {
                    mMotorController.ultrasonicPower(OFF);
                } catch (RemoteException e) {

                }
            }
        });

        getVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMotorController.getVersionInfo((byte)1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews(){
        txt1=(TextView)findViewById(R.id.txt_1);
        txt2=(TextView)findViewById(R.id.txt_2);
        txt3=(TextView)findViewById(R.id.txt_3);
        txt4=(TextView)findViewById(R.id.txt_4);
        txt5=(TextView)findViewById(R.id.txt_5);
        txt6=(TextView)findViewById(R.id.txt_6);
        txt7=(TextView)findViewById(R.id.txt_7);
        txt8=(TextView)findViewById(R.id.txt_8);
        txt9=(TextView)findViewById(R.id.txt_9);
        txt10=(TextView)findViewById(R.id.txt_10);
        txt11=(TextView)findViewById(R.id.txt_11);
        txt12=(TextView)findViewById(R.id.txt_12);
        txt13=(TextView)findViewById(R.id.txt_13);
        txtForward=(TextView)findViewById(R.id.txt_obstacle_forward);
        txtBack=(TextView)findViewById(R.id.txt_obstacle_back);
        txtLeft=(TextView)findViewById(R.id.txt_obstacle_left);
        txtRight=(TextView)findViewById(R.id.txt_obstacle_right);
        txtForwardLeft=(TextView)findViewById(R.id.txt_obstacle_forward_left);
        txtForwardRight=(TextView)findViewById(R.id.txt_obstacle_forward_right);
        txtBackLeft=(TextView)findViewById(R.id.txt_obstacle_back_left);
        txtBackRight=(TextView)findViewById(R.id.txt_obstacle_back_right);
        ultrasonicOn=(Button)findViewById(R.id.btn_ultrasonic_on);
        ultrasonicOff=(Button)findViewById(R.id.btn_ultrasonic_off);
        getVersion=(Button)findViewById(R.id.btn_getVersion);
        txtVersion=(TextView)findViewById(R.id.txt_version);

        /*注册头部实时位置广播*/
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.yongyida.robot.r150.battery_change");
        registerReceiver(positionReceiver,intentFilter);

        //绑定MotorService
        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private BroadcastReceiver positionReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] data=intent.getByteArrayExtra("data");
            int type=intent.getIntExtra("cmdType",0);
            if(type==16){
                setTxt(data);
            }else if(type == 17){
                try {
                    String str=new String(data,"GB2312");
                    txtVersion.setText(str);
                    txtVersion.setTextColor(Color.rgb(255,0,0));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        private void setTxt(byte[] data){
            int ultrasonic1=((data[5] & 0xff) * 0x100)+( data[4] & 0xff);
            int ultrasonic2=((data[7] & 0xff) * 0x100)+( data[6] & 0xff);
            int ultrasonic3=((data[9] & 0xff) * 0x100)+( data[8] & 0xff);
            int ultrasonic4=((data[11] & 0xff) * 0x100)+( data[10] & 0xff);
            int ultrasonic5=((data[13] & 0xff) * 0x100)+( data[12] & 0xff);
            int ultrasonic6=((data[15] & 0xff) * 0x100)+( data[14] & 0xff);
            int ultrasonic7=((data[17] & 0xff) * 0x100)+( data[16] & 0xff);
            int ultrasonic8=((data[19] & 0xff) * 0x100)+( data[18] & 0xff);
            int ultrasonic9=((data[21] & 0xff) * 0x100)+( data[20] & 0xff);
            int ultrasonic10=((data[23] & 0xff) * 0x100)+( data[22] & 0xff);
            int ultrasonic11=((data[25] & 0xff) * 0x100)+( data[24] & 0xff);
            int ultrasonic12=((data[27] & 0xff) * 0x100)+( data[26] & 0xff);
            int ultrasonic13=((data[29] & 0xff) * 0x100)+( data[28] & 0xff);
            txt1.setText(""+ultrasonic1);
            txt2.setText(""+ultrasonic2);
            txt3.setText(""+ultrasonic3);
            txt4.setText(""+ultrasonic4);
            txt5.setText(""+ultrasonic5);
            txt6.setText(""+ultrasonic6);
            txt7.setText(""+ultrasonic7);
            txt8.setText(""+ultrasonic8);
            txt9.setText(""+ultrasonic9);
            txt10.setText(""+ultrasonic10);
            txt11.setText(""+ultrasonic11);
            txt12.setText(""+ultrasonic12);
            txt13.setText(""+ultrasonic13);

            if((data[38] & 0x01)==1){
                txtForward.setText(""+1);
                txtForward.setTextColor(Color.rgb(255, 0, 0));
            }else {
                txtForward.setText(""+0);
                txtForward.setTextColor(Color.rgb(0, 255, 0));
            }

            if((data[38] & 0x02)==2){
                txtBack.setText(""+1);
                txtBack.setTextColor(Color.rgb(255, 0, 0));
            }else {
                txtBack.setText(""+0);
                txtBack.setTextColor(Color.rgb(0, 255, 0));
            }

            if((data[38] & 0x04)==4){
                txtLeft.setText(""+1);
                txtLeft.setTextColor(Color.rgb(255, 0, 0));
            }else {
                txtLeft.setText(""+0);
                txtLeft.setTextColor(Color.rgb(0, 255, 0));
            }

            if((data[38] & 0x08)==8){
                txtRight.setText(""+1);
                txtRight.setTextColor(Color.rgb(255, 0, 0));
            }else {
                txtRight.setText(""+0);
                txtRight.setTextColor(Color.rgb(0, 255, 0));
            }

            if((data[38] & 0x10)==16){
                txtForwardLeft.setText(""+1);
                txtForwardLeft.setTextColor(Color.rgb(255, 0, 0));
            }else {
                txtForwardLeft.setText(""+0);
                txtForwardLeft.setTextColor(Color.rgb(0, 255, 0));
            }

            if((data[38] & 0x20)==32){
                txtForwardRight.setText(""+1);
                txtForwardRight.setTextColor(Color.rgb(255, 0, 0));
            }else {
                txtForwardRight.setText(""+0);
                txtForwardRight.setTextColor(Color.rgb(0, 255, 0));
            }

            if((data[38] & 0x40)==64){
                txtBackLeft.setText(""+1);
                txtBackLeft.setTextColor(Color.rgb(255, 0, 0));
            }else {
                txtBackLeft.setText(""+0);
                txtBackLeft.setTextColor(Color.rgb(0, 255, 0));
            }

            if((data[38] & 0x80)==128){
                txtBackRight.setText(""+1);
                txtBackRight.setTextColor(Color.rgb(255, 0, 0));
            }else {
                txtBackRight.setText(""+0);
                txtBackRight.setTextColor(Color.rgb(0, 255, 0));
            }
        }

    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(positionReceiver);
        unbindService(motorServiceConnection);
    }
    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.ultrasonic, "success");
        finish();
    }
    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.ultrasonic, "failed");
        finish();
    }
}
