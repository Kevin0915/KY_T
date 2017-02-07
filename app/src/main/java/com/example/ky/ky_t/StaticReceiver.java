package com.example.ky.ky_t;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by KY on 2017/2/2.
 */

public class StaticReceiver extends BroadcastReceiver {
    @Override		//AndroidManifest.xml 中註冊(靜態註冊)
    public void onReceive(Context context, Intent intent) {

        Log.e("====KY StaticReceiver=", "In");
        Toast.makeText(context, "BOOT_COMPLETED.", Toast.LENGTH_SHORT).show();
    }}
