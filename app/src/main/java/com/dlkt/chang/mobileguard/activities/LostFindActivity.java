package com.dlkt.chang.mobileguard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.utils.Const;
import com.dlkt.chang.mobileguard.utils.SpTools;

/**
 * Created by Administrator on 2016/6/20.
 * 手机防盗界面
 */
public class LostFindActivity extends Activity {
    private TextView title_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        //判断是否进入过设置导航界面，没有的话需要进入
        if (!SpTools.getSpBoolean(getApplicationContext(), Const.LOGINSETTINGVIEWS, false)) {//默认没有进入
            //没有进入过手机防盗设置界面，首先进入设置向导界面
            Intent intent = new Intent(this,Setup1Activity.class);
            startActivity(intent);
            finish();
        } else {
            //进入过，则直接加载手机防盗主界面
            setContentView(R.layout.activity_lost_find);
            title_center=(TextView)findViewById(R.id.tv_title_center);

            title_center.setText("手机防盗");
        }
    }
}
