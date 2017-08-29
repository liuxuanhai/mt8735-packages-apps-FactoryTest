package com.mediatek.factorymode.lamp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class ProjectionLamp extends Activity{
	public final static String ACTION_OPEN = "com.yongyida.robot.hdmi.ACTION_OPEN";
    public final static String ACTION_CLOSE = "com.yongyida.robot.hdmi.ACTION_CLOSE";
    public final static String ACTION_EXTERNEL = "com.yongyida.robot.hdmi.ACTION_EXTERNEL";
    public final static String ACTION_INTERNEL = "com.yongyida.robot.hdmi.ACTION_INTERNEL";
	private Button projectionLcdBtn;
	private Button openBtn,closeBtn;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projection_lamp);
		projectionLcdBtn=(Button)findViewById(R.id.projection_lcd_btn);
        projectionLcdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProjectionLamp.this,ProjectionLcd.class);
                startActivity(intent);
            }
        });
		
		openBtn=(Button)findViewById(R.id.open_projection);
        closeBtn=(Button)findViewById(R.id.close_projection);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBtn.setEnabled(false);
                closeBtn.setEnabled(true);
                Intent intent=new Intent(ACTION_OPEN);
                try{
                    sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ACTION_CLOSE);
                try{
                    sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
                closeBtn.setEnabled(false);
                openBtn.setEnabled(true);
            }
        });

    }

	public void button003(View view) {
		Intent intent=new Intent(ACTION_INTERNEL);
		try{
            sendBroadcast(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void button004(View view) {
		Intent intent=new Intent(ACTION_EXTERNEL);
		try{
            sendBroadcast(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void button005(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.projection_lamp, "success");
        finish();
    }

    public void button006(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.projection_lamp, "failed");
        finish();
    }
}
