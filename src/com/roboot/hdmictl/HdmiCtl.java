package com.roboot.hdmictl;

public class HdmiCtl {
	
	public static native int HdmiPowerOFF(); 
	public static native int HdmiPowerON(); 
	public static native int HdmiSwitchInternal(int dist);
	public static native int HdmiSwitchExternal(int dist);
	
}
