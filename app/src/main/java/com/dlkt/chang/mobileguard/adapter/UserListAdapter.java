package com.dlkt.chang.mobileguard.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;
import com.dlkt.chang.mobileguard.domain.UserInfo;

import java.util.List;

public class UserListAdapter extends BaseAdapter implements SectionIndexer{
	private Context mContext;
	private List<UserInfo> mUsers;
	private LayoutInflater mLayoutInflater;
	public UserListAdapter(Context mContext,List<UserInfo> mUsers){
		this.mContext=mContext;
		this.mUsers=mUsers;
		mLayoutInflater=LayoutInflater.from(mContext);
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param
	 */
	public void updateListView(List<UserInfo> mUsers){
		this.mUsers = mUsers;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mUsers.size();
	}

	@Override
	public Object getItem(int position) {
		return mUsers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			ViewHolder viewHolder=null;
			if(convertView==null){
                viewHolder=new ViewHolder();
                convertView=mLayoutInflater.inflate(R.layout.item_user_list, null);
                viewHolder.tvCataLog=(TextView)convertView.findViewById(R.id.catalog);
                viewHolder.tvName=(TextView) convertView.findViewById(R.id.tv_single_name);
                viewHolder.ivPhoto=(ImageView) convertView.findViewById(R.id.iv_head);
                viewHolder.cb=(CheckBox)convertView.findViewById(R.id.cb_for_invite);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder) convertView.getTag();
            }
			if(mUsers.size()==0){
                return null;
            }
			UserInfo user=mUsers.get(position);
			String nickName=user.getName();
			//boolean inOnline=user.isOnline();
			if(TextUtils.isEmpty(nickName)){
                viewHolder.tvName.setText("临时名字");
            }
			//隐藏复选框
			viewHolder.cb.setVisibility(View.GONE);
			viewHolder.tvName.setText(nickName);
			//设置字母目录是否显示
			//根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(position);

			//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if(position == getPositionForSection(section)){
                viewHolder.tvCataLog.setVisibility(View.VISIBLE);
                viewHolder.tvCataLog.setText(user.getSortLetters());
            }else{
                viewHolder.tvCataLog.setVisibility(View.GONE);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}
	class ViewHolder{
		TextView tvCataLog;//字母目录
		ImageView ivPhoto;
		TextView tvName;
		CheckBox cb;
	}


	@Override
	public Object[] getSections() {
		return null;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			try {
				String sortStr = mUsers.get(i).getSortLetters();
				char firstChar = sortStr.toUpperCase().charAt(0);
				if (firstChar == section) {
                    return i;
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		try {
			return mUsers.get(position).getSortLetters().charAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
