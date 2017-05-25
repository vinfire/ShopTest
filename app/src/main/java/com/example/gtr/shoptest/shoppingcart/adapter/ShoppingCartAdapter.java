package com.example.gtr.shoptest.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gtr.shoptest.R;
import com.example.gtr.shoptest.home.bean.GoodsBean;
import com.example.gtr.shoptest.shoppingcart.bean.CartStorage;
import com.example.gtr.shoptest.shoppingcart.view.AddSubView;
import com.example.gtr.shoptest.utils.Constants;

import java.util.List;

/**
 * Created by GTR on 2017/5/21.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {


    private Context mContext;
    private List<GoodsBean> mDatas;
    private TextView tvShopcartTotal;
    private CheckBox checkboxAll;  //完成状态下的全选CheckBox
    private CheckBox cbAll;  //编辑状态下的全选CheckBox


    public ShoppingCartAdapter(Context context, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        mContext = context;
        mDatas = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();
        //设置点击事件
        setListener();
        //校验是否全选
        checkAll();
    }

    private void setListener() {
       setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onItemClick(int postion) {
               // 1.根据位置找到对应的Bean对象
               GoodsBean goodsBean = mDatas.get(postion);
               // 2.设置取反状态
               goodsBean.setSelected(!goodsBean.isSelected());
               // 3.刷新状态
               notifyItemChanged(postion);
               // 4.校验是否全选
               checkAll();
               // 5.重新计算总价格
               showTotalPrice();
           }
       });

        //设置checkboxAll的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isChecked = checkboxAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isChecked);
                //3.计算总价格
                showTotalPrice();
            }
        });

        //设置cbAll的点击事件
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isChecked = cbAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isChecked);
            }
        });
    }

    /**
     * 设置全选和非全选
     * @param isChecked
     */
    public void checkAll_none(boolean isChecked) {
        if (mDatas != null && mDatas.size()>0){
            for (int i = 0; i < mDatas.size(); i++) {
                GoodsBean goodsBean = mDatas.get(i);
               goodsBean.setSelected(isChecked);
                notifyItemChanged(i);
            }
        }
    }

    public void checkAll() {
        if (mDatas != null && mDatas.size()>0){
            int number = 0;
            for (int i = 0; i < mDatas.size(); i++) {
                GoodsBean goodsBean = mDatas.get(i);
                if (!goodsBean.isSelected()){
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                }else {
                    //选中的个数
                    number++;
                }
            }
            if (number == mDatas.size()){
                //全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        }else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText(getTotalPrice()+"");
    }

    /**
     * 计算总价格
     * @return
     */
    private double getTotalPrice() {
        double totalPrice = 0.00;
        if (mDatas!=null && mDatas.size()>0){
            for (int i = 0; i < mDatas.size(); i++) {
                GoodsBean goodsBean = mDatas.get(i);
                if (goodsBean.isSelected()){
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber())*Double.valueOf(goodsBean.getCover_price());
                    Log.i("Price", "getTotalPrice: "+totalPrice);
                }
            }
        }
        return totalPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_shop_cart, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //1.根据位置得到对应的对象
        final GoodsBean goodsBean = mDatas.get(position);
        //2.设置数据
        holder.cbGov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText("￥"+goodsBean.getCover_price());
        holder.numberAddSubView.setValue(goodsBean.getNumber());
        holder.numberAddSubView.setMinValue(1);
        holder.numberAddSubView.setMaxValue(10);

        //设置商品数量的变化
        holder.numberAddSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                // 1.当前列表内存中要更新
                goodsBean.setNumber(value);
                // 2.本地要更新
                CartStorage.getInstance().updateData(goodsBean);
                // 3.刷新适配器
                notifyItemChanged(position);
                // 4.再次计算总价格
                showTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void deleteData() {
        if (mDatas != null && mDatas.size()>0){
            for (int i = 0; i < mDatas.size(); i++) {
                //删除选中的
                GoodsBean goodsBean = mDatas.get(i);
                if (goodsBean.isSelected()){
                    //在内存中移除
                    mDatas.remove(goodsBean);
                    //同步到本地
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);
                    i--;
                }
            }

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox cbGov;
        private ImageView ivGov;
        private TextView tvDescGov;
        private TextView tvPriceGov;
        private AddSubView numberAddSubView;

        public ViewHolder(View itemView) {
            super(itemView);
            cbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            numberAddSubView = (AddSubView) itemView.findViewById(R.id.numberAddSubView);
            //设置Item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(getLayoutPosition());
                }
            });
        }
    }

    /**
     * 点击Item的监听者
     */
    public interface OnItemClickListener{
        /**
         * 当点击某条的时候被回调
         * @param postion
         */
        public void onItemClick(int postion);
    }

    private static OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
