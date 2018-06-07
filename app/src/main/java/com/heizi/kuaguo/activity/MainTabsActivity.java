package com.heizi.kuaguo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.heizi.kuaguo.Constants;
import com.heizi.kuaguo.MyApplication;
import com.heizi.kuaguo.R;
import com.heizi.kuaguo.block.my.FragmentMy;
import com.heizi.kuaguo.block.source.FragmentSourceHome;
import com.heizi.mycommon.adapter.CommonFragmentPagerAdapter;
import com.heizi.mycommon.utils.Utils;
import com.heizi.mycommon.view.NoScrollViewPager;
import com.heizi.mycommon.view.NoticeTextView;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;


public class MainTabsActivity extends BaseActivity implements OnClickListener,
        Constants {

    private static final String TAG = MainTabsActivity.class.getSimpleName();
    private static MainTabsActivity instance;
    public FragmentManager manager;
    private long time;
    @InjectViews({R.id.tabs_guide1, R.id.tabs_guide2, R.id.tabs_guide3, R.id.tabs_guide4, R.id.tabs_guide5})
    public List<NoticeTextView> rbtns;

    @InjectView(R.id.vp_main)
    NoScrollViewPager vp_main;

    List<Fragment> fragments;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getSupportFragmentManager();
        instance = this;
    }

    public static MainTabsActivity getInstance() {
        return instance;
    }

    @Override
    protected void initView() {
        super.initView();

        setTitleVisible(false);


        fragments = new ArrayList<Fragment>() {{
            add(new FragmentSourceHome());
            add(new FragmentMy());
            add(new FragmentMy());
            add(new FragmentMy());
            add(new FragmentMy());
        }};

        if (null != fragments && !fragments.isEmpty()) {
            vp_main.setNoScroll(true);
            vp_main.setOffscreenPageLimit(fragments.size());
            vp_main.setAdapter(new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        }

        rbtns.get(0).performClick();
        registerBoradcastReceiver();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        userModel = application.refreshUserModel();
//        if (userModel != null)
//            connect(userModel.getRongyun_token());
    }

    public void changeToMain() {
        changeTabState(rbtns.get(0));
    }

    public void changeToMy() {
        changeTabState(rbtns.get(2));
    }


    private void changeTabState(View v) {
        int b = R.color.blue8;
        int g = R.color.blue9;


        rbtns.get(0).setTextColor(v.getId() == R.id.tabs_guide1 ? getResources()
                .getColor(b) : getResources().getColor(g));
        rbtns.get(1).setTextColor(v.getId() == R.id.tabs_guide2 ? getResources()
                .getColor(b) : getResources().getColor(g));
        rbtns.get(2).setTextColor(v.getId() == R.id.tabs_guide3 ? getResources()
                .getColor(b) : getResources().getColor(g));
        rbtns.get(3).setTextColor(v.getId() == R.id.tabs_guide4 ? getResources()
                .getColor(b) : getResources().getColor(g));
        rbtns.get(4).setTextColor(v.getId() == R.id.tabs_guide5 ? getResources()
                .getColor(b) : getResources().getColor(g));


        float alpha = 1;
        float alpha6 = 0.8f;
        // 设置透明度
        rbtns.get(0).setAlpha(v.getId() == R.id.tabs_guide1 ? alpha : alpha6);
        rbtns.get(1).setAlpha(v.getId() == R.id.tabs_guide2 ? alpha : alpha6);
        rbtns.get(2).setAlpha(v.getId() == R.id.tabs_guide3 ? alpha : alpha6);
        rbtns.get(3).setAlpha(v.getId() == R.id.tabs_guide4 ? alpha : alpha6);
        rbtns.get(4).setAlpha(v.getId() == R.id.tabs_guide5 ? alpha : alpha6);

        // 设置文字显示
        rbtns.get(0).setText(v.getId() == R.id.tabs_guide1 ? "找帮手" : "找帮手");
        rbtns.get(1).setText(v.getId() == R.id.tabs_guide2 ? "接任务" : "接任务");
        rbtns.get(2).setText(v.getId() == R.id.tabs_guide3 ? "我的任务" : "我的任务");
        rbtns.get(3).setText(v.getId() == R.id.tabs_guide4 ? "合作" : "合作");
        rbtns.get(4).setText(v.getId() == R.id.tabs_guide5 ? "我的" : "我的");


        //设置顶部距离
//        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams1.setMargins(0, 13, 0, 0);
//        layoutParams1.weight = 1;
//
//        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams2.setMargins(0, 0, 0, 0);
//        layoutParams2.weight = 1;
//        rbtns.get(0).setLayoutParams(v.getId() == R.id.tabs_guide1 ? layoutParams1 : layoutParams2);
//        rbtns.get(1).setLayoutParams(v.getId() == R.id.tabs_guide2 ? layoutParams1 : layoutParams2);
//        rbtns.get(2).setLayoutParams(v.getId() == R.id.tabs_guide3 ? layoutParams1 : layoutParams2);


        rbtns.get(0).setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(
                        v.getId() == R.id.tabs_guide1 ? R.mipmap.home_1_03
                                : R.mipmap.home_0_03), null, null);
        rbtns.get(1).setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(
                        v.getId() == R.id.tabs_guide2 ? R.mipmap.home_1_05
                                : R.mipmap.home_0_05), null, null);
        rbtns.get(2).setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(
                        v.getId() == R.id.tabs_guide3 ? R.mipmap.home_1_07
                                : R.mipmap.home_0_07), null, null);
        rbtns.get(3).setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(
                        v.getId() == R.id.tabs_guide4 ? R.mipmap.home_1_07
                                : R.mipmap.home_0_07), null, null);
        rbtns.get(4).setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(
                        v.getId() == R.id.tabs_guide5 ? R.mipmap.home_1_09
                                : R.mipmap.home_0_09), null, null);

        if (v.getId() == R.id.tabs_guide1) {
            vp_main.setCurrentItem(0);
        } else if (v.getId() == R.id.tabs_guide2) {
            vp_main.setCurrentItem(1);
        } else if (v.getId() == R.id.tabs_guide3) {
            vp_main.setCurrentItem(2);
        } else if (v.getId() == R.id.tabs_guide4) {
            vp_main.setCurrentItem(3);
        } else if (v.getId() == R.id.tabs_guide5) {
            vp_main.setCurrentItem(4);
        }

    }


    @OnClick({R.id.tabs_guide1, R.id.tabs_guide2, R.id.tabs_guide3, R.id.tabs_guide4, R.id.tabs_guide5})
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tabs_guide1:// 找帮手
                changeTabState(v);
                break;
            case R.id.tabs_guide2:// 接任务
                changeTabState(v);

                break;
            case R.id.tabs_guide3:// 我的任务
                changeTabState(v);

                break;
            case R.id.tabs_guide4:// 合作
                changeTabState(v);

                break;
            case R.id.tabs_guide5:// 我的
//                if (application.getUserModel() == null && application.refreshUserModel() == null) {
//                    Intent intent = new Intent();
//                    intent.setClass(MainTabsActivity.this, ActLogin.class);
//                    startActivity(intent);
//                } else {
                    changeTabState(v);
//                }
                break;
            default:
                break;
        }
    }

    // 得到广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MyApplication.action_token_error)) {
                Utils.toastShow(MainTabsActivity.this, "登录过期，请重新登录!");
                MyApplication.getInstance().logout();
                Intent intent1 = new Intent();
                intent1.setClass(MainTabsActivity.this, MainTabsActivity.class);
                startActivity(intent1);
                MainTabsActivity.getInstance().changeToMain();
            }
            // 刷新底部五个按钮的显示数量
            else if (action.equals(MyApplication.action_refresh_login)) {
            }
        }
    };

    // 注册广播 接收未登录状态 底部按钮刷新
    private void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(MyApplication.action_token_error);
        myIntentFilter.addAction(MyApplication.action_refresh_login);
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onBackPressed() {
        // 是WebViewFragment有回退功能
        if (System.currentTimeMillis() - time > 2500) {
            Toast.makeText(this, getString(R.string.string_exit),
                    Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        UMShareAPI.get(this).release();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
