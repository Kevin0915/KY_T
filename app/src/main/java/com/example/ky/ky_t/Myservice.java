package com.example.ky.ky_t;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KY on 2017/1/24.
 */

public  class Myservice extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("====KY MainService=","onStartCommand"+"");
        // 設定每次時間觸發時執行的動作，將這些動作包成物件，放進 TimerTask 型態的參考中。
        TimerTask action = new TimerTask() {
            @Override
            public void run() {
                Log.e("====KY MainService=", "Running"+"");
            }
        };

        // 將定時器物件建立出來。
        Timer timer = new Timer();
        // 利用 schedule() 方法，將執行動作、延遲時間(1秒)、間隔時間(1秒) 輸入方法中。
        // 執行此方法後將會定時執行動作。
        timer.schedule(action, 1000, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //定要Override onBind()方法，不過現在用不到，return值可以設為null
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.e("====KY MainService=", "服務"+"建立");
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("====KY MainService=", "服務"+"銷毀");

    }
}
