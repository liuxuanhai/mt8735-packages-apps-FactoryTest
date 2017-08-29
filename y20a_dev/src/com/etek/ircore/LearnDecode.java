package com.etek.ircore;

/**
 * Created by 应用 on 2015/11/13.
 */
public class LearnDecode {
	private static final String libETLearnLib = "ETLearnLib";
	
    static {
        System.loadLibrary(libETLearnLib);
    }
    
    public static native  byte[] getET4007LearnCode(byte[] code);
//    public static native  byte[] getET4003LearnCode(byte[] code);
}
