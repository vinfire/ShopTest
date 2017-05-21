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
 * 频道GridView的适配器
 */

public class ChannelGridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<HomeResultBean.ResultBean.ChannelInfoBean> datas;

    public ChannelGridViewAdapter(Context context, List<HomeResultBean.ResultBean.ChannelInfoBean> channelInfo) {
        mContext = context;
        datas = channelInfo;
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
            convertView = View.inflate(mContext, R.layout.item_channel,null);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_channel);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeResultBean.ResultBean.ChannelInfoBean channelInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + channelInfoBean.getImage()).into(viewHolder.ivIcon);
        viewHolder.tvTitle.setText(channelInfoBean.getChannel_name());

        return convertView;
    }

    static class ViewHolder{
        ImageView ivIcon;
        TextView tvTitle;
    }
}
