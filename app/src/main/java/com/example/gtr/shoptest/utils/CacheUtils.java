package com.example.gtr.shoptest.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 缓存工具类
 */

public class CacheUtils {
    /**
     * 得到保存的String类型的数据
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("myshop", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 保存的String类型的数据
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("myshop", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }









}
