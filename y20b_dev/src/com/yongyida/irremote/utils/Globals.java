package com.yongyida.irremote.utils;

import com.yongyida.irremote.ircore.IInfraredDevice;
import com.yongyida.irremote.ircore.impl.ET4007IRDevice;

public class Globals {
	public static String REMOTE_SHARED_PREFF;
	public static boolean DEBUG = true; // 调试信息打印使能

	public final static int LEARN_RESUIT_REQUEST = 100;

	public static int deviceID = 1;
	public static String deviceName = "";
	public static int ICType;
	public static int LocalLanguage;
	public static boolean INITIAL;
	public static boolean isAdd;

	public static boolean isIrSendOK = false;

	public static boolean isGetList = false;

	static IInfraredDevice infDevice;

	public static IInfraredDevice getInfraredDevice() {
		if (infDevice == null) {
			infDevice = getDeviceType();
		}
		return infDevice;
	}

	public static IInfraredDevice getDeviceType() {
		IInfraredDevice mDevice;

		switch (Globals.ICType) {
		case com.yongyida.irremote.constant.ICType.ET4007:
			mDevice = new ET4007IRDevice();
			break;
		default:
			mDevice = new ET4007IRDevice();
			// mDevice = new DummyIRDevice();
			break;

		}
		return mDevice;
	}

}
