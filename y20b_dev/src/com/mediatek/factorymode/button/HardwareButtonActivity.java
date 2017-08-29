package com.mediatek.factorymode.button;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class HardwareButtonActivity extends Activity {
    public static final String BUTTON_SWITCH = "com.yongyida.robot.ACTION_HDMI_SHORT_PRESSED";
    private TextView tvButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_button_hardwarebutton);
        tvButton=(TextView)findViewById(R.id.tv_button);

        IntentFilter intent=new IntentFilter();
        intent.addAction(BUTTON_SWITCH);
        registerReceiver(buttonSwitch,intent);
    }

    private BroadcastReceiver buttonSwitch=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(BUTTON_SWITCH.equals(intent.getAction())){
                tvButton.setBackgroundColor(Color.rgb(0, 255, 0));
            }
        }
    };
    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.hardwareButton, "success");
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.hardwareButton, "failed");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(buttonSwitch);
    }
}
