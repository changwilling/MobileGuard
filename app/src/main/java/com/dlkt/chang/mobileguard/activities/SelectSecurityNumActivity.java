package com.dlkt.chang.mobileguard.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.adapter.UserListAdapter;
import com.dlkt.chang.mobileguard.domain.UserInfo;
import com.dlkt.chang.mobileguard.utils.CharacterParser;
import com.dlkt.chang.mobileguard.utils.CommonTools;
import com.dlkt.chang.mobileguard.utils.ContactLogic;
import com.dlkt.chang.mobileguard.utils.DialogFactory;
import com.dlkt.chang.mobileguard.utils.PinyinComparator;
import com.dlkt.chang.mobileguard.view.ClearEditText;
import com.dlkt.chang.mobileguard.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 * 选择安全号码界面
 */
public class SelectSecurityNumActivity extends Activity {
    private TextView title_center;
    private ListView lv_users;
    private SideBar sideBar;
    private TextView tv_dialog;
    private ClearEditText clearEditText;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private List<UserInfo> mUsers=new ArrayList<>();
    private UserListAdapter mAdapter;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initViews();
    }

    /**
     * 初始化数据，主要是联系人数据,获取本地联系人数据一般通过异步的方式获取，并进行本地的存储
     */
    private void initData() {
        //初始化数据,获取手机联系人数据，启动异步任务来完成
        //启动dialog-->模拟等待
        CommonTools.showProgressDialog(this, "正在加载...");
        GetContactsAsynTask task = new GetContactsAsynTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);//表示马上启动一个线程，可以立即执行，无需等待
    }

    private void initViews() {
        setContentView(R.layout.activity_select_security_num);
        title_center = (TextView) findViewById(R.id.tv_title_center);
        title_center.setText("联系人列表");

        lv_users = (ListView) findViewById(R.id.lv_select_security_num);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        tv_dialog = (TextView) findViewById(R.id.dialog);
        clearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(tv_dialog);

        //设置监听
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lv_users.setSelection(position);
                }
            }
        });
        //点击list的item运行的方法
        lv_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //弹出对话框，显示联系人信息和手机号码，让你点击是否确认选择，如果确认选择，则执行setResult
                UserInfo info=mUsers.get(position);
                String name=info.getName();
                final String phoneNum=info.getPhoneNum();
                String txtHint="联系人:"+name+"  电话:"+phoneNum;
                mDialog= DialogFactory.getInstace().getWarningDialog(SelectSecurityNumActivity.this,
                        "确定使用该联系人的电话作为安全号码？",txtHint,"使用该号码作为安全号码",true,
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                                Intent intent=new Intent();
                                intent.putExtra("phoneNum",phoneNum);
                                SelectSecurityNumActivity.this.setResult(RESULT_OK,intent);
                                SelectSecurityNumActivity.this.finish();
                            }
                        });
            }
        });
        //根据输入框输入的值来过滤结果
        clearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 刷新列表的方法
     */
    private void refreshList() {
        //初始化adapter,设置adapter
        if (mAdapter == null) {
            mAdapter = new UserListAdapter(getApplicationContext(), mUsers);
        }
        lv_users.setAdapter(mAdapter);
    }

    /**
     * 根据输入框的值，来过滤数据，并显示listView更新
     *
     * @param filterStr 过滤的str
     */
    private void filterData(String filterStr) {
        List<UserInfo> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mUsers;
        } else {
            filterDateList.clear();
            for (UserInfo info : mUsers) {
                String name = info.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(info);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        mAdapter.updateListView(filterDateList);
    }

    /**
     * 获取手机联系人的异步任务
     */
    class GetContactsAsynTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //后台执行，进行查找过程
            mUsers = ContactLogic.getPhoneContacts(SelectSecurityNumActivity.this);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            CommonTools.closeProgressDialog();
            refreshList();//刷新列表显示
            super.onPostExecute(aVoid);
        }
    }
}
