package com.example.ky.ky_t;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main4Activity extends AppCompatActivity {
    private Button mbutton;
    private Button mPhoto;
    private Button mCamera;
    private ImageView mImage;
    private List<String> thumbs;//存放縮圖的id
    private List<String> imagePaths;  //存放圖片的路徑
    private DisplayMetrics mPhone;
    private final static int CAMERA = 66 ;
    private final static int PHOTO = 99 ;
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private final int REQUEST_PERMISSION_CAMERA_CODE= 2;
    private String pic_str_time;
    private Uri camera_uri;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mbutton=(Button) findViewById(R.id.button);
        mCamera=(Button) findViewById(R.id.button1);
        mPhoto = (Button) findViewById(R.id.button2);
        mImage = (ImageView) findViewById(R.id.imageView);
        //讀取手機解析度
        mPhone = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);
        //讀取手機解析度
        //解決Android 6.0權限問題KY:http://www.it610.com/article/4916320.htm


        mbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                String[] PERMISSIONS_STORAGE = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                };

                int permission = ActivityCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {//使用者回傳已擁有權限－PERMISSION_GRANTED;無權限－PERMISSION_DENIED
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                            Main4Activity.this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                }
                else{

                    //確認是否有插入SDCard
                    if(checkSDCard())
                    {
                        Log.i("KY_Trace", "checkSDCard");
                        //帶入SDCard內的圖片路徑(SDCard: DCIM資料夾/100MEDIA資料夾/001圖片)
                        mImage.setImageBitmap(getBitmapFromSDCard("/sdcard/DCIM/Camera/P_20161129_125325_vHDR_Auto.jpg"));
                    }
                    else Toast.makeText(Main4Activity.this,
                            "尚未插入SDCard",
                            Toast.LENGTH_SHORT).show();

                }
                //解決Android 6.0權限問題KY:http://www.it610.com/article/4916320.htm

            }
        });
        mCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //開啟相機功能，並將拍照後的圖片存入SD卡相片集內，須由startActivityForResult且
                //帶入
               // requestCode進行呼叫，原因為拍照完畢後返回程式後則呼叫onActivityResult
                int permission = ActivityCompat.checkSelfPermission(Main4Activity.this,Manifest.permission.CAMERA);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            Main4Activity.this,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_PERMISSION_CAMERA_CODE
                    );

                }
                else{
                    /*
                    File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
                    imagesFolder.mkdirs();
                    File image = new File(imagesFolder, "image_001.jpg");
                    Uri uri1=Uri.fromFile(image);
                    Log.e("====ASUSuri1:", uri1 + " " );
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri1);

                     */

                    //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    // TODO Auto-generated method stub
                    //設定照片儲存的URI資訊
                    ContentValues value = new ContentValues();
                    value.put(MediaStore.Images.Media.TITLE, "CaptureImage");
                    value.put(MediaStore.Images.Media.BUCKET_ID, "CaptureImage");
                    value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    Log.e("====ASUS_value:", value + " " );
                    // 設定照片路徑與檔名
                    // 要增加紀錄，我们可以呼叫ContentResolver.insert()方法
                    //https://developer.android.com/guide/topics/providers/content-provider-basics.html?hl=zh-tw
                    camera_uri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value);//http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2012/0821/367.html
                    //uri==> content://media/external/images/media/12062  ;**content://media/internal/images  URI會返回設備上存的所有圖片; “content://”, 代表數據的路徑
                    //KY http://blog.maxkit.com.tw/2013/12/android.html

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
                    pic_str_time = formatter.format(curDate);

                    File tmpFile = new File(Environment.getExternalStorageDirectory(),pic_str_time+".jpg");
                    Log.i("====","file path = "+tmpFile.getPath());
                    Uri outputFileUri = Uri.fromFile(tmpFile);

                    //KY http://blog.maxkit.com.tw/2013/12/android.html
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    // intent.putExtra(MediaStore.EXTRA_OUTPUT, uri.getPath());//error case,but why?

                    Log.e("====KY_uri:", camera_uri + " " );
                    Log.e("====KY_urigetPath:", camera_uri.getPath() + " " );
                    Log.e("====KY_outputFileUri", outputFileUri + " " );
                    startActivityForResult(intent, CAMERA);
                }



            }
        });
        mPhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //開啟相簿相片集，須由startActivityForResult且帶入requestCode進行呼叫，原因
                //為點選相片後返回程式呼叫onActivityResult
                Intent intent = new Intent();// 建立 "選擇檔案 Action" 的 Intent
                intent.setType("image/*");// 過濾檔案格式
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PHOTO);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //藉由requestCode判斷是否為開啟相機或開啟相簿而呼叫的，且data不為null
        if ((requestCode == CAMERA || requestCode == PHOTO ) && data != null)
        {
            //取得照片路徑uri
            Uri uri = data.getData();
            Log.e("====KY data:", data +"onActivityResult");
            Log.e("====KY data.getData():", data.getData() + " " +"onActivityResult");
            Log.e("====KY uri:", uri + " " +"onActivityResult");
            ContentResolver cr = this.getContentResolver();//應用程式會透過 ContentResolver 用戶端物件存取內容供應程式的資料
            //顯示拍照的影像


            try
            {
                //讀取照片，型態為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                //判斷照片為橫向或者為直向，並進入ScalePic判斷圖片是否要進行縮放
                if(bitmap.getWidth()>bitmap.getHeight()){
                    Log.i("====KY", "bitmap.getWidth()>bitmap.getHeight()");
                    ScalePic(bitmap,mPhone.heightPixels);
                }
                else {
                    Log.i("====KY", "bitmap.getWidth()<bitmap.getHeight()");
                    ScalePic(bitmap,mPhone.widthPixels);
                }
            }
            catch (FileNotFoundException e)
            {
            }
        }

        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == CAMERA)
        {
            Log.i("====KY", "requestCode == CAMERA");
           // Log.e("====KY getExternalStorageDirectory",Environment.getExternalStorageDirectory() + " " +"onActivityResult");

             Bitmap bitmap = BitmapFactory.decodeFile(Environment
             .getExternalStorageDirectory() + "/"+pic_str_time+".jpg");
            //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if(bitmap!=null){
                Log.e("====KY getExternalStorageDirectory",Environment.getExternalStorageDirectory() + " " +"onActivityResult");
                mImage.setImageBitmap(bitmap);
            }else{
                Log.i("=====","bitmap==null");
            }



        }
    }
    private static boolean checkSDCard()
    {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        return false;
    }

    private static Bitmap getBitmapFromSDCard(String file)
    {
        try
        {
            String sd = Environment.getExternalStorageDirectory().toString();
            Log.i("KY_Trace", "getBitmapFromSDCard");
            //String myJpgPath = "/sdcard/DSC_0001.JPG";
            //Bitmap bitmap = BitmapFactory.decodeFile(sd + "/" + file);
            Bitmap bitmap = BitmapFactory.decodeFile(file);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CAMERA_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ContentValues value = new ContentValues();
                    value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    Uri uri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            value);
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri.getPath());
                    startActivityForResult(intent, CAMERA);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(checkSDCard())
                    {
                        Log.i("KY_Trace", "checkSDCard");
                        //帶入SDCard內的圖片路徑(SDCard: DCIM資料夾/100MEDIA資料夾/001圖片)
                        mImage.setImageBitmap(getBitmapFromSDCard("/sdcard/DCIM/Camera/P_20161129_125325_vHDR_Auto.jpg"));
                    } else Toast.makeText(Main4Activity.this,
                            "尚未插入SDCard",
                            Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void ScalePic(Bitmap bitmap,int phone) {
//縮放比例預設為1
        float mScale = 1 ;
        Log.i("KY_Trace", "ScalePic");
        //如果圖片寬度大於手機寬度則進行縮放，否則直接將圖片放入ImageView內
        if(bitmap.getWidth() > phone )
        {
            //判斷縮放比例
            mScale = (float)phone/(float)bitmap.getWidth();

            Matrix mMat = new Matrix() ;
            mMat.setScale(mScale, mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,
                    0,
                    0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    mMat,
                    false);
            mImage.setImageBitmap(mScaleBitmap);
        }
        else mImage.setImageBitmap(bitmap);


    }

}
