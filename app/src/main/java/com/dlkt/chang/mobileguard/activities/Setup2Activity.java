package com.dlkt.chang.mobileguard.activities;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.utils.Const;
import com.dlkt.chang.mobileguard.utils.SpTools;
import com.dlkt.chang.mobileguard.utils.CommonTools;

/**
 * Created by Administrator on 2016/6/21.
 */
public class Setup2Activity extends BaseSetupActivity{

    private TextView title_center;
    private Button bt_bind;
    private ImageView iv_bind;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_setup_2);
        title_center=(TextView)findViewById(R.id.tv_title_center);
        title_center.setText("2.手机卡绑定");

        bt_bind=(Button)findViewById(R.id.bt_setup2_bindsim);
        iv_bind=(ImageView)findViewById(R.id.iv_setup2_isbind);

        //初始化imageview的Resuource
        if(TextUtils.isEmpty(SpTools.getSpString(getApplicationContext(),Const.BINDSIM,""))){
            //未绑定
            iv_bind.setImageResource(R.mipmap.unlock);
        }else{
            //绑定了
            iv_bind.setImageResource(R.mipmap.lock);
        }

        //设置监听
        bt_bind.setOnClickListener(this);
        iv_bind.setOnClickListener(this);
    }

    @Override
    protected void preActivity() {
        Intent intent=new Intent(this,Setup1Activity.class);
        startActivity(intent);
    }

    @Override
    public void nextPage() {
        //如果没有绑定sim卡则不允许用户进行启动页面操作
        if(TextUtils.isEmpty(SpTools.getSpString(getApplicationContext(),Const.BINDSIM,""))){
            CommonTools.showToast(getApplicationContext(),"请先绑定sim卡");
            return;
        }
        super.nextPage();
    }

    @Override
    public void nextActivity() {

        Intent intent=new Intent(this,Setup3Activity.class);
        startActivity(intent);
    }

    /**
     * 重写父类onClick方法
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_setup2_bindsim:
                //绑定sim卡，同时更换imageview显示图片
                //绑定sim卡，实际上就是记录sim卡信息到偏好设置
                //首先根据偏好设置判断是否绑定
                if(TextUtils.isEmpty(SpTools.getSpString(getApplicationContext(), Const.BINDSIM,""))){
                    //没有绑定-->进行绑定
                    TelephonyManager tm= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    //工具手机管理类，获取sim卡信息-->序列号,s
                    String simSerialNumber=tm.getSimSerialNumber()+"1";
                    //进行绑定
                    SpTools.setSpString(getApplicationContext(),Const.BINDSIM,simSerialNumber);
                    //设置imageView背景图片
                    iv_bind.setImageResource(R.mipmap.lock);
                }else{
                    //绑定了-->解除绑定
                    SpTools.setSpString(getApplicationContext(),Const.BINDSIM,"");
                    iv_bind.setImageResource(R.mipmap.unlock);
                }
                break;
            default:
                break;
        }
    }
}
