package com.dlkt.chang.mobileguard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.utils.Const;
import com.dlkt.chang.mobileguard.utils.SpTools;
import com.dlkt.chang.mobileguard.utils.ToastTools;
import com.dlkt.chang.mobileguard.view.CircleImageView;


/**
 * 主界面
 * Created by Administrator on 2016/6/15.
 */
public class HomeActivity extends Activity implements AdapterView.OnItemClickListener {
    private GridView gv_menus;
    private int icons[] = {R.mipmap.mobile_shoujifangdao, R.mipmap.mobile_tongxunweishi, R.mipmap.mobile_ruanjianguanjia
            , R.mipmap.mobile_jinchengguanli, R.mipmap.mobile_liuliangtongji, R.mipmap.mobile_bingduchasha
            , R.mipmap.mobile_huancunguanli, R.mipmap.mobile_gaojigongju, R.mipmap.mobile_shezhizhongxin};

    private String names[] = {"手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "病毒查杀",
            "缓存清理", "高级工具", "设置中心"};
    private AlertDialog dialog;
    private TextView title_left;
    private TextView title_right;
    private TextView title_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
        addListeners();
    }

    /**
     * 添加监听器
     */
    private void addListeners() {
        gv_menus.setOnItemClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        MyMenuAdapter mAdapter = new MyMenuAdapter();
        gv_menus.setAdapter(mAdapter);
    }

    private void initViews() {
        setContentView(R.layout.activity_home);
        //设置titleBar
        title_center=(TextView)findViewById(R.id.tv_title_center);
        title_center.setText("手机管家");

        gv_menus = (GridView) findViewById(R.id.gv_home_menus);
    }

    /**
     * 点击每个item运行的方法
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://手机防盗
                //检查是否设置过手机防盗密码，如果设置了，则让用户输入手机密码
                if (TextUtils.isEmpty(SpTools.getSpString(getApplicationContext(), Const.PHONEPWD, ""))) {
                    //通过偏好设置判断,没有设置时默认为空
                    //没有设置，弹出设置密码对话框
                    showSettingPwdDialog();
                }else{//设置过了，让用户再次输入，认证
                    showEnterDialog();
                }
                break;
            case 1:
                break;
        }

    }

    /**
     * 输入进入认证pwd的dilog
     */
    private void showEnterDialog() {
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=View.inflate(getApplicationContext(),R.layout.dialog_view_enter_code,null);
        final EditText etCode=(EditText)view.findViewById(R.id.et_dialog_enter_code);
        Button btPositive=(Button)view.findViewById(R.id.bt_dialog_setting_code_positive);
        Button btNegtive=(Button)view.findViewById(R.id.bt_dialog_setting_code_negtive);
        btPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断与偏好设置中的密码是否一致
                String pwd=etCode.getText().toString().trim();
                String sp_pwd=SpTools.getSpString(getApplicationContext(),Const.PHONEPWD,"");
                if(pwd.equals(sp_pwd)){//一致,跳转界面,并且保存设置信息
                    Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }else{
                    ToastTools.showToast(getApplicationContext(),"密码输入错误");
                }
            }
        });
        btNegtive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog=builder.create();
        dialog.show();
    }

    /**
     * 显示弹出设置密码对话框的dialog
     */
    private void showSettingPwdDialog() {
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=View.inflate(this,R.layout.dialog_view_setting_code,null);
        final EditText etCode1 = (EditText) view.findViewById(R.id.et_dialog_setting_code_1);
        final EditText etCode2 = (EditText) view.findViewById(R.id.et_dialog_setting_code_2);
        Button btPositive=(Button)view.findViewById(R.id.bt_dialog_setting_code_positive);
        Button btNegtive=(Button)view.findViewById(R.id.bt_dialog_setting_code_negtive);
        btPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//确定，如果密码一致，保存密码
                String code1=etCode1.getText().toString().trim();
                String code2=etCode2.getText().toString().trim();
                if(TextUtils.isEmpty(code1)&&TextUtils.isEmpty(code2)){
                    ToastTools.showToast(getApplicationContext(),"两次密码输入均不能为空");
                    return;
                }
                if(!code1.equals(code2)){
                    ToastTools.showToast(getApplicationContext(),"两次密码输入不一致，请重新输入");
                    return;
                }
                SpTools.setSpString(getApplicationContext(),Const.PHONEPWD,code1);
                //启动手机防盗界面
                Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        btNegtive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();;
            }
        });
        builder.setView(view);
        dialog=builder.create();
        dialog.show();
    }

    /**
     * 显示主界面按钮的适配器
     */
    class MyMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return icons[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_home_menu, null);
                vh = new ViewHolder();
                vh.ivIcon = (CircleImageView) convertView.findViewById(R.id.iv_item_home_icon);
                vh.tv_iconName = (TextView) convertView.findViewById(R.id.tv_item_home_name);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.ivIcon.setImageResource(icons[position]);
            vh.tv_iconName.setText(names[position]);
            return convertView;
        }

        class ViewHolder {
            private CircleImageView ivIcon;
            private TextView tv_iconName;
        }
    }
}
