package com.heizi.kuaguo.block.maidan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.MainTabsActivity;
import com.heizi.kuaguo.block.my.ActivityMyPoint;


/**
 * 分享弹窗
 * <p>
 * Created by leo on 17/10/20.
 */

public class AlertShare extends AlertDialog {

    Activity mContext;
    Button btn_sure, btn_cancle;

    public AlertShare(Activity context) {
        super(context);
        mContext = context;
    }

    public AlertShare(Activity context, boolean cancelable, OnCancelListener cancelListener, TextView tvContent) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    public AlertShare(Activity context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_share);
        setCanceledOnTouchOutside(false);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                MainTabsActivity.getInstance().changeToMy();
                Intent intent = new Intent();
                intent.setClass(mContext, ActivityMyPoint.class);
                mContext.startActivity(intent);
                mContext.finish();

            }
        });
    }

}
