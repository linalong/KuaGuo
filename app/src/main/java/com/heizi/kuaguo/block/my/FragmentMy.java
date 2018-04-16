package com.heizi.kuaguo.block.my;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heizi.mycommon.utils.ImageFactory;
import com.heizi.mycommon.utils.LoadingD;
import com.heizi.mycommon.utils.SystemUtil;
import com.heizi.mycommon.utils.Utils;
import com.heizi.mycommon.view.RoundImageView;
import com.heizi.mylibrary.callback.IResponseCallback;
import com.heizi.mylibrary.model.DataSourceModel;
import com.heizi.mylibrary.model.ErrorModel;
import com.heizi.mylibrary.retrofit2.ParseStringProtocol;
import com.heizi.kuaguo.R;
import com.heizi.kuaguo.UserModel;
import com.heizi.kuaguo.activity.MainTabsActivity;
import com.heizi.kuaguo.block.login.ActChangePwd;
import com.heizi.kuaguo.block.login.ActLogin;
import com.heizi.kuaguo.fragment.BaseFragment;
import com.heizi.kuaguo.utils.RefreshUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by leo on 17/5/29.
 */

public class FragmentMy extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.ll_1)
    LinearLayout ll_1;//我的邀请码
    @InjectView(R.id.ll_2)
    LinearLayout ll_2;//分享软件
    @InjectView(R.id.ll_3)
    LinearLayout ll_3;//收藏店铺
    @InjectView(R.id.ll_4)
    LinearLayout ll_4;//修改密码
    @InjectView(R.id.ll_5)
    LinearLayout ll_5;//个人信息
    @InjectView(R.id.ll_6)
    LinearLayout ll_6;//联系我们
    @InjectView(R.id.ll_7)
    LinearLayout ll_7;//注销登录
    @InjectView(R.id.ll_8)
    LinearLayout ll_8;//意见反馈
    @InjectView(R.id.ll_9)
    LinearLayout ll_9;//奖励信息


    @InjectView(R.id.ll_mypoint)
    LinearLayout ll_mypoint;//我的积分
    @InjectView(R.id.ll_7)
    LinearLayout ll_givepoint;//积分转赠
    @InjectView(R.id.ll_7)
    LinearLayout ll_rule;//积分规则

    @InjectView(R.id.iv_avatar)
    RoundImageView iv_avatar;
    @InjectView(R.id.nv_msg)
    ImageView nv_msg;
    @InjectView(R.id.nv_qiandao)
    ImageView nv_qiandao;
    @InjectView(R.id.tv_name)
    TextView tv_name;

    RefreshUtils refreshUtils;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);

        if (userModel != null) {
            setData(userModel);
            if (!TextUtils.isEmpty(userModel.getToken()))
                getMsg();
        }
        refreshUtils = new RefreshUtils();
        refreshUtils.registerBoradcastReceiver(mActivity, new RefreshUtils.refreshCallback() {
            @Override
            public void call(UserModel userModel) {
                setData(userModel);
            }
        });

    }

    private void setData(UserModel userModel) {
        tv_name.setText(userModel.getNickname());
        if (!TextUtils.isEmpty(userModel.getHead_img())) {
            ImageFactory.displayImage(userModel.getHead_img(), iv_avatar, R.mipmap.photo, R.mipmap.photo);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        refreshUtils.unregisterBoradcastReceiver(getActivity());
    }

    @OnClick({R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4, R.id.ll_5, R.id.ll_6, R.id.ll_7, R.id.ll_8, R.id.ll_9, R.id.ll_mypoint, R.id.ll_givepoint, R.id.ll_rule, R.id.nv_msg, R.id.nv_qiandao})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_1:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityMyInvite.class, null);
                }

                break;
            case R.id.ll_2:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
