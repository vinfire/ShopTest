package com.example.gtr.shoptest.shoppingcart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gtr.shoptest.R;
import com.example.gtr.shoptest.base.BaseFragment;
import com.example.gtr.shoptest.home.bean.GoodsBean;
import com.example.gtr.shoptest.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.gtr.shoptest.shoppingcart.bean.CartStorage;

import java.util.List;

/**
 * 购物车的Fragment
 */

public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ShoppingCartFragment";
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;

    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;
    private LinearLayout llEmptyShopcart;
    private ShoppingCartAdapter adapter;

    private static final int ACTION_EDIT = 1; //编辑状态
    private static final int ACTION_COMPLETE = 2; //完成状态

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shoppingcart, null);

        tvShopcartEdit = (TextView) view.findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        llCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);
        checkboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) view.findViewById(R.id.btn_check_out);
        llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        cbAll = (CheckBox) view.findViewById(R.id.cb_all);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnCollection = (Button) view.findViewById(R.id.btn_collection);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmptyCartTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);
        llEmptyShopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);

        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection.setOnClickListener(this);

        initListener();
        return view;
    }

    private void initListener() {
        //设置默认的编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) v.getTag();
                if (action == ACTION_EDIT){
                    //切换为完成状态
                    showDelete();
                }else {
                    //切换为编辑状态
                    hideDelete();
                }
            }
        });
    }

    private void showDelete() {
        //1.设置状态和文本（完成）
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        //2.变成非勾选
        if (adapter != null){
            adapter.checkAll_none(false);
            adapter.checkAll();
        }
        //3.显示删除视图
        llDelete.setVisibility(View.VISIBLE);

        //4.隐藏结算视图
        llCheckAll.setVisibility(View.GONE);
    }

    private void hideDelete() {

        //1.设置状态和文本（编辑）
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        //2.变成非勾选
        if (adapter != null){
            adapter.checkAll_none(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }
        //3.显示结算视图
        llCheckAll.setVisibility(View.VISIBLE);

        //4.隐藏删除视图
        llDelete.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        super.initData();

        showData();
    }

    @Override
    public void onClick(View v) {
        if (v == btnCheckOut) {
            // Handle clicks for btnCheckOut
        } else if (v == btnDelete) {
            // 删除选中的
            adapter.deleteData();
            // 校验状态
            adapter.checkAll();
            //若数据大小为0
            if (adapter.getItemCount()==0){
                emptyShoppingCart();
            }
        } else if (v == btnCollection) {
            // Handle clicks for btnCollection
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    private void showData() {
        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();
        if (goodsBeanList != null && goodsBeanList.size()>0){
            tvShopcartEdit.setVisibility(View.VISIBLE);
            //有数据
            llCheckAll.setVisibility(View.VISIBLE);
            //把当没有数据显示的布局隐藏
            llEmptyShopcart.setVisibility(View.GONE);
            //设置适配器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            adapter = new ShoppingCartAdapter(mContext, goodsBeanList,tvShopcartTotal,checkboxAll,cbAll);
            recyclerview.setAdapter(adapter);
        }else {
            //没有数据
            //显示数据为空的布局
            emptyShoppingCart();
        }
    }

    private void emptyShoppingCart() {
        llEmptyShopcart.setVisibility(View.VISIBLE);
        //隐藏编辑按钮
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }


}


