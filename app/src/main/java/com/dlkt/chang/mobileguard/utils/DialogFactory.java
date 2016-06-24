package com.dlkt.chang.mobileguard.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dlkt.chang.mobileguard.R;

/**
 * 需要的dialog有： 1.包括提示的title,确定、取消按钮的dialog 2.包括下拉item的dialog 特点:单例
 */
public class DialogFactory {
	private static DialogFactory mDialogFactory;
	private static Dialog mDialog;

	private DialogFactory() {

	}

	
	public static DialogFactory getInstace() {
		if (mDialogFactory == null) {
			mDialogFactory = new DialogFactory();
		}
		return mDialogFactory;
	}

	// 获得下拉item的dialog
	public Dialog getItemsDialog(Context context, String title, String[] items,
			OnClickListener listener) {
		AlertDialog.Builder builer = new AlertDialog.Builder(context);
		builer.setTitle(title).setItems(items, listener)
				.setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		mDialog = builer.create();
		// mDialog.show();
		return mDialog;
	}

	/**
	 * 需要复制的dialog-->如android粘贴板
	 * @param context
	 * @param saveListener
     * @return
     */
	public Dialog getClipDialog(Context context,View.OnClickListener saveListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		mDialog = builder.create();
		mDialog.show();
		mDialog.getWindow().setGravity(Gravity.CENTER);
		mDialog.getWindow().setContentView(R.layout.alert_dialog_clip);
		TextView tvClip=(TextView)mDialog.getWindow().findViewById(R.id.alert_tv_clip);
		TextView tvCancle=(TextView)mDialog.getWindow().findViewById(R.id.alert_tv_cancle);
		tvClip.setOnClickListener(saveListener);
		tvCancle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		return mDialog;
	}

	/**
	 * 进行保存的dialog
	 * @param context
	 * @param saveListener
     * @return
     */
	public Dialog getSaveDialog(Context context,View.OnClickListener saveListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		mDialog = builder.create();
		mDialog.show();
		mDialog.getWindow().setGravity(Gravity.CENTER);
		mDialog.getWindow().setContentView(R.layout.alert_dialog_clip);
		TextView tvClip=(TextView)mDialog.getWindow().findViewById(R.id.alert_tv_clip);
		tvClip.setText("保存");
		TextView tvCancle=(TextView)mDialog.getWindow().findViewById(R.id.alert_tv_cancle);
		tvClip.setOnClickListener(saveListener);
		tvCancle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		return mDialog;
	}

	/** 包含确定取消的warningDialog,用户直接使用dialg，不需要调用show
	 * @param context 上下文
	 * @param title dialog标题
	 * @param content 内容
	 * @param confirm 确认键的内容
	 * @param listener 确认键的监听器
	 *  */
	public Dialog getWarningDialog(Context context, String title,
			String content, String confirm,boolean showCancleBt,
			View.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		mDialog = builder.create();
		mDialog.show();
		mDialog.getWindow().setGravity(Gravity.CENTER);
		mDialog.getWindow().setContentView(R.layout.alert_dialog_layout);
		TextView tvTitle = (TextView) mDialog.getWindow().findViewById(
				R.id.alert_tv_title);
		TextView tvContent = (TextView) mDialog.getWindow().findViewById(
				R.id.alert_tv_content);
		TextView tvConfirm = (TextView) mDialog.getWindow().findViewById(
				R.id.alert_tv_confirm);
		TextView tvCancle = (TextView) mDialog.getWindow().findViewById(
				R.id.alert_tv_cancle);
		if(!showCancleBt){
			tvCancle.setVisibility(View.GONE);
		}
		View viewLine1 = (View) mDialog.getWindow().findViewById(R.id.line_1);
		// 设置标题-->不是很常用
		if (title == null || title.length() == 0) {
			tvTitle.setVisibility(View.GONE);
			viewLine1.setVisibility(View.GONE);
		} else {
			tvTitle.setText(title);
		}
		// 设置内容-->一般都有
		tvContent.setText("" + content);
		// 设置确认处的文字，比如"清除消息列表"
		tvConfirm.setText("" + confirm);
		tvCancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		tvConfirm.setOnClickListener(listener);
		return mDialog;
	}

}
