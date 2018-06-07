package com.heizi.kuaguo.block.my;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.DatePicker;
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

public class ActivityAddIndustry extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    @InjectView(R.id.rg)
    MyRadioGroup rg;
    @InjectView(R.id.rbtn1)
    RadioButton rbtn1;
    @InjectView(R.id.rbtn2)
    RadioButton rbtn2;
    @InjectView(R.id.rbtn3)
    RadioButton rbtn3;

    @InjectView(R.id.ll_yuyan)
    LinearLayout ll_yuyan;
    @InjectView(R.id.ll_begin)
    LinearLayout ll_begin;
    @InjectView(R.id.ll_end)
    LinearLayout ll_end;

    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.tv_begin_time)
    TextView tv_begin_time;
    @InjectView(R.id.tv_end_time)
    TextView tv_end_time;

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
        return R.layout.activity_add_industry;
    }


    @Override
    protected void initView() {
        super.initView();
        tv_title.setText(getResources().getString(R.string.tianjiahangye));
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
        tv_content.setText("运输");

        Map map = new HashMap();
        map.put("1", "运输");
        map.put("2", "餐饮");
        map.put("3", "航空");
        map.put("4", "制造");
        typePicker.setData(map);
    }

    @OnClick({R.id.ll_yuyan, R.id.ll_begin, R.id.ll_end})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_yuyan:
                typePicker.show();
                break;
            case R.id.ll_begin:
                String[] time_begin = tv_begin_time.getText().toString().split("-");
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_begin_time.setText(String.format("%d-%d-%d", year, month + 1, dayOfMonth));
                    }
                }, Integer.parseInt(time_begin[0]), Integer.parseInt(time_begin[1]) - 1, Integer.parseInt(time_begin[2])).show();
                break;
            case R.id.ll_end:
                String[] time_end = tv_end_time.getText().toString().split("-");
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_end_time.setText(String.format("%d-%d-%d", year, month + 1, dayOfMonth));
                    }
                }, Integer.parseInt(time_end[0]), Integer.parseInt(time_end[1]) - 1, Integer.parseInt(time_end[2])).show();
                break;
        }
    }
}
