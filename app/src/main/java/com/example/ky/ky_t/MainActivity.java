package com.example.ky.ky_t;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ky.ky_t.R.style.dialog;

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
        //2017/01/17 動態註冊 BroadcastReceiver
        IntentFilter itFilter = new IntentFilter("tw.android.ACTION_01");
        mMyReceiver = new MyBroadcaseReceiver();
        registerReceiver(mMyReceiver, itFilter); //註冊廣播接收器
        //2017/01/17 動態註冊BroadcastReceiver

        //2017/01/24 startService
        //開啟 Service
        /*
            Intent intent = new Intent(MainActivity.this, Myservice.class);
            startService(intent);
       */

        //2017/01/24 startService

        //2017/01/26 bindService

        //Intent intent = new Intent(MainActivity.this, Mybservice.class);
        //bindService(intent, connc, Context.BIND_AUTO_CREATE);
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
        //2017/01/17 BroadcastReceiver
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
               // Intent intent = new Intent("com.vrinux.static");
                intent.putExtra("Name", "hellogv");
                intent.putExtra("Blog", "Matt愛玩~死好");
                sendBroadcast(intent);

                //new MyDialog(MainActivity.this).imageRes(R.mipmap.ic_launcher).show();
                //2017/02/08 AlertDialog
               // normalDialogEvent();
                //pro_normalDialogEvent();
                ListDialogEvent();
                //2017/02/08 AlertDialog
            }
            });
        //2017/01/17 BroadcastReceiver
        button01.setOnTouchListener(new View.OnTouchListener(){

            @Override
                    public boolean onTouch(View v,MotionEvent event){
// TODO Auto-generated method stub
                if(event.getAction()==MotionEvent.ACTION_DOWN){  //按下的時候
                    myTextView.setText("大家要高道德標準. ");
                    myTextView.setTextColor(Color.GREEN);
                    Log.e("====KY onTouch=", "ACTION_DOWN");//step:1

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {  //起來的時候
                    myTextView.setText("要錢?先說沒辦法. ");
                    myTextView.setTextColor(Color.RED);
                    Log.e("====KY onTouch=", "ACTION_UP");//step:1



                }
                /*
                if(event.getAction()==MotionEvent.ACTION_MOVE) {
                    myTextView.setText("阿誠阿呆");
                    myTextView.setTextColor(Color.RED);
                    Log.e("====KY onTouch=", "ACTION_MOVE");//step:1

                }
                */
                    return false;
            }
        });

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextView.setText("Click阿呆");
                myTextView.setTextColor(Color.RED);
                Log.e("====KY Click=", "Click");//step:1

            }
        });
        button01.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {

                Log.e("====KY onLongClick=", "onLongClick");//step:1
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
            Toast.makeText(MainActivity.this,"白癡"+name, Toast.LENGTH_SHORT).show();
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


//2017/02/08 AlertDialog


    private void normalDialogEvent() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Matt 午餐時間")//對話方塊的標題
                .setIcon(R.drawable.m7) //設定標題圖片
                .setMessage("要含嗎?")//對話方塊的內容
                .setPositiveButton("想含", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "一次含3支", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("等等含", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "先刷牙", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("嘴好酸", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "流出來了", Toast.LENGTH_SHORT).show();
                    }
                });
        //2017/02/13 實現透明的對話框
        AlertDialog dialog = myAlertDialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.alpha = 0.6f;
        dialog.getWindow().setAttributes(params);
        //2017/02/13 實現透明的對話框
    }
    /*
         AlertDialog的源码发现三個函数都是protected，所以不能用AlertDialog alertDialog = new AlertDialog()

         需要AlertDialog.Builder build = new AlertDialog.Builder(this);
    */
    private void pro_normalDialogEvent() {
        final AlertDialog build = new AlertDialog.Builder(this).create();
        View view = getLayoutInflater().inflate(R.layout.pro_dialog_mydialog, null);
        //把自定义的布局設置到dialog中，注意，布局設置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
        build.setView(view, 0, 0, 0, 0);
        //一定要先show出来再設置dialog的参数，不然就不会改变dialog的大小了

        build.show();
        //得到当前显示设备的宽度，单位是像素
       // int width = getWindowManager().getDefaultDisplay().getWidth();
        //Log.e("====KY DialogEvent=",width+"");
        //得到这个dialog界面的参数对象
        WindowManager.LayoutParams params = build.getWindow().getAttributes();
        //設置dialog的界面宽度
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //設置dialog高度为包裹内容
        params.alpha = 0.8f;
        //我們可以通過修改對話框佈局參數的 alpha 屬性, 實現透明的對話框.
        params.height =  WindowManager.LayoutParams.WRAP_CONTENT;
        //設置dialog的重心
        params.gravity = Gravity.CENTER;
        //dialog.getWindow().setLayout(width-(width/6), LayoutParams.WRAP_CONTENT);
        //用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
        //最后把这个参数对象设置进去，即与dialog绑定
        build.getWindow().setAttributes(params);
        Window window = build.getWindow();
        window.setWindowAnimations(R.style.mystyle);

        Button leftButton = (Button) view.findViewById(R.id.splash_dialog_left);
        Button rightButton = (Button) view.findViewById(R.id.splash_dialog_right);
        Button midButton = (Button) view.findViewById(R.id.splash_dialog_mid);
        TextView warnMessage = (TextView) view.findViewById(R.id.warnmessage);
        warnMessage.setText("Matt Dork");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // downloadApk(downloadurl);
                Toast.makeText(getApplicationContext(), "一次含3支", Toast.LENGTH_SHORT).show();
                build.dismiss();
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "滿出來了", Toast.LENGTH_SHORT).show();
                build.dismiss();
               // loadMainActivity();
            }
        });
        midButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "先刷牙", Toast.LENGTH_SHORT).show();
                build.dismiss();
                // loadMainActivity();
            }
        });

    }

    private void ListDialogEvent(){
        final String[] drug = {"通乳丸","通睪丸","治療攝護腺肥大","神經病","砍掉重練"};

        AlertDialog.Builder dialog_list = new AlertDialog.Builder(this);
        dialog_list.setTitle("Matt 藥單 ");
        dialog_list.setIcon(R.drawable.m7); //設定標題圖片

        dialog_list.setItems(drug, new DialogInterface.OnClickListener(){
            @Override

            //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, "Matt需要" + drug[which], Toast.LENGTH_SHORT).show();
            }
        });


        //2017/02/13
        AlertDialog dialog = dialog_list.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.alpha = 0.9f;
        dialog.getWindow().setAttributes(params);
        //整個對話框的特效
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.mystyle);
        //整個對話框的特效
        //2017/02/13

    }


    //2017/02/08 AlertDialog
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

