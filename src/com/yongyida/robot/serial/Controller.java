/**
 * Copyright (C) 2015 Zhensheng Yongyida Robot Co.,Ltd. All rights reserved.
 * 
 * @author: hujianfeng@yongyida.com
 * @version 0.1
 * @date 201-05-14
 * 
 */
package com.yongyida.robot.serial;

/**
 * 机器人控制接口
 * 
 */
public interface Controller {
	
	public int open(String devName);
	
	public int close();
	
	/**
	 * 设置驱动类型
	 * @param drvType
	 * 
	 */
	public void setDrvType(int drvType);
	
	/**
	 * 设置速度
	 * @param speed
	 * 
	 */
	public void setSpeed(int speed);
	
	/**
	 * 设置控制监听
	 * @param ControllListener
	 * 
	 */
	public void setControllListener(ControllListener listener);
	
	/**
	 * doCommand, 执行命令
	 * 
	 * @param int leftDirection, 左电机转动方向, 0:正转, -1: 反转
	 *        int rightDirection, 右电机转动方向, 0:正转, -1: 反转
	 *        float leftSpeed, 左电机转动速率
	 *		  float rightSpeed, 右电机转动速率
	 *        int argType, 参数类型, 0: 参数表示时间（单位：毫秒）, 1: 参数表示距离（单位：厘米）
     *        int argValue, 参数值, 表示转动的距离或时间
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int doCommand(
			int leftDirection,
			int rightDirection,
			float leftSpeed,
			float rightSpeed,
			int argType,
			int argValue);
	
	/**
	 * forward, 前进
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int forward(int arg);

	/**
	 * back, 后退
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int back(int arg);

	/**
	 * left, 左转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int left(int arg);

	/**
	 * right, 右转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int right(int arg);
	
	/**
	 * stop, 停止
	 * 
	 * @param void
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int stop();
	
	/**
	 * doHeadCommand, 执行头部命令
	 * 
	 * @param int motorSelect, 电机选择, 0为左右摇头电机, 1为上下点头电机
	 *        int direct, 电机转动方向, 0:正转, 1:反转
	 *        float motorSpeed, 电机转动速率
	 *        int argType, 参数类型, 0: 参数表示时间（单位：毫秒）, 1: 参数表示距离（单位：厘米）
     *        int argValue, 参数值, 表示转动的距离或时间
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int doHeadCommand(
			int motorSelect,
			int direct,
			float motorSpeed,
			int argType,
			int argValue);

	/**
	 * headLeft, 头部左转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headLeft(int arg);

	/**
	 * headRight, 头部右转
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headRight(int arg);
	
	/**
	 * headUp, 头部向上
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headUp(int arg);

	/**
	 * headDown, 头部向下
	 * 
	 * @param int arg, 参数
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headDown(int arg);
	
	/**
	 * headStop, 停止
	 * 
	 * @param void
	 * 
	 * @return int, 返回值
	 * 
	 */
	public int headStop();
}

