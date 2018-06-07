package com.heizi.kuaguo.block.my;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;
import com.heizi.kuaguo.utils.RefreshUtils;
import com.heizi.kuaguo.utils.SelectPhotoUtils;
import com.heizi.kuaguo.utils.VisibleEnum;
import com.heizi.mycommon.utils.ImageFactory;
import com.heizi.mycommon.utils.Utils;
import com.heizi.mylibrary.callback.IResponseCallback;
import com.heizi.mylibrary.model.DataSourceModel;
import com.heizi.mylibrary.model.ErrorModel;
import com.heizi.mylibrary.retrofit2.ParseStringProtocol;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by leo on 17/6/24.
 */

public class ActivityUserInfo extends BaseSwipeBackCompatActivity implements View.OnClickListener {

    @InjectView(R.id.iv_avatar)
    ImageView iv_avatar;
    @InjectView(R.id.tv_name)
    TextView tv_name;

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
    @InjectView(R.id.ll_7)
    LinearLayout ll_7;
    @InjectView(R.id.ll_8)
    LinearLayout ll_8;
    @InjectView(R.id.ll_9)
    LinearLayout ll_9;
    @InjectView(R.id.ll_10)
    LinearLayout ll_10;
    @InjectView(R.id.ll_11)
    LinearLayout ll_11;


    private ParseStringProtocol protocolEditName;
    private IResponseCallback<DataSourceModel<String>> callbackEditName;


    String userNameTmp = "";
    //本地图片路径,服务器图片路径
    private String localFilePath = "", img_url = "";
    SelectPhotoUtils selectPhotoUtils;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectPhotoUtils = new SelectPhotoUtils(this, null, btn_back,
                PHOTO_PICKED_WITH_DATA, CAMERA_WITH_DATA);
    }


    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("我的信息");
        tv_name.setText("ssss");
