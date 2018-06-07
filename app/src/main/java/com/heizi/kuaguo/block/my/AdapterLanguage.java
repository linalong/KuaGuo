package com.heizi.kuaguo.block.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heizi.kuaguo.R;
import com.solo.library.SlideBaseAdapter;
import com.solo.library.SlideTouchView;

import java.util.List;

/**
 * Created by leo on 2018/5/10.
 */

public class AdapterLanguage extends SlideBaseAdapter {
    List list;

    public AdapterLanguage(List list) {
        this.list = list;
    }

    @Override
    public int[] getBindOnClickViewsIds() {
        return new int[]{R.id.btn_del};  //必须调用, 删除按钮或者其他你想监听点击事件的View的id
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = new MyViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, null);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            holder.mSlideTouchView = (SlideTouchView) convertView.findViewById(R.id.mSlideTouchView);
            convertView.setTag(holder);

            bindSlideState(holder.mSlideTouchView); //必须调用

        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        bindSlidePosition(holder.mSlideTouchView, position);//必须调用

        holder.tv1.setText(String.valueOf(list.get(position)));
        return convertView;
    }


    class MyViewHolder {
        TextView tv1;
        TextView tv2;
        SlideTouchView mSlideTouchView;
    }

}
