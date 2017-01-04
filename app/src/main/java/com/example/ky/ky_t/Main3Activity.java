package com.example.ky.ky_t;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.ky.ky_t.R.id.image;

public class Main3Activity extends AppCompatActivity {

    private ImageView mimageView;
    private ImageButton mButton1;
    private ImageButton mButton2;
    private static Toast toast;
    private ImageButton mButton3;
    private RelativeLayout layout1;
    private Bitmap bmp;
    private int mTurn=0;

    private int displayWidth;
    private int displayHeight;
    private float scaleWidth=1;
    private float scaleHeight=1;

    ArrayList<String>  dog= null;
    private int fruitNo=0;


    ArrayList<String>  Nw_Src= null;
    private int count=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        dog= new ArrayList<String>();

        dog.add("cute");
        dog.add("cute2");
        dog.add("cute3");
        //ShowFirstdog();

        Nw_Src=new ArrayList<String>();
        Nw_Src.add("https://lh3.googleusercontent.com/yao.summer/RzlV4tPEGYI/AAAAAAAAAbg/JNzmg-s8SMo/s800/1080192.jpg");
        Nw_Src.add("https://lh3.googleusercontent.com/yao.summer/RzlW1dPEGhI/AAAAAAAAAco/1EyFt8S2RaU/s800/1080217.jpg");
        Nw_Src.add("https://lh3.googleusercontent.com/yao.summer/RzlXNNPEGjI/AAAAAAAAAc4/9ZnZgzlOGpg/s800/1080220.jpg");




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    /* 取得屏幕分辨率大小 */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        displayWidth = dm.widthPixels;
    /* 屏幕高度須扣除下方Button高度 */
        displayHeight = dm.heightPixels-80;
        Log.i("生命週期", "MainActivity3_onCreate");
        Log.e("====ASUSdisplayWidth:", displayWidth + " " + displayHeight + ":ASUSdisplayHeight ======" );
    /* 初始化相關變量 */
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.cute);

        mimageView = (ImageView) findViewById(R.id.imageView);
        //layout1 = (RelativeLayout)findViewById(R.id.layout1);
        mButton1 = (ImageButton) findViewById(R.id.imageButton1);
        mButton2 = (ImageButton) findViewById(R.id.imageButton2);
       // mButton3 = (ImageButton) findViewById(R.id.imageButton3);
        mButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mTurn==0) {
                    big();
                }
                else if(mTurn==1){
                    small();
                }


