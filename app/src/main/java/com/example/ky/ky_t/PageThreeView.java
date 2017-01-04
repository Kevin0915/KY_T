package com.example.ky.ky_t;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by KY on 2016/12/8.
 */

public class PageThreeView extends PageView{
    public PageThreeView(Context context) {
        super(context);
        Log.i("KY_Trace", "PageThreeView");
        View view = LayoutInflater.from(context).inflate(R.layout.page3_content, null);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText("Page three");
        addView(view);
    }

    @Override
    public void refreshView() {
        Log.i("KY_Trace", "refreshView");

    }
}