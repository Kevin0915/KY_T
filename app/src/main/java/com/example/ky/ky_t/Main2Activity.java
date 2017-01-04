package com.example.ky.ky_t;



import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;


public class Main2Activity extends AppCompatActivity {
    final static int KEY_CHAIN = 333;
    final static int KEY_HOLE = 333;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private EditText mEditText = null;
    private ImageButton img_button;
    TextView TextView;
    private ListView listView01;
    private String[] show_text = {"認真工作","錢的問題","要錢沒辦法","藍受","香菇"};
    private ArrayAdapter listAdapter;
    private static Toast toast;
    private CheckBox mCheckBox;
    private CheckBox mCheckBox2;

    private HttpURLConnection connection = null;
    private String urlString="http://hmkcode.appspot.com/rest/controller/get.json";

    //private String urlString="http://www.android.com/";

    //JSON http://abkabkabkyes.blogspot.tw/2014/04/androidurljson.html
    private String [] jtital;
    private String [] jid;
    private int t1;
    private String responseString;
    //JSON

    //Socket http://www.cnblogs.com/lknlfy/archive/2012/03/04/2379628.html
    private boolean stop = true;
    private boolean Isclient = true;
    private Handler mHandler = null;
    private Handler smHandler = null;
    private Socket clientSocket = null;
    private ServerSocket mServerSocket = null;
    private AcceptThread mAcceptThread = null;
    private OutputStream coutStream = null;
    private c_ReceiveThread cmReceiveThread = null;
    private s_ReceiveThread smReceiveThread = null;
    //Socket  http://www.cnblogs.com/lknlfy/archive/2012/03/04/2379628.html
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.i("生命週期", "activity_main2_onCreate");
        TextView = (TextView)findViewById(R.id.textView);
        listView01 = (ListView)findViewById(R.id.listView1);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        mEditText = (EditText)findViewById(R.id.editText2);
        img_button=(ImageButton)findViewById(R.id.imageButton);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mCheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
        mCheckBox2.setChecked(true);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,show_text);
        listView01.setAdapter(listAdapter);
        //ListView的onClick
       // ActionBar actionBar = getActionBar();//android.support.v7.app中的activity，就不能用getActionBar(),要使用getSupportActionBar()
      //  actionBar.setDisplayShowTitleEnabled(false);
       // actionBar.setDisplayShowCustomEnabled(true);
       // actionBar.setDisplayHomeAsUpEnabled(true);
        // ActionBar actionBar =getSupportActionBar();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView.setText("Activity2_Test ");
                Intent intent = new Intent();
                intent.putExtra("result", "startActivityForResult的結果");
                setResult(KEY_HOLE,intent);
                Main2Activity.this.finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)//http://wannadoitnow.blogspot.tw/2015/10/android-httpurlconnection-httprequest.html
            {
                //建立多執行緒進行網路Server API串接的資料傳輸與讀取
                Log.e("====KY Trace", "HttpsURLConnection.HTTP_Start" );
                Log.e("====KY Trace urlString", urlString );
                new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            // 初始化 URL
                            URL url = new URL(urlString);
                            // 取得連線物件

                            connection = (HttpURLConnection) url.openConnection();
                            // 設定 request timeout
                            connection.setReadTimeout(1500);
                            connection.setConnectTimeout(1500);
                            // 是否添加參數(ex : json...等)
                            //connection.setDoOutput(true);//"http://hmkcode.appspot.com/rest/controller/get.json" 會產生405?
                            connection.setDoInput(true);
                            // 使用甚麼方法做連線
                            connection.setRequestMethod("GET");

                            // 模擬 Chrome 的 user agent, 因為手機的網頁內容較不完整
                            //connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
                            // 設定開啟自動轉址
                            connection.setInstanceFollowRedirects(true);
                            // 若要求回傳 200 OK 表示成功取得網頁內容
                            int responseCode = connection.getResponseCode();
                            Log.e("====KY Trace", responseCode+"" );
                            connection.connect();

                            // https redirect 302
                            if( responseCode ==  HttpURLConnection.HTTP_MOVED_TEMP) {
                                String newUrl = connection.getHeaderField("Location");
                                String cookies = connection.getHeaderField("Set-Cookie");
                                connection = (HttpURLConnection) new URL(newUrl).openConnection();
                                connection.setRequestProperty("Cookie", cookies);
                            }
                            // https redirect http://wannadoitnow.blogspot.tw/2015/10/android-httpurlconnection.html
                            responseCode = connection.getResponseCode();
                            if( responseCode == HttpURLConnection.HTTP_OK ){
                                Log.e("====KY Trace", "HttpsURLConnection.HTTP_OK" );
                                InputStream inputStream = connection.getInputStream();
                                BufferedReader bufferedReader  = new BufferedReader( new InputStreamReader(inputStream) );
                                //API的回傳文字，格式為json格式

                               //將json格式解開並取出Title名稱


                                String tempStr;
                                StringBuffer stringBuffer = new StringBuffer();

                                while( ( tempStr = bufferedReader.readLine() ) != null ) {
                                    Log.e("====KY Trace tempStr", tempStr +"" );
                                    stringBuffer.append( tempStr );
                                }

                                bufferedReader.close();
                                inputStream.close();
                                // 取得網頁內容類型
                                String  mime = connection.getContentType();

                               // Log.e("====KY Trace mime", mime);

                                boolean isMediaStream = false;



                                // 判斷是否為串流檔案
                                if( mime.indexOf("audio") == 0 ||  mime.indexOf("video") == 0 ){
                                    isMediaStream = true;
                                }

                                // 網頁內容字串
                                responseString = stringBuffer.toString();
                                Log.e("====KY Trace RS2 ", responseString);

                                Log.e("====KY Trace","responseString" );

                                //JSON

/*
                                JSONArray dataArray = new JSONArray(responseString);

                                Log.e("====KY Trace dataArray", dataArray.length()+"");
                                jtital = new String [dataArray.length()];
                                jid = new String [dataArray.length()];
                                for (int i = 0; i < dataArray.length(); i++) {
                                    //接下來這兩行在做同一件事情，就是把剛剛JSON陣列裡的物件抓取出來
                                    //並取得裡面的字串資料
                                    //dataArray.getJSONObject(i)這段是在講抓取data陣列裡的第i個JSON物件
                                    //抓取到JSON物件之後再利用.getString(“欄位名稱”)來取得該項value

                                    jtital[i] = dataArray.getJSONObject(i).getString("Tital");
                                    jid[i] = dataArray.getJSONObject(i).getString("Id");
                                    Log.e("====KY Trace tital", jtital[i] +"" );
                                    Log.e("====KY Trace id", jid[i] +"" );
                                }
*/
                                //JSON



                            }
                            else{
                                Log.e("====KY Trace", "HttpsURLConnection.HTTP_FAIL" );

                            }



                        } catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        finally {
                            // 中斷連線
                            if( connection != null ) {
                                Log.e("====KY Trace", "connection.disconnect()");

                                connection.disconnect();
                                try {

                                    //JSON
                                    //JSONObject jsonObject=new JSONObject(responseString);//非JSON不能直接放
                                    String str = "";

                                    JSONObject jsonObj =new JSONObject(responseString);// convert String to JSONObject
                                    JSONArray JArray= jsonObj.getJSONArray("articleList");// get articles array
                                    Log.e("====KY Trace JsonArray","responseString");
                                    for(int i=0;i<jsonObj.getJSONArray("articleList").length();i++) {
                                        str += "JArray length = " + jsonObj.getJSONArray("articleList").length();// --> 2
                                        str += "\n--------\n";
                                        str += "===ID===:"+i;//ID
                                        str += "\n--------\n";
                                        str += JArray.getJSONObject(i); // get first article in the array
                                        str += "\n--------\n";
                                        str += "names: " + JArray.getJSONObject(i).names();// get first article keys [title,url,categories,tags]
                                        str += "\n--------\n";
                                        str += "url:" + JArray.getJSONObject(i).getString("url");// return an article url
                                        str += "\n--------\n";
                                    }
                                    Log.e("====KY Trace JsonArray",str);

                                    //jsonObj.put("Handsome","yes");

                                   // jsonObj.put("MyName","Deyu");

                                    //JArray.put(jsonObj);


                                    //JSON


                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }




                            }
                        }
                    }
                }).start();



            }
        });

        button3.setOnClickListener(new View.OnClickListener()
                     {

                         @Override


                        public void onClick(View v)
                        {
                            makeTextAndShow(Main2Activity.this, "連線中...",toast.LENGTH_SHORT);

                            mHandler = new Handler()
                                  {

                                        @Override

                                         public void handleMessage(Message msg)
                                        {

                                               //显示接收到的内容

                                            TextView.setText((msg.obj).toString());
                                            //Log.e("====KY C_Handler=", "Handler");
                                            Log.e("====KY C_Handler_MSG=",(msg.obj).toString());




                                           }
                                    };
                               // TODO Auto-generated method stub
                            /*
                               try
                               {
                                        //实例化对象并连接到服务器
                                       clientSocket = new Socket("192.168.1.104",8888);
                                    }
                                catch (UnknownHostException e)
                              {
                                         // TODO Auto-generated catch block
                                         e.printStackTrace();
                                   }
                                catch (IOException e)
                                {
                                         // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                               // displayToast("连接成功！");
                                makeTextAndShow(Main2Activity.this, "連線成功",toast.LENGTH_SHORT);
                                //连接按钮使能
                                //connectButton.setEnabled(false);
                                //发送按钮使能
                                //sendButton.setEnabled(true);
*/

                                cmReceiveThread = new c_ReceiveThread();

                                stop = false;
                                //开启线程
                                cmReceiveThread.start();
                                try {
                                     //先讓他等待個 1 秒
                                    Log.e("====KY Sleep=", "Sleep");
                                    cmReceiveThread.sleep(1000);
                                    if (cmReceiveThread.isAlive())
                                        makeTextAndShow(Main2Activity.this, "連線成功...",toast.LENGTH_SHORT);
                                    else
                                        makeTextAndShow(Main2Activity.this, "連線逾時...",toast.LENGTH_SHORT);

                            } catch (InterruptedException e)
                                {

                                }

                            }



                    });

        button6.setOnClickListener(new View.OnClickListener()
                       {

                        @Override
                       public void onClick(View v)
                         {
                                // TODO Auto-generated method stub
                                 byte[] msgBuffer = null;
                                 //获得EditTex的内容
                                String text = mEditText.getText().toString();
                                 try {
                                         //字符编码转换
                                         msgBuffer = text.getBytes("GB2312");
                                     } catch (UnsupportedEncodingException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    }


                                 try {
                                         //get Socket的输出流
                                        coutStream = clientSocket.getOutputStream();
                                     } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }


                                try {
                                        //发送数据
                                        coutStream.write(msgBuffer);
                                     } catch (IOException e) {
                                         // TODO Auto-generated catch block
                                        e.printStackTrace();
                                     }
                               //清空内容
                               mEditText.setText("");
                               makeTextAndShow(Main2Activity.this, "發送成功.....",toast.LENGTH_SHORT);

                             }
                    });

        img_button.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                Log.e("====KY S_Handler=", "Handler");

                 /*
                  接來這邊很重要。由於 Android 系統不允許主執行緒負擔太重，若是停留太久會造成異常中止，因此如果有負擔重的背景運作，
                  我們可以交給 Thread 來做；但 Android 系統又為了安全性不允許副執行緒更動主執行緒的 UI ，所以 Android API 第八層
                  就生出了這個 Handler 類別，用於監聽副執行緒所傳送過來的 Message 類別，來給主執行緒更新 UI 。
                  在主執行緒中註冊一個 Handler 類別就好比註冊一個聆聽 btn按下的方法。「 Handler 物件」監聽 Message 傳送訊息過來，
                  就好比「聆聽 btn 被按下的方法」則執行方法內的程式碼。如同 btn 長在 layout 上 ， Message 則是長在副執行緒中
                 */
                smHandler = new Handler()
                {


                    @Override

                    public void handleMessage(Message msg)
                    {


                        //Log.e("====KY handleMessage=", msg+"");

                        switch(msg.what)
                        {
                            case 0:
                            {
                                //顯示客户端IP
                                //ipTextView.setText((msg.obj).toString());
                                //使能发送按钮
                                // sendButton.setEnabled(true);
                                Log.e("====KY IP=",(msg.obj).toString());
                                break;
                            }
                            case 1:
                            {
                                //顯示接收到的資料
                                TextView.setText((msg.obj).toString());
                                Log.e("====KY DATA=",(msg.obj).toString());
                               // makeTextAndShow(Main2Activity.this, "接收到Client資料",toast.LENGTH_SHORT);
                                break;
                            }
                        }

                    }
                };

                //    makeTextAndShow(Main2Activity.this, "server 設定開始.....",toast.LENGTH_SHORT);

                mAcceptThread = new AcceptThread();
                //开启监听线程
                mAcceptThread.start();
                makeTextAndShow(Main2Activity.this, "server 設定成功.....",toast.LENGTH_SHORT);



            }
        });
        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //設定CheckBox勾選狀態
                mCheckBox2.setChecked(true);
                button6.setText("CLIENT SEND");
                button3.setVisibility(View.VISIBLE);
                Isclient=true;
            }
        });
        button5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //設定CheckBox未勾選狀態
                mCheckBox2.setChecked(false);
                button6.setText("SEVER SEND");
                Isclient=false;
                button3.setVisibility(View.GONE);
            }
        });
        mCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if(isChecked)
                {
                    //CheckBox狀態 : 已勾選，顯示TextView
                      button6.setText("CLIENT SEND");
                      button3.setVisibility(View.VISIBLE);
                }
                else
                {
                    //CheckBox狀態 : 未勾選，隱藏TextView
                      button6.setText("SEVER SEND");
                      button3.setVisibility(View.GONE);
                }
            }
        });
        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){

                makeTextAndShow(Main2Activity.this, "點選的是"+show_text[position],toast.LENGTH_SHORT);
                //listView01.setVisibility(view.INVISIBLE); //隱藏ListView

                Log.e("Trace position:", position + " "+"Trace id:"+id);
            } //end onItemClick
        }); //end setOnItemClickListener





    }

    private class AcceptThread extends Thread
        {
              @Override
              public void run()
                {

                        Log.e("====KY mAcceptThread","run");
                        try {
                                //啟動Server開啟Port接口 8888
                               mServerSocket = new ServerSocket(8888);

                            Log.e("====KY mServerSocket=", mServerSocket +"");
                             } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        try {
                                //等待客户端的連線（阻塞）程式會卡住等Client連上
                                clientSocket = mServerSocket.accept();
                            Log.e("====KY clientSocket=", clientSocket +"");
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                 e.printStackTrace();
                            }

                         smReceiveThread = new s_ReceiveThread(clientSocket);
                         stop = false;
                        //開啟接收thread
                         Log.e("====KY smReceiveThread=", "Start");
                         smReceiveThread.start();

                        Message msg = new Message();
                        msg.what = 0;
                         //获取客户端IP
                       msg.obj = clientSocket.getInetAddress().getHostAddress();
                         //发送消息
                         smHandler.sendMessage(msg);

                    }

            }
    private class s_ReceiveThread extends Thread
    {
        private InputStream mInputStream = null;
        private byte[] buf ;
        private String str = null;

        s_ReceiveThread(Socket s)
        {         Log.e("====KY s_Receive Socket", s+"s_ReceiveThread");
            try {
                //获得输入流
                this.mInputStream = s.getInputStream();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run()
        {      Log.e("====KY s_run","s_ReceiveThread");
            while(!stop)
            {
                this.buf = new byte[512];

                //读取输入的数据(阻塞读)
                try {
                    this.mInputStream.read(buf);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //字符编码转换
                try {
                    this.str = new String(this.buf, "GB2312").trim();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Message msg = new Message();
                msg.what = 1;
                msg.obj = this.str;
                //发送消息
               // Log.e("====KY s_ReceiveThread","sendMessage");
                smHandler.sendMessage(msg);

            }
        }
    }
    private class c_ReceiveThread extends Thread//Client side
        {
              private InputStream inStream = null;

               private byte[] buf;
               private String str = null;



/*
              c_ReceiveThread(Socket s)
                 {
                        Log.e("====KY c_ReceiveThread=", "c_ReceiveThread");
                        try {
                                 s = new Socket("192.168.1.104",8888);
                                 //获得输入流
                                this.inStream = s.getInputStream();
                            } catch (IOException e) {
                               // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                   }
*/
                 @Override
                 public void run()
                 {
                     Log.e("====KY c_ReceiveThread=", "run");

                     try {
                         clientSocket  = new Socket("192.168.43.119",8888);
                         Log.e("====KY clientSocket=", clientSocket+"");
                         Log.e("====KY isConnected=", clientSocket.isConnected()+"");
                      //   makeTextAndShow(Main2Activity.this, "連線成功",toast.LENGTH_SHORT);
                         //获得输入流
                         this.inStream =clientSocket.getInputStream();
                         Log.e("====KY inStream=", this.inStream+"");
                     } catch (IOException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }
                     Log.e("====KY  this.stop",stop+"");
                     while((!stop) && (clientSocket!=null))//(clientSocket!=null) NO crash
                            {
                                 this.buf = new byte[512];
                                 Log.e("====KY   this.buf",this.buf+"");
                                // Log.e("====KY isConnected=", clientSocket.isConnected()+"");
                                // Log.e("====KY isClosed=", clientSocket.isClosed()+"");
                               try {


                                        Log.e("====KY isConnected", "sendUrgentData"+"");
                                        clientSocket.sendUrgentData(0xFF);//確認是否SERVER斷線
                                        Log.e("====KY isConnected=", "clientSocket.sendUrgentData"+"");
                                       //讀入數據（阻塞）
                                        this.inStream.read(this.buf);//if Null >>> crash
                                    } catch (IOException e) {
                                       // TODO Auto-generated catch block
                                         e.printStackTrace();
                                     stop=true;
                                     Log.e("====KY disConnected=", "clientSocket.sendUrgentData"+"");
                                    }

                                //字符编码转换
                                 try {
                                         this.str = new String(this.buf, "GB2312").trim();
                                       Log.e("====KY   this.str",this.str+"");
                                     } catch (UnsupportedEncodingException e) {
                                        // TODO Auto-generated catch block
                                       e.printStackTrace();
                                    }
                                Log.e("====KY  this.str",this.str);
                                Message msg = new Message();
                               msg.obj = this.str;
                                //发送消息
                             mHandler.sendMessage(msg);

                           }
                    }


             }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("生命週期", "activity_main2_onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();

            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                makeTextAndShow(Main2Activity.this, "action_settings",toast.LENGTH_SHORT);
                return true;

            case R.id.action_exit:
                makeTextAndShow(Main2Activity.this, "exit",toast.LENGTH_SHORT);
                return true;

        }
        return false;
    }


    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        //finish();
        Log.i("生命週期", "activity_main2_onStop");
        //Toast.makeText(getBaseContext(), "go2 結束了", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.i("生命週期", "activity_main2_onPause");
    }
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Log.i("生命週期", "activity_main2_onRestart");
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i("生命週期", "activity_main2_onResume");
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("生命週期", "activity_main2_onDestroy");
        if(cmReceiveThread != null)
        {
            stop = true;
            cmReceiveThread.interrupt();
        }
        if(smReceiveThread != null)
        {
            stop = true;
            smReceiveThread.interrupt();
        }
    }

}
