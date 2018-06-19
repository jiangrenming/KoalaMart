package com.koalafield.cmart.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.photo.CameraProxy;
import com.koalafield.cmart.photo.PhotoCallback;
import com.koalafield.cmart.utils.AndoridSysUtils;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class SelectPopupwindow extends PopupWindow implements View.OnClickListener,PopupWindow.OnDismissListener{
    private View mRootView;
    private boolean isCrop;
    private CameraProxy proxy;
    private String photoDir;
    private  TextView tv_pick_phone, tv_pick_zone, tv_cancel;
    private Context mContext;
    private int navigationHeight = 0;

    public SelectPopupwindow(Activity activity, boolean isCrop, String dir,PhotoCallback callback){
        initData(activity,isCrop,dir,callback);
        this.mContext = activity;
        initView(activity);
        initEvent();
        initPopupwindow();
    }

    private void initData(Activity activity,boolean isCrop, String dir,PhotoCallback callback){
        this.isCrop = isCrop;
        this.photoDir = dir;
        proxy = new CameraProxy(callback,activity);
    }

    private void initView(Activity activity){
        mRootView = LayoutInflater.from(activity).inflate(R.layout.take_phone_window_layout,null,false);
        tv_pick_phone = (TextView) mRootView.findViewById(R.id.tv_pick_phone);
        tv_pick_zone = (TextView) mRootView.findViewById(R.id.tv_pick_zone);
        tv_cancel = (TextView) mRootView.findViewById(R.id.tv_cancel);
    }

    private void initEvent(){
        tv_pick_phone.setOnClickListener(this);
        tv_pick_zone.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    private void initPopupwindow(){
        //设置SelectPicPopupWindow的View  
        this.setContentView(mRootView);
        //设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高  
       this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置背景
        this.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外退出
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.PopupWindow);
        //设置消失监听
        this.setOnDismissListener(this);
        //设置背景透明度
        setBackgroundAlpha(0.5f);
    }

    public CameraProxy getProxy(){
        return this.proxy;
    }

    public void show(){
        if (AndoridSysUtils.checkDeviceHasNavigationBar(mContext)){
            navigationHeight = AndoridSysUtils.getNavigationBarHeigh(mContext);
        }
        this.showAtLocation(mRootView, Gravity.BOTTOM, 0, navigationHeight);
    }

    @Override
    public void onClick(View v) {
        String filePath = photoDir+System.currentTimeMillis()+".jpg";
        switch (v.getId()){
            case R.id.tv_pick_zone:
                if (isCrop)
                    proxy.getPhotoFromCameraCrop(filePath);
                else
                    proxy.getPhotoFromCamera(filePath);
                break;
            case R.id.tv_pick_phone:
                if (isCrop)
                    proxy.getPhotoFromAlbumCrop(filePath);
                else
                    proxy.getPhotoFromAlbum(filePath);
                break;
            case R.id.tv_cancel:
                break;
        }
        this.dismiss();
        setBackgroundAlpha(1);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity)mContext).getWindow().setAttributes(lp);
    }
}
