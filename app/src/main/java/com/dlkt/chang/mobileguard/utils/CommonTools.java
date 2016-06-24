package com.dlkt.chang.mobileguard.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.dlkt.chang.mobileguard.view.CustomProgress;

/**
 * 土司工具类
 * Created by Administrator on 2016/6/16.
 */
public class CommonTools {
    /**
     * 获得当前版本名称的方法
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getCurrentVersion(Context context)
            throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
        return packageInfo.versionName;

    }

    /**
     * 展示土司的方法
     * @param context
     * @param message
     */
    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    private static CustomProgress customProgress;

    /**
     * 显示进度条
     *
     * @param activity
     */
    public static void showProgressDialog(Activity activity, String msg) {
        if(customProgress!=null&&customProgress.isShowing()){
            return;
        }
        customProgress= CustomProgress.show(activity, msg, true, null);
    }

    public static void closeProgressDialog() {
        if (customProgress != null) {
            customProgress.dismiss();
        }
    }
}
