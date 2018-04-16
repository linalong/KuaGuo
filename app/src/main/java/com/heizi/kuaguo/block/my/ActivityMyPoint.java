package com.heizi.kuaguo.block.my;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heizi.mycommon.adapter.CommonAdapter;
import com.heizi.mycommon.adapter.ViewHolderHelper;
import com.heizi.mycommon.utils.Utils;
import com.heizi.mylibrary.callback.IResponseCallback;
import com.heizi.mylibrary.model.DataSourceModel;
import com.heizi.mylibrary.model.ErrorModel;
import com.heizi.mylibrary.retrofit2.ParseListProtocol;
import com.heizi.mylibrary.retrofit2.ParseStringProtocol;
import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/9/26.
 */

public class ActivityMyPoint extends BaseListActivity {

    TextView tv_point;

    CommonAdapter adapter;
    List<ModelOrder> listData = new ArrayList<>();
    LinearLayout ll_notice;
    ParseListProtocol<ModelOrder> parseListProtocol;
    IResponseCallback<DataSourceModel<ModelOrder>> callback;

    private String points = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_points_list;
    }


    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("积分记录");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_point = (TextView) findViewById(R.id.tv_point);
        ll_notice = (LinearLayout) findViewById(R.id.ll_notice);
        //listview
        adapter = new CommonAdapter(this, listData, R.layout.item_order) {
            @Override
            public void getView(final int position, ViewHolderHelper holder) {
                TextView tv_name = holder.findViewById(R.id.tv_name);
                TextView tv_time = holder.findViewById(R.id.tv_time);
                final TextView tv_state = holder.findViewById(R.id.tv_state);
                TextView tv_point_use = holder.findViewById(R.id.tv_point_use);
                LinearLayout ll_use = holder.findViewById(R.id.ll_use);
                TextView tv_point_get = holder.findViewById(R.id.tv_point_get);
                tv_time.setText(listData.get(position).getPay_time());
                tv_point_get.setText("+" + listData.get(position).getObtain_integral());
                if (listData.get(position).getType() == 1) {
                    tv_state.setVisibility(View.VISIBLE);
                    ll_use.setVisibility(View.VISIBLE);
                    tv_point_use.setText("-" + listData.get(position).getIntegral_proportion());
                    tv_name.setText(listData.get(position).getSeller_name());
                    if (listData.get(position).getState() == 2) {
                        tv_state.setText("退款");
                        tv_state.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                postRefund(listData.get(position).getId());
                            }
                        });
                    } else if (listData.get(position).getState() == 3) {
                        tv_state.setText("退款中");
                    } else if (listData.get(position).getState() == 9) {
                        tv_state.setText("已退款");
                    } else if (listData.get(position).getState() == 9) {
                        tv_state.setText("已完成");
                    }
                } else {
                    tv_state.setVisibility(View.GONE);
                    ll_use.setVisibility(View.GONE);
                    if (listData.get(position).getType() == 9) {
                        tv_name.setText("评论奖励");
                    } else if (listData.get(position).getType() == 10) {
                        tv_name.setText("签到奖励");
                    } else if (listData.get(position).getType() == 11) {
                        tv_name.setText("注册奖励");
                    }
                }


            }
        };
        mListView.setAdapter(adapter);
        getUser();
    }

    @Override
    protected void initData() {
        super.initData();
        parseListProtocol = new ParseListProtocol<>(this, SERVER_URL_NEW + orders, ModelOrder.class);
        callback = new IResponseCallback<DataSourceModel<ModelOrder>>() {
            @Override
            public void onSuccess(DataSourceModel<ModelOrder> data) {
                if (!isFinishing()) {
                    isBusy = false;
                    mDialog.hideDialog();
                    //先判断是否是刷新,刷新成功则清除数据
                    if (isRefresh) {
                        pageIndex = 1;
                        listData.removeAll(listData);
                    }
                    if (data.list.size() > 0) {
                        listData.addAll(data.list);
                        adapter.notifyDataSetChanged();
                        //数据小于请求的每页数据
                        if (data.list.size() < pageSize) {
                            mListView.setPullLoadEnable(false);
                        }
                    } else {
                        mListView.setPullLoadEnable(false);
                    }

                    if (listData.size() == 0) {
                        ll_notice.setVisibility(View.VISIBLE);
                    } else {
                        ll_notice.setVisibility(View.GONE);
                    }
                    isRefresh = false;
                    onLoad();
                }

            }

            @Override
            public void onFailure(ErrorModel errorModel) {
                if (!isFinishing()) {
                    isBusy = false;
                    isRefresh = false;
                    onLoad();
                    mDialog.hideDialog();
                }
            }

            @Override
            public void onStart() {
                isBusy = true;
                mDialog.showDialog();
            }
        };

        if (listData.size() == 0)
            getData();
    }

    @Override
    protected void getData() {
        super.getData();
        Map<String, String> map = new HashMap<>();
        map.put("token", userModel.getToken());
        map.put("p", pageIndex + "");
        map.put("pagesize", pageSize + "");
        parseListProtocol.postData(map, callback);
    }

    private void getUser() {
        ParseStringProtocol protocol = new ParseStringProtocol(this
                , SERVER_URL_NEW + MY_ACCOUNT);
        Map<String, String> map = new HashMap<>();
        map.put("token", userModel.getToken());
        protocol.postData(map, new IResponseCallback<DataSourceModel<String>>() {
            @Override
            public void onSuccess(DataSourceModel<String> data) {
                try {
                    JSONObject jsonObject = new JSONObject(data.json);
                    points = jsonObject.getString("integral_balance");
                    tv_point.setText(points);
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
     * 申请退款
     */
    private void postRefund(String order_id) {
        ParseStringProtocol protocol = new ParseStringProtocol(this
                , SERVER_URL_NEW + refund);
        Map<String, String> map = new HashMap<>();
        map.put("token", userModel.getToken());
        map.put("order_id", order_id);
        protocol.postData(map, new IResponseCallback<DataSourceModel<String>>() {
            @Override
            public void onSuccess(DataSourceModel<String> data) {
                if (!isFinishing()) {
                    Utils.toastShow(ActivityMyPoint.this, "申请成功");
                    mDialog.hideDialog();
                    onRefresh();
                }
            }

            @Override
            public void onFailure(ErrorModel errorModel) {
                if (!isFinishing()) {
                    isBusy = false;
                    mDialog.hideDialog();
                    Utils.toastShow(ActivityMyPoint.this, errorModel.getMsg());
                }
            }

            @Override
            public void onStart() {
                isBusy = true;
                mDialog.showDialog();
            }
        });
    }

}
