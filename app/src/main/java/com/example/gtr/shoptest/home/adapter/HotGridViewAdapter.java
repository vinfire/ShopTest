package com.example.gtr.shoptest.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gtr.shoptest.R;
import com.example.gtr.shoptest.home.bean.HomeResultBean;
import com.example.gtr.shoptest.utils.Constants;

import java.util.List;

/**
 * Created by GTR on 2017/5/19.
 */

public class HotGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<HomeResultBean.ResultBean.HotInfoBean> datas;

    public HotGridViewAdapter(Context context, List<HomeResultBean.ResultBean.HotInfoBean> hotInfo) {
        mContext = context;
        datas = hotInfo;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.ivHot = (ImageView) convertView.findViewById(R.id.iv_hot);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据位置得到对应的数据
        HomeResultBean.ResultBean.HotInfoBean hotInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + hotInfoBean.getFigure()).into(viewHolder.ivHot);
        viewHolder.tvName.setText(hotInfoBean.getName());
        viewHolder.tvPrice.setText("￥"+hotInfoBean.getCover_price());
        return convertView;
    }

    class ViewHolder{
        ImageView ivHot;
        TextView tvName;
        TextView tvPrice;
    }
}
