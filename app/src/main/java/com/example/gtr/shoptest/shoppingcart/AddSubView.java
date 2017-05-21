package com.example.gtr.shoptest.shoppingcart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gtr.shoptest.R;

/**
 * Created by GTR on 2017/5/21.
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private final ImageView ivSub;
    private final TextView tvValue;
    private final ImageView ivAdd;


    private int value = 1;  //当前值
    private int minValue;  //最小值
    private int maxValue;  //最大值

    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //把布局文件实例化，并且加载到AddSubView类中
        View.inflate(context, R.layout.add_sub_view, this);
        ivSub = (ImageView) findViewById(R.id.iv_sub);
        tvValue = (TextView) findViewById(R.id.tv_value);
        ivAdd = (ImageView) findViewById(R.id.iv_add);

        value = getValue();
        setValue(value);

        //设置点击事件
        ivSub.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sub:
                subNumber();
                break;
            case R.id.iv_add:
                addNumber();
                break;
            default:
                break;
        }
    }

    private void subNumber() {
        if (value>minValue){
            value--;
        }
        setValue(value);

        if (mListener != null){
            mListener.onNumberChange(value);
        }
    }

    private void addNumber() {
        if (value<maxValue){
            value++;
        }
        setValue(value);

        if (mListener != null){
            mListener.onNumberChange(value);
        }
    }

    public int getValue() {
        String valueStr = tvValue.getText().toString().trim();
        if (TextUtils.isEmpty(valueStr)){
            value = Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tvValue.setText(value+"");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public interface OnNumberChangeListener{
        /**
         * 当数据发生变化的时候回调
         * @param value
         */
        public void onNumberChange(int value);
    }

    private  OnNumberChangeListener mListener;

    public void setOnNumberChangeListener(OnNumberChangeListener listener){
        mListener = listener;
    }
}
