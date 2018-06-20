package com.koalafield.cmart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.ShoppingCart;
import com.koalafield.cmart.utils.StringUtils;

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

        TextView yanse = holder.getView(R.id.yanse);
        TextView chicun = holder.getView(R.id.chicun);
        TextView leixing = holder.getView(R.id.leixing);
        TextView zhongliang = holder.getView(R.id.zhongliang);
        TextView fenge = holder.getView(R.id.fenge);

        if (StringUtils.isEmpty(item.getColor())){
            yanse.setVisibility(View.GONE);
        }else {
            yanse.setText("颜色:"+(item.getColor()));
        }
        if (StringUtils.isEmpty(item.getSize())){
            chicun.setVisibility(View.GONE);
        }else {
            chicun.setText("尺寸:"+(item.getSize()));
        }
        if (StringUtils.isEmpty(item.getType())){
            leixing.setVisibility(View.GONE);
        }else {
            leixing.setText("类型:"+(item.getType()));
        }
        if (StringUtils.isEmpty(item.getWeight())){
            zhongliang.setVisibility(View.GONE);
        }else {
            zhongliang.setText("重量:"+(item.getWeight()));
        }
        if (StringUtils.isEmpty(item.getMaterial())){
            fenge.setVisibility(View.GONE);
        }else {
            fenge.setText("风格:"+(item.getMaterial()));
        }

        holder.setText(R.id.goods_name,item.getCommodity().getName())
                .setText(R.id.pay_price,item.getCommodity().getCurrentPrice())
                .setText(R.id.pay_count,String.valueOf("共"+item.getCount())+"件");
        ImageView pay_img = holder.getView(R.id.pay_img);
        Glide.with(mContext).load(item.getCommodity().getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(pay_img);
    }
}
