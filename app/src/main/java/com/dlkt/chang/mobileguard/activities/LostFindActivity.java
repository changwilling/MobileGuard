package com.dlkt.chang.mobileguard.activities;

import android.app.Activity;
import android.os.Bundle;

import com.dlkt.chang.mobileguard.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public class LostFindActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }
    private void initViews() {
        //判断是否进入过设置导航界面，没有的话需要进入
        setContentView(R.layout.activity_lost_find);
    }
}
