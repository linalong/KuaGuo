package com.heizi.kuaguo.block.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseListActivity;
import com.heizi.kuaguo.activity.MainTabsActivity;
import com.heizi.kuaguo.utils.VisibleEnum;
import com.heizi.mycommon.adapter.CommonAdapter;
import com.solo.library.ISlide;
import com.solo.library.OnClickSlideItemListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 录入证件照
 * Created by leo on 17/9/19.
 */

public class ActivityCertifiList extends BaseListActivity implements View.OnClickListener { //listview

    @InjectView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @InjectView(R.id.btn_ok)
    Button btn_ok;
    @InjectView(R.id.btn_cancle)
    Button btn_cancle;
    @InjectView(R.id.ll_none)
    LinearLayout ll_none;
    @InjectView(R.id.tv_notice)
    TextView tv_notice;
    @InjectView(R.id.btn_notice)
    Button btn_notice;
    private CommonAdapter adapter;
    List<String> listData = new ArrayList<>();

//    private List<ModelCommit> listData = new ArrayList<>();
//    ParseListProtocol<ModelCommit> parseListProtocol;
//    IResponseCallback<DataSourceModel<ModelCommit>> callback;


    AdapterLanguage adapterLanguage;
    int type = VisibleEnum.SAVE.getCode();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_language_list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        type = getIntent().getExtras().getInt("type");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        if (type == VisibleEnum.NEXT.getCode()) {
            btn_ok.setText(getResources().getString(R.string.xiayibu));
            btn_cancle.setVisibility(View.VISIBLE);
        } else {
            btn_ok.setText(getResources().getString(R.string.baocun));
        }
        tv_title.setText(getResources().getString(R.string.luruzhengzhao));
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getResources().getString(R.string.tianjia));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_notice.setText(getResources().getString(R.string.tianjiazhengzhao));
        tv_notice.setText(getResources().getString(R.string.zanwuzhengzhao));
        //listview
        adapterLanguage = new AdapterLanguage(listData);
        adapterLanguage.setOnClickSlideItemListener(new OnClickSlideItemListener() {
            @Override
            public void onItemClick(ISlide iSlide, View view, int i) {

            }

            @Override
            public void onClick(ISlide iSlide, View v, int position) {
                //控件的所有子控件的点击回调都会回调此方法
                if (v.getId() == R.id.btn_del) { //对删除按钮的监听（上面adapter的getBindOnClickViewsIds()中设置了R.id.btn_del）
                    iSlide.close(); //关闭当前的按钮
                    listData.remove(position);
                    adapterLanguage.notifyDataSetChanged();
                    if (listData.size() == 0) {
                        mListView.setVisibility(View.GONE);
                        ll_none.setVisibility(View.VISIBLE);
                    } else {
                        mListView.setVisibility(View.VISIBLE);
                        ll_none.setVisibility(View.GONE);
                    }
                }
            }
        });
        mListView.setAdapter(adapterLanguage);
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
        listData.add("雅思");
        listData.add("计算机2级");
        adapterLanguage.notifyDataSetChanged();
        measureListHeight(mListView);
    }

    @OnClick({R.id.tv_right, R.id.btn_notice, R.id.btn_ok, R.id.btn_cancle})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                startActivity(this, ActivityAddCertifi.class, bundle);
                break;
            case R.id.btn_notice:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", 0);
                startActivity(this, ActivityAddCertifi.class, bundle2);
                break;
            case R.id.btn_ok:
                if (type == VisibleEnum.NEXT.getCode()) {
                    Intent intent = new Intent(this, MainTabsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    btn_ok.setText(getResources().getString(R.string.baocun));
                }

                break;
            case R.id.btn_cancle:
                if (type == VisibleEnum.NEXT.getCode()) {
                    Intent intent = new Intent(this, MainTabsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (type == VisibleEnum.SAVE.getCode()) {

                } else if (type == VisibleEnum.EDIT.getCode()) {

                }
                break;
        }
    }
}
