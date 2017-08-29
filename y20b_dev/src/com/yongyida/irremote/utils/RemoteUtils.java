package com.yongyida.irremote.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.widget.Toast;

import com.etek.ircore.RemoteCore;
import com.yongyida.irremote.constant.ICType;

public class RemoteUtils {
	private final static String TAG = "RemoteUtils";
	static Toast toast = null;

	private static final String ET_IR_SEND = "/sys/class/etek/sec_ir/ir_send";

	public static int getICType() {
		File file = new File(ET_IR_SEND);
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
		ETLogger.debug("version = " + version);
		String[] values = version.split(",");
		if (values[0].equalsIgnoreCase("0X28") && values[1].equalsIgnoreCase("0X07")
				&& values[2].equalsIgnoreCase("0X08")) {
			RemoteCore.IRinit();
			
			ShowLogs.showLog("i", TAG, "IRinit..........................................");
			return ICType.ET4007;

		} else {
			
			return ICType.DUMMY;
		}
	}
}
