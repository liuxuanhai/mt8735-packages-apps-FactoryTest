package com.mediatek.factorymode.motor;

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
public class MotorActivity extends Activity {
	private MotorController mMotorController;
    private Button btnMotorForward,btnHeadUp;
    private Button btnMotorBack,btnHeadDown;
    private Button btnMotorLeft,btnHeadLeft;
    private Button btnMotorRight,btnHeadRight;
	private Button btnMotorTurnLeft,btnMotorTurnRight;
    private Button btn1,btn2,btn3,btn4;
    //获取MotorService实例
    private ServiceConnection motorServiceConnection=new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mMotorController=MotorController.Stub.asInterface(service);
            try{
                mMotorController.setDrvType(0);
            }catch (RemoteException e){
                e.printStackTrace();
            }
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
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.forward(3000);
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
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.back(3000);
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
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.left(3000);
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
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.right(3000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
		
		//向左转
        btnMotorTurnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.turnLeft(2000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //向右转
        btnMotorTurnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.turnRight(2000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        //头向上
        btnHeadUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.headUp(3000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //头向下
        btnHeadDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.headDown(3000);
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
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.headLeftEnd();
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
					mMotorController.setDrvType(0);
					mMotorController.setSpeed(50);
                    mMotorController.headRightEnd();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
		//头恢复到中间
		btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					mMotorController.setDrvType(0);
                    mMotorController.setSpeed(50);
                    mMotorController.headLeftTurnMid();
					mMotorController.headUpDownTurnMid();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
		//左右摇头
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					mMotorController.setDrvType(0);
                    mMotorController.setSpeed(50);
					mMotorController.headUpDownStop();
                    mMotorController.headShake();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //停止摇头
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMotorController.headStop();
                    mMotorController.headUpDownStop();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
		//上下点头
		btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
					mMotorController.setDrvType(0);
                    mMotorController.setSpeed(50);
                    mMotorController.headStop();
                    mMotorController.headUpAndDown();
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
        btn1=(Button)findViewById(R.id.btn_head_mid);
        btn2=(Button)findViewById(R.id.btn_head_shake);
        btn3=(Button)findViewById(R.id.btn_head_stop);
		btn4=(Button)findViewById(R.id.btn_head_Up_Down);
    }
    private void bindMotorService(){
        Intent intent = new Intent();
        intent.setAction("com.yongyida.robot.MotorService");
        intent.setPackage("com.yongyida.robot.motorcontrol");
        bindService(intent,motorServiceConnection, Context.BIND_AUTO_CREATE);
    }
	
}
