package com.dlkt.chang.mobileguard.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.domain.UrlBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static final int LOADMAIN = 1;//启动新界面
    private static final int SHOWUPDATEDIALOG = 2;
    private RelativeLayout rl_root;
    private TextView tv_version_name;
    private int versinCode;
    private String versionName;
    private UrlBean urlBean;
    private long startMilles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
        addAimation();
        checkVersion();
    }

    /**
     * 初始化view的方法
     */
    private void initViews() {
        setContentView(R.layout.activity_splash);
        rl_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
        tv_version_name = (TextView) findViewById(R.id.tv_splash_version_name);
    }

    /**
     * 添加动画的方法
     */
    private void addAimation() {
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(3000);
        aa.setFillAfter(true);

        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(3000);
        ra.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(3000);
        sa.setFillAfter(true);

        AnimationSet as = new AnimationSet(true);
        as.addAnimation(aa);
        as.addAnimation(ra);
        as.addAnimation(sa);
        //让view播放动画
        rl_root.startAnimation(as);
    }

    /**
     * 初始化数据，获取自己的版本信息
     */
    private void initData() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            versinCode = info.versionCode;
            versionName = info.versionName;
            tv_version_name.setText("" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查版本信息的方法
     */
    private void checkVersion() {
        //检查版本，要访问服务器，获取最新版本信息，如果版本需要更新，则获得版本的url，下载apk进行安装更新
        new Thread() {
            @Override
            public void run() {
                try {
                    startMilles = System.currentTimeMillis();
                    URL url = new URL("http://172.30.3.216:8080/MobileGuard/versionInfo.java");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    int resCode = conn.getResponseCode();
                    if (resCode == 200) {//数据获取成功
                        //获取读取的字节流
                        InputStream is = conn.getInputStream();
                        //将字节流转换为字符流
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        //读取第一行信息
                        String line = br.readLine();
                        //json字符串信息的封装
                        StringBuilder builder = new StringBuilder();
                        while (line != null) {
                            builder.append(line);
                            line = br.readLine();
                        }
                        urlBean = parseJson(builder);
                        isNewVersion(urlBean);
                    } else {//失败
                        Toast.makeText(SplashActivity.this, "获取最新版本信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADMAIN:
                    loadMain();
                    break;
                case SHOWUPDATEDIALOG://显示更新dialog
                    showUpdateDialog();
                    break;
            }
        }
    };

    /**
     * 启动主界面的方法
     */
    private void loadMain() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * 显示更新的dialog
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("提醒")
                .setMessage("有新版本是否更新，新版本有以下特性：" + urlBean.getDesc())
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //更新下载apk
                        downLoadNewApk();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消更新，直接进入主界面
                loadMain();
            }
        });
        builder.show();
    }

    /**
     * 下载最新apk的方法
     */
    private void downLoadNewApk() {
        HttpUtils utils = new HttpUtils();
        //获取apk的保存路径，放在sdcard的根目录中
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();

        //这里会出错的原因有basePath应该指定一个文件，而不是单一一个路径,因此应该加上xx.apk(也就是需要自己取名，而非直接试用网络上的名字)
        utils.download(urlBean.getUrl(), basePath + "/MobileGuard.apk", new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                Toast.makeText(SplashActivity.this, "下载成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_LONG).show();
                System.out.println("exception:" + e.getMessage());
                //进入主页面
                loadMain();
            }
        });
    }

    /**
     * 判断是否有新版本的方法
     * 该方法目前依然在工作线程中，要想要弹出dialog，操作UI，必须放在主线程中，因此需要试用handler
     */
    private void isNewVersion(UrlBean urlBean) {
        int serverCode = urlBean.getVersionCode();
        long endMilles = System.currentTimeMillis();
        if (endMilles - startMilles < 3000) {//小于3秒，也要休息到三秒，让动画播放完毕，然后再跳转或者提示更新版本
            try {
                Thread.sleep(3000 - (endMilles - startMilles));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (serverCode == versinCode) {//没有新版本
            //启动主界面
            Message msg = Message.obtain();
            msg.what = LOADMAIN;
            mHandler.sendMessage(msg);
        } else {//版本不一致
            //显示更新版本dialog
            Message msg = Message.obtain();
            msg.what = SHOWUPDATEDIALOG;
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 更具StringBuilder解析json，返回URLbean实体类
     *
     * @param builder
     * @return
     */
    private UrlBean parseJson(StringBuilder builder) {
        UrlBean bean = new UrlBean();
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(builder + "");
            int versionCode = jsonObj.getInt("versionCode");
            String versionName = jsonObj.getString("versionName");
            String versionDesc = jsonObj.getString("versionDesc");
            String url = jsonObj.getString("APKURL");
            bean.setVersionCode(versionCode);
            bean.setVersionName(versionName);
            bean.setUrl(url);
            bean.setDesc(versionDesc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

}
