package com.yongyida.irremote.ircore.impl;

import android.annotation.SuppressLint;

import com.ircode.IRCode;
import com.yongyida.irremote.ircore.IInfraredDevice;
import com.yongyida.irremote.utils.ETLogger;

public class DummyIRDevice implements IInfraredDevice
{
	
	@SuppressLint("NewApi") @Override
	public void sendIr(int freq, int[] data)
	{
		// TODO Auto-generated method stub
		ETLogger.debug("dummy sendIr..........####......freq = "+freq+" , data.length = "+(data==null?-1:data.length));
		
	}

	@Override
	public boolean hasLearn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IRCode irCodeReceiver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendLearnCmd(int cmd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getLearnIRCode() {
		return 0;
	}


}