//                    new ShareAction(getActivity())
//                            .withText("hello")
//                            .withMedia(new UMImage(getActivity(), BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo)))
//                            .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA)
//                            .setCallback(new UMShareListener() {
//                                @Override
//                                public void onStart(SHARE_MEDIA share_media) {
//
//                                }
//
//                                @Override
//                                public void onResult(SHARE_MEDIA share_media) {
//                                    Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
//                                }
//
//                                @Override
//                                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                                    Toast.makeText(getActivity(), "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//
//                                @Override
//                                public void onCancel(SHARE_MEDIA share_media) {
//                                    Toast.makeText(getActivity(), "取消了", Toast.LENGTH_LONG).show();
//                                }
//                            })
//                            .open();
                }

                break;

            case R.id.ll_3:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityMyCollect.class, null);
                }
                break;

            case R.id.ll_4:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActChangePwd.class, null);
                }
                break;

            case R.id.ll_5:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityUserInfo.class, null);
                }

                break;

            case R.id.ll_6:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    if (userModel != null && !TextUtils.isEmpty(userModel.getService_phone()))
                        SystemUtil.call(getActivity(), userModel.getService_phone());
                    else
                        Utils.toastShow(getActivity(), "客服走丢了...");
                }

                break;

            case R.id.ll_7:
                application.logout();
                Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_SHORT).show();
                MainTabsActivity.getInstance().changeToMain();

                break;
            case R.id.ll_8:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityFeedback.class, null);
                }
                break;
            case R.id.ll_9:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityReward.class, null);
                }

                break;

            case R.id.ll_mypoint:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityMyPoint.class, null);
                }
                break;

            case R.id.ll_givepoint:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityGivePoint.class, null);
                }
                break;
            case R.id.ll_rule:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityPointsExplain.class, null);
                }
                break;
            case R.id.nv_msg:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    startActivity(mActivity, ActivityMessageList.class, null);
                }
                break;
            case R.id.nv_qiandao:
                if (application.getUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ActLogin.class);
                    startActivity(intent);
                } else {
                    postQiandao();
                }
                break;

        }
    }

    private void getMsg() {
        ParseStringProtocol protocol = new ParseStringProtocol(getActivity(), SERVER_URL_NEW + unreadMessages);
        Map<String, String> map = new HashMap<>();
        map.put("token", userModel.getToken());
        protocol.postData(map, new IResponseCallback<DataSourceModel<String>>() {
            @Override
            public void onSuccess(DataSourceModel<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.json);
                    int unread = jsonObject.getInt("unread");
                    if (unread == 0) {
                        nv_msg.setImageDrawable(getResources().getDrawable(R.mipmap.iv_ring));
                    } else {
                        nv_msg.setImageDrawable(getResources().getDrawable(R.mipmap.iv_ring2));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(ErrorModel errorModel) {

            }

            @Override
            public void onStart() {

            }
        });
    }

    /**
     * 签到
     */
    private void postQiandao() {
        ParseStringProtocol protocol = new ParseStringProtocol(getActivity(), SERVER_URL_NEW + signIn);
        Map<String, String> map = new HashMap<>();
        map.put("token", userModel.getToken());
        protocol.postData(map, new IResponseCallback<DataSourceModel<String>>() {
            @Override
            public void onSuccess(DataSourceModel<String> response) {
                if (!getActivity().isFinishing()) {
                    isBusy = false;
                    LoadingD.hideDialog();
                    try {
                        JSONObject jsonObject = new JSONObject(response.json);
                        int get_point = jsonObject.getInt("get_point");
                        Utils.toastShow(getActivity(), "签到成功,获得" + get_point + "积分");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(ErrorModel errorModel) {
                if (!getActivity().isFinishing()) {
                    isBusy = false;
                    LoadingD.hideDialog();
                    Utils.toastShow(getActivity(), errorModel.getMsg());
                }

            }

            @Override
            public void onStart() {
                isBusy = true;
                LoadingD.showDialog();
            }
        });
//        new AlertShare(getActivity()).show();
    }
}
