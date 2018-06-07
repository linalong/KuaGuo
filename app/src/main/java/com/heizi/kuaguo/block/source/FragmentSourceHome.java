package com.heizi.kuaguo.block.source;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.heizi.kuaguo.Constants;
import com.heizi.kuaguo.MyApplication;
import com.heizi.kuaguo.R;
import com.heizi.kuaguo.block.home.ActivityCity;
import com.heizi.kuaguo.block.home.ActivitySearch;
import com.heizi.kuaguo.block.home.ModelAddress;
import com.heizi.kuaguo.block.home.ModelProduct;
import com.heizi.kuaguo.block.login.ActLogin;
import com.heizi.kuaguo.block.maidan.ActivityScanCode;
import com.heizi.kuaguo.fragment.BaseScrollListFragment;
import com.heizi.kuaguo.utils.UtilDialog;
import com.heizi.mycommon.adapter.CommonAdapter;
import com.heizi.mycommon.adapter.ViewHolderHelper;
import com.heizi.mycommon.utils.Utils;
import com.heizi.mylibrary.callback.IResponseCallback;
import com.heizi.mylibrary.model.DataSourceModel;
import com.heizi.mylibrary.retrofit2.ParseListProtocol;
import com.markmao.pulltorefresh.widget.OnScrollChangedListener;

import java.util.ArrayList;
import java.util.List;

import static com.heizi.kuaguo.R.id.tv_local;


/**
 * 首页
 * 1.首页列表默认关联城市id和分类id  百度定位变化后获取城市id,id获取成功重新刷新列表,从activityCity更改城市返回后重新刷新列表
 * Created by leo on 17/5/16.
 */

