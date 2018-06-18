package com.koalafield.cmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.OrderBean;
import com.koalafield.cmart.bean.order.OrderItemAttrs;
import com.koalafield.cmart.bean.order.OrderListBean;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.activity.order.OrderDetailsActivity;

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
    protected void convert(BaseViewHolder holder, final OrderListBean item) {
        holder.setText(R.id.order_number,item.getBillNo())
                .setText(R.id.order_time,item.getCreatedTime())
                .setText(R.id.order_all_count,String.valueOf(item.getTotalCount()))
                .setText(R.id.trans_tax,item.getCurrency()+" "+item.getDeliveryFree())
                .setText(R.id.order_curreny,item.getCurrency()+" ")
                .setText(R.id.order_allprice,String.format("%.2f",item.getTotalPrice()))
                .setText(R.id.order_state,item.getStatusText());
        RecyclerView item_order = holder.getView(R.id.item_order);
        OrderAdapter orderAdapter = new OrderAdapter(mContext,item.getGoodsList());
        RecyclerViewHelper.initRecyclerViewV(mContext,item_order,true,orderAdapter);
        orderAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext,OrderDetailsActivity.class);
                intent.putExtra("billNo",item.getBillNo());
                mContext.startActivity(intent);
            }
        });
    }


}
