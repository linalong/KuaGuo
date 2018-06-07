package com.heizi.kuaguo.block.my;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseListActivity;
import com.heizi.kuaguo.utils.VisibleEnum;
import com.heizi.mycommon.adapter.CommonAdapter;
import com.heizi.mycommon.adapter.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 基本情况
 * Created by leo on 17/9/19.
 */

public class ActivityBasicTagList extends BaseListActivity implements View.OnClickListener { //listview

    @InjectView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @InjectView(R.id.btn_ok)
    Button btn_ok;
    @InjectView(R.id.tv_des)
    TextView tv_des;
    private CommonAdapter adapter;
    List<String> listData = new ArrayList<>();

//    private List<ModelCommit> listData = new ArrayList<>();
//    ParseListProtocol<ModelCommit> parseListProtocol;
//    IResponseCallback<DataSourceModel<ModelCommit>> callback;


    int type = VisibleEnum.SAVE.getCode();


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_basic_tag_list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        type = getIntent().getExtras().getInt("type");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText(getResources().getString(R.string.jibenqingkuang));
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

        if (type == VisibleEnum.NEXT.getCode() || type == VisibleEnum.EDIT.getCode()) {
            tv_des.setVisibility(View.VISIBLE);
        } else {
            tv_des.setVisibility(View.GONE);
        }
        //listview
        adapter = new CommonAdapter(this, listData, R.layout.item_basic_tag) {
            @Override
            public void getView(int position, ViewHolderHelper holder) {
//                RoundImageView iv_photo = holder.findViewById(R.id.iv_photo);
//                TextView tv_name = holder.findViewById(R.id.tv_name);
//                TextView tv_score = holder.findViewById(R.id.tv_score);
//                TextView tv_time = holder.findViewById(R.id.tv_time);
//                TextView tv_des = holder.findViewById(R.id.tv_des);
//                RatingBar rb_star = holder.findViewById(R.id.rb_star);
//                rb_star.setProgress((int) Double.parseDouble(listData.get(position).getScore()));
//                tv_score.setText(listData.get(position).getScore());
//                tv_name.setText(listData.get(position).getNickname());
//                ImageFactory.displayImage(listData.get(position).getHead_url(), iv_photo, R.mipmap.default_img_fail, R.mipmap.default_img_fail);
//
//                GridView grid = holder.findViewById(R.id.grid);
//                final List<String> dataList = new ArrayList<>();
//                if (!TextUtils.isEmpty(listData.get(position).getImage1())) {
//                    dataList.add(listData.get(position).getImage1());
//                }
//                if (!TextUtils.isEmpty(listData.get(position).getImage2())) {
//                    dataList.add(listData.get(position).getImage2());
//                }
//                if (!TextUtils.isEmpty(listData.get(position).getImage3())) {
//                    dataList.add(listData.get(position).getImage3());
//                }
//                CommonAdapter adapterGrid = new CommonAdapter(ActivityBasicTagList.this, dataList, R.layout.item_device_signed_action_image) {
//                    @Override
//                    public void getView(int position, ViewHolderHelper holder) {
//                        ImageView iv_select_pic3 = holder.findViewById(R.id.iv_select_pic3);
//                        ImageFactory.displayImage(dataList.get(position), iv_select_pic3, R.mipmap.iv_fail_small, R.mipmap.iv_fail_small);
//                    }
//                };
//                grid.setAdapter(adapterGrid);
//                adapterGrid.notifyDataSetChanged();
//                tv_des.setText(listData.get(position).getComment());
            }
        };
        mListView.setAdapter(adapter);
        mListView.setAutoLoadEnable(false);
        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(false);
    }


    @Override
    protected void initData() {
        super.initData();
//        parseListProtocol = new ParseListProtocol<>(this, SERVER_URL_NEW + COMMITLIST, ModelCommit.class);
//        callback = new IResponseCallback<DataSourceModel<ModelCommit>>() {
//            @Override
//            public void onSuccess(DataSourceModel<ModelCommit> data) {
//                if (!isFinishing()) {
//                    isBusy = false;
//                    mDialog.hideDialog();
//                    //先判断是否是刷新,刷新成功则清除数据
//                    if (isRefresh) {
//                        pageIndex = 1;
//                        listData.removeAll(listData);
//                    }
//                    if (data.list.size() > 0) {
//                        listData.addAll(data.list);
//                        //数据小于请求的每页数据
//                        if (data.list.size() < pageSize) {
//                            mScrollView.setPullLoadEnable(false);
//                        }
//                    } else {
//                        mScrollView.setPullLoadEnable(false);
//                    }
//                    measureListHeight();
//                    adapter.notifyDataSetChanged();
//
//
//                    isRefresh = false;
//                    onLoad();
//                }
//
//            }
//
//            @Override
//            public void onFailure(ErrorModel errorModel) {
//                if (!isFinishing()) {
//                    isBusy = false;
//                    mDialog.hideDialog();
//                    Utils.toastShow(ActivityBasicTagList.this, errorModel.getMsg());
//                    isRefresh = false;
//                    onLoad();
//                }
//
//            }
//
//            @Override
//            public void onStart() {
//                isBusy = true;
//                mDialog.showDialog();
//            }
//        };
//
//        if (listData.size() == 0)
        getData();

    }

    /**
     */
    protected void getData() {
//        Map<String, String> maps = new HashMap<>();
//        maps.put("pagesize", pageSize + "");
//        maps.put("p", pageIndex + "");
//        if (parseListProtocol != null)
//            parseListProtocol.postData(maps, callback);
        geneItems();
    }


    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            listData.add("Test XScrollView item " + (pageIndex));
        }
        Log.d("==", "Test XListView item " + (pageIndex));
        adapter.notifyDataSetChanged();
        measureListHeight(mListView);
    }


    @OnClick({R.id.btn_ok})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (type == VisibleEnum.NEXT.getCode()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", VisibleEnum.NEXT.getCode());
                    startActivity(ActivityBasicTagList.this, ActivityIndustryList.class, bundle);
                } else if (type == VisibleEnum.SAVE.getCode()) {

                } else if (type == VisibleEnum.EDIT.getCode()) {

                }

                break;
        }
    }
}
