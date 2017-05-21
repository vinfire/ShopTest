package com.example.gtr.shoptest.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gtr.shoptest.GoodsInfoActivity;
import com.example.gtr.shoptest.R;
import com.example.gtr.shoptest.home.bean.GoodsBean;
import com.example.gtr.shoptest.home.bean.HomeResultBean;
import com.example.gtr.shoptest.utils.Constants;
import com.example.gtr.shoptest.utils.DisplayUtil;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GTR on 2017/5/17.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {

    public static final int BANNER = 0;  //广告条幅类型
    public static final int CHANNEL = 1;  //频道类型
    public static final int ACT = 2;  //活动类型
    public static final int SECKILL = 3;  //秒杀类型
    public static final int RECOMMEND = 4;  //推荐类型
    public static final int HOT = 5;  //热卖类型
    private static final String TAG = "HomeFragmentAdapter";
    private static final String GOODS_BEAN = "goodsBean";

    private final Context mContext;
    private final HomeResultBean.ResultBean mResultBean;

    private int currentType = 0;
    private final LayoutInflater mInflater;
    private long dt;

    public HomeFragmentAdapter(Context context, HomeResultBean.ResultBean resultBean) {
        this.mContext = context;
        this.mResultBean = resultBean;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, mInflater.inflate(R.layout.home_rv_banner, parent, false));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, mInflater.inflate(R.layout.home_rv_channel, parent, false));
        } else if (viewType == ACT) {
            return new ActViewHolder(mContext, mInflater.inflate(R.layout.home_rv_act, parent, false));
        }else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, mInflater.inflate(R.layout.home_rv_seckill, parent, false));
        }else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, mInflater.inflate(R.layout.home_rv_recommend, parent, false));
        }else if (viewType == HOT) {
            return new HotViewHolder(mContext, mInflater.inflate(R.layout.home_rv_hot, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==BANNER){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(mResultBean.getBanner_info());
        }else if (getItemViewType(position)==CHANNEL){
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(mResultBean.getChannel_info());
        }else if (getItemViewType(position)==ACT){
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(mResultBean.getAct_info());
        }else if (getItemViewType(position)==SECKILL){
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(mResultBean.getSeckill_info());
        }else if (getItemViewType(position)==RECOMMEND){
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(mResultBean.getRecommend_info());
        }else if (getItemViewType(position)==HOT){
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(mResultBean.getHot_info());
        }
    }

    /**
     * 总共有多少个item
     * @return
     */
    @Override
    public int getItemCount() {
        return 6;
    }

    /**
     * 得到类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position){
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
            default:
                break;
        }
        return currentType;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private Banner mBanner;
        public BannerViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mBanner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<HomeResultBean.ResultBean.BannerInfoBean> bannerInfo) {
            //设置Banner的数据
            ArrayList<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < bannerInfo.size(); i++) {
                String imageUrl = Constants.BASE_URL_IMAGE + bannerInfo.get(i).getImage();
                imagesUrl.add(imageUrl);
            }
            //设置图片加载器
            mBanner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            mBanner.setImages(imagesUrl);
            //设置banner动画效果(手风琴)
            mBanner.setBannerAnimation(Transformer.Accordion);
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();

            //设置item的点击事件
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(mContext, "position = "+position, Toast.LENGTH_SHORT).show();


                    //商品信息
//                    GoodsBean goodsBean = new GoodsBean();
//                    goodsBean.setCover_price(hotInfoBean.getCover_price());
//                    goodsBean.setFigure(hotInfoBean.getFigure());
//                    goodsBean.setName(hotInfoBean.getName());
//                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
//                    startGoodsInfoActivity(goodsBean);
                }
            });
        }


    }

    /**
     * 启动商品信息列表页面
     * @param goodsBean
     */
    private void startGoodsInfoActivity(GoodsBean goodsBean) {
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN, goodsBean);
        mContext.startActivity(intent);
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片
            Glide.with(context).load(path).into(imageView);
        }
    }


    private class ChannelViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private GridView mGridView;

        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mGridView = (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void setData(List<HomeResultBean.ResultBean.ChannelInfoBean> channelInfo) {
            //设置GridView的适配器
            ChannelGridViewAdapter adapter = new ChannelGridViewAdapter(mContext, channelInfo);
            mGridView.setAdapter(adapter);

            //设置item的点击事件
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position = "+position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class ActViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ViewPager mViewpager;

        public ActViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mViewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<HomeResultBean.ResultBean.ActInfoBean> actInfo) {
            //设置间距
            mViewpager.setPageMargin(DisplayUtil.dip2px(mContext, 20));
            //设置ViewPager的适配器
            mViewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return actInfo.size();
                }

                /**
                 * @param view 页面
                 * @param object  instantiateItem方法返回的值
                 * @return
                 */
                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + actInfo.get(position).getIcon_url()).into(imageView);
                    //添加到容器中
                    container.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "position = "+position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

            //setPageTransformer 决定动画效果
            mViewpager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
        }
    }

    private class SeckillViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView tvTimeSeckill;
        private TextView tvMoreSeckill;
        private RecyclerView rvSeckill;
        private SeckillRecyclerViewAdapter adapter;

        public SeckillViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            tvTimeSeckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tvMoreSeckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rvSeckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
        }

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt = dt - 1000;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String time = format.format(new Date(dt));
                tvTimeSeckill.setText(time);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt<=0){
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        public void setData(final HomeResultBean.ResultBean.SeckillInfoBean seckillInfo) {
            //通过Handler设置秒杀倒计时
            dt = Integer.valueOf(seckillInfo.getEnd_time()) - Integer.valueOf(seckillInfo.getStart_time());

            handler.sendEmptyMessageDelayed(0, 1000);

            //设置RecyclerView的适配器
            rvSeckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            adapter = new SeckillRecyclerViewAdapter(mContext, seckillInfo.getList());

            //设置item的点击事件
            adapter.setOnSeckillRecyclerViewClick(new SeckillRecyclerViewAdapter.OnSeckillRecyclerViewClick() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "秒杀--position: "+position, Toast.LENGTH_SHORT).show();
                    HomeResultBean.ResultBean.SeckillInfoBean.ListBean listBean = seckillInfo.getList().get(position);
                    //商品信息
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setFigure(listBean.getFigure());
                    goodsBean.setName(listBean.getName());
                    goodsBean.setProduct_id(listBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
            rvSeckill.setAdapter(adapter);
        }
    }


    private class RecommendViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView tvMoreRecommend;
        private GridView gvRecommend;

        public RecommendViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            tvMoreRecommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gvRecommend = (GridView) itemView.findViewById(R.id.gv_recommend);


        }

        public void setData(final List<HomeResultBean.ResultBean.RecommendInfoBean> recommendInfo) {
            //设置适配器
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(mContext, recommendInfo);
            gvRecommend.setAdapter(adapter);

            gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position = "+position, Toast.LENGTH_SHORT).show();
                    HomeResultBean.ResultBean.RecommendInfoBean recommendInfoBean = recommendInfo.get(position);
                    //商品信息
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }


    private class HotViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView tvMoreHot;
        private GridView gvHot;

        public HotViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            tvMoreHot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gvHot = (GridView) itemView.findViewById(R.id.gv_hot);
        }

        public void setData(final List<HomeResultBean.ResultBean.HotInfoBean> hotInfo) {
            //为GridView设置适配器
            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, hotInfo);
            gvHot.setAdapter(adapter);

            //设置item的监听
            gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position = "+position, Toast.LENGTH_SHORT).show();
                    //热卖商品信息
                    HomeResultBean.ResultBean.HotInfoBean hotInfoBean = hotInfo.get(position);
                    //商品信息
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }
}
