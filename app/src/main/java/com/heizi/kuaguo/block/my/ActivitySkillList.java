package com.heizi.kuaguo.block.my;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;
import com.heizi.kuaguo.utils.VisibleEnum;
import com.heizi.mycommon.adapter.CommonAdapter;
import com.heizi.mycommon.adapter.ViewHolderHelper;
import com.heizi.mycommon.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 选择技能
 * Created by leo on 17/9/19.
 */

public class ActivitySkillList extends BaseSwipeBackCompatActivity implements View.OnClickListener { //listview

    @InjectView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @InjectView(R.id.btn_ok)
    Button btn_ok;
    @InjectView(R.id.btn_cancle)
    Button btn_cancle;
    @InjectView(R.id.list_view1)
    ListView mListView1;
    @InjectView(R.id.list_view2)
    ListView mListView2;
    @InjectView(R.id.scroll_view)
    ScrollView mScrollView;

    @InjectView(R.id.tv_num)
    TextView tv_num;

    protected int pageIndex = 1, pageSize = 10;
    protected boolean isRefresh = false;// 当前是否在加载数据

    List<String> listData1 = new ArrayList<>();
    List<String> listData2 = new ArrayList<>();

//    private List<ModelCommit> listData = new ArrayList<>();
//    ParseListProtocol<ModelCommit> parseListProtocol;
//    IResponseCallback<DataSourceModel<ModelCommit>> callback;

    CommonAdapter adapter1, adapter2;
    int type = VisibleEnum.SAVE.getCode();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_skill_list;
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
        tv_title.setText(getResources().getString(R.string.xuanzejineng));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_num.setText(String.format(getResources().getString(R.string.niyixuanze), listData1.size()));
        adapter1 = new CommonAdapter(this, listData1, R.layout.item_skill_on) {
            @Override
            public void getView(int position, ViewHolderHelper holder) {
                TextView tv_name = holder.findViewById(R.id.tv_name);
                tv_name.setText(listData1.get(position));
            }
        };
        adapter2 = new CommonAdapter(this, listData2, R.layout.item_skill_off) {
            @Override
            public void getView(int position, ViewHolderHelper holder) {
                TextView tv_name = holder.findViewById(R.id.tv_name);
                tv_name.setText(listData2.get(position));
            }
        };
        mListView1.setAdapter(adapter1);
        mListView2.setAdapter(adapter2);
        mScrollView.setFillViewport(true);

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listData2.add(0, listData1.get(position));
                listData1.remove(position);
                tv_num.setText(String.format(getResources().getString(R.string.niyixuanze), listData1.size()));
                measureListHeight();
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        });
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listData1.size() < 8) {
                    listData1.add(0, listData2.get(position));
                    listData2.remove(position);
                    tv_num.setText(String.format(getResources().getString(R.string.niyixuanze), listData1.size()));
                    measureListHeight();
                    adapter1.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                } else {
                    Utils.toastShow(ActivitySkillList.this, getResources().getString(R.string.zuiduoxuanzejineng));
                }
            }
        });
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
        listData1.add("111111");
        listData1.add("222222");
        listData1.add("333333");

        listData2.add("444444");
        listData2.add("555555");
        listData2.add("666666");
        listData2.add("777777");
        listData2.add("888888");
        listData2.add("999999");
        measureListHeight();
    }


    protected int measureListHeight1() {
        // get ListView adapter
        ListAdapter adapter = mListView1.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, mListView1);
            if (null == item) continue;

            /**
             * 解决item高度不固定时出问题
             */
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView1.getWidth(), View.MeasureSpec.AT_MOST);
            item.measure(desiredWidth, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView1.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // calculate ListView height
        params.height = totalHeight + (mListView1.getDividerHeight() * (adapter.getCount() - 1));

        mListView1.setLayoutParams(params);

        return params.height;
    }

    protected int measureListHeight2() {
        // get ListView adapter
        ListAdapter adapter = mListView2.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, mListView2);
            if (null == item) continue;

            /**
             * 解决item高度不固定时出问题
             */
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView2.getWidth(), View.MeasureSpec.AT_MOST);
            item.measure(desiredWidth, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView2.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // calculate ListView height
        params.height = totalHeight + (mListView2.getDividerHeight() * (adapter.getCount() - 1));

        mListView2.setLayoutParams(params);

        return params.height;
    }

    public void measureListHeight() {
        measureListHeight1();
        measureListHeight2();
    }

    @OnClick({R.id.btn_ok, R.id.btn_cancle})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (type == VisibleEnum.NEXT.getCode()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", VisibleEnum.NEXT.getCode());
                    startActivity(ActivitySkillList.this, ActivityLanguageList.class, bundle);
                } else {
                    btn_ok.setText(getResources().getString(R.string.baocun));
                }

                break;
            case R.id.btn_cancle:
                if (type == VisibleEnum.NEXT.getCode()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", VisibleEnum.NEXT.getCode());
                    startActivity(ActivitySkillList.this, ActivityLanguageList.class, bundle);
                } else if (type == VisibleEnum.SAVE.getCode()) {

                } else if (type == VisibleEnum.EDIT.getCode()) {

                }
                break;
        }
    }
}
