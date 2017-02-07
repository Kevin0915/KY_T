package com.example.ky.ky_t;

import android.app.Service;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.ky.ky_t.ScaleImage.makeTextAndShow;

public class Main5Activity extends AppCompatActivity {
    private GridView gridView;
    private int[] image = {
            R.drawable.m1, R.drawable.m2, R.drawable.m3,
            R.drawable.m4, R.drawable.m5, R.drawable.m6

    };
    private String[] imgText = {
            "來學一下LLAPI", "What's Gilbert team's request?",
            "要當老子有的是錢啊!", "ㄟ~CH,儀器怎麼沒開?",
            "Kevin你做出來,我給你一個team", "沒有錢,但可以給你們滿滿der大平台!"
    };
    private Vibrator mVibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        List<Map<String,Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String,Object> item = new HashMap<>();//map集合用法
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        //http://www.slideshare.net/itembedded/androidui-14600775;ArrayList跟gridView結合
        SimpleAdapter adapter = new SimpleAdapter(this,
                items, R.layout.grid_item, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
            gridView = (GridView)findViewById(R.id.main_page_gridview);
            gridView.setNumColumns(2);
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mVibrator.vibrate(1000);
                makeTextAndShow(Main5Activity.this,"楊駭客曰:"+ imgText[position], LENGTH_SHORT);
                //Toast.makeText(Main5Activity.this, "楊駭客曰:" , Toast.LENGTH_SHORT).show();
            }
        });

    }
}
