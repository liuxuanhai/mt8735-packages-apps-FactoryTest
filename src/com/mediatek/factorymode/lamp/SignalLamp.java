package com.mediatek.factorymode.lamp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.yyd.breathled.BreathLed;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class SignalLamp extends Activity{
    private final String TAG="SignalLamp";
    private final String LED_CHEST = "chest";
    private final String LED_EAR = "ear";
    private final String POWER_ON = "on";
    private final String POWER_OFF = "off";
    private Button led_chest;
    private Button led_ear;
	private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signal_lamp);
        initView();
    }
         private void initView() {
        led_chest=(Button)findViewById(R.id.led_chest);
        led_ear=(Button)findViewById(R.id.led_ear);
        textView=(TextView)findViewById(R.id.textView);
        led_chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreathLed breathLed = new BreathLed();
                breathLed.openDev();
                if (breathLed.getOnoff(LED_CHEST)==0) {
                    if(breathLed.setOnoff(LED_CHEST, POWER_ON)==0){
                        textView.setText("呼吸灯成功打开");
                        textView.setTextColor(Color.rgb(0, 255, 0));
                    }else {
                        textView.setText("呼吸灯打开失败");
                        textView.setTextColor(Color.rgb(255, 0, 0));
                    }
                }else if (breathLed.getOnoff(LED_CHEST)==1){
                    if(breathLed.setOnoff(LED_CHEST,POWER_OFF)==0){
                        textView.setText("呼吸灯成功关闭");
                        textView.setTextColor(Color.rgb(0, 255, 0));
                    }else {
                        textView.setText("呼吸灯关闭失败");
                        textView.setTextColor(Color.rgb(255, 0, 0));
                    }
                }else{
                    textView.setText("呼吸灯设备故障");
                    textView.setTextColor(Color.rgb(255, 0, 0));
                }
                breathLed.closeDev();
                breathLed = null;
            }
        });

        led_ear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreathLed breathLed = new BreathLed();
                breathLed.openDev();
                if (breathLed.getOnoff(LED_EAR)==0) {
                    if(breathLed.setOnoff(LED_EAR, POWER_ON)==0){
                        textView.setText("两耳信号灯成功打开");
                        textView.setTextColor(Color.rgb(0, 255, 0));
                    }else {
                        textView.setText("两耳信号灯打开失败");
                        textView.setTextColor(Color.rgb(255, 0, 0));
                    }
                }else if(breathLed.getOnoff(LED_EAR)==1){
                    if(breathLed.setOnoff(LED_EAR,POWER_OFF)==0){
                        textView.setText("两耳信号灯成功关闭");
                        textView.setTextColor(Color.rgb(0, 255, 0));
                    }else {
                        textView.setText("两耳信号灯关闭失败");
                        textView.setTextColor(Color.rgb(255, 0, 0));
                    }
                }else{
                    textView.setText("两耳信号灯设备故障");
                    textView.setTextColor(Color.rgb(255, 0, 0));
                }
                breathLed.closeDev();
                breathLed = null;
            }
        });
    }
    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.signal_lamp, "success");
        finish();
    }

    public void button003(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.signal_lamp, "failed");
        finish();
    }
}
