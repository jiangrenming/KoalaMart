package com.koalafield.cmart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.categry.GoodsListBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class CategryGoodsAdapter extends BaseQuickAdapter<GoodsListBean> {

    public CategryGoodsAdapter(Context context, List<GoodsListBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.categry_goods_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final GoodsListBean item) {
        ImageView item_img = holder.getView(R.id.item_img);
        ImageView add_cart = holder.getView(R.id.add_cart);
        Glide.with(mContext).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(item_img);
        holder.setText(R.id.categry_two_name,item.getName())
                .setText(R.id.price,item.getCurrency()+String.format("%.2f",  item.getCurrentPrice()));

        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChooseCartCallBack.choose(item,view);
            }
        });
    }

    public  ChooseCartCallBack mChooseCartCallBack;

    public void setmChooseCartCallBack(ChooseCartCallBack chooseCartCallBack){
        this.mChooseCartCallBack = chooseCartCallBack;
    }

    public  interface  ChooseCartCallBack{
        void choose(GoodsListBean item,View view);

    }
}