public class FragmentSourceHome extends BaseScrollListFragment implements
        View.OnClickListener, Constants, OnScrollChangedListener {
    LinearLayout ll_title, ll_scroll1, ll_scroll2;
    TextView tv_local1, tv_local2, tv_tuijian, tv_jiedan;
    EditText et_search1, et_search2;
    ImageView iv_sxuan1, iv_sxuan2;
    RelativeLayout rl_jiage;

    private LinearLayout ll_notice;
    //listview
    private List<String> listData = new ArrayList<>();
    private CommonAdapter adapter;


    private static AMapLocationClient mlocationClient;
    private static AMapLocationClientOption mLocationOption;

    //获取列表
    private ParseListProtocol<ModelProduct> parsePanoList;
    private IResponseCallback<DataSourceModel<ModelProduct>> callbackPanoList;


    ModelAddress modelAddress;

    private boolean isRepeat = true;


    public static int codeGetCity = 0x009888;

    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            listData.add("Test XScrollView item " + (pageIndex));
        }
        Log.d("==", "Test XListView item " + (pageIndex));
        adapter.notifyDataSetChanged();
        measureListHeight();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_source_home;
    }

    @Override
    protected int getLayoutTop() {
        return R.layout.fragment_source_home_top;
    }

    @Override
    public void onScrollChanged(int top, int oldTop) {
        int mBuyLayout2ParentTop = Math.max(top, ll_scroll1.getTop() + Utils.typedValueDP(mActivity, 150));
        ll_scroll2.layout(0, mBuyLayout2ParentTop, ll_scroll2.getWidth(), mBuyLayout2ParentTop + ll_scroll2.getHeight());

        if (top > Utils.typedValueDP(mActivity, 150)) {
            ll_scroll2.setVisibility(View.VISIBLE);
        } else {
            ll_scroll2.setVisibility(View.GONE);
        }


    }

    @Override
    protected void initView(View v) {

        super.initView(v);
        ll_scroll1 = (LinearLayout) v.findViewById(R.id.ll_scroll1);
        ll_scroll2 = (LinearLayout) v.findViewById(R.id.ll_scroll2);

        tv_local1 = (TextView) v.findViewById(R.id.tv_local1);
        tv_local1.setOnClickListener(this);
        tv_local2 = (TextView) v.findViewById(R.id.tv_local2);
        tv_local2.setOnClickListener(this);
        tv_tuijian = (TextView) v.findViewById(R.id.tv_tuijian);
        tv_tuijian.setOnClickListener(this);
        tv_jiedan = (TextView) v.findViewById(R.id.tv_jiedan);
        tv_jiedan.setOnClickListener(this);
        rl_jiage = (RelativeLayout) v.findViewById(R.id.rl_jiage);
        rl_jiage.setOnClickListener(this);
        et_search1 = (EditText) v.findViewById(R.id.et_search1);
        et_search1.setOnClickListener(this);
        et_search2 = (EditText) v.findViewById(R.id.et_search2);
        et_search2.setOnClickListener(this);
        iv_sxuan1 = (ImageView) v.findViewById(R.id.iv_sxuan1);
        iv_sxuan1.setOnClickListener(this);
        iv_sxuan2 = (ImageView) v.findViewById(R.id.iv_sxuan2);
        iv_sxuan2.setOnClickListener(this);
        ll_title = (LinearLayout) v.findViewById(R.id.ll_title);
        mScrollView.setOnScrollChangedListener(this);
        modelAddress = MyApplication.getInstance().modelAddress;

        ll_notice = (LinearLayout) v.findViewById(R.id.ll_notice);
        //listview
        adapter = new CommonAdapter(getActivity(), listData, R.layout.item_source_home) {
            @Override
            public void getView(int position, ViewHolderHelper holder) {
//                TextView tv_name = holder.findViewById(R.id.tv_name);
//                ImageView iv_store = holder.findViewById(R.id.iv_store);
//                RatingBar rb_star = holder.findViewById(R.id.rb_star);
//                TextView tv_score = holder.findViewById(R.id.tv_score);
//                TextView tv_zhekou = holder.findViewById(R.id.tv_zhekou);
//                TextView tv_commit = holder.findViewById(R.id.tv_commit);
//                TextView tv_distance = holder.findViewById(R.id.tv_distance);
//                tv_score.setText(listData.get(position).getAve_score() + "分");
//                rb_star.setProgress((int) Double.parseDouble(listData.get(position).getAve_score()));
//                tv_name.setText(listData.get(position).getName());
//                tv_zhekou.setText("返利折扣: " + listData.get(position).getIntegral_ratio() + "%");
//                tv_commit.setText(listData.get(position).getComment_num() + "评论");
//                tv_distance.setText(listData.get(position).getDistance());
//                ImageFactory.displayImage(listData.get(position).getHead_img(), iv_store, R.mipmap.iv_fail_mid, R.mipmap.iv_fail_mid);
//
//                DragView view = holder.findViewById(R.id.drag_view);
//                view.setTag(position);
////                views.add(view);
//                view.setOnDragStateListener(new DragView.DragStateListener() {
//                    @Override
//                    public void onOpened(DragView dragView) {
//                    }
//
//                    @Override
//                    public void onClosed(DragView dragView) {
//                    }
//
//                    @Override
//                    public void onForegroundViewClick(DragView dragView, View v) {
//                        int pos = (int) dragView.getTag();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("id", listData.get(pos).getId());
//                        startActivity(getActivity(), ActivityStoreDetail.class, bundle);
//                    }
//
//                    @Override
//                    public void onBackgroundViewClick(DragView dragView, View v) {
//                        int pos = (int) dragView.getTag();
//
//                        listData.remove(pos);
//                        adapter.notifyDataSetChanged();
//                        measureListHeight();
//                    }
//                });
            }
        };
        mListView.setAdapter(adapter);
        measureListHeight();


    }

    @Override
    protected void initViewTop(View v) {
        super.initViewTop(v);
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.haiwaiziyuan));
    }

    AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            ModelAddress model = new ModelAddress();
            model.setCity(aMapLocation.getCity());
            model.setLatitude(aMapLocation.getLatitude());
            model.setLongitude(aMapLocation.getLongitude());
            if (!TextUtils.isEmpty(aMapLocation.getAoiName())) {
                model.setAddress(aMapLocation.getAoiName());
            } else if (!TextUtils.isEmpty(aMapLocation.getPoiName())) {
                model.setAddress(aMapLocation.getPoiName());
            } else if (!TextUtils.isEmpty(aMapLocation.getStreet())) {
                model.setAddress(aMapLocation.getStreet());
            } else {
                final AlertDialog myDialog = new AlertDialog.Builder(getActivity()).create();
                myDialog.setCanceledOnTouchOutside(false);
                myDialog.show();
                myDialog.getWindow().setContentView(R.layout.alert_home);
                TextView tvContent = (TextView) myDialog.getWindow()
                        .findViewById(R.id.tv_content);
                tvContent.setText("获取位置信息失败,是否重试?");
                final Button btn_sure = (Button) myDialog.getWindow().findViewById(R.id.btn_sure);
                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mlocationClient.startLocation();
                        myDialog.cancel();
                    }
                });
                myDialog.getWindow().findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mlocationClient.stopLocation();
                        myDialog.cancel();
                    }
                });
                tv_local1.setText("定位失败");
                return;
            }

            MyApplication.getInstance().modelAddress = model;
            modelAddress = model;
            tv_local1.setText(modelAddress.getAddress());
            mlocationClient.stopLocation();
            if (listData.size() == 0)
                getData();

        }
    };


    @Override
    protected void initData() {
        super.initData();

        getData();

//        getDataPager();
//
//        getDataCateroty();
//
//        parsePanoList = new ParseListProtocol<>(mActivity, SERVER_URL_NEW + STORE_LIST, ModelProduct.class);
//        callbackPanoList = new IResponseCallback<DataSourceModel<ModelProduct>>() {
//            @Override
//            public void onSuccess(DataSourceModel<ModelProduct> data) {
//                if (!getActivity().isFinishing()) {
//                    isBusy = false;
//                    //先判断是否是刷新,刷新成功则清除数据
//                    if (isRefresh) {
//                        pageIndex = 1;
//                        listData.removeAll(listData);
//                    }
//                    if (data.list.size() > 0) {
//                        listData.addAll(data.list);
//                        measureListHeight();
//                        //数据小于请求的每页数据
//                        if (data.list.size() < pageSize) {
//                            mScrollView.setPullLoadEnable(false);
//                        }
//                    } else {
//                        mScrollView.setPullLoadEnable(false);
//                    }
//                    adapter.notifyDataSetChanged();
//
//                    if (listData.size() == 0) {
//                        ll_notice.setVisibility(View.VISIBLE);
//                    } else {
//                        ll_notice.setVisibility(View.GONE);
//                    }
//                    isRefresh = false;
//                    onLoad();
//                }
//            }
//
//            @Override
//            public void onFailure(ErrorModel errorModel) {
//                if (!getActivity().isFinishing()) {
//                    isBusy = false;
//                    isRefresh = false;
//                    onLoad();
//                }
//            }
//
//            @Override
//            public void onStart() {
//                isBusy = true;
//            }
//        };
//
//        if (modelAddress != null) {
//            tv_local.setText(modelAddress.getAddress());
//            if (listData.size() == 0)
//                getData();
//        } else {
//            mlocationClient = new AMapLocationClient(getApplicationContext());
//            mLocationOption = new AMapLocationClientOption();
//            mlocationClient.setLocationListener(mapLocationListener);
//            mLocationOption.setInterval(2000);
//            mlocationClient.setLocationOption(new AMapLocationClientOption());
//            mlocationClient.startLocation();
//        }


    }

    /**
     */
    protected void getData() {
        geneItems();
//        Map<String, String> maps = new HashMap<>();
//        if (modelAddress != null) {
//            maps.put("city_id", modelAddress.getCity());
//            maps.put("longitude", modelAddress.getLongitude() + "");
//            maps.put("latitude", modelAddress.getLatitude() + "");
//        }
//        maps.put("pagesize", pageSize + "");
//        maps.put("p", pageIndex + "");
//        if (parsePanoList != null)
//            parsePanoList.postData(maps, callbackPanoList);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case tv_local:
                Bundle bundle = new Bundle();
                if (modelAddress != null)
                    bundle.putString("cityname", modelAddress.getCity());
                startActivityForResult(mActivity, ActivityCity.class, bundle, codeGetCity);
                break;
            case R.id.iv_saosao:
                if (application.getUserModel() == null && application.refreshUserModel() == null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ActLogin.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ActivityScanCode.class);
                    startActivity(intent);
                }
                break;
            case R.id.et_search1:
//                startActivity(mActivity, ActivityCommit.class, null);
//                startActivity(mActivity, ActivitySearch.class, null);
                break;
            case R.id.ll_scroll2:
//                startActivity(mActivity, ActivityCommit.class, null);
                startActivity(mActivity, ActivitySearch.class, null);
                break;

            case R.id.iv_sxuan1:
//                startActivity(mActivity, ActivityCommit.class, null);
                startActivity(mActivity, ActivityShaiXuan.class, null);
                break;
            case R.id.iv_sxuan2:
//                startActivity(mActivity, ActivityCommit.class, null);
                startActivity(mActivity, ActivityShaiXuan.class, null);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        UtilDialog.dismiss();
        mlocationClient.unRegisterLocationListener(mapLocationListener);
        mlocationClient.stopLocation();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //在城市页选择城市后的处理,和上次不是同一个城市则重新获取
        if (requestCode == codeGetCity) {
            modelAddress = MyApplication.getInstance().modelAddress;
            tv_local1.setText(modelAddress.getAddress());
            onRefresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
