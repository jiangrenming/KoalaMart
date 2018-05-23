package com.koalafield.cmart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.goods.GoodsCollectionsBean;
import com.koalafield.cmart.bean.user.PurchaseOffBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/23.
 */

public class PurchareOffAdapter extends BaseQuickAdapter<PurchaseOffBean> {


    public PurchareOffAdapter(Context context, List<PurchaseOffBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.layout_purshcase;
    }

    @Override
    protected void convert(BaseViewHolder holder, final PurchaseOffBean item) {
        ImageView puroff_img = holder.getView(R.id.puroff_img);
        ImageView puroff_add = holder.getView(R.id.puroff_add);
        holder.setText(R.id.puroff_name,item.getName())
                .setText(R.id.puroff_curreny,item.getCurrency())
                .setText(R.id.puroff_price,String.format("%.2f", Double.valueOf(item.getCurrentPrice())));
        Glide.with(mContext).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(puroff_img);
        puroff_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddCartCallBack.onSucessCollect(item);
            }
        });
    }
    private AddCartCallBack mAddCartCallBack;

    public void setAddCartCallBack( AddCartCallBack addCartCallBack){
        this.mAddCartCallBack = addCartCallBack;
    }

    public  interface  AddCartCallBack{
        void onSucessCollect(PurchaseOffBean item);
    }
}
