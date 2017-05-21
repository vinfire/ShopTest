package com.example.gtr.shoptest;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gtr.shoptest.home.bean.GoodsBean;
import com.example.gtr.shoptest.shoppingcart.bean.CartStorage;
import com.example.gtr.shoptest.utils.Constants;

import java.io.Serializable;

public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tvMoreShare;
    private TextView tvMoreSearch;
    private TextView tvMoreHome;
    private Button btnMore;

    private GoodsBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);

        //接收数据
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsBean");

        initViews();

        if (goodsBean != null) {
            setDataForView(goodsBean);
        }

        initListener();


    }


    private void initViews() {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);

        llGoodsRoot = (LinearLayout) findViewById(R.id.ll_goods_root);
        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);

        tvMoreShare = (TextView) findViewById(R.id.tv_more_share);
        tvMoreSearch = (TextView) findViewById(R.id.tv_more_search);
        tvMoreHome = (TextView) findViewById(R.id.tv_more_home);
        btnMore = (Button) findViewById(R.id.btn_more);
    }


    private void initListener() {
        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);

        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);

        tvMoreShare.setOnClickListener(this);
        tvMoreSearch.setOnClickListener(this);
        tvMoreHome.setOnClickListener(this);
        btnMore.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            finish();
        } else if (v == ibGoodInfoMore) {
            // Handle clicks for ibGoodInfoMore
        } else if (v == btnGoodInfoAddcart) {
            //添加到购物车
            CartStorage.getInstance().addData(goodsBean);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCallcenter) {
            // Handle clicks for btnGoodInfoAddcart
        } else if (v == tvGoodInfoCollection) {
            // Handle clicks for btnGoodInfoAddcart
        } else if (v == tvGoodInfoCart) {
            // Handle clicks for btnGoodInfoAddcart
        } else if (v == tvMoreShare) {
            // Handle clicks for btnGoodInfoAddcart
        } else if (v == tvMoreSearch) {
            // Handle clicks for btnGoodInfoAddcart
        } else if (v == tvMoreHome) {
            // Handle clicks for btnGoodInfoAddcart
        } else if (v == btnMore) {
            // Handle clicks for btnGoodInfoAddcart
        }
    }


    private void setDataForView(GoodsBean goodsBean) {
        String figure = goodsBean.getFigure();
        String name = goodsBean.getName();
        String coverPrice = goodsBean.getCover_price();
        String productId = goodsBean.getProduct_id();

        if (figure != null){
            Glide.with(this).load(Constants.BASE_URL_IMAGE + figure).into(ivGoodInfoImage);
        }
        if (name != null){
            tvGoodInfoName.setText(name);
        }
        if (coverPrice != null) {
            tvGoodInfoPrice.setText("￥" + coverPrice);
        }

        setWebViewData(productId);
    }

    private void setWebViewData(String productId) {
        if (productId != null){

            wbGoodInfoMore.setWebViewClient(new WebViewClient(){
                /**
                 * 低版本时
                 */
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 返回值是 true 的时候控制去 WebView 打开，为 false 调用系统 浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }

//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        view.loadUrl(request.getUrl().toString());
//                    }
//                    // 返回值是 true的时候控制去 WebView打开，为 false调用系统浏览器或第三方浏览器
//                    return true;
//                }
            });

            WebSettings settings = wbGoodInfoMore.getSettings();
            // 启用支持 javascript
            settings.setJavaScriptEnabled(true);
            //支持双击页面后变大变小
            settings.setUseWideViewPort(true);
            // 优先使用缓存
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            wbGoodInfoMore.loadUrl("http://www.baidu.com");
        }
    }
}
