package com.koalafield.cmart.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.presenter.user.DisCountPresenter;
import com.koalafield.cmart.presenter.user.IDisCountPresenter;
import com.koalafield.cmart.ui.view.user.IDisCountListView;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.EmptyLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jiangrenming on 2018/5/24.
 */

public class DisCountTimeFragment extends BaseFragment implements IDisCountListView<List<DisCountBean>> {

    @BindView(R.id.time_discount)
    RecyclerView time_discount;
    @BindView(R.id.discount_refresh)
    SwipeRefreshLayout discount_refresh;
    @BindView(R.id.empty_discount)
    LinearLayout empty_discount;
    private  int pageIndex  = 0;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_time_layout;
    }

    @Override
    protected void initViews() {}

    @Override
    protected void updateViews() {
        IDisCountPresenter countPresenter = new DisCountPresenter(this);
        countPresenter.setParams(1,pageIndex);
        countPresenter.getData();

    }
    @Override
    public void onDisCountSucessFul(List<DisCountBean> data) {

    }

    @Override
    public void onDisCountFailure(String message) {

    }

    @Override
    public void loadDisCountEmptyData() {
        SwipeRefreshHelper.controlRefresh(discount_refresh,false);
        hideLoading();
        discount_refresh.setVisibility(View.GONE);
        empty_discount.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadDisCountNoMoreData() {

    }

    @Override
    public void loadDisCountMoreData(List<DisCountBean> data) {

        pageIndex++;
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (discount_refresh != null) {
            SwipeRefreshHelper.init(discount_refresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pageIndex = 0;
                    updateViews();
                    SwipeRefreshHelper.controlRefresh(discount_refresh,false);
                }
            });
        }
    }
}
