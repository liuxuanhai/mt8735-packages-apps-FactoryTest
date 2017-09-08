package com.mediatek.factorymode.touch;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

public class TouchActivity extends Activity {

	public Boolean REGISTERED;
	public Boolean BUTTON001_PRESSED;

	private TextView txt000,txt001,txt002,txt003,txt004,txt005;
	private TextView txt006,txt007,txt008,txt009,txt010,txt011,txt012;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);

		BUTTON001_PRESSED = false;
		REGISTERED = false;

		txt000 = (TextView) findViewById(R.id.txt_tip);
		txt001 = (TextView) findViewById(R.id.txt_head_left);
		txt002 = (TextView) findViewById(R.id.txt_head_right);
		txt003 = (TextView) findViewById(R.id.txt_ear_left);
		txt004 = (TextView) findViewById(R.id.txt_ear_right);
		txt005 = (TextView) findViewById(R.id.txt_shoulder_left);
		txt006 = (TextView) findViewById(R.id.txt_shoulder_right);
		txt007 = (TextView) findViewById(R.id.txt_arm_left);
		txt008 = (TextView) findViewById(R.id.txt_arm_right);
		txt009 = (TextView) findViewById(R.id.txt_head_back);
		txt010 = (TextView) findViewById(R.id.txt_head_chin);
		txt011 = (TextView) findViewById(R.id.txt_chest);
		txt012 = (TextView) findViewById(R.id.txt_body_infrared);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			String extra = intent.getStringExtra("android.intent.extra.Touch");
			String result = intent.getStringExtra("android.intent.extra.yyd");
			String ps_sta = intent.getStringExtra("android.intent.extra.PS");
			Log.i("TouchActivity", "Action: " + action + ", Extra string: " + extra);
			if(result!=null){
				if(result.equals("yyd10")){
					txt001.setBackgroundColor(Color.rgb(0, 255, 0));
					txt000.setText(R.string.touch_head_left);
				}else if(result.equals("yyd11")){
					txt002.setBackgroundColor(Color.rgb(0, 255, 0));
					txt000.setText(R.string.touch_head_right);
				}
			}
			if(ps_sta!=null){
				if(ps_sta.equals("pir_in")){
					txt012.setBackgroundColor(Color.rgb(0, 255, 0));
					txt000.setText(R.string.touch_middle);
				}else if(ps_sta.equals("pir_out")){
					txt012.setBackgroundColor(Color.rgb(255, 0, 0));
					txt000.setText(R.string.touch_middle);
				}
			}
			if(extra!=null){
				switch (extra){
					case "yyd9":
						txt003.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_ear_left);
						break;
					case "yyd8":
						txt004.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_ear_right);
						break;
					case "yyd7":
						txt005.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_shoulder_left);
						break;
					case "yyd5":
						txt006.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_shoulder_right);
						break;
					case "yyd6":
						txt007.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_arm_left);
						break;
					case "yyd4":
						txt008.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_arm_right);
						break;
					case "yyd2":
						txt010.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_head_chin);
						break;
					case "yyd0":
						txt009.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_body_back);
						break;
					case "yyd1":
						txt011.setBackgroundColor(Color.rgb(0, 255, 0));
						txt000.setText(R.string.touch_chest);
						break;

					default:
						break;

				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (REGISTERED) {
			unregisterReceiver(mReceiver);
		}
	}

	public void button001(View view) {
		if (BUTTON001_PRESSED) {
			return;
		}
		REGISTERED = true;
		BUTTON001_PRESSED = true;
		IntentFilter filter = new IntentFilter("TouchSensor");
		registerReceiver(mReceiver, filter);
	}

	public void button002(View view) {
		if (REGISTERED) {
			unregisterReceiver(mReceiver);
			REGISTERED = false;
		}
	}

	public void button003(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.touch, "success");
		finish();
	}

	public void button004(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.touch, "failed");
		finish();
	}
}
