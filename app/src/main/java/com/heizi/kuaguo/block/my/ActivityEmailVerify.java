package com.heizi.kuaguo.block.my;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;
import com.heizi.mycommon.utils.SharePreferenceUtil;
import com.heizi.mycommon.utils.Utils;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 邮箱验证
 * Created by leo on 17/9/28.
 */

public class ActivityEmailVerify extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    @InjectView(R.id.btn_get_code)
    Button btn_get_code;
    @InjectView(R.id.btn_ok)
    Button btn_ok;
    @InjectView(R.id.et_verification_code)
    EditText et_verification_code;
    @InjectView(R.id.et_email)
    EditText et_email;

    private long leftTime = 0;
    private long closeTime = 0L;
    private SharePreferenceUtil su;
    private MyCount mc;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_emailverify;
    }

    @Override
    protected void initView() {
        super.initView();
        su = new SharePreferenceUtil(this);
        tv_title.setText("信息验证");
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

    @OnClick({R.id.btn_ok, R.id.btn_get_code, R.id.btn_back})
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.btn_ok:

                break;
            case R.id.btn_get_code:
                if (checkData()) {
                    if (mc != null)
                        mc.cancel();
                    mc = new MyCount(60 * 1000, 1000); // 第一参数是总的时间，第二个是间隔时间 都是毫秒为单位
                    mc.start();
                }
                break;
            case R.id.btn_back:
                finish();

                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //恢复验证码相关状态
        leftTime = su.getRegisterLeftTiming();
        closeTime = su.getRegisterTime();
        if (leftTime < 3)
            return;

        long curT = System.currentTimeMillis();
        if (closeTime != 0 && curT - closeTime >= leftTime * 1000) {

        } else {
            mc = new MyCount(leftTime * 1000 - (curT - closeTime), 1000);
            mc.start();
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        if (leftTime < 3) {
            su.setRegisterLeftTiming(0);
            su.setRegisterTime(0);
        } else {
            su.setRegisterLeftTiming(leftTime);
            closeTime = System.currentTimeMillis();
            su.setRegisterTime(closeTime);
            if (mc != null)
                mc.cancel();
        }
    }

    // 倒计时
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (!isFinishing()) {
                btn_get_code.setEnabled(true);
                btn_get_code.setText(getResources().getString(R.string.fasongyanzhengma));
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (!isFinishing()) {
                btn_get_code.setEnabled(false);
                btn_get_code.setText(String.format(
                        getString(R.string.register_countdown),
                        millisUntilFinished / 1000));
                leftTime = millisUntilFinished / 1000;
            }
        }
    }

    // 验证输入信息是否正确
    private boolean checkData() {
        if (Utils.isNull(et_email.getText().toString())
                || !Utils.checkIsEmail(et_email.getText().toString())) {
            Utils.toastShow(this, getResources().getString(R.string.qingshuruzhengqueyouxiang));
            return false;
        }
        return true;
    }

}
