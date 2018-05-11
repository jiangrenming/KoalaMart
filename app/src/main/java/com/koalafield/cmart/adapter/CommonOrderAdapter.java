package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.bean.order.OrderBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/12.
 * 订单的共同适配器
 */

public class CommonOrderAdapter extends BaseQuickAdapter<OrderBean> {


    public CommonOrderAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderBean item) {

    }

}
