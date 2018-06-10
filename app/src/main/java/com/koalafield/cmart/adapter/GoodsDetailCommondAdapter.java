package com.koalafield.cmart.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.goods.GoodsRecoomendBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public class GoodsDetailCommondAdapter extends BaseQuickAdapter<GoodsRecoomendBean> {


    public GoodsDetailCommondAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.commond_goods_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, GoodsRecoomendBean item) {
        ImageView goods_commond_img = holder.getView(R.id.goods_commond_img);
        TextView old_price = holder.getView(R.id.old_price);
        if (item.getCurrentPrice() >= item.getOriginalPrice()){
            old_price.setVisibility(View.GONE);
        }else {
            old_price.setVisibility(View.VISIBLE);
            old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            old_price.setText(String.format("%.2f", item.getOriginalPrice()));
        }
        holder.setText(R.id.goods_commond_name,item.getName()).setText(R.id._curreny,item.getCurrency()+" ").setText(R.id.current_price,String.format("%.2f", item.getCurrentPrice()));
        Glide.with(mContext).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(goods_commond_img);
    }


}
