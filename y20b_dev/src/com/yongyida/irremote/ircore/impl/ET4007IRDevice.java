package com.yongyida.irremote.ircore.impl;

import com.etek.ircore.LearnDecode;
import com.etek.ircore.RemoteCore;
import com.ircode.IRCode;
import com.yongyida.irremote.ircore.IInfraredDevice;
import com.yongyida.irremote.utils.ETLogger;
import com.yongyida.irremote.utils.Tools;

import android.util.Log;

public class ET4007IRDevice implements IInfraredDevice {
	private final boolean DEBUG = true;
	
	// private static final String ET_IR_LEARN =
	// "/sys/class/etek/sec_ir/ir_learn";
	private static final String ET_IR_STATE = "/sys/class/etek/sec_ir/ir_state";

	/**
	 *  红外发送方法 
	 *  @param freq 红外频率
	 *  @param data 按键的红外数据
	 * */
	@Override
	public void sendIr(int freq, int[] data) {
		if(DEBUG) ETLogger.debug("sendIr: freq = " + freq + " , data.length = "
				+ (data == null ? -1 : data.length));
		IRCode irCode = new IRCode(freq, data);
		if(DEBUG) ETLogger.debug("new IRCode(freq, data)");
		byte[] codes = RemoteCore.prontoToETcode(freq, data);  // 协议
		if(DEBUG) ETLogger.debug(" RemoteCore.prontoToETcode(freq, data)");
		
//		ETLogger.debug("irCode: " + JSON.toJSONString(irCode));
//		ETLogger.debug("code start: codes.length = " + codes.length);
// 		String codeStr = Tools.bytesToHexString(codes);
//		ETLogger.debug("irCode_ToHexString: " + Tools.bytesToHexString(codes));
		RemoteCore.sendIRCode(codes, codes.length);   // 此处卡顿
		if(DEBUG) ETLogger.debug("finished sendIRCode");
	}
	
	

	public ET4007IRDevice() {
		RemoteCore.IRinit();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean hasLearn() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 接收红外数据
	 * @return 返回IRCode对象，成员有频率和数据
	 * */
	@Override
	public IRCode irCodeReceiver() {
		// TODO Auto-generated method stub
		// String data = Tools.readSysFile(ET_IR_LEARN);
		// byte[] learnNewData = Tools.strToIntarray(data);
		// IRCode ircode = RemoteCore.ET4007Learn(learnNewData);
		
		IRCode irCode = null;
		if(DEBUG) Log.i(TAG, "irCodeReceiver  1");
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
				byte[] data_code = null;
				do{
					byte[] data = RemoteCore.readLearnIRCode();
					if(DEBUG) Log.i(TAG, "irCodeReceiver  2");
					data_code = LearnDecode.getET4007LearnCode(data);     // 转换
					if(DEBUG) Log.i(TAG, "irCodeReceiver  3");
				} while(data_code == null || data_code.length < 15);
				if(DEBUG) Log.i(TAG, "irCodeReceiver  data=" + new String(data_code));
//				irCode = RemoteCore.ET4007Learn(data_code);
//				
//			}
//		}).start();
		if(DEBUG) Log.i(TAG, "irCodeReceiver  4");
		
		
		
		return irCode;
	}

	/**
	 * 发送 1 - 学习模式；
	 * 发送 0 - 终止学习模式
	 * */
	@Override
	public boolean sendLearnCmd(int cmd) {
		if (cmd == 1) {
			RemoteCore.learnIRCodeStart();
			if(DEBUG) Log.i(TAG, "sendLearnCmd  1");
			RemoteCore.setLearnTimeout(60);
		} else {
			RemoteCore.learnIRCodeStop();
		}
		return false;
	}

	/**
	 * 学习红外成功与否的结果
	 * */
	@Override
	public boolean getState() {
		int ret = RemoteCore.learnIRCodeMain();
		String state = Tools.readSysFile(ET_IR_STATE);
		// Logger.debug("et4007 state is "+state);
		if ("1".equals(state)) {
			return true;
		} else {
			return false;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public int getLearnIRCode() {
		int ret = RemoteCore.learnIRCodeMain();
		return ret;
	}

}
