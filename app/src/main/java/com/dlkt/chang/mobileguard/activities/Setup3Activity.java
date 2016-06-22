package com.dlkt.chang.mobileguard.activities;

import android.content.Intent;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;

/**
 * Created by Administrator on 2016/6/21.
 */
public class Setup3Activity extends BaseSetupActivity{

    private TextView title_center;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_setup_3);
        title_center=(TextView)findViewById(R.id.tv_title_center);
        title_center.setText("3.设置安全号码");

    }

    @Override
    protected void preActivity() {
        Intent intent=new Intent(this,Setup2Activity.class);
        startActivity(intent);
    }

    @Override
    public void nextActivity() {
        Intent intent=new Intent(this,Setup4Activity.class);
        startActivity(intent);
    }


}
