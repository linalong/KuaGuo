package com.heizi.kuaguo.block.my;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;
import com.heizi.mycommon.utils.Utils;
import com.heizi.mycommon.view.PayPsdInputView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 支付密码
 * Created by leo on 17/9/28.
 */

public class ActivityPayPwd extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    @InjectView(R.id.pwd_1)
    PayPsdInputView pwd_1;
    @InjectView(R.id.pwd_2)
    PayPsdInputView pwd_2;
    @InjectView(R.id.pwd_3)
    PayPsdInputView pwd_3;

    @InjectView(R.id.ll_1)
    LinearLayout ll_1;

    @InjectView(R.id.btn_ok)
    Button btn_ok;

    int type = 0;//0设置，1修改

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        type = getIntent().getExtras().getInt("");
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_paypwd;
    }

    @Override
    protected void initView() {
        super.initView();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (type == 0) {
            tv_title.setText(getResources().getString(R.string.shezhizhifumima));
            ll_1.setVisibility(View.GONE);
            pwd_2.requestFocus();
        } else {
            tv_title.setText(getResources().getString(R.string.xiugaizhifumima));
            pwd_1.requestFocus();
        }

        pwd_3.setComparePassword(pwd_2.getPasswordString(), new PayPsdInputView.onPasswordListener() {
            @Override
            public void onDifference(String oldPsd, String newPsd) {

            }

            @Override
            public void onEqual(String psd) {

            }

            @Override
            public void inputFinished(String inputPsd) {
                if (!pwd_2.getPasswordString().equals(inputPsd)) {
                    pwd_3.cleanPsd();
                    Toast.makeText(ActivityPayPwd.this, getResources().getString(R.string.liangcibuyizhi), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick({R.id.btn_back, R.id.btn_ok})
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.btn_back:
                finish();

                break;
            case R.id.btn_ok:
                if (checkData()) {

                }
                break;
            default:
                break;
        }
    }

    // 验证输入信息是否正确
    private boolean checkData() {
        if (type == 0) {
            if (Utils.isNull(pwd_1.getText().toString())) {
                Utils.toastShow(this, getResources().getString(R.string.qingshurujiumima));
                return false;
            }
        }
        if (Utils.isNull(pwd_2.getText().toString())) {
            Utils.toastShow(this, getResources().getString(R.string.qingshurushezhimima));
            return false;
        }
        if (!pwd_2.getText().toString().equals(pwd_3.getText().toString())) {
            Utils.toastShow(this, getResources().getString(R.string.liangcibuyizhi));
            return false;
        }

        return true;
    }


}
