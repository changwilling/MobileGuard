package com.dlkt.chang.mobileguard.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.dlkt.chang.mobileguard.R;

/**
 * 主界面
 * Created by Administrator on 2016/6/15.
 */
public class HomeActivity extends Activity{
    private GridView gv_menus;
    //private int imgRes[]={R.};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    private void initViews() {
        setContentView(R.layout.activity_home);
        gv_menus=(GridView)findViewById(R.id.gv_home_menus);
    }
}
