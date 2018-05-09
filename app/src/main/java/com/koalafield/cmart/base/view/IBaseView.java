package com.koalafield.cmart.base.view;


import com.koalafield.cmart.widget.EmptyLayout;

/**
 *
 * @author jiangrenming
 * @date 2018/4/19
 */

public interface IBaseView {

    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示网络错误
     * @param onRetryListener 点击监听
     */
    void showNetError(EmptyLayout.OnRetryListener onRetryListener);

}
