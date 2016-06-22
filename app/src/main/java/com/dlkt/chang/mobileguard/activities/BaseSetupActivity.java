package com.dlkt.chang.mobileguard.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/6/21.
 */
public abstract class BaseSetupActivity extends Activity implements View.OnClickListener{

    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGesture();
        initViews();
    }

    /**
     * 初始化手势
     */
    private void initGesture() {
        SettingGestureListener gl=new SettingGestureListener();
        mDetector=new GestureDetector(this,gl);
    }

    public abstract  void initViews();

    @Override
    public void onClick(View v) {

    }

    /**
     * 前一个界面
     */
    public void prePage(){
        preActivity();
        finish();
        preAnimation();
    }

    /**
     * 播放进入前一个界面的动画
     */
    private void preAnimation() {
    }

    /**
     * 前一个界面操作
     */
    protected abstract void preActivity();

    /**
     * 下一个界面处理
     */
    public void nextPage(){
        nextActivity();
        finish();
        nextAnimation();
    }

    /**
     * 播放进入下一个页面的动画
     */
    private void nextAnimation() {
    }

    /**
     * 启动下一个页面的操作
     */
    public abstract void nextActivity();

    /**
     * 设置界面手势监听器
     * GestureDetector提供了两个接口(OnGestureListener,OnDoubleTapListener)，一个内部类
     * SimpleOnGestureListener(该内部类实现了两个接口),但是实现OnGestureListener,需要重载所有的方法
     * 而自己的手势操作继承内部类SimpleOnGestureListener，可以根据自己的需求来实现相应的方法
     */
    private class SettingGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //判断左右滑动的手势，因此主要判断x轴
            float s=e2.getX()-e1.getX();//位移变化
            if(s<0&&Math.abs(s)>200){//向右滑动，判断距离和速率,速率大于400像素/秒
                nextPage();
                return false;
            }
            if(s>0&&Math.abs(s)>200){//向左滑动，判断距离和速率,速率大于400像素/秒
                prePage();
                return false;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /**
     * 将onTouch事件交给SettingGestureListener处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