//        if (!TextUtils.isEmpty(userModel.getHead_img())) {
//            ImageFactory.displayImage(userModel.getHead_img(), iv_avatar, R.mipmap.photo, R.mipmap.photo);
//        }
    }

    @Override
    protected void initData() {
        super.initData();

        protocolEditName = new ParseStringProtocol(this, SERVER_URL_NEW + MY_INFO);
        callbackEditName = new IResponseCallback<DataSourceModel<String>>() {
            @Override
            public void onSuccess(DataSourceModel<String> data) {
                if (!isFinishing()) {
                    isBusy = false;
                    mDialog.hideDialog();
                    userModel.setNickname(userNameTmp);
                    application.setUserModel(userModel);
                    RefreshUtils.sendBroadcast(ActivityUserInfo.this, userModel);
                    Utils.toastShow(ActivityUserInfo.this, "昵称修改成功!");
//                JSONObject jsonObject = new JSONObject(data.json);
//                userModel.setHead_img(jsonObject.getString("user_avatar"));
//                application.setUserModel(userModel);
//                RefreshUtils.sendBroadcast(ActivityUserInfo.this, userModel);
                }
            }

            @Override
            public void onFailure(ErrorModel errorModel) {
                if (!isFinishing()) {
                    isBusy = false;
                    mDialog.hideDialog();
                }
            }

            @Override
            public void onStart() {
                isBusy = true;
                mDialog.showDialog();
            }
        };

    }


    /**
     * 上传图片
     */
    private void upLoadImg(String localFilePath) {

        AjaxParams params = new AjaxParams();
        try {
            params.put("head_img", new File(localFilePath));
            params.put("token", userModel.getToken());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FinalHttp fh = new FinalHttp();
        fh.configTimeout(20000);
        fh.post(SERVER_URL_NEW + MY_INFO, params, new AjaxCallBack<String>() {
            public void onStart() {
                isBusy = true;
                mDialog.showDialog();
            }

            @Override
            public void onSuccess(String json) {
                if (!isFinishing()) {
                    isBusy = false;
                    mDialog.hideDialog();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("result"));
                        userModel.setHead_img(jsonObject1.getString("head_img"));
                        application.setUserModel(userModel);
                        RefreshUtils.sendBroadcast(ActivityUserInfo.this, userModel);
                        Utils.toastShow(ActivityUserInfo.this, "头像修改成功!");
                        Log.d("==", "上传图片成功:" + json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onFailure(Throwable t, int errorNo, String strMsg) {
                if (!isFinishing()) {
                    isBusy = false;
                    mDialog.hideDialog();
                    Log.d("==", "上传图片失败:" + strMsg);
                }
            }
        });
    }


    @OnClick({R.id.btn_back, R.id.iv_avatar, R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4, R.id.ll_5, R.id.ll_6, R.id.ll_7, R.id.ll_8, R.id.ll_9, R.id.ll_10, R.id.ll_11})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_right:
            case R.id.iv_avatar:
                selectPhotoUtils.ShowPop1(btn_back);
                break;

            case R.id.ll_4:
                Bundle bundle4 = new Bundle();
                bundle4.putInt("type", VisibleEnum.EDIT.getCode());
                startActivity(this, ActivityBasicInfo.class, bundle4);
                break;

            case R.id.ll_5:
                Bundle bundle5 = new Bundle();
                bundle5.putInt("type", VisibleEnum.EDIT.getCode());
                startActivity(this, ActivityLanguageList.class, bundle5);
                break;

            case R.id.ll_6:
                Bundle bundle6 = new Bundle();
                bundle6.putInt("type", VisibleEnum.EDIT.getCode());
                startActivity(this, ActivitySkillList.class, bundle6);
                break;

            case R.id.ll_7:
                Bundle bundle7 = new Bundle();
                bundle7.putInt("type", VisibleEnum.EDIT.getCode());
                startActivity(this, ActivityIndustryList.class, bundle7);
                break;

            case R.id.ll_8:
                Bundle bundle8 = new Bundle();
                bundle8.putInt("type", VisibleEnum.EDIT.getCode());
                startActivity(this, ActivityCertifiList.class, bundle8);
                break;

            case R.id.ll_9:
                Bundle bundle9 = new Bundle();
                bundle9.putInt("type", VisibleEnum.EDIT.getCode());
                startActivity(this, ActivityBasicTagList.class, bundle9);
                break;

            case R.id.ll_10:
                Bundle bundle10 = new Bundle();
                bundle10.putInt("type", VisibleEnum.EDIT.getCode());
                startActivity(this, ActivityBasicInfo.class, bundle10);
                break;

            case R.id.ll_11:
                Bundle bundle11 = new Bundle();
                bundle11.putInt("type", VisibleEnum.EDIT.getCode());
                startActivity(this, ActivityBasicInfo.class, bundle11);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                if (requestCode == PHOTO_PICKED_WITH_DATA) {// 从相册选择图片
                    try {
                        Uri uri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = managedQuery(uri, proj,
                                null, null, null);
                        int column_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String filePath = cursor.getString(column_index);
                        localFilePath = filePath;
                        File file = new File(filePath);

                        ImageFactory.displayImage("file://" + file, iv_avatar, R.mipmap.photo, R.mipmap.photo);
                        upLoadImg(localFilePath);

                    } catch (Exception e) {
                    }
                }
            }
            if (requestCode == CAMERA_WITH_DATA) {// 相机
                String filePath = selectPhotoUtils.getFilePath();
                Uri imgUri = selectPhotoUtils.getImgUri();
                try {
                    if (filePath != null && imgUri != null) {
                        File file = new File(filePath);
                        localFilePath = filePath;
                        ImageFactory.displayImage("file://" + file, iv_avatar, R.mipmap.photo, R.mipmap.photo);
                        upLoadImg(localFilePath);
                    }
                } catch (Exception e) {
                }
            }

        }
    }
}
