package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.OrderBean;
import com.koalafield.cmart.bean.order.OrderListBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/12.
 * 订单的共同适配器
 */

public class CommonOrderAdapter extends BaseQuickAdapter<OrderListBean> {

    public CommonOrderAdapter(Context context) {
        super(context);
    }

    public CommonOrderAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.my_order_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderListBean item) {

    }


}
