package com.mediatek.factorymode.infrared;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.mediatek.factorymode.volume.VolumeActivity;
import com.yongyida.irremote.ircore.IInfraredDevice;
import com.yongyida.irremote.ircore.impl.DummyIRDevice;
import com.yongyida.irremote.ircore.impl.ET4007IRDevice;
import com.yongyida.irremote.utils.Globals;
import com.yongyida.irremote.utils.RemoteUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class InfraredActivity extends Activity {

	private IInfraredDevice mDevice = null;
	private int freq = 38000; // 发送参数
	private int[] poweroff = { 170, 166, 23, 59, 23, 19, 22, 59, 23, 60, 23, 18, 22, 19, 22, 59, 23, 19, 22, 18, 22,
			61, 22, 18, 22, 19, 22, 59, 23, 60, 22, 18, 22, 61, 22, 18, 22, 60, 23, 59, 23, 60, 23, 60, 23, 18, 22, 60,
			23, 60, 22, 60, 22, 18, 22, 19, 22, 18, 22, 19, 22, 59, 23, 19, 22, 18, 22, 60, 23, 60, 22, 60, 23, 18, 22,
			19, 22, 18, 22, 19, 22, 18, 22, 19, 22, 18, 22, 18, 23, 59, 23, 60, 23, 59, 23, 61, 22, 59, 23, 198, 169,
			167, 22, 59, 23, 19, 22, 59, 23, 60, 23, 18, 22, 19, 22, 59, 23, 19, 22, 18, 22, 61, 22, 18, 22, 19, 22,
			59, 23, 61, 22, 18, 22, 60, 23, 18, 23, 59, 23, 61, 22, 59, 23, 60, 23, 18, 22, 60, 23, 59, 23, 60, 23, 19,
			22, 18, 22, 18, 22, 19, 22, 60, 22, 19, 22, 18, 22, 60, 23, 60, 23, 60, 22, 19, 22, 18, 22, 18, 22, 19, 22,
			18, 22, 19, 22, 18, 22, 19, 22, 58, 23, 60, 23, 60, 22, 60, 23, 60, 23, 3842 }; // POWEROFF

	public Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infrared);

		Globals.ICType = RemoteUtils.getICType();
		mDevice = new ET4007IRDevice();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		super.onDestroy();
	}

	public void button001(View view) {
		if (getICType()) {
			if (timer == null) {
				timer = new Timer();
			}
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					mDevice.sendIr(freq, poweroff);
				}
			}, 50, 200);
		}
	}

	public void button002(View view) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void button003(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.infrared, "success");
		finish();
	}

	public void button004(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.infrared, "failed");
		finish();
	}

	public Boolean getICType() {
		File file = new File("/sys/class/etek/sec_ir/ir_send");
		FileReader rd;
		char[] buf = new char[20];
		try {
			rd = new FileReader(file);
			int len = rd.read(buf);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String version = String.valueOf(buf);
		Log.d("DUBBER", "version = " + version);
		String[] values = version.split(",");
		if (values[0].equalsIgnoreCase("0X28") && values[1].equalsIgnoreCase("0X07")
				&& values[2].equalsIgnoreCase("0X08")) {
			Log.i("infrared", "infrared..........................................");
			return true;
		} else {
			return false;
		}
	}

}
