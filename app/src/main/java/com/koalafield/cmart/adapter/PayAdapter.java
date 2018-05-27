package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.ShoppingCart;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class PayAdapter extends BaseQuickAdapter<ShoppingCart> {


    public PayAdapter(Context context) {
        super(context);
    }



    @Override
    protected int attachLayoutRes() {
        return R.layout.pay_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, ShoppingCart item) {
        holder.setText(R.id.yanse,item.getColor() == null ? "": item.getColor())
                .setText(R.id.chicun,item.getSize() == null ?"":item.getSize())
                .setText(R.id.goods_name,item.getCommodity().getName())
                .setText(R.id.pay_curreny,item.getCommodity().getCurrency())
                .setText(R.id.pay_price,item.getCommodity().getCurrentPrice())
                .setText(R.id.zhongliang,item.getWeight()== null ? "" :item.getWeight())
                .setText(R.id.leixing,item.getType()== null ? "" :item.getType())
                .setText(R.id.fenge,item.getMaterial() == null ? "" :item.getMaterial())
                .setText(R.id.pay_count,String.valueOf(item.getCount()));
        ImageView pay_img = holder.getView(R.id.pay_img);
        Glide.with(mContext).load(item.getCommodity().getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(pay_img);
    }
}