/*
                if(fruitNo>=dog.size()-1)
                    fruitNo=0; //超過題目位址,回到初始
                else
                    fruitNo++;

                ShowFirstdog();
*/
            }
        });


        mButton2.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v) {
                //建立一個AsyncTask執行緒進行圖片讀取動作，並帶入圖片連結網址路徑

                final String[] srcArray = {"https://lh3.googleusercontent.com/yao.summer/RzlV4tPEGYI/AAAAAAAAAbg/JNzmg-s8SMo/s800/1080192.jpg",
                        "https://lh3.googleusercontent.com/yao.summer/RzlW1dPEGhI/AAAAAAAAAco/1EyFt8S2RaU/s800/1080217.jpg",
                        "https://lh3.googleusercontent.com/yao.summer/RzlXNNPEGjI/AAAAAAAAAc4/9ZnZgzlOGpg/s800/1080220.jpg"};



                makeTextAndShow(Main3Activity.this,"開始下載...", LENGTH_SHORT);
                new AsyncTask<String, Integer, Bitmap>()//一個String， 或者是一個String陣列都可以。
                {

                    private ProgressDialog progressBar;

                    protected void onPreExecute() {
                        //執行前 設定可以在這邊設定
                        Log.e("====String:", "onPreExecute");
                        super.onPreExecute();

                        progressBar = new ProgressDialog(Main3Activity.this);
                        progressBar.setMessage("Loading...");
                        progressBar.setCancelable(false);
                        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressBar.show();
                        //初始化進度條並設定樣式及顯示的資訊。
                    }


                    protected Bitmap doInBackground(String... params)// in background thread 執行中，在背景做任務,不能寫任何有關UI程式碼
                    {
                        int progress = 0;

                        Log.i("Trace", "doInBackground");

                        String url = params[count];
                        count++;

                        try {
                            for (int i = 0; i < 3; i++) {
                                Thread.sleep(300);
                                publishProgress(progress += 33);//呼叫publishProgress時，丟入一個值，會到onProgressUpdate之中，在這邊更新進度條的進度
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.e("====params:", params[0]);
                        publishProgress(100);
                        return getBitmapFromURL(url);
                    }

                    protected void onProgressUpdate(Integer... values) {
                        //執行中 可以在這邊告知使用者進度
                        Log.e("====String:", "onProgressUpdate");
                        super.onProgressUpdate(values);
                        progressBar.setProgress(values[0]);
                        //取得更新的進度
                    }


                    @Override
                    protected void onPostExecute(Bitmap result) // in main thread  執行後，最後的結果會在這邊
                    {
                        Log.i("Trace", "in main thread");

                        makeTextAndShow(Main3Activity.this,"下載完成......", LENGTH_SHORT);

                        progressBar.dismiss();
                        mimageView.setImageBitmap(result);
                        if(count>=srcArray.length) {
                            count = 0;
                        }
                        super.onPostExecute(result);
                    }
                }.execute(srcArray);

            }

        });







        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

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

    private void  ShowFirstdog(){
        Log.e("Trace:","ShowFirstdog");
        Log.e("Trace fruitNo:", fruitNo + " ");
        mimageView = (ImageView) findViewById(R.id.imageView);

        String[] dogarray = new String[fruitNo]; //宣告字串陣列大小

        dogarray = dog.toArray(dogarray); //將List放到字串陣列裡來
        Log.e("Trace fruitNo:",dogarray[fruitNo] );
        String uri = "@drawable/" + dogarray[fruitNo].toString(); //圖片路徑和名稱

        int imageResource = getResources().getIdentifier(uri, null, getPackageName()); //取得圖片Resource來源

        mimageView.setImageResource(imageResource);

    }
    //讀取網路圖片，型態為Bitmap
    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            Log.e("====String:",imageUrl);
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e)
        {
            Log.i("Trace", "IOException");
            e.printStackTrace();
            return null;
        }
    }
    private void small()
    {
        int bmpWidth=bmp.getWidth();
        int bmpHeight=bmp.getHeight();
        Log.e("====ASUSbmpWidth:", bmpWidth + " " + bmpHeight + ":ASUSbmpHeight ======" );
    /* 設置圖片縮小的比例 */
        double scale=0.8;
    /* 計算出這次要縮小的比例 */
        scaleWidth=(float) (scaleWidth*scale);
        scaleHeight=(float) (scaleHeight*scale);
        Log.e("====ASUSscaleWidth:", scaleWidth + " " + scaleHeight + ":ASUSscaleHeight ======" );
    /* 產生reSize後的Bitmap對象 */
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,bmpHeight,matrix,true);
        int rebmpWidth=resizeBmp.getWidth();
        int rebmpHeight=resizeBmp.getHeight();
        Log.e("====ASUSrebmpWidth:", rebmpWidth + " " + rebmpHeight + ":ASUSrebmpHeight ======" );
        if((rebmpWidth > 1) && (rebmpHeight > 1)) {
            mimageView.setImageBitmap(resizeBmp);

            makeTextAndShow(Main3Activity.this,"變小了....阿斯", LENGTH_SHORT);
        }
        else
        {

            makeTextAndShow(Main3Activity.this,"奈米等級了....顆顆", LENGTH_SHORT);
            mTurn=0;
        }







        /*if(id==0)
        {
      //如果是第一次按，就刪除原來默認的ImageView
            layout1.removeView(mimageView);
        }
        else
        {
      //如果不是第一次按，就刪除上次放大縮小所產生的ImageView
            layout1.removeView((ImageView)findViewById(id));
        }*/
        //產生新的ImageView，放入reSize的Bitmap對象，再放入Layout中*/
        /*id++;
        ImageView imageView = new ImageView(Main3Activity.this);
        imageView.setId(id);
        imageView.setImageBitmap(resizeBmp);
        layout1.addView(mimageView);
        setContentView(layout1);*/

    /*因為圖片放到最大時放大按鈕會disable，所以在縮小
            時把它重設為enable */
        //mButton2.setEnabled(true);
    }
    private void big()
    {
        int bmpWidth=bmp.getWidth();
        int bmpHeight=bmp.getHeight();
        Log.e("====ASUSbmpWidth:", bmpWidth + " " + bmpHeight + ":ASUSbmpHeight ======" );
    /* 設置圖片要放大的比例 */
        double scale=1.25;
    /* 計算出這次要放大的比例 */
        scaleWidth=(float) (scaleWidth*scale);
        scaleHeight=(float) (scaleHeight*scale);
        Log.e("====ASUSscaleWidth:", scaleWidth + " " + scaleHeight + ":ASUSscaleHeight ======" );//1080 1840
    /* 產生reSize後的Bitmap對象 */
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,bmpHeight,matrix,true);
        int rebmpWidth=resizeBmp.getWidth();
        int rebmpHeight=resizeBmp.getHeight();
        Log.e("====ASUSrebmpWidth:", rebmpWidth + " " + rebmpHeight + ":ASUSrebmpHeight ======" );
        if(rebmpWidth<=displayWidth && rebmpHeight<=displayHeight) {

            makeTextAndShow(Main3Activity.this,"變大了....阿斯", LENGTH_SHORT);
            mimageView.setImageBitmap(resizeBmp);

        }
        else
        {

            makeTextAndShow(Main3Activity.this,"太大了....依估", LENGTH_SHORT);
            mTurn=1;
        }
        /*if(id==0)
        {
      //如果是第一次按，就刪除原來默認的ImageView
            layout1.removeView(mimageView);
        }
        else
        {
      //如果不是第一次按，就刪除上次放大縮小所產生的ImageView
            layout1.removeView((ImageView)findViewById(id));
        }*/
        //產生新的ImageView，放入reSize的Bitmap對象，再放入Layout中*/
        /*id++;
        ImageView imageView = new ImageView(Main3Activity.this);
        imageView.setId(id);
        imageView.setImageBitmap(resizeBmp);
        layout1.addView(mimageView);
        setContentView(layout1);*/

    /*因為圖片放到最大時放大按鈕會disable，所以在縮小
            時把它重設為enable */
        //mButton2.setEnabled(true);
    }
}
