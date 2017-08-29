package com.mediatek.factorymode.lamp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mediatek.factorymode.R;

/**
 * Created by sunyibin on 2016/6/13 0013.
 */
public class ProjectionLcd extends Activity{
    private TextView backgroundView;
    private int clickNum=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projection_lcd_activity);
        backgroundView=(TextView)findViewById(R.id.color_txt);
        backgroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(++clickNum);
            }
        });

    }
    private void changeColor(int paramInt){
        switch (paramInt) {
            case 2:
                this.backgroundView.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                this.backgroundView.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                this.backgroundView.setBackgroundColor(Color.GRAY);
                break;
            case 5:
                this.backgroundView.setBackgroundColor(Color.BLUE);
                break;
            case 6:
                this.backgroundView.setBackgroundColor(Color.rgb(255,0,255));    //紫色
                break;
            case 7:
                this.backgroundView.setBackgroundColor(Color.BLACK);
                break;
            case 8:
                this.backgroundView.setBackgroundColor(Color.WHITE);
                break;
            default:
                finish();
                break;
        }
    }
}
