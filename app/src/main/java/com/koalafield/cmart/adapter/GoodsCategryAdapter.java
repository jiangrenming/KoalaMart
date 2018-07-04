package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.home.GoodItemBean;
import com.koalafield.cmart.bean.home.GoodsCategryBean;
import com.koalafield.cmart.utils.AndroidTools;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/15
 */

public class GoodsCategryAdapter extends BaseQuickAdapter<GoodItemBean> {


    public GoodsCategryAdapter(Context context, List<GoodItemBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.goositem_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, GoodItemBean item) {
        ImageView img = holder.getView(R.id.goods_item_img);
        holder.setText(R.id.goods_item_name,item.getName())
                .setText(R.id.goods_item_price,(item.getCurrency()+ (item.getCurrentPrice() == null ? "0.00" : item.getCurrentPrice())));
        Glide.with(mContext).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(img);
    }

}
