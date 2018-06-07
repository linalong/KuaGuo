package com.heizi.kuaguo.block.my;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;
import com.heizi.mycommon.view.MyRadioGroup;
import com.lljjcoder.citypickerview.widget.TypePicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by leo on 17/12/5.
 */

public class ActivityAddLanguage extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    @InjectView(R.id.rg)
    MyRadioGroup rg;
    @InjectView(R.id.rbtn1)
    RadioButton rbtn1;
    @InjectView(R.id.rbtn2)
    RadioButton rbtn2;
    @InjectView(R.id.rbtn3)
    RadioButton rbtn3;
    @InjectView(R.id.rbtn4)
    RadioButton rbtn4;
    @InjectView(R.id.ll_yuyan)
    LinearLayout ll_yuyan;


    TextView tv_content;

    /**
     * 语言列表
     */
    TypePicker typePicker;

    int type = 0;//0添加，1修改

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        type = getIntent().getExtras().getInt("type");
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_language;
    }


    @Override
    protected void initView() {
        super.initView();
        tv_title.setText(getResources().getString(R.string.tianjiayuyan));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getResources().getString(R.string.baocun));
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setOnClickListener(this);

        typePicker = new TypePicker(this);
        typePicker.setOnTypeItemClickListener(new TypePicker.OnTypeItemClickListener() {
            @Override
            public void onSelected(String agencyName, String agencyId) {
                tv_content.setText(agencyName);
            }

            @Override
            public void onCancel() {

            }
        });
        tv_content.setText("汉语");

        Map map = new HashMap();
        map.put("1", "汉语");
        map.put("2", "英语");
        map.put("3", "法语");
        map.put("4", "德语");
        typePicker.setData(map);
    }

    @OnClick({})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                typePicker.show();
                break;
        }
    }
}
