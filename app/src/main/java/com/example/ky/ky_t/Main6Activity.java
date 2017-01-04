package com.example.ky.ky_t;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<PageView> pageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        initData();
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SamplePagerAdapter());
    }

    private void initData() {
        pageList = new ArrayList<>();
        pageList.add(new PageOneView(Main6Activity.this));
        pageList.add(new PageTwoView(Main6Activity.this));
        pageList.add(new PageThreeView(Main6Activity.this));
    }
    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            Log.i("=====KY_Trace", "getCount");
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            Log.i("=====KY_Trace", "isViewFromObject");
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            Log.i("=====KY_Trace", "instantiateItem");
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("=====KY_Trace", "destroyItem");
            container.removeView((View) object);
        }



    }


}
