package com.yyd.robot.misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.SecurityException;

import android.R.integer;
import android.R.string;
import android.util.Log;

public class Misc {

	private static final String TAG = "miscLib";
	/*
	 * Do not remove or rename the field mFd: it is used by native method close();
	 */

	public Misc(){
		Log.d(TAG, " after load_library!");
	}

	public native int open();
	public native int close();
	public native int setGsensorData(String ctrl, int len);
	static {
		System.loadLibrary("misc");
	}
}