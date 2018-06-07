package com.heizi.kuaguo.block.my;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的钱包
 * Created by leo on 17/9/28.
 */

public class ActivityMyWallet extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    @InjectView(R.id.ll_verify)
    LinearLayout ll_verify;

    @InjectView(R.id.ll_wallet)
    LinearLayout ll_wallet;

    @InjectView(R.id.ll_mail)
    LinearLayout ll_mail;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("我的钱包");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick({R.id.ll_wallet, R.id.ll_mail})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_wallet:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                startActivity(this, ActivityPayPwd.class, bundle);
                break;

            case R.id.ll_mail:
                startActivity(this, ActivityEmailVerify.class, null);
                break;
        }
    }
}
