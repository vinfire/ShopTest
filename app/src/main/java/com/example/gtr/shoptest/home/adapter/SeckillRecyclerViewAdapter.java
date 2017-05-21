package com.example.gtr.shoptest.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gtr.shoptest.R;
import com.example.gtr.shoptest.home.bean.HomeResultBean;
import com.example.gtr.shoptest.utils.Constants;

import java.util.List;

/**
 * 秒杀的适配器
 */

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeResultBean.ResultBean.SeckillInfoBean.ListBean> mBeanListe;

    public SeckillRecyclerViewAdapter(Context context, List<HomeResultBean.ResultBean.SeckillInfoBean.ListBean> beanList) {
        mContext = context;
        mBeanListe = beanList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivFigure;
        private TextView tvCoverPrice;
        private TextView tvOriginPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFigure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tvCoverPrice = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tvOriginPrice = (TextView) itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSeckillRecyclerViewClick != null){
                        onSeckillRecyclerViewClick.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_seckill, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //根据位置得到对应的数据
        HomeResultBean.ResultBean.SeckillInfoBean.ListBean listBean = mBeanListe.get(position);
        //绑定数据
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into(holder.ivFigure);
        holder.tvCoverPrice.setText(listBean.getCover_price());
        holder.tvOriginPrice.setText(listBean.getOrigin_price());
    }


    @Override
    public int getItemCount() {
        return mBeanListe.size();
    }

    public interface OnSeckillRecyclerViewClick{
        /**
         * 当某条被点击的时候回调
         * @param position
         */
        public void onItemClick(int position);
    }

    private OnSeckillRecyclerViewClick onSeckillRecyclerViewClick;

    /**
     * 外界通过此方法设置 item 的监听
     * @param onSeckillRecyclerViewClick
     */
    public void setOnSeckillRecyclerViewClick(OnSeckillRecyclerViewClick onSeckillRecyclerViewClick){
        this.onSeckillRecyclerViewClick = onSeckillRecyclerViewClick;
    }
}
