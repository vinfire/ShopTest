package com.example.gtr.shoptest.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.gtr.shoptest.R;
import com.example.gtr.shoptest.base.BaseFragment;
import com.example.gtr.shoptest.home.adapter.HomeFragmentAdapter;
import com.example.gtr.shoptest.home.bean.HomeResultBean;
import com.example.gtr.shoptest.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

/**
 * 首页的Fragment
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment" ;
    private RecyclerView rvHome;
    private ImageButton ibTop;
    private TextView tvSearchHome;
    private TextView tvMessageHome;
    private HomeResultBean.ResultBean resultBean;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ibTop = (ImageButton) view.findViewById(R.id.ib_top);
        tvSearchHome = (TextView) view.findViewById(R.id.tv_search_home);
        tvMessageHome = (TextView) view.findViewById(R.id.tv_message_home);

        // 设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //联网请求主页的数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL_JSON;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    /**
                     * 当请求失败的时候回调
                     * @param call
                     * @param e
                     * @param id
                     */
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG, "onError: 首页请求失败=="+e.getMessage());
                    }

                    /**
                     * 当联网成功的时候回调
                     * @param response 请求成功的数据
                     * @param id
                     */
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "onResponse: 首页请求成功=="+response);
                        //解析数据
                        processData(response);
                    }
                });
    }

    private void processData(String response) {
        //使用FastJson解析Json数据
        HomeResultBean homeResultBean = JSON.parseObject(response, HomeResultBean.class);
        resultBean = homeResultBean.getResult();
        if (resultBean != null){
            //有数据
            //设置适配器
            HomeFragmentAdapter adapter = new HomeFragmentAdapter(mContext, resultBean);
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);  //使用LinearLayoutManager无法实现对item的监听
            //设置跨度大小的监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <=3){
                        //隐藏
                        ibTop.setVisibility(View.GONE);
                    }else {
                        //显示
                        ibTop.setVisibility(View.VISIBLE);
                    }
                    return 1;
                }
            });
            rvHome.setLayoutManager(manager);
            rvHome.setAdapter(adapter);
        }else {
            //没有数据
            Toast.makeText(mContext, "数据为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void initListener() {
        // 置顶的监听
        ibTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 回到顶部
                rvHome.scrollToPosition(0);
            }
        });

        // 搜素的监听
        tvSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        // 消息的监听
        tvMessageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
