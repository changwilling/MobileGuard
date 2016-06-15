package com.dlkt.chang.mobileguard.activities;

import android.app.Activity;
import android.os.Bundle;

import com.dlkt.chang.mobileguard.R;

/**
 * Created by Administrator on 2016/6/15.
 */
public class HomeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        setContentView(R.layout.activity_home);
    }
}
