package com.example.ky.ky_t;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by KY on 2016/11/24.
 */

public class ScaleImage extends ImageView
{
    //初始狀態的Matrix
    private Matrix mMatrix = new Matrix();
    //進行變動狀況下的Matrix
    private Matrix mChangeMatrix = new Matrix();
    //圖片的Bitmap
    private Bitmap mBitmap = null;
    //手機畫面尺寸資訊
    private DisplayMetrics mDisplayMetrics;
    //設定縮放最小比例
    private float mMinScale = 1.0f;
    //設定縮放最大比例
    private float mMaxScale = 5.0f;
    //圖片狀態 - 初始狀態
    private  static final int STATE_NONE = 0;
    //圖片狀態 - 拖動狀態
    private static final int STATE_DRAG = 1;
    //圖片狀態 - 縮放狀態
    private static final int STATE_ZOOM = 2;
    //當下的狀態
    private int mState = STATE_NONE;
    //第一點按下的座標
    private PointF mFirstPointF = new PointF();
    //第二點按下的座標
    private PointF mSecondPointF = new PointF();
    //兩點距離
    private float mDistance = 1f;
    //圖片中心座標
    private float mCenterX,mCenterY;
    //KY
    private static Toast toast;

    private float mlastX=0;
    private float mlastY=0;
    private float mthisX=0;
    private float mthisY=0;
    private long mlastDownTime=0;
    private long mthisEventTime=0;
    private long mlongPressTime=0;
    //KY
    public static void makeTextAndShow(final Context context, final String text, final int duration) {
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
    //KY
    static boolean isLongPressed(float lastX, float lastY, float thisX,
                                 float thisY, long lastDownTime, long thisEventTime,
                                 long longPressTime) {
        float offsetX = Math.abs(thisX - lastX);
        float offsetY = Math.abs(thisY - lastY);
        long intervalTime = thisEventTime - lastDownTime;
        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
            return true;
        }
        return false;
    }
    //KY
    //ScaleImage類別，xml呼叫運用
    public ScaleImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        //取得圖片Bitmap
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) this.getDrawable();
        if(mBitmapDrawable != null)
        {
            mBitmap = mBitmapDrawable.getBitmap();
            build_image();
        }
    }

    //圖片縮放層級設定
    private void Scale()
    {
        //取得圖片縮放的層級
        float level[] = new float[9];
        mMatrix.getValues(level);

        //狀態為縮放時進入
        if (mState == STATE_ZOOM)
        {
            //若層級小於1則縮放至原始大小
            if (level[0] < mMinScale)
            {
                mMatrix.setScale(mMinScale, mMinScale);
                mMatrix.postTranslate(mCenterX,mCenterY);
            }

            //若縮放層級大於最大層級則顯示最大層級
            if (level[0] > mMaxScale)  mMatrix.set(mChangeMatrix);
        }
    }

    //兩點距離
    private float Spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);

    }

    //兩點中心
    private void MidPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    //圖片縮放設定
    public void build_image()
    {
        //取得Context
        Context mContext = getContext();
        //取得手機畫面尺寸資訊
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();

        //設置縮放的型態
        this.setScaleType(ScaleType.MATRIX);
        //將Bitmap帶入
        this.setImageBitmap(mBitmap);

        //將圖片放置畫面中央
        mCenterX = (float)((mDisplayMetrics.widthPixels/2)-(mBitmap.getWidth()/2));
        mCenterY = (float)((mDisplayMetrics.heightPixels/3)-(mBitmap.getHeight()/2));
        mMatrix.postTranslate(mCenterX,mCenterY);

        //將mMatrix帶入
        this.setImageMatrix(mMatrix);
        //KY
        //設置Touch觸發的Listener動作
       
        //KY


        this.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //多點觸碰偵測
                switch(event.getAction() & MotionEvent.ACTION_MASK)
                {
                    //第一點按下進入
                    case MotionEvent.ACTION_DOWN :
                        mChangeMatrix.set(mMatrix);
                        mFirstPointF.set(event.getX(), event.getY());
                        mState = STATE_DRAG;
                        //KY
                        mlastX=event.getX();
                        mlastY=event.getY();
                        mlastDownTime=event.getEventTime();

                        //KY
                        Log.i("KY_Trace", "ACTION_DOWN");

                        break;

                    //第二點按下進入
                    case MotionEvent.ACTION_POINTER_DOWN :
                        mDistance = Spacing(event);
                        //只要兩點距離大於10就判定為多點觸碰
                        if (Spacing(event) > 10f)
                        {
                            mChangeMatrix.set(mMatrix);
                            MidPoint(mSecondPointF, event);
                            mState = STATE_ZOOM;
                        }
                        Log.i("KY_Trace", "ACTION_POINTER_DOWN");

                        break;

                    //離開觸碰
                    case MotionEvent.ACTION_UP :
                        Log.i("KY_Trace", "ACTION_UP");
                        break;

                    //離開觸碰，狀態恢復
                    case MotionEvent.ACTION_POINTER_UP :
                        mState = STATE_NONE;
                        Log.i("KY_Trace", "ACTION_POINTER_UP");
                        break;

                    //滑動過程進入
                    case MotionEvent.ACTION_MOVE :
                        Log.i("KY_Trace", "ACTION_MOVE");
                        if (mState == STATE_DRAG)
                        {
                            mMatrix.set(mChangeMatrix);
                            mMatrix.postTranslate(event.getX() - mFirstPointF.x, event.getY() - mFirstPointF.y);
                        }
                        else if (mState == STATE_ZOOM)
                        {
                            float NewDistance = Spacing(event);
                            if (NewDistance > 10f)
                            {
                                mMatrix.set(mChangeMatrix);
                                float NewScale = NewDistance / mDistance;
                                mMatrix.postScale(NewScale, NewScale, mSecondPointF.x, mSecondPointF.y);
                            }
                        }
                        break;
                }

                //將mMatrix滑動縮放控制帶入
                ScaleImage.this.setImageMatrix(mMatrix);
                //縮放設定
                Scale();

                return true;
            }
        });
    }
}

