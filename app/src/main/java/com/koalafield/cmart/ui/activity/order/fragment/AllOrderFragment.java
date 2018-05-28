package com.koalafield.cmart.ui.activity.order.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CommonOrderAdapter;
import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.bean.order.OrderBean;
import com.koalafield.cmart.bean.order.OrderListBean;
import com.koalafield.cmart.presenter.order.IOrderPresenter;
import com.koalafield.cmart.presenter.order.OrderPresenter;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.view.order.IOrderView;
import com.koalafield.cmart.utils.Constants;
import com.koalafield.cmart.utils.SwipeRefreshHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 * 全部订单
 */

public class AllOrderFragment extends BaseFragment implements IOrderView<List<OrderListBean>>{

    @BindView(R.id.order_recyclerView)
    RecyclerView order_recyclerView;
    @BindView(R.id.empty_order)
    LinearLayout empty_order;
    @BindView(R.id.order_type)
    TextView order_type;

    private CommonOrderAdapter orderAdapter;
    private IOrderPresenter presenter;
    private int pageIndex = 0;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_all_order;
    }

    @Override
    protected void initViews() {
        orderAdapter = new CommonOrderAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewV(mContext,order_recyclerView,true,orderAdapter);
        orderAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                presenter.setType(getParams());
                presenter.getMoreData();
            }
        });
        orderAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    protected void updateViews() {
        presenter = new OrderPresenter(this);
        presenter.setType(getParams());
        presenter.getData();

    }

    private Map<String,String> getParams(){
        Map<String,String> params = new HashMap<>();
        params.put("status",String.valueOf(Constants.ALL_ORDER));
        return  params;

    }


    @Override
    public void onSucessOrderList(List<OrderListBean> data) {
        if (data !=null && data.size() >0 ){
            pageIndex++;
            orderAdapter.updateItems(data);
        }
    }

    @Override
    public void onFailureOrder(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadEmptyData() {
        mSwipeRefresh.setVisibility(View.GONE);
        empty_order.setVisibility(View.VISIBLE);
        order_type.setText("暂无订单");
    }

    @Override
    public void loadMoreData(List<OrderListBean> data) {
        orderAdapter.loadComplete();
        orderAdapter.addItems(data);
        pageIndex++;
    }

    @Override
    public void loadNoMoreData() {
        orderAdapter.loadComplete();
        orderAdapter.noMoreData();
    }
}
