package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.bean.order.OrderItemAttrs;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/29.
 */

public class OrderDetailsAdapter extends BaseQuickAdapter<OrderItemAttrs> {


    public OrderDetailsAdapter(Context context, List<OrderItemAttrs> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderItemAttrs item) {

    }
}
