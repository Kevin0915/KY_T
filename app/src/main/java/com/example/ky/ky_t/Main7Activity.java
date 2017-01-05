package com.example.ky.ky_t;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main7Activity extends AppCompatActivity {//http://blog.csdn.net/a396901990/article/details/40153759
    private RecyclerView mRecyclerView;

    private MyAdapter myAdapter;

    private Toast toast;

    private List<Actor> actors = new ArrayList<Actor>();

    private String[] names = { "Matt", "必誠", "萎人", "駭客", "刷存在感", "愛玩ㄇㄟˋ~"};

    private String[] pics = { "m1", "m2", "m3", "m4", "m5", "m6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        for(int i = 0; i < 6; i++){
            actors.add(new Actor(names[i], pics[i]));
        }
        Log.e("====KY onCreate=", "onCreate");
        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        // 设置LinearLayoutManager
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        myAdapter = new MyAdapter(this, actors);

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(myAdapter);
    }
    public class Actor
    {
        String name;

        String picName;

        public Actor(String name, String picName)
        {
            this.name = name;
            this.picName = picName;
        }

        public int getImageResourceId( Context context )
        {
            try
            {
                return context.getResources().getIdentifier(this.picName, "drawable", context.getPackageName());

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return -1;
            }
        }
    }
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {

        private List<Actor> actors;

        private Context mContext;


        public MyAdapter( Context context , List<Actor> actors)
        {
            Log.e("====KY MyAdapter=", "建構元");
            this.mContext = context;
            this.actors = actors;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i )
        {
            // 给ViewHolder设置布局文件
            Log.e("====KY MyAdapter=", "onCreateViewHolder");
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
            ViewHolder mViewHolder=new ViewHolder(v);
            mViewHolder.setIsRecyclable(true);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder, int i )
        {
            // 给ViewHolder设置元素
            Log.e("====KY MyAdapter=", "onBindViewHolder");
            Actor p = actors.get(i);
            viewHolder.mTextView.setText(p.name);
            viewHolder.mImageView.setImageDrawable(mContext.getDrawable(p.getImageResourceId(mContext)));

        }

        @Override
        public int getItemCount()
        {
            // 返回数据总数
            return actors == null ? 0 : actors.size();
        }

        // 重写的自定义ViewHolder
        public  class ViewHolder
                extends RecyclerView.ViewHolder
        {
            public TextView mTextView;

            public ImageView mImageView;

            public ViewHolder( View v )
            {

                super(v);
                Log.e("====KY ViewHolder=", "建構元");
                mTextView = (TextView) v.findViewById(R.id.name);
                mImageView = (ImageView) v.findViewById(R.id.pic);
            }
        }

        /**********定義点击事件**********/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("====KY", "activity_main7_onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main7, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_refresh:
                Log.e("====KY Pic=", "add");
                makeTextAndShow(Main7Activity.this, "add",toast.LENGTH_SHORT);
                if (myAdapter.getItemCount() != names.length) {
                    actors.add(new Actor(names[myAdapter.getItemCount()], pics[myAdapter.getItemCount()]));
                    mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                    myAdapter.notifyDataSetChanged();
                }
                return true;

            case R.id.action_search:
                Log.e("====KY Pic=", "remove");
                makeTextAndShow(Main7Activity.this, "remove",toast.LENGTH_SHORT);
                Toast.makeText(this, "remove", Toast.LENGTH_SHORT).show();
                if (myAdapter.getItemCount() != 0) {
                    actors.remove(myAdapter.getItemCount()-1);
                    mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                    myAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.action_settings:

                makeTextAndShow(Main7Activity.this, "action_settings",toast.LENGTH_SHORT);
                return true;

            case R.id.action_exit:
                makeTextAndShow(Main7Activity.this, "exit",toast.LENGTH_SHORT);
                return true;

        }
        return false;
    }

    public void makeTextAndShow(final Context context, final String text, final int duration) {
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
}
