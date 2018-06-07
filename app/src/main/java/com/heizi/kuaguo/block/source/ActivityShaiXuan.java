package com.heizi.kuaguo.block.source;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;
import com.heizi.kuaguo.block.my.ActivityBasicTagList;
import com.heizi.kuaguo.block.my.ActivityIndustryList;
import com.heizi.kuaguo.block.my.ActivitySkillList;
import com.heizi.kuaguo.utils.VisibleEnum;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by leo on 2018/6/6.
 */

public class ActivityShaiXuan extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    @InjectView(R.id.ll_1)
    LinearLayout ll_1;
    @InjectView(R.id.ll_2)
    LinearLayout ll_2;
    @InjectView(R.id.ll_3)
    LinearLayout ll_3;
    @InjectView(R.id.ll_4)
    LinearLayout ll_4;
    @InjectView(R.id.ll_5)
    LinearLayout ll_5;
    @InjectView(R.id.ll_6)
    LinearLayout ll_6;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_shaixuan;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText(getResources().getString(R.string.shaixuan));
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getResources().getString(R.string.chongzhi));
    }

    @OnClick({R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4, R.id.ll_5, R.id.ll_6})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", VisibleEnum.SAVE.getCode());
                startActivity(ActivityShaiXuan.this, ActivitySkillList.class, bundle1);
                break;
            case R.id.ll_2:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", VisibleEnum.SAVE.getCode());
                startActivity(ActivityShaiXuan.this, ActivityIndustryList.class, bundle2);
                break;
            case R.id.ll_3:
                break;
            case R.id.ll_4:
                Bundle bundle4 = new Bundle();
                bundle4.putInt("type", VisibleEnum.SAVE.getCode());
                startActivity(ActivityShaiXuan.this, ActivityBasicTagList.class, bundle4);
                break;
            case R.id.ll_5:
//                Bundle bundle5 = new Bundle();
//                bundle5.putInt("type", VisibleEnum.SAVE.getCode());
//                startActivity(ActivityShaiXuan.this, ActivityBasicTagList.class, bundle5);
                break;
            case R.id.ll_6:
//                Bundle bundle6 = new Bundle();
//                bundle6.putInt("type", VisibleEnum.SAVE.getCode());
//                startActivity(ActivityShaiXuan.this, ActivityBasicTagList.class, bundle6);
                break;
        }

    }
}
