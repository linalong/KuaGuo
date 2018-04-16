package com.heizi.kuaguo.block.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.heizi.mycommon.adapter.CommonAdapter;
import com.heizi.mycommon.adapter.ViewHolderHelper;
import com.heizi.mycommon.utils.ImageFactory;
import com.heizi.mycommon.utils.LoadingD;
import com.heizi.mycommon.utils.Utils;
import com.heizi.mylibrary.callback.IResponseCallback;
import com.heizi.mylibrary.model.DataSourceModel;
import com.heizi.mylibrary.model.ErrorModel;
import com.heizi.mylibrary.retrofit2.ParseListProtocol;
import com.heizi.kuaguo.R;
import com.heizi.kuaguo.activity.BaseSwipeBackCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by leo on 17/12/21.
 */

public class ActivityCategory extends BaseSwipeBackCompatActivity {
    @InjectView(R.id.grid)
    GridView grid;
    CommonAdapter adapterGrid;
    private List<ModelCategory> dataList = new ArrayList<>();


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_category;
    }

    @Override
    protected void initView() {
        super.initView();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("全部分类");
        adapterGrid = new CommonAdapter(ActivityCategory.this, dataList, R.layout.item_home_category_item) {
            @Override
            public void getView(int position, ViewHolderHelper holder) {
                ImageView img = holder.findViewById(R.id.img);
                ImageFactory.displayImage(dataList.get(position).getIcon_url(), img, 0, 0);
                TextView title = holder.findViewById(R.id.title);
                title.setText(dataList.get(position).getName());
            }
        };

        grid.setAdapter(adapterGrid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityCategory.this, ActivityStoreList.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", dataList.get(position).getName());
                bundle.putString("categoryId", dataList.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        getDataCateroty();
    }

    //获取分类
    private void getDataCateroty() {

        ParseListProtocol<ModelCategory> parseListProtocol = new ParseListProtocol(this, SERVER_URL_NEW + HOME_CATEGORY, ModelCategory.class);
        parseListProtocol.getData(new HashMap(), new IResponseCallback<DataSourceModel<ModelCategory>>() {
            @Override
            public void onSuccess(DataSourceModel<ModelCategory> data) {
                if (!isFinishing()) {
                    isBusy = false;
                    if (data.list.size() > 0) {
                        for (ModelCategory model : data.list) {
                            if (!model.getName().contains("全部")) {
                                dataList.add(model);
                            }
                        }
                        adapterGrid.notifyDataSetChanged();
                    }
                    LoadingD.hideDialog();
                }

            }

            @Override
            public void onFailure(ErrorModel errorModel) {
                if (!isFinishing()) {
                    Utils.toastShow(ActivityCategory.this, errorModel.getMsg());
                    isBusy = false;
                    LoadingD.hideDialog();
                }
            }

            @Override
            public void onStart() {
                isBusy = true;
                LoadingD.showDialog();
            }
        });
    }


}
