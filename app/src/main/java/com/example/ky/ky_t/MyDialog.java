package com.example.ky.ky_t;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by KY on 2017/2/8.
 */

public class MyDialog implements DialogInterface.OnCancelListener, View.OnClickListener{

    private Context mContext;
    private int mResId;
    private Dialog mDialog;
    private ImageView dialog_image;
    private Button dialog_btn;

    MyDialog(Context context){
        this.mContext = context;
        Log.e("====KY MyDialog=", "建構元");
    }

    public MyDialog imageRes(int resId){
        this.mResId = resId;
        return this;
    }

    public MyDialog show(){
        mDialog = new Dialog(mContext, R.style.dialog);
        mDialog.setContentView(R.layout.dialog_mydialog);
        mDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        // 點邊取消
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);

        dialog_image = (ImageView)mDialog.findViewById(R.id.dialog_image);
        dialog_btn = (Button)mDialog.findViewById(R.id.dialog_btn);
        dialog_image.setImageResource(mResId);
        dialog_btn.setOnClickListener(this);
        mDialog.setOnCancelListener(this);
        mDialog.show();
        Log.e("====KY MyDialog=", "show");
        return this;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mDialog.dismiss();
        Log.e("====KY MyDialog=", "onCancel");
    }

    @Override
    public void onClick(View v) {
        Log.e("====KY MyDialog=", "onClick");
        mDialog.dismiss();
    }
}

