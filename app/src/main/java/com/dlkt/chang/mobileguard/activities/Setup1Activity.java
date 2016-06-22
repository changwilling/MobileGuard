package com.dlkt.chang.mobileguard.activities;

import android.content.Intent;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;

/**
 * Created by Administrator on 2016/6/21.
 */
public class Setup1Activity extends BaseSetupActivity{

    private TextView title_center;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_setup_1);
        title_center=(TextView)findViewById(R.id.tv_title_center);
        title_center.setText("1.欢迎使用手机防盗");

    }

    @Override
    protected void preActivity() {
        //不操作，结束后返回主界面
    }

    @Override
    public void nextActivity() {
        Intent intent=new Intent(this,Setup2Activity.class);
        startActivity(intent);
    }


}
