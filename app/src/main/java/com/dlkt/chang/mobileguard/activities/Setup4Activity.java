package com.dlkt.chang.mobileguard.activities;

import android.content.Intent;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.utils.Const;
import com.dlkt.chang.mobileguard.utils.SpTools;

/**
 * Created by Administrator on 2016/6/21.
 */
public class Setup4Activity extends BaseSetupActivity{

    private TextView title_center;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_setup_4);
        title_center=(TextView)findViewById(R.id.tv_title_center);
        title_center.setText("4.恭喜您完成设置");

    }

    @Override
    protected void preActivity() {
        Intent intent=new Intent(this,Setup3Activity.class);
        startActivity(intent);
    }

    @Override
    public void nextActivity() {
        //保存已经设置手机防盗状态
        SpTools.setSpBoolean(this, Const.LOGINSETTINGVIEWS,true);
        Intent intent=new Intent(this,LostFindActivity.class);
        startActivity(intent);
    }


}
