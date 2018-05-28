package com.koalafield.cmart.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.DisCountAdapter;
import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.presenter.user.DisCountPresenter;
import com.koalafield.cmart.presenter.user.IDisCountPresenter;
import com.koalafield.cmart.ui.activity.order.PayActivity;
import com.koalafield.cmart.ui.view.user.IDisCountListView;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.EmptyLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private DisCountAdapter disCountAdapter;
    private    IDisCountPresenter countPresenter;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_time_layout;
    }

    @Override
    protected void initViews() {
        initSwipeRefresh();
        disCountAdapter = new DisCountAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewV(mContext,time_discount,true,disCountAdapter);
        disCountAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                Map<String,String> params = new HashMap<>();
                params.put("pageIndex",String.valueOf(pageIndex));
                params.put("enabled",String.valueOf(1));
                countPresenter.setParams(params);
                countPresenter.getMoreData();
            }
        });

    }

    @Override
    protected void updateViews() {
        countPresenter = new DisCountPresenter(this);
        Map<String,String> params = new HashMap<>();
        params.put("pageIndex",String.valueOf(pageIndex));
        params.put("enabled",String.valueOf(1));
        countPresenter.setParams(params);
        countPresenter.getData();

    }
    @Override
    public void onDisCountSucessFul(final List<DisCountBean> data) {
        for (int i = 0; i < data.size(); i++) {
            Log.i("返回的数据:",data.get(i).toString());
        }
        if (data != null &&data.size() >0){
            pageIndex++;
            disCountAdapter.updateItems(data);
        }
        disCountAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DisCountBean disCountBean = data.get(position);
                Intent intent = new Intent();
                intent.putExtra("counpon",disCountBean);
                getActivity().setResult(Activity.RESULT_OK,intent);
            }
        });
    }

    @Override
    public void onDisCountFailure(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
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
        disCountAdapter.loadComplete();
        disCountAdapter.noMoreData();
    }

    @Override
    public void loadDisCountMoreData(List<DisCountBean> data) {
        disCountAdapter.loadComplete();
        disCountAdapter.addItems(data);
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
