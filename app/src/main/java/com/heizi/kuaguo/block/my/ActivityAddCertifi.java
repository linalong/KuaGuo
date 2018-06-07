package com.heizi.kuaguo.block.my;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heizi.kuaguo.Constants;
import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;
import com.heizi.kuaguo.utils.SelectPhotoUtils;
import com.heizi.kuaguo.utils.Utils;
import com.heizi.mycommon.utils.FileUtils;
import com.heizi.mycommon.utils.ImageFactory;
import com.lljjcoder.citypickerview.widget.TypePicker;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 添加证照信息
 * Created by leo on 17/12/5.
 */

public class ActivityAddCertifi extends BaseSwipeBackCompatActivity implements View.OnClickListener {


    @InjectView(R.id.ll_zhengshu)
    LinearLayout ll_zhengshu;
    @InjectView(R.id.ll_time)
    LinearLayout ll_time;

    @InjectView(R.id.tv_content)
    TextView tv_content;
    @InjectView(R.id.tv_time)
    TextView tv_time;

    @InjectView(R.id.et_score)
    EditText et_score;


    @InjectView(R.id.grid_view_photo)
    GridView grid_view_photo;
    AdapterAddImage adapter;
    private String camperPicName;
    public static String cameraPicPath;
    List<ModelAddImage> dataList = new ArrayList<>();

    SelectPhotoUtils selectPhotoUtils;


    /**
     * 证书列表
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
        return R.layout.activity_add_certifi;
    }


    @Override
    protected void initView() {
        super.initView();
        selectPhotoUtils = new SelectPhotoUtils(this, null, btn_back,
                PHOTO_PICKED_WITH_DATA, CAMERA_WITH_DATA);
        tv_title.setText(getResources().getString(R.string.tianjiazhengzhao));
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
        tv_content.setText("雅思");

        Map map = new HashMap();
        map.put("1", "雅思");
        map.put("2", "托福");
        map.put("3", "计算机二级");
        typePicker.setData(map);

        if (type == 1)
            adapter = new AdapterAddImage(this, false);
        else
            adapter = new AdapterAddImage(this, true);
        adapter.setCallback(new AdapterAddImage.Callback() {
            @Override
            public void call(int position) {
                dataList.remove(position);
            }
        });

        grid_view_photo.setAdapter(adapter);
        Utils.setgridViewHeightBasedOnChildren(grid_view_photo, 3);

        grid_view_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                camperPicName = "" + System.currentTimeMillis();
                if (position < 6) {
                    ModelAddImage info = (ModelAddImage) adapter.getItem(position);
                    if (info.getType() == 1) {
                        selectPictrue();
                    } else {
                        if (info.getIsSusses() == 1) {
                            //重新上传图片
                            dataList.remove(position);
                            selectPictrue();
                        }
                    }
                } else {
                    com.heizi.mycommon.utils.Utils.toastShow(ActivityAddCertifi.this, "不好意思，暂时只能上传6张图片");
                }
            }
        });
    }

    public void selectPictrue() {
        selectPhotoUtils.ShowPop1(btn_back);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                if (requestCode == PHOTO_PICKED_WITH_DATA) {// 从相册选择图片
                    setPictrue(data);
                } else if (requestCode == PHOTO_PICKED_WITH_DATA) {
                    setPictrue(data);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPictrue(Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String file = Utils.getRealPathFromURI(data, selectedImage, ActivityAddCertifi.this);
                loadPictrue(file);
            }
        } else {
            try {
                //增加图片到系统图库 。部分手机会失败返回null
                ImageFactory.compressAndGenImage(cameraPicPath, cameraPicPath, 500, false);
                loadPictrue(cameraPicPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public void loadPictrue(final String file) {
        ModelAddImage uploadImageNew = new ModelAddImage();
        uploadImageNew.setLocalUrl(file);
        dataList.add(uploadImageNew);
        adapter.setData(dataList);
        String fileSize = FileUtils.getAutoFileOrFilesSize(file);
        Log.d("==", "fileSize: " + FileUtils.getAutoFileOrFilesSize(file));
        Utils.setgridViewHeightBasedOnChildren(grid_view_photo, 3);
    }

    /**
     * 上传图片
     */
    private void upLoadImg(String localFilePath) {
        Map<String, RequestBody> maps = new HashMap<>();
        File fileSave = new File(localFilePath);
        RequestBody requestBodySave = RequestBody.create(MediaType.parse("image/*"), fileSave);
        maps.put("image\"; filename=\"" + fileSave.getName() + "", requestBodySave);

        AjaxParams params = new AjaxParams();
        try {
            params.put("image", new File(localFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FinalHttp fh = new FinalHttp();
        fh.configTimeout(20000);
        fh.post(Constants.uploadImage, params, callBack);
    }


    private AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
        public void onStart() {
            if (!isFinishing())
                mDialog.showDialog();
        }

        @Override
        public void onSuccess(String json) {

            if (!isFinishing()) {
                String result = "";

                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    result = jsonObject.getString("msg");
                    if (code == 1) {
                        Log.d("==", "上传图片返回数据" + json);
                        for (int i = 0; i < dataList.size(); i++) {
                            if (dataList.get(i).getIsSusses() == 2) {
                                ModelAddImage deviceSignedActionImage =
                                        dataList.get(i);
                                deviceSignedActionImage.setHttpUrl("" + result);
                                deviceSignedActionImage.setIsSusses(0);
                                break;
                            }
                        }
                        for (int i = 0; i < dataList.size(); i++) {
                            if (dataList.get(i).getIsSusses() != 0) {
                                dataList.get(i).setIsSusses(2);
                                upLoadImg(dataList.get(i).getLocalUrl());
                                return;
                            }
                        }
                        if (!isFinishing())
                            mDialog.hideDialog();
                    } else {
                        mDialog.hideDialog();
                        com.heizi.mycommon.utils.Utils.toastShow(ActivityAddCertifi.this, "上传图片失败");
                        for (int i = 0; i < dataList.size(); i++) {
                            if (dataList.get(i).getIsSusses() == 2) {
                                dataList.get(i).setIsSusses(1);
                                break;
                            }
                        }
                        tv_right.setEnabled(true);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        public void onFailure(Throwable t, int errorNo, String strMsg) {
            if (!isFinishing()) {
                mDialog.hideDialog();
                com.heizi.mycommon.utils.Utils.toastShow(ActivityAddCertifi.this, "上传图片失败");
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).getIsSusses() == 2) {
                        dataList.get(i).setIsSusses(1);
                        break;
                    }
                }
                tv_right.setEnabled(true);
            }
        }
    };

    @OnClick({R.id.ll_zhengshu, R.id.ll_time})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_zhengshu:
                typePicker.show();
                break;
            case R.id.ll_time:
                String[] time_begin = tv_time.getText().toString().split("-");
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_time.setText(String.format("%d-%d-%d", year, month + 1, dayOfMonth));
                    }
                }, Integer.parseInt(time_begin[0]), Integer.parseInt(time_begin[1]) - 1, Integer.parseInt(time_begin[2])).show();
                break;
        }
    }

}
