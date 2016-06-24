package com.dlkt.chang.mobileguard.activities;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.utils.Const;
import com.dlkt.chang.mobileguard.utils.SpTools;

/**
 * Created by Administrator on 2016/6/21.
 */
public class Setup4Activity extends BaseSetupActivity{

    private TextView title_center;
    private LinearLayout ll_content;
    private CheckBox cb_fangdao;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_setup_4);
        title_center=(TextView)findViewById(R.id.tv_title_center);
        title_center.setText("4.恭喜您完成设置");

        ll_content=(LinearLayout)findViewById(R.id.ll_setup4_content);
        cb_fangdao=(CheckBox)findViewById(R.id.cb_setup4_fangdao);

        //添加监听
        ll_content.setOnClickListener(this);
        cb_fangdao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //1.给checkBox打勾或者取消打勾  2.开启或关闭服务
                if(isChecked){//监听到选中了

                }else{//监听到没有选中

                }
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_setup4_content:
                if(cb_fangdao.isChecked()){//之前被选中
                    //设置为没选中
                    cb_fangdao.setChecked(false);
                }else{
                    //设置为选中
                    cb_fangdao.setChecked(true);
                }
                break;
        }
    }
}
