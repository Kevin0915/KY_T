package com.example.ky.ky_t;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KY on 2017/1/25.
 */

public  class Mybservice extends Service {

    public class ServiceBinder extends Binder {
        Mybservice getService() {
            return Mybservice.this;
        }
    }
    private final IBinder mBinder = new ServiceBinder();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("====KY MainService=","onStartCommand"+"");

        return super.onStartCommand(intent, flags, startId);
    }
    public void custom()
    {
        // 設定每次時間觸發時執行的動作，將這些動作包成物件，放進 TimerTask 型態的參考中。
        TimerTask action = new TimerTask() {
            @Override
            public void run() {
                Log.e("====KY MainbService=", "Running"+"");
            }
        };

        // 將定時器物件建立出來。
        Timer timer = new Timer();
        // 利用 schedule() 方法，將執行動作、延遲時間(1秒)、間隔時間(1秒) 輸入方法中。
        // 執行此方法後將會定時執行動作。
        timer.schedule(action, 1000, 1000);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.i("服務", "呼叫onBind方法");
        return mBinder;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.e("====KY MainbService=", "服務"+"建立");
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("====KY MainbService=", "服務"+"銷毀");

    }
}