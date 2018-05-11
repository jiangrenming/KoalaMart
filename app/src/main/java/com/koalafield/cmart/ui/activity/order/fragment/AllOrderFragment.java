package com.koalafield.cmart.ui.activity.order.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CommonOrderAdapter;
import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.bean.order.OrderBean;
import com.koalafield.cmart.utils.SwipeRefreshHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 * 全部订单
 */

public class AllOrderFragment extends BaseFragment{

    @BindView(R.id.order_recyclerView)
    RecyclerView order_recyclerView;

    private CommonOrderAdapter orderAdapter;
    private ArrayList<OrderBean> orderList = new ArrayList();

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_all_order;
    }

    @Override
    protected void initViews() {
        SwipeRefreshHelper.init(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟假数据
                for (int i = 0; i < 4; i++) {
                    OrderBean order = new OrderBean();
                    order.setOrder_time("2018/5/11"+i);
                    order.setOrderCount(3+i);
                    order.setOrderNo("1234567890"+i);
                    order.setStatue("P");
                    order.setTotalAmount(1230);

                }

            }
        });
        orderAdapter = new CommonOrderAdapter(mContext,orderList);
        RecyclerViewHelper.initRecyclerViewV(mContext,order_recyclerView,false,orderAdapter);

    }

    @Override
    protected void updateViews() {

    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
