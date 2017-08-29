package com.mediatek.factorymode.hdmi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class HdmiTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_hdmi_hdmitest);
    }

    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.hdmi, "success");
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.hdmi, "failed");
        finish();
    }
}
