package com.example.ky.ky_t;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private TextView mContentView;
    private TextView myTextView;
    private ImageView mImage;
    private ImageButton mimgbutton;
    private ScaleImage mScaleImage;
    private int count=0;
    private int i=0;
    private static Toast toast;
    private Button mbutton;
    //KY
    private GestureDetector mGestureDetector;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
            Log.i("KY_Trace", "mHidePart2Runnable");
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            Log.i("KY_Trace", "mShowPart2Runnable");
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */


    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(count==0) {
                mImage.setVisibility(View.GONE);
                mScaleImage.setVisibility(View.VISIBLE);
               // makeTextAndShow(FullscreenActivity.this, "可以阿斯~",toast.LENGTH_SHORT);

                ScaleImage.makeTextAndShow(FullscreenActivity.this, "可以阿斯~",toast.LENGTH_SHORT);
                count++;

            }else{
                mImage.setVisibility(View.VISIBLE);
                mScaleImage.setVisibility(View.GONE);
                ScaleImage.makeTextAndShow(FullscreenActivity.this, "靠北~",toast.LENGTH_SHORT);
                count=0;

            }

            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i("生命週期", "activity_fullscreen_onCreate");
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mbutton=(Button) findViewById(R.id.dummy_button);
        mContentView = (TextView) findViewById(R.id.fullscreen_content);
        mImage = (ImageView) findViewById(R.id.image);
        mScaleImage = (ScaleImage) findViewById(R.id.scale_image);
        mContentView.setText("圖片滑動測試");
        myTextView = (TextView) findViewById(R.id.mytextview);
        mGestureDetector = new GestureDetector(this, new MyOnGestureListener());
        Animation am = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // 動畫開始到結束的執行時間 (1000 = 1 秒)
        am.setDuration( 1000 );
        // 動畫重複次數 (Animation.INFINITE表示一直重複)
        am.setRepeatCount(Animation.RESTART);
        // 圖片配置動畫
        mImage.setAnimation(am);
        // 動畫開始
        am.startNow();

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("KY_Trace", "mbutton.setOnClickListener");
                if(count==0) {
                    mImage.setVisibility(View.GONE);
                    mScaleImage.setVisibility(View.VISIBLE);
                    // makeTextAndShow(FullscreenActivity.this, "可以阿斯~",toast.LENGTH_SHORT);

                    ScaleImage.makeTextAndShow(FullscreenActivity.this, "可以阿斯~",toast.LENGTH_SHORT);
                    count++;

                }else{
                    mImage.setVisibility(View.VISIBLE);
                    mScaleImage.setVisibility(View.GONE);
                    ScaleImage.makeTextAndShow(FullscreenActivity.this, "靠北~",toast.LENGTH_SHORT);
                    count=0;

                }

            }
        });
        mImage.setLongClickable(true);
        mImage.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {

                ScaleImage.makeTextAndShow(FullscreenActivity.this, "靠北ㄚㄚㄚ~",toast.LENGTH_SHORT);
                return true;
            }
        });
        mImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ScaleImage.makeTextAndShow(FullscreenActivity.this, "ㄚㄚㄚ~",toast.LENGTH_SHORT);
            }
        });
        /*
        mImage.setOnTouchListener(new View.OnTouchListener() {


            public boolean onTouch(View v, MotionEvent event) {
                Log.i(getClass().getName(), "KY_onTouch-----" + getActionName(event.getAction()));
                mGestureDetector.onTouchEvent(event);

                // 一定要返回true，不然獲得不到完整的事件
                return true;
            }
        });
*/

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Log.i("KY_Trace", "mContentView.setOnClickListener");
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.




    }

    @Override
        // Trigger the initial hide() shortly after the activity has been
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("生命週期", "onPostCreate_onCreate");
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        Log.i("KY_Trace", "toggle");
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        Log.i("KY_Trace", "hide");
        ActionBar actionBar = getSupportActionBar();//kevin trace
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        Log.i("KY_Trace", "show");

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override

        public boolean onSingleTapUp(MotionEvent e) {
            Log.i(getClass().getName(), "KY_SimpleOnGestureListeneronSingleTapUp-----" + getActionName(e.getAction()));
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.i(getClass().getName(), "KY_SimpleOnGestureListeneronLongPress-----" + getActionName(e.getAction()));


        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i(getClass().getName(),
                    "KY_SimpleOnGestureListeneronScroll-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
                            + e2.getX() + "," + e2.getY() + ")");
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(getClass().getName(),
                    "KY_SimpleOnGestureListeneronFling-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
                            + e2.getX() + "," + e2.getY() + ")");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.i(getClass().getName(), "KY_SimpleOnGestureListeneronShowPress-----" + getActionName(e.getAction()));
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.i(getClass().getName(), "KY_SimpleOnGestureListeneronDown-----" + getActionName(e.getAction()));
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i(getClass().getName(), "KY_SimpleOnGestureListeneronDoubleTap-----" + getActionName(e.getAction()));
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i(getClass().getName(), "KY_SimpleOnGestureListeneronDoubleTapEvent-----" + getActionName(e.getAction()));
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i(getClass().getName(), "KY_SimpleOnGestureListeneronSingleTapConfirmed-----" + getActionName(e.getAction()));
            return false;
        }
    }
    private String getActionName(int action) {
        String name = "";
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                name = "ACTION_DOWN";
                //toggle();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                name = "ACTION_MOVE";
                break;
            }
            case MotionEvent.ACTION_UP: {
                name = "ACTION_UP";

                break;
            }

            default:
                break;
        }
        return name;
    }




    //KY
}



