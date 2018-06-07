package com.heizi.kuaguo.block.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.heizi.kuaguo.R;

import java.util.LinkedList;
import java.util.List;


public class AdapterAddImage extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private boolean isShow;

    private List<ModelAddImage> data = new LinkedList<ModelAddImage>();
    private ModelAddImage lastImge = new ModelAddImage("last_image", "last_image", 1, 0, null);

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void call(int position);
    }


    public AdapterAddImage(Context context, boolean isShow) {
        this.isShow = isShow;
        this.context = context;
        inflater = LayoutInflater.from(context);
        setDataList(data);
    }

    public void setData(List<ModelAddImage> dataList) {
        setDataList(dataList);
        notifyDataSetChanged();
    }

    private void setDataList(List<ModelAddImage> dataList) {
        if (dataList != null) {
            data.clear();
            data.addAll(dataList);
        }
        if (isShow) {
            if (data.size() < 6) {
                data.add(lastImge);
            }

        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public ModelAddImage getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_device_signed_action_image, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setBuild(data, position);
        // TODO Auto-generated method stub
        return convertView;
    }

    class ViewHolder {
        private ImageView mIv, mDelete;


        public ViewHolder(View v) {
            // TODO Auto-generated constructor stub
            mIv = (ImageView) v.findViewById(R.id.iv_select_pic3);
            mDelete = (ImageView) v.findViewById(R.id.iv_delete);
        }

        public void setBuild(List<ModelAddImage> images, final int position) {
            // TODO Auto-generated method stub
            System.out.println("图片地址==" + images.toString());
            String imaLocal = images.get(position).getLocalUrl();
            String imaHttp = images.get(position).getHttpUrl();
            images.get(position).setProcessImageView(mIv);
            if (isShow) {
                mDelete.setVisibility(View.VISIBLE);
            } else {
                mDelete.setVisibility(View.GONE);
            }
            if (imaLocal != null) {
                if (imaLocal.equals("last_image") && isShow) {//解决了图片乱跳的问题（本地图片和网络图片的判断的问题，imageloader会解决）
                    //最后一张图片
                    com.heizi.kuaguo.utils.Utils.downloadIcon2View("drawable://" + R.mipmap.image_add_icon, mIv, R.mipmap.image_add_icon, R.mipmap.image_add_icon);
                    mDelete.setVisibility(View.GONE);
                } else {
//                    mIv.setImageBitmap(ImageFactory.getBitmap(imaLocal));
                    //加载本地
                    com.heizi.kuaguo.utils.Utils.downloadIcon2View("file://" + imaLocal, mIv, R.mipmap.default_img_fail, R.mipmap.default_img_fail);

                }
            } else if (imaHttp != null) {
                if (imaHttp.equals("last_image") && isShow) {
                    //最后一张图片
                    com.heizi.kuaguo.utils.Utils.downloadIcon2View("drawable://" + R.mipmap.image_add_icon, mIv, R.mipmap.image_add_icon, R.mipmap.image_add_icon);
                } else {
                    //加载网络
                    com.heizi.kuaguo.utils.Utils.downloadIcon2View(imaHttp, mIv, R.mipmap.default_img_fail, R.mipmap.default_img_fail);
                }
            }
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    callback.call(position);
                    notifyDataSetChanged();
                }
            });
        }

    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

}
