package com.example.ky.ky_t;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by KY on 2017/1/11.
 */
//LINE: RecyclerView.java 9157
public abstract class OnItemTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;



   // public OnItemTouchListener(RecyclerView recyclerView,OnItemClickListener listener) {
   public OnItemTouchListener(RecyclerView recyclerView) {
        //this.clickListener = listener;
        mRecyclerView = recyclerView;
       /*GestureDetectorCompat.java Line:504
        public GestureDetectorCompat(Context context, OnGestureListener listener) {
        this(context, listener, null);
    }
        */
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(), new MyGestureListener());
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.e("====KY OnItemTouch", "onTouchEvent");
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.e("====KY OnItemTouch", "onInterceptTouchEvent");
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh);
/*
GestureDetector.JAVA line:175
public static class SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener,
            OnContextClickListener {

        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

       ......
    }
 */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("====KY GestureListener", "onSingleTapUp");
            View childe = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childe != null) {
                RecyclerView.ViewHolder VH = mRecyclerView.getChildViewHolder(childe);
               onItemClick(VH);
            }
            return true;//why?
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("====KY GestureListener", "onLongPress");
            View childe = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childe != null) {
                RecyclerView.ViewHolder VH = mRecyclerView.getChildViewHolder(childe);
                onItemClick(VH);
            }
        }


    }

}
