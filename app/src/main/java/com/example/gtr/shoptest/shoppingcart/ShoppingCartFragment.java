package com.example.gtr.shoptest.shoppingcart;

import android.util.Log;
import android.view.View;

import com.example.gtr.shoptest.R;
import com.example.gtr.shoptest.base.BaseFragment;
import com.example.gtr.shoptest.home.bean.GoodsBean;
import com.example.gtr.shoptest.shoppingcart.bean.CartStorage;

import java.util.List;

/**
 * 购物车的Fragment
 */

public class ShoppingCartFragment extends BaseFragment {

    private static final String TAG = "ShoppingCartFragment";

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shoppingcart, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();
        for (int i = 0; i < goodsBeanList.size(); i++) {
            Log.i(TAG, "initData: "+goodsBeanList.get(i).toString());
        }
    }
}
