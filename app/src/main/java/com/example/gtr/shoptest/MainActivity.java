package com.example.gtr.shoptest;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.gtr.shoptest.base.BaseFragment;
import com.example.gtr.shoptest.community.fragment.CommunityFragment;
import com.example.gtr.shoptest.home.fragment.HomeFragment;
import com.example.gtr.shoptest.shoppingcart.ShoppingCartFragment;
import com.example.gtr.shoptest.type.fragment.TypeFragment;
import com.example.gtr.shoptest.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private ArrayList<BaseFragment> fragments;
    private int position = 0;
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife和当前activity绑定
        ButterKnife.bind(this);

        //初始化各个 Fragment
        initFragment();

        //设置RadioGroup的监听
        intiListener();

        rgMain.check(R.id.rb_home);

    }

    /**
     * 注：添加的时候要按照顺序
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }

    private void intiListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:  //首页
                        position = 0;
                        break;
                    case R.id.rb_type:  //分类
                        position = 1;
                        break;
                    case R.id.rb_community:  //发现
                        position = 2;
                        break;
                    case R.id.rb_cart:  //购物车
                        position = 3;
                        break;
                    case R.id.rb_user:  //个人中心
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }
                //根据位置去取不同的Fragment
                BaseFragment baseFragment = getFragment(position);
                switchFragment(tempFragment, baseFragment);
            }
        });
    }

    private BaseFragment getFragment(int position) {
        if (fragments!=null && fragments.size()>0){
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(BaseFragment fromFragment, BaseFragment nextFragment) {

        if (tempFragment != nextFragment && nextFragment != null) {
            tempFragment = nextFragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //判断 nextFragment 是否添加
            if (!nextFragment.isAdded()) {
                //隐藏当前Fragment
                if (fromFragment != null) {
                    transaction.hide(fromFragment);
                }
                transaction.add(R.id.frameLayout, nextFragment).commit();
            } else {
                //隐藏当前Fragment
                if (fromFragment != null) {
                    transaction.hide(fromFragment);
                }
                transaction.show(nextFragment).commit();
            }
        }
    }

}
