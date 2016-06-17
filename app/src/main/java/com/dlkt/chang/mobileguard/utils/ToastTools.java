package com.dlkt.chang.mobileguard.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 土司工具类
 * Created by Administrator on 2016/6/16.
 */
public class ToastTools {
    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
