package com.yongyida.irremote.utils;

import android.util.Log;

public class ShowLogs {
	public static void showLog(String category, String tag, String msg){
		if(Globals.DEBUG){
			if ("d".equalsIgnoreCase(category)) {
				Log.d(tag, msg);
			} else if ("e".equalsIgnoreCase(category)) {
				Log.e(tag, msg);
			} else if ("i".equalsIgnoreCase(category)) {
				Log.i(tag, msg);
			} else if ("v".equalsIgnoreCase(category)) {
				Log.v(tag, msg);
			} else if ("w".equalsIgnoreCase(category)) {
				Log.w(tag, msg);
			}    
		}
	}
}
