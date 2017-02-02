package com.example.ky.ky_t;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private MyBroadcaseReceiver mMyReceiver; //自訂一個繼承 BroadcastReceiver 的類別
    private TextView myTextView;
    private  TextView myTextView1;
    private static Toast toast;
    private Button button01;
    private Button button02;
    private Button button03;
    private Button button04;
    private Button button05;
    private Button button06;
    private Button button07;
    final static int KEY_CHAIN = 333;
    final static int KEY_HOLE = 333;
    //2017/01/26 bindService
    Mybservice myService;
    //2017/01/26 bindService
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("生命週期", "MainActivity_onCreate");
        //2017/01/17 BroadcastReceiver
        IntentFilter itFilter = new IntentFilter("tw.android.ACTION_01");
        mMyReceiver = new MyBroadcaseReceiver();
        registerReceiver(mMyReceiver, itFilter); //註冊廣播接收器
        //2017/01/17 BroadcastReceiver

        //2017/01/24 startService
        //開啟 Service
        /*
            Intent intent = new Intent(MainActivity.this, Myservice.class);
            startService(intent);
       */

        //2017/01/24 startService

        //2017/01/26 bindService
        Intent intent = new Intent(MainActivity.this, Mybservice.class);
        bindService(intent, connc, Context.BIND_AUTO_CREATE);
        //unbindService(connc);
        //2017/01/26 bindService

        setContentView(R.layout.activity_main);
        button01 = (Button)findViewById(R.id.Button01);
        button02 = (Button)findViewById(R.id.Button02);
        button03 = (Button)findViewById(R.id.Button03);
        button04 = (Button)findViewById(R.id.Button04);
        button05 = (Button)findViewById(R.id.Button05);
        button06 = (Button)findViewById(R.id.Button06);
        button07 = (Button)findViewById(R.id.Button07);



        myTextView = (TextView)findViewById(R.id.mytextview);
        myTextView.setText("MATT  中年危機");
        // myTextView.append("\tExample TextView by corn");
        myTextView.setTextSize(20);
        myTextView.setTextColor(Color.GREEN);

        myTextView1 = (TextView)findViewById(R.id.mytextview1);
        myTextView1.setText("MATT  醒醒好嗎?");
        myTextView1.setClickable(true);
        myTextView1.setFocusable(true);
        // myTextView.append("\tExample TextView by corn");
        myTextView1.setTextSize(20);
        myTextView1.setTextColor(Color.RED);
        myTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextView1.setTextColor(Color.BLACK);
                /*
                String strURL1 = "http://z642319240.pixnet.net/album/photo/86796894-%E6%9C%89%E7%97%85%E5%B0%B1%E8%A9%B2%E5%90%83%E8%97%A5";
                Intent ie = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL1));
                startActivity(ie);
                */
                Intent intent = new Intent("tw.android.ACTION_01");
                intent.putExtra("Name", "hellogv");
                intent.putExtra("Blog", "http://www.jcodecraeer.com");
                sendBroadcast(intent);
            }
            });

        button01.setOnTouchListener(new View.OnTouchListener(){

            @Override
                    public boolean onTouch(View v,MotionEvent event){
// TODO Auto-generated method stub
                if(event.getAction()==MotionEvent.ACTION_DOWN){  //按下的時候
                    myTextView.setText("大家要高道德標準. ");
                    myTextView.setTextColor(Color.GREEN);

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {  //起來的時候
                    myTextView.setText("要錢?先說沒辦法. ");
                    myTextView.setTextColor(Color.RED);
                }
                return false;
            }
        });
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Intent物件
                Intent intent = new Intent();
                //從MainActivity 到Main2Activity
                intent.setClass(MainActivity.this , FullscreenActivity.class);
                intent.putExtra("name","喔~有意思!");
                //開啟Activity
                startActivity(intent);
            }
        });
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Intent物件
                Intent intent = new Intent();
                //從MainActivity 到Main2Activity
                intent.setClass(MainActivity.this , Main2Activity.class);
               // startActivity(intent);
                startActivityForResult(intent, KEY_CHAIN);
            }
        });
        button04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Intent物件
                Intent intent = new Intent();
                //從MainActivity 到Main3Activity
                intent.setClass(MainActivity.this , Main3Activity.class);
                // startActivity(intent);
                startActivity(intent);

            }
        });
        button05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Intent物件
                Intent intent = new Intent();
                //從MainActivity 到Main5Activity
                intent.setClass(MainActivity.this , Main4Activity.class);
                // startActivity(intent);
                startActivity(intent);

            }
        });
        button06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Intent物件
                Intent intent = new Intent();
                //從MainActivity 到Main5Activity
                intent.setClass(MainActivity.this , Main5Activity.class);
                // startActivity(intent);
                startActivity(intent);

            }
        });
        button07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Intent物件
                Intent intent = new Intent();
                //從MainActivity 到Main5Activity
                intent.setClass(MainActivity.this , Main7Activity.class);
                // startActivity(intent);
                startActivity(intent);

            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("返回訊息", "回到onActivityResult");

        if(requestCode == KEY_CHAIN){
            if(resultCode == KEY_HOLE){
                Log.i("返回訊息", "正常收到KEY_HOLE的資料");
                myTextView.setText("速度太慢囉,多買一台CMW500 ");
                Log.i("返回訊息", data.getExtras().getString("result"));

            }
        }
    }
    //2017/01/20 BroadcastReceiver
    public class MyBroadcaseReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            String name = intent.getStringExtra("Blog");
            Toast.makeText(MainActivity.this,"動態"+name, Toast.LENGTH_SHORT).show();
        }
    }
    //2017/01/20 BroadcastReceiver

    //2017/01/26 bindService
    private ServiceConnection connc = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            Log.e("====KY onService","Connected"+"");
            myService=((Mybservice.ServiceBinder)service).getService();
            myService.custom();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("====KY onService","Disconnected"+"");
            // TODO Auto-generated method stub
            myService=null;
        }

    };
    //2017/01/26 bindService
    private static void makeTextAndShow(final Context context, final String text, final int duration) {
        if (toast == null) {
            //如果還沒有用過makeText方法，才使用
            toast = android.widget.Toast.makeText(context, text, duration);
            Log.e("KY Trace", "toast == null" );
        } else {
            Log.e("KY Trace", "toast != null" );

            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }

    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        //finish();
        Log.i("生命週期", "MainActivity_onStop");
        //Toast.makeText(getBaseContext(), "go2 結束了", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.i("生命週期", "MainActivity_onPause");
    }
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Log.i("生命週期", "MainActivity_onRestart");
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i("生命週期", "MainActivity_onResume");
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("生命週期", "MainActivity_onDestroy");
    }

}

