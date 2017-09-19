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

	public TextView textView000;
	public TextView textView001;
	public TextView textView002;
	public TextView textView003;
	public TextView textView004;
	public TextView textView006;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);

		BUTTON001_PRESSED = false;
		REGISTERED = false;

		//textView000 = (TextView) findViewById(R.id.textview000_activity_touch);
		
		textView001 = (TextView) findViewById(R.id.textview001_activity_touch);
		textView002 = (TextView) findViewById(R.id.textview002_activity_touch);
		textView003 = (TextView) findViewById(R.id.textview003_activity_touch);
		textView004 = (TextView) findViewById(R.id.textview004_activity_touch);
		textView006 = (TextView) findViewById(R.id.textview006_activity_touch);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			String extra = intent.getStringExtra("android.intent.extra.Touch");
			Log.i("TouchActivity", "Action: " + action + ", Extra string: " + extra);
			
//			if (extra.equals("t_pir")) {
//				textView000.setBackgroundColor(Color.rgb(0, 255, 0));
//				textView006.setText(R.string.touch_middle);
//			}

			if (extra.equals("t_head")) {
				textView001.setBackgroundColor(Color.rgb(0, 255, 0));
				textView006.setText(R.string.touch_head);
			}
			if (extra.equals("t_back")) {
				textView002.setBackgroundColor(Color.rgb(0, 255, 0));
				textView006.setText(R.string.touch_back);
			}
			if (extra.equals("t_left")) {
				textView003.setBackgroundColor(Color.rgb(0, 255, 0));
				textView006.setText(R.string.touch_left);
			}
			if (extra.equals("t_right")) {
				textView004.setBackgroundColor(Color.rgb(0, 255, 0));
				textView006.setText(R.string.touch_right);
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (REGISTERED) {
			unregisterReceiver(mReceiver);
		}
	};

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
