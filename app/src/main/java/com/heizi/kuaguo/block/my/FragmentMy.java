package com.heizi.kuaguo.block.my;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.UserModel;
import com.heizi.kuaguo.fragment.BaseFragment;
import com.heizi.kuaguo.utils.RefreshUtils;
import com.heizi.mycommon.utils.ImageFactory;
import com.heizi.mycommon.utils.LoadingD;
import com.heizi.mycommon.utils.Utils;
import com.heizi.mycommon.view.RoundImageView;
import com.heizi.mylibrary.callback.IResponseCallback;
import com.heizi.mylibrary.model.DataSourceModel;
import com.heizi.mylibrary.model.ErrorModel;
import com.heizi.mylibrary.retrofit2.ParseStringProtocol;

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

    @InjectView(R.id.rl_info)
    RelativeLayout rl_info;
    @InjectView(R.id.ll_1)
    LinearLayout ll_1;//我的钱包
    @InjectView(R.id.ll_2)
    LinearLayout ll_2;//我的收藏


    @InjectView(R.id.iv_avatar)
    RoundImageView iv_avatar;
    @InjectView(R.id.nv_msg)
    ImageView nv_msg;
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

    @OnClick({R.id.ll_1, R.id.ll_2, R.id.nv_msg, R.id.rl_info})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_1:
//                if (application.getUserModel() == null) {
//                    Intent intent = new Intent();
//                    intent.setClass(mActivity, ActLogin.class);
//                    startActivity(intent);
//                } else {
                startActivity(mActivity, ActivityMyWallet.class, null);
//                }

                break;
            case R.id.ll_2:
//                if (application.getUserModel() == null) {
//                    Intent intent = new Intent();
//                    intent.setClass(mActivity, ActLogin.class);
//                    startActivity(intent);
//                } else {
//                    startActivity(mActivity, ActivityMyCollect.class, null);
//                }
                break;

            case R.id.nv_msg:
//                if (application.getUserModel() == null) {
//                    Intent intent = new Intent();
//                    intent.setClass(mActivity, ActLogin.class);
//                    startActivity(intent);
//                } else {
//                    startActivity(mActivity, ActivityMessageList.class, null);
//                }
                break;

            case R.id.rl_info:
                startActivity(mActivity, ActivityUserInfo.class, null);
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
