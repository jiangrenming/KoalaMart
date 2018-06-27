package com.koalafield.cmart.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.presenter.IBasePresenter;
import com.koalafield.cmart.base.view.IBaseView;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.EmptyLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jiangrenming
 * @date 2018/5/9
 * 碎片的fragment
 */

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {

    /**
     * 注意，资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.empty_layout)
    EmptyLayout mEmptyLayout;
    @Nullable
    @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout mSwipeRefresh;

    protected Context mContext;
    //缓存Fragment view
    private View mRootView;
    private boolean mIsMulti = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            ButterKnife.bind(this, mRootView);
            initViews();
            initSwipeRefresh();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews();
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void showLoading() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
        /*if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
        }*/
    }

    @Override
    public void hideLoading() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
        SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);
        /*if (mEmptyLayout != null) {
            mEmptyLayout.hide();
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
            SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);
        }*/
    }

    @Override
    public void showNetError(final EmptyLayout.OnRetryListener onRetryListener) {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
       /* if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(onRetryListener);
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
        }*/
    }

    /**
     * 初始化下拉刷新
     */
    public void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            SwipeRefreshHelper.init(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateViews();
                    SwipeRefreshHelper.controlRefresh(mSwipeRefresh,false);
                }
            });
        }
    }

    /**
     * 绑定布局文件
     * @return  布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 更新视图控件
     */
    protected abstract void updateViews();
}
