package com.example.gtr.shoptest.utils;

/**
 * 配置各个页面的联网地址
 */

public class Constants {
    //Android Studio默认的模拟器使用这个IP
    public static final String BASE = "http://10.0.2.2:80";

    //请求Json数据基本URL
    public static final String BASE_URL_JSON = BASE + "/atguigu/json/";
    //请求图片基本URL
    public static final String BASE_URL_IMAGE = BASE + "/atguigu/img/";

    //请求首页Json数据的URL
    public static final String HOME_URL_JSON = BASE + "/atguigu/json/HOME_URL.json";
}
