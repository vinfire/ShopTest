package com.example.gtr.shoptest.shoppingcart.bean;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.gtr.shoptest.MyApplication;
import com.example.gtr.shoptest.home.bean.GoodsBean;
import com.example.gtr.shoptest.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GTR on 2017/5/20.
 */

public class CartStorage {

    private static final String TAG = "CartStorage";
    private final Context mContext;
    private final String JSON_CART = "json_cart";
    //SparseArray的性能优于HashMap，用于存放<Interger,Object>
    private SparseArray<GoodsBean> sparseArray;

    private static CartStorage mCartStorage;

    private CartStorage(Context context){
        mContext = context;
        sparseArray = new SparseArray<>(100);
        listToSparseArray();
    }

    /**
     * 从本地读取的数据加入到SparseArray中
     */
    private void listToSparseArray() {
        List<GoodsBean> goodsBeanList = getAllData();
        if (goodsBeanList == null){
            Log.i(TAG, "listToSparseArray: "+null);
        }
        //把List数据转换成SparseArray
        for (int i = 0; i < goodsBeanList.size(); i++) {
            GoodsBean goodsBean = goodsBeanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    /**
     * 获取本地所有的数据
     * @return
     */
    public List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //1. 从本地获取
        String json = CacheUtils.getString(mContext, JSON_CART);
        //2. 使用Gson转换成列表
        if (!TextUtils.isEmpty(json)){

            //用Gson把String转换成List
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>(){}.getType());
        }
        return goodsBeanList;
    }

    public static CartStorage getInstance(){
        if (mCartStorage==null){
            synchronized (CartStorage.class){
                if (mCartStorage==null){
                    mCartStorage = new CartStorage(MyApplication.getContext());
                }
            }
        }
        return mCartStorage;
    }

    /**
     * 添加数据
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean){

        //1. 添加到内存中SparseArray
        //如果当前数据已经存在， 就修改成number递增
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData != null){
            //内存中已存在这条数据
            tempData.setNumber(tempData.getNumber()+1);
        }else {
            tempData = goodsBean;
        }
        //同步到内存中
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), tempData);

        //2. 同步到本地
        saveToLacal();
    }

    /**
     * 删除数据
     * @param goodsBean
     */
    public void deleteData(GoodsBean goodsBean){

        //1. 在内中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));

        //2. 同步到本地
        saveToLacal();
    }

    /**
     * 更新数据
     * @param goodsBean
     */
    public void updateData(GoodsBean goodsBean){

        //1. 在内中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);

        //2. 同步到本地
        saveToLacal();
    }

    /**
     * 保存数据到本地
     */
    private void saveToLacal() {
        //1.把SparseArray转换成List集合
        List<GoodsBean> goodsBeanList = sparseArrayTOList();
        //2.用Gson把列表转换成String类型
        String json = new Gson().toJson(goodsBeanList);
        //3.把String数据保存
        CacheUtils.saveString(mContext, JSON_CART, json);
    }

    private List<GoodsBean> sparseArrayTOList() {
        ArrayList<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }
}
