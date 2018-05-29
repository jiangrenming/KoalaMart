package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.OrderItemAttrs;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/29.
 */

public class OrderAdapter extends BaseQuickAdapter<OrderItemAttrs> {

    public OrderAdapter(Context context, List<OrderItemAttrs> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.pay_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderItemAttrs item) {
        holder.setText(R.id.yanse,item.getColor() == null ? "颜色:": "颜色:"+(item.getColor()))
                .setText(R.id.chicun,item.getSize() == null ?"尺寸:": "尺寸:"+item.getSize())
                .setText(R.id.goods_name,item.getSubject())
                .setText(R.id.pay_curreny,"AUD")
                .setText(R.id.pay_price,String.format("%.2f",item.getPrice()))
                .setText(R.id.zhongliang,item.getWeight()== null ? "重量" :"重量:"+item.getWeight())
                .setText(R.id.leixing,item.getType()== null ? "类型:" : "类型:"+item.getType())
                .setText(R.id.fenge,item.getMaterial() == null ? "风格:" :"风格:"+item.getMaterial())
                .setText(R.id.pay_count,String.valueOf("共"+item.getCount())+"件");
        ImageView pay_img = holder.getView(R.id.pay_img);
        Glide.with(mContext).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(pay_img);
    }
}
