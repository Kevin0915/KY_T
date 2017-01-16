package com.example.ky.ky_t;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by KY on 2017/1/10.
 */
//RecyclerView 點擊事件 2017/01/10

/*

    public static class SimpleOnItemTouchListener implements RecyclerView.OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }


   */
/*

 public static class SimpleOnItemTouchListener implements RecyclerView.OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
 */
public class ItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetectorCompat gestureDetector;
    //KY test 0116
    private int touchSlop ;
    private int mLastDownX,mLastDownY;
    private long mDownTime;
    //是否是单击事件
    private boolean isSingleTapUp = false;
    //是否是长按事件
    private boolean isLongPressUp = false;
    private boolean isMove = false;
    //KY test 0116

    public interface OnItemClickListener {

        void onItemClick(View view , int position);

        void onItemLongClick(View view, int position);
    }

    public ItemClickListener(Context context,OnItemClickListener listener){
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        clickListener = listener;
    }
/*
    public ItemClickListener(final RecyclerView recyclerView, OnItemClickListener listener) {
        this.clickListener = listener;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        Log.e("====KY GestureD", "onSingleTapUp");
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                            clickListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        Log.e("====KY GestureD", "onLongPress");
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            clickListener.onItemLongClick(childView,
                                    recyclerView.getChildAdapterPosition(childView));
                        }
                    }
                });
    }
*/

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.e("====KY ClickListener", "onInterceptTouchEvent");
        //gestureDetector.onTouchEvent(e);
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()){
            /**
             *  如果是ACTION_DOWN事件，那么记录当前按下的位置，
             *  记录当前的系统时间。
             */
            case MotionEvent.ACTION_DOWN:
                mLastDownX = x;
                mLastDownY = y;
                mDownTime = System.currentTimeMillis();
                isMove = false;
                break;
            /**
             *  如果是ACTION_MOVE事件，此时根据TouchSlop判断用户在按下的时候是否滑动了，
             *  如果滑动了，那么接下来将不处理点击事件
             */
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(x - mLastDownX)>touchSlop || Math.abs(y - mLastDownY)>touchSlop){
                    isMove = true;
                }
                break;
            /**
             *  如果是ACTION_UP事件，那么根据isMove标志位来判断是否需要处理点击事件；
             *  根据系统时间的差值来判断是哪种事件，如果按下事件超过1s，则认为是长按事件，
             *  否则是单击事件。
             */
            case MotionEvent.ACTION_UP:
                if(isMove){
                    break;
                }
                if(System.currentTimeMillis()-mDownTime > 1000){
                    isLongPressUp = true;
                }else {
                    isSingleTapUp = true;
                }
                break;
        }

        if(isSingleTapUp ){
            //根据触摸坐标来获取childView
            View childView = rv.findChildViewUnder(e.getX(),e.getY());
            isSingleTapUp = false;
            if(childView != null){
                //回调mListener#onItemClick方法
                clickListener.onItemClick(childView,rv.getChildLayoutPosition(childView));
                return true;
            }
            return false;
        }
        if (isLongPressUp ){
            View childView = rv.findChildViewUnder(e.getX(),e.getY());
            isLongPressUp = false;
            if(childView != null){
                clickListener.onItemLongClick(childView, rv.getChildLayoutPosition(childView));
                return true;
            }
            return false;
        }

        return false;
    }
}
//RecyclerView 點擊事件 2017/01/10