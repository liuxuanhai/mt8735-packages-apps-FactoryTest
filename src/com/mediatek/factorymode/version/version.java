package com.mediatek.factorymode.version;

import com.mediatek.factorymode.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.factorymode.Utils;
import java.lang.System;
import android.os.SystemProperties;

public class version extends Activity {
	private TextView mInfo;
	private TextView textView001;
	private SharedPreferences mSp;

	public void VersionTest() {
		StringBuilder localStringBuilder = new StringBuilder();
		String display_id = SystemProperties.get("ro.build.display.id");
		String value = SystemProperties.get("ro.build.date");
		localStringBuilder.append(display_id).append("\n").append(value);// S006-SSD-P4-FM-ZH
		mInfo.setText(localStringBuilder.toString());

		String barCode = SystemProperties.get("gsm.serial");
		if (!TextUtils.isEmpty(barCode)) {
			textView001.setText(getResources().getString(R.string.version_barcode) + barCode);
		} else {
			textView001.setText(getResources().getString(R.string.version_barcode) + getResources().getString(R.string.version_cant));
		}
	}

	public void button001(View view) {
		finish();
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.version);
		SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
		this.mSp = localSharedPreferences;
		TextView localTextView = (TextView) findViewById(R.id.version_info);
		mInfo = localTextView;
		textView001 = (TextView) findViewById(R.id.textview001_activity_version);

		VersionTest();
	}
}
