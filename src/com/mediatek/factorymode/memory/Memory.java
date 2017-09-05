package com.mediatek.factorymode.memory;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.ShellExe;
import com.mediatek.factorymode.Utils;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import android.text.format.Formatter;
import android.app.ActivityManager;
import android.content.Context;

public class Memory extends Activity
  implements View.OnClickListener
{
  private TextView mBtFailed;
  private TextView mBtOk;
  private TextView mCommInfo;
  SharedPreferences mSp;

  private String getInfo(String paramString)
  {
      StringBuilder sb = new StringBuilder();
      long blockSize = 0;
      long blockCount = 0;
      long availCount = 0;
      long totalSize = 0;
      long availSize = 0;
 
     //StatFs rootsf = new StatFs(Environment.getRootDirectory().getPath()); 
     StatFs rootsf = new StatFs(Environment.getDataDirectory().getPath()); 
      blockSize = rootsf.getBlockSize();  
      blockCount = rootsf.getBlockCount();  
      availCount = rootsf.getAvailableBlocks(); 
      //totalSize = blockCount * blockSize / 1024L / 1024L;
      //availSize = availCount * blockSize / 1024L / 1024L;
      sb.append(this.getString(R.string.internal_memory)).append("\n\n");
      sb.append(this.getString(R.string.sdcard_totalsize)).append(this.getTotalMemory()).append("\n\n");
      sb.append(this.getString(R.string.sdcard_freesize)).append(this.getAvailMemory()).append("\n\n");
      
      return sb.toString();
  }

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    //int i = 2131230850; 	
    int j = paramView.getId();
    int k = this.mBtOk.getId();
    int kk = this.mBtFailed.getId();
    if (j == k)
    {
       Utils.SetPreferences(this, localSharedPreferences, R.string.memory_name, "success");
       finish();
    }
    if (j == kk)
    {
    	Utils.SetPreferences(this, localSharedPreferences, R.string.memory_name, "failed");
    	 finish();
    }
   
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.memory);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    TextView localTextView1 = (TextView)findViewById(R.id.memory_bt_ok);
    this.mBtOk = localTextView1;
    this.mBtOk.setOnClickListener(this);
    TextView localTextView2 = (TextView)findViewById(R.id.memory_bt_failed);
    this.mBtFailed = localTextView2;
    this.mBtFailed.setOnClickListener(this);
    TextView localTextView3 = (TextView)findViewById(R.id.comm_info);
    this.mCommInfo = localTextView3;
    TextView localTextView4 = this.mCommInfo;
    String str = getInfo("cat /proc/driver/nand");
    localTextView4.setText(str);
  }
  
	private String getAvailMemory() {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// 将获取的内存大小规格化
    }
  
     private String getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            //for (String num : arrayOfString) {
            //    Log.i(str2, num + "\t");
            //}
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }
}