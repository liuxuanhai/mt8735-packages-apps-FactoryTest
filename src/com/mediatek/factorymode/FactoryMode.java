package com.mediatek.factorymode;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.util.Log;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

// import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

import com.mediatek.factorymode.motor.MotorActivity;
import com.mediatek.factorymode.version.version;
import com.mediatek.utils.UIUtils;

public class FactoryMode extends Activity implements
		AdapterView.OnItemClickListener {
	public View.OnClickListener cl;
	final int[] itemString;
	private MyAdapter mAdapter;
	private GridView mGrid;
	private String TAG = "FactoryMode";

	private List mListData;
	private SharedPreferences mSp = null;

	public FactoryMode() {
		int[] arrayOfInt = { R.string.touchscreen_name, R.string.lcd_name,
				R.string.battery_name,
				R.string.speaker_name, R.string.microphone_name,
				R.string.wifi_name, R.string.bluetooth_name,
				R.string.telephone_name, R.string.backlight_name,
				R.string.memory_name, R.string.sdcard_name,
				R.string.gsensor_name, R.string.camera_name, R.string.motor,
				R.string.modify_volume, R.string.touch,
				R.string.gsensor, R.string.five_mic, R.string.auto_charge,
				R.string.ultrasonic, R.string.foot_motor};
		this.itemString = arrayOfInt;
	}
	//R.string.otg ,R.string.gps_name,R.string.infrared 暂时去掉otg,gps,infrared测试
	private void SetColor(TextView paramTextView) {
		if (this.itemString.length == 0) {
			return;
		}
		SharedPreferences localSharedPreferences1 = getSharedPreferences(
				"FactoryMode", 0);
		this.mSp = localSharedPreferences1;
		int localObject = 0;
		int i = this.itemString.length;
		while (true) {
			if (localObject >= i)
				break;
			Resources localResources = getResources();
			int j = this.itemString[localObject];
			String str1 = localResources.getString(j);
			String str2 = paramTextView.getText().toString();
			String str4;
			if (str1.equals(str2)) {
				SharedPreferences localSharedPreferences2 = this.mSp;
				int k = this.itemString[localObject];
				String str3 = getString(k);
				str4 = localSharedPreferences2.getString(str3, null);
				if (str4.equals("success")) {
					int l = getApplicationContext().getResources().getColor(
							R.color.Blue);
					paramTextView.setTextColor(l);
				} else if (str4.equals("default")) {
					int i1 = getApplicationContext().getResources().getColor(
							R.color.black);
					paramTextView.setTextColor(i1);
				} else if (str4.equals("failed")) {
					int i2 = getApplicationContext().getResources().getColor(
							R.color.Red);
					paramTextView.setTextColor(i2);
				}
			}
			++localObject;
		}
	}

	private List getData() {
		boolean bool1 = true;
		ArrayList localArrayList = new ArrayList();
		SharedPreferences localSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int localObject = 0;
		while (true) {
			int i = this.itemString.length;
			if (localObject >= i) {
				break;
			}
			int j = this.itemString[localObject];
			String str1 = getString(j);
			boolean bool2 = localSharedPreferences.getBoolean(str1, bool1);
			if (bool1 == bool2) {
				int k = this.itemString[localObject];
				String str2 = getString(k);
				localArrayList.add(str2);
			}
			++localObject;
		}
		return localArrayList;
	}

	private void init() {
		String str1 = "default";
		SharedPreferences localSharedPreferences = getSharedPreferences(
				"FactoryMode", 0);
		this.mSp = localSharedPreferences;
		SharedPreferences.Editor localEditor = this.mSp.edit();
		int localObject = 0;
		while (true) {
			int i = this.itemString.length;
			if (localObject >= i)
				break;
			int j = this.itemString[localObject];
			String str2 = getString(j);
			String exist = localSharedPreferences.getString(str2, null);
			if (exist == null)
				localEditor.putString(str2, str1);
			++localObject;
		}
		String str3 = getString(R.string.headsethook_name);
		localEditor.putString(str3, str1);
		localEditor.commit();
	}

	protected void onActivityResult(int paramInt1, int paramInt2,
			Intent paramIntent) {
		System.gc();
		Intent localIntent = new Intent();
		localIntent.setClassName(this, "com.mediatek.factorymode.Report");
		startActivity(localIntent);
	}

	public void onCreate(Bundle paramBundle) {
		Log.d(TAG, "onCreate");

		sendBroadcast(new Intent("com.yongyida.robot.FACTORYSTART"));
		sendBroadcast(new Intent("com.yydrobot.STOP"));
		
		Intent intent=new Intent("com.yongyida.robot.VOICE");
        intent.putExtra("data","rotate_off");    //进入工厂模式关闭声源定位
        sendBroadcast(intent);

		requestWindowFeature(1);
		super.onCreate(paramBundle);
		setContentView(R.layout.main);

		init();

		/* 注册光机重力感应校准广播 */
		IntentFilter intentCaliFilter = new IntentFilter();
		intentCaliFilter.addAction("com.yongyida.factorytest.caliIsSuccess");
		registerReceiver(caliBroadcastReceiver, intentCaliFilter);
		
		/*注册5mic测试广播*/
		IntentFilter fiveMicFilter=new IntentFilter();
        fiveMicFilter.addAction("com.yongyida.factorytest.micTestSuccess");
        registerReceiver(micBroadcastReceiver,fiveMicFilter);

		this.mGrid = (GridView) findViewById(R.id.main_grid);
		this.mListData = getData();
		this.mAdapter = new MyAdapter(this);
	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(caliBroadcastReceiver); // 注销光机重力感应广播
		unregisterReceiver(micBroadcastReceiver);  //注销5mic测试广播
		
		Intent intent=new Intent("com.yongyida.robot.VOICE");
        intent.putExtra("data","rotate_on");    //退出工厂模式打开声源定位
        sendBroadcast(intent);
		
		sendBroadcast(new Intent("com.yongyida.robot.FACTORYCLOSE"));
		Log.e(TAG, "onDestroy");
		Process.killProcess(Process.myPid());
	}

	public void button001(View view) {
		onDestroy();
	}

	public void button002(View view) {
		Intent intent = new Intent().setClass(this, version.class);
		startActivity(intent);
	}

	public void onItemClick(AdapterView paramAdapterView, View paramView,
			int paramInt, long paramLong) {

		Intent localIntent = new Intent();
		localIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
		String str1 = (String) this.mListData.get(paramInt);
		String str2 = null;
		Log.d(TAG, "onItemClick");
		if (getString(R.string.speaker_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.audio.AudioTest";
		} else if (getString(R.string.battery_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.BatteryLog";
		} else if (getString(R.string.touchscreen_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.touchscreen.TouchPadTest";
		} else if (getString(R.string.camera_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.camera.CameraTest";
		} else if (getString(R.string.wifi_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.wifi.WiFiTest";
		} else if (getString(R.string.bluetooth_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.bluetooth.Bluetooth";
		} else if (getString(R.string.telephone_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.signal.Signal";
		} /*else if (getString(R.string.gps_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.gps.GPS";
		}*/ else if (getString(R.string.backlight_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.backlight.BackLight";
		} else if (getString(R.string.memory_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.memory.Memory";
		} else if (getString(R.string.microphone_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.microphone.MicRecorder";
		} else if (getString(R.string.gsensor_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.sensor.GSensor";
		} else if (getString(R.string.msensor_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.sensor.MSensor";
		} else if (getString(R.string.lsensor_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.sensor.LSensor";
		} else if (getString(R.string.psensor_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.sensor.PSensor";
		} else if (getString(R.string.sdcard_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.sdcard.SDCard";
		} else if (getString(R.string.lcd_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.lcd.LCD";
		} else if (getString(R.string.subcamera_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.camera.SubCamera";
		} else if (getString(R.string.led_name).equals(str1)) {
			str2 = "com.mediatek.factorymode.led.Led";
		} else if (getString(R.string.version).equals(str1)) {
			str2 = "com.mediatek.factorymode.version.version";
		} else if (getString(R.string.motor).equals(str1)) {
			str2 = "com.mediatek.factorymode.motor.MotorActivity";
		} else if (getString(R.string.modify_volume).equals(str1)) {
			str2 = "com.mediatek.factorymode.volume.VolumeActivity";
		} else if (getString(R.string.infrared).equals(str1)) {
			str2 = "com.mediatek.factorymode.infrared.InfraredActivity";
		} else if (getString(R.string.touch).equals(str1)) {
			str2 = "com.mediatek.factorymode.touch.TouchActivity";
		} else if (getString(R.string.gsensor).equals(str1)) {
			sendBroadcast(new Intent("com.yydrobot.STARTGSENSOR")
					.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES));
		} else if (getString(R.string.five_mic).equals(str1)) {
			Intent intent=new Intent("com.yydrobot.MICTEST");
			intent.putExtra("data","startMicTest");
			sendBroadcast(intent);
		} else if(getString(R.string.auto_charge).equals(str1)){
			str2 = "com.mediatek.factorymode.power.AutoChargePower";
		} else if(getString(R.string.foot_motor).equals(str1)){
			str2 = "com.mediatek.factorymode.power.FootMotorPower";
		} else if(getString(R.string.ultrasonic).equals(str1)){
			str2 = "com.mediatek.factorymode.power.UltrasonicPower";
		}
		/*
		 * else if(getString(R.string.otg).equals(str1)){
		 * str2="com.mediatek.factorymode.otg.OTG"; }
		 */
		if (str2 != null) {
			localIntent.setClassName(this, str2);
			startActivity(localIntent);
		}
	}

	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume");
		this.mGrid.setAdapter(this.mAdapter);
		this.mGrid.setOnItemClickListener(this);
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MyAdapter(Context arg2) {
			LayoutInflater localLayoutInflater = LayoutInflater.from(arg2);
			this.mInflater = localLayoutInflater;
		}

		public MyAdapter(FactoryMode paramInt, int arg3) {
		}

		public int getCount() {
			if (FactoryMode.this.mListData == null)
				return 0;
			return FactoryMode.this.mListData.size();
		}

		public Object getItem(int paramInt) {
			return Integer.valueOf(paramInt);
		}

		public long getItemId(int paramInt) {
			return paramInt;
		}

		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			View localView = this.mInflater.inflate(R.layout.main_grid, null);
			TextView localTextView = (TextView) localView
					.findViewById(R.id.factor_button);
			localTextView.setTextSize(20);
			CharSequence localCharSequence = (CharSequence) FactoryMode.this.mListData
					.get(paramInt);
			localTextView.setText(localCharSequence);
			FactoryMode.this.SetColor(localTextView);
			int pWidth = mGrid.getWidth();
			int pHight = mGrid.getHeight();
			GridView.LayoutParams params = new GridView.LayoutParams(
					pWidth / 4, pHight / 6);
			localView.setLayoutParams(params);
			return localView;
		}
	}

	/* 光机校准成功或失败广播 */
	private BroadcastReceiver caliBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					"com.yongyida.factorytest.caliIsSuccess")) {
				if (intent.getBooleanExtra("result", false)) {
					SharedPreferences sharedPreferences = context
							.getSharedPreferences("FactoryMode", 0);
					Utils.SetPreferences(context, sharedPreferences,
							R.string.gsensor, "success");
				} else {
					SharedPreferences sharedPreferences = context
							.getSharedPreferences("FactoryMode", 0);
					Utils.SetPreferences(context, sharedPreferences,
							R.string.gsensor, "failed");
				}
			}
		}
	};
	
	/*5mic测试成功或失败广播*/
	private BroadcastReceiver micBroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.yongyida.factorytest.micTestSuccess")){
                if(intent.getBooleanExtra("result",false)){
                    SharedPreferences sharedPreferences = context.getSharedPreferences("FactoryMode", 0);
                    Utils.SetPreferences(context, sharedPreferences, R.string.five_mic, "success");
                }else {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("FactoryMode", 0);
                    Utils.SetPreferences(context, sharedPreferences, R.string.five_mic, "failed");
                }
            }
        }
    };
}
