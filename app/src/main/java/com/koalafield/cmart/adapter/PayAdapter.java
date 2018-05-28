package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.ShoppingCart;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class PayAdapter extends BaseQuickAdapter<ShoppingCart> {


    public PayAdapter(Context context) {
        super(context);
    }

    public PayAdapter(Context context, List<ShoppingCart> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.pay_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, ShoppingCart item) {
        holder.setText(R.id.yanse,item.getColor() == null ? "颜色:": "颜色:"+(item.getColor()))
                .setText(R.id.chicun,item.getSize() == null ?"尺寸:": "尺寸:"+item.getSize())
                .setText(R.id.goods_name,item.getCommodity().getName())
                .setText(R.id.pay_curreny,item.getCommodity().getCurrency())
                .setText(R.id.pay_price,item.getCommodity().getCurrentPrice())
                .setText(R.id.zhongliang,item.getWeight()== null ? "重量" :"重量:"+item.getWeight())
                .setText(R.id.leixing,item.getType()== null ? "类型:" : "类型:"+item.getType())
                .setText(R.id.fenge,item.getMaterial() == null ? "风格:" :"风格:"+item.getMaterial())
                .setText(R.id.pay_count,String.valueOf("共"+item.getCount())+"件");
        ImageView pay_img = holder.getView(R.id.pay_img);
        Glide.with(mContext).load(item.getCommodity().getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(pay_img);
    }
}
