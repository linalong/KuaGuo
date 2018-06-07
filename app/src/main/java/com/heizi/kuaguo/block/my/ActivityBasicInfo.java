package com.heizi.kuaguo.block.my;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;
import com.heizi.kuaguo.utils.VisibleEnum;
import com.lljjcoder.citypickerview.widget.TypePicker;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 地区、需求、时薪维护
 * Created by leo on 17/12/5.
 */

public class ActivityBasicInfo extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    @InjectView(R.id.ll_dingwei)
    LinearLayout ll_dingwei;

    @InjectView(R.id.ll_guojia)
    LinearLayout ll_guojia;
    @InjectView(R.id.ll_chengshi)
    LinearLayout ll_chengshi;
    @InjectView(R.id.ll_diqu)
    LinearLayout ll_diqu;
    @InjectView(R.id.tv_guojia)
    TextView tv_guojia;
    @InjectView(R.id.tv_chengshi)
    TextView tv_chengshi;
    @InjectView(R.id.tv_diqu)
    TextView tv_diqu;

    @InjectView(R.id.et_score)
    EditText et_score;
    @InjectView(R.id.tv_count)
    TextView tv_count;

    @InjectView(R.id.et_price)
    EditText et_price;
    @InjectView(R.id.tv_price)
    TextView tv_price;
    @InjectView(R.id.ll_price)
    LinearLayout ll_price;

    @InjectView(R.id.ll_bottom)
    LinearLayout ll_bottom;

    @InjectView(R.id.btn_ok)
    Button btn_ok;


    TextView tv_content;

    /**
     * 语言列表
     */
    TypePicker typePicker;

    int type = VisibleEnum.SAVE.getCode();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        type = getIntent().getExtras().getInt("type");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_basic_info;
    }


    @Override
    protected void initView() {
        super.initView();
        tv_title.setText(getResources().getString(R.string.jibenziliao));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (type == VisibleEnum.NEXT.getCode()) {
            btn_ok.setText(getResources().getString(R.string.xiayibu));
        } else {
            btn_ok.setText(getResources().getString(R.string.baocun));
        }


        typePicker = new TypePicker(this);
        typePicker.setOnTypeItemClickListener(new TypePicker.OnTypeItemClickListener() {
            @Override
            public void onSelected(String agencyName, String agencyId) {
            }

            @Override
            public void onCancel() {

            }
        });
//        tv_content.setText("汉语");
//
//        Map map = new HashMap();
//        map.put("1", "汉语");
//        map.put("2", "英语");
//        map.put("3", "法语");
//        map.put("4", "德语");
//        typePicker.setData(map);
    }

    @OnClick({R.id.btn_ok})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_content:
//                typePicker.show();
//                break;
            case R.id.btn_ok:
                if (type == VisibleEnum.NEXT.getCode()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", VisibleEnum.NEXT.getCode());
                    startActivity(ActivityBasicInfo.this, ActivityBasicTagList.class, bundle);
                } else if (type == VisibleEnum.SAVE.getCode()) {

                } else if (type == VisibleEnum.EDIT.getCode()) {

                }

                break;
        }
    }
}
