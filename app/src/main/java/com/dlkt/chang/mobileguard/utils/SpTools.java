package com.dlkt.chang.mobileguard.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/6/20.
 * 偏好设置工具类
 */
public class SpTools {
    public static String getSpString(Context context, String key, String defaultVaulue) {
        SharedPreferences sp = context.getSharedPreferences(Const.SPFILE, Context.MODE_PRIVATE);
        return sp.getString(key, defaultVaulue);
    }
    public static void setSpString(Context context, String key,String value) {
        SharedPreferences sp=context.getSharedPreferences(Const.SPFILE,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static boolean getSpBoolean(Context context,String key,boolean defaultValue){
        SharedPreferences sp=context.getSharedPreferences(Const.SPFILE,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defaultValue);
    }
    public static void setSpBoolean(Context context,String key,boolean value){
        SharedPreferences sp=context.getSharedPreferences(Const.SPFILE,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
}
