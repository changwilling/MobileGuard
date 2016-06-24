package com.dlkt.chang.mobileguard.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.utils.CommonTools;
import com.dlkt.chang.mobileguard.utils.Const;
import com.dlkt.chang.mobileguard.utils.SpTools;

/**
 * Created by Administrator on 2016/6/21.
 */
public class Setup3Activity extends BaseSetupActivity{

    private TextView title_center;
    private EditText et_security_num;
    private Button bt_getNum;
    private static final int REQUEST_CODE_FOR_SECURITY_NUM=1001;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_setup_3);
        title_center=(TextView)findViewById(R.id.tv_title_center);
        title_center.setText("3.设置安全号码");

        et_security_num=(EditText)findViewById(R.id.et_setup3_security_num);
        bt_getNum=(Button)findViewById(R.id.bt_setup3_getnumber);

        //设置监听
        bt_getNum.setOnClickListener(this);

        //根据偏好设置初始化UI
        String safaNum=SpTools.getSpString(getApplicationContext(), Const.SAFENUM,"");
        et_security_num.setText(""+safaNum);
    }

    @Override
    protected void preActivity() {
        Intent intent=new Intent(this,Setup2Activity.class);
        startActivity(intent);
    }

    @Override
    public void nextPage() {
        //首先判断安全号码是否为空，为空也无法进行下一步
        String safeNum=et_security_num.getText().toString().trim();
        if(TextUtils.isEmpty(safeNum)){
            //安全号码为空
            CommonTools.showToast(getApplicationContext(),"安全号码不能为空");
            return;
        }else{
            //偏好设置设定
            //放在偏好设置中
            SpTools.setSpString(getApplicationContext(),Const.SAFENUM,safeNum);
        }
        super.nextPage();
    }

    @Override
    public void nextActivity() {
        Intent intent=new Intent(this,Setup4Activity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_setup3_getnumber:
                //启动安全号码选择页面
                Intent intentSafeNum=new Intent(Setup3Activity.this,SelectSecurityNumActivity.class);
                startActivityForResult(intentSafeNum,REQUEST_CODE_FOR_SECURITY_NUM);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_FOR_SECURITY_NUM&&resultCode==RESULT_OK){
            if(data==null){
                return;
            }
            String safeNum=data.getStringExtra("phoneNum");
            //UI显示
            et_security_num.setText(safeNum);
        }
    }
}
