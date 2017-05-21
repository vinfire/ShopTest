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
 * Created by GTR on 2017/5/18.
 */

public class RecommendGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomeResultBean.ResultBean.RecommendInfoBean> datas;

    public RecommendGridViewAdapter(Context context, List<HomeResultBean.ResultBean.RecommendInfoBean> recommendInfo) {
        mContext= context;
        datas = recommendInfo;
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
        if (convertView==null){
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.ivRecommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据位置得到对应的数据
        HomeResultBean.ResultBean.RecommendInfoBean recommendInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + recommendInfoBean.getFigure()).into(viewHolder.ivRecommend);
        viewHolder.tvName.setText(recommendInfoBean.getName());
        viewHolder.tvPrice.setText("￥"+recommendInfoBean.getCover_price());
        return convertView;
    }

    class ViewHolder{
        ImageView ivRecommend;
        TextView tvName;
        TextView tvPrice;
    }
}
