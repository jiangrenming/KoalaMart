package com.koalafield.cmart.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.base.presenter.IBasePresenter;
import com.koalafield.cmart.base.view.IBaseView;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.utils.AndroidTools;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StackActivityManager;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.EmptyLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * @author jiangrenming
 * @date 2018/4/18
 * activity的基础类
 */

public abstract  class BaseActivity<T extends IBasePresenter> extends FragmentActivity implements IBaseView {

    /**
     * 把 EmptyLayout 放在基类统一处理
     */
    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;

    @LayoutRes
    public  abstract int attchLayoutRes();
    public  abstract  void initDatas();
    public  abstract  void upDateViews();
    private long lastClickTime;
    public T presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attchLayoutRes());
        ButterKnife.bind(this);
        initDatas();
        upDateViews();
    }

    @Override
    public void showLoading() {
        if (mEmptyLayout  != null){
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.hide();
        }
    }

    @Override
    public void showNetError(EmptyLayout.OnRetryListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(onRetryListener);
        }
    }

    /**
     * 控制点击事件。
     *
     * @return
     */
    public boolean allowNext() {
        long currentTime = System.currentTimeMillis();
        long temClickTime = lastClickTime;
        lastClickTime = currentTime;
        return (currentTime - temClickTime) > 800;
    }

    /**
     * 设置获取焦点
     * @param view
     */
    public  void setFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }
    /**
     * 登录界面
     */
    public  void skipLogin(Context context){
        ShareBankPreferenceUtils.clearData("tickets");
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("type",3);
        startActivity(intent);
    }
}
