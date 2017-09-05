package com.mediatek.factorymode.motor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.content.ComponentName;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

import java.util.Locale;
import com.yongyida.robot.motorcontrol.MotorController;
public class MotorActivity extends Activity {
	private MotorController mMotorController;
    private Button btnMotorForward,btnHeadUp;
    private Button btnMotorBack,btnHeadDown;
    private Button btnMotorLeft,btnHeadLeft;
    private Button btnMotorRight,btnHeadRight;
	private Button btnMotorTurnLeft,btnMotorTurnRight;
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
        setContentView(R.layout.activity_motor);
        initView();
        bindMotorService();
        //前进
        btnMotorForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMotorController.forward(0.1f, 1200);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //后退
        btnMotorBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
				
                    mMotorController.back(0.1f, 1200);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        //原地左转
        btnMotorLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMotorController.left(100,3000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //原地右转
        btnMotorRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMotorController.right(100,3000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
		
		//轮子停止
        btnMotorTurnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					
                    mMotorController.stop();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //头部回正
        btnMotorTurnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                    mMotorController.headLeft(90);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        //横向左移
        btnHeadUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMotorController.leftMove(0.1f, 1200);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //横向右移
        btnHeadDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMotorController.rightMove(0.1f, 1200);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //头向左
        btnHeadLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMotorController.headLeft(180);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //头向右
        btnHeadRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					
                    mMotorController.headRight(0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void button009(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.motor, "success");
        unbindService(motorServiceConnection);
        finish();
    }

    public void button010(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.motor, "failed");
        unbindService(motorServiceConnection);
        finish();
    }
    private void initView(){
        btnMotorForward=(Button)findViewById(R.id.btn_forward);
        btnHeadUp=(Button)findViewById(R.id.btn_head_up);
        btnMotorBack=(Button)findViewById(R.id.btn_back);
        btnHeadDown=(Button)findViewById(R.id.btn_head_down);
        btnMotorLeft=(Button)findViewById(R.id.btn_left);
        btnMotorRight=(Button)findViewById(R.id.btn_right);
        btnHeadRight=(Button)findViewById(R.id.btn_head_right);
		btnHeadLeft=(Button)findViewById(R.id.btn_head_left);
		btnMotorTurnLeft=(Button)findViewById(R.id.btn_turn_left);
		btnMotorTurnRight=(Button)findViewById(R.id.btn_turn_right);
    }
    private void bindMotorService(){
        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection, Context.BIND_AUTO_CREATE);
    }
	
}
