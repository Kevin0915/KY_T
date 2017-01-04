package com.example.ky.ky_t;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by KY on 2016/12/8.
 */
//http://www.openhome.cc/Gossip/JavaGossip-V1/AbstractClass.htm
public abstract class PageView extends RelativeLayout {//在宣告類別時使用"abstract"關鍵字，表示這是一個抽象類別

    public PageView(Context context) {
        super(context);

    }
    public abstract void refreshView();
}
