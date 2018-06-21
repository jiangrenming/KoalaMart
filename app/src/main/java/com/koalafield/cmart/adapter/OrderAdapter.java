package com.koalafield.cmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.OrderItemAttrs;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.utils.StringUtils;

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
    protected void convert(BaseViewHolder holder, final OrderItemAttrs item) {

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

        holder.setText(R.id.goods_name,item.getSubject())
                .setText(R.id.pay_price,String.format("%.2f",item.getPrice()))
                .setText(R.id.pay_count,String.valueOf("x"+item.getCount()));

        ImageView pay_img = holder.getView(R.id.pay_img);
        Glide.with(mContext).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(pay_img);
        pay_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                intent.putExtra("contentId",item.getContentId());
                mContext.startActivity(intent);
            }
        });
    }
}
