package com.example.ky.ky_t;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by KY on 2016/12/8.
 */

public class PageOneView extends PageView{
    private ImageView mImage;
    public PageOneView(Context context) {
        super(context);
        Log.i("KY_Trace", "PageOneView");
        View view = LayoutInflater.from(context).inflate(R.layout.page_content, null);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText("Page one");
        addView(view);
    }

    @Override
    public void refreshView() {
        Log.i("KY_Trace", "refreshView");

    }
}
