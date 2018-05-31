package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.search.SearchListBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class SearchListAdapter extends BaseQuickAdapter<SearchListBean> {

    public SearchListAdapter(Context context, List<SearchListBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_search;
    }

    @Override
    protected void convert(BaseViewHolder holder, SearchListBean item) {
        ImageView search_img =holder.getView(R.id.search_img);
        Glide.with(mContext).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(search_img);
        holder.setText(R.id.search_name,item.getName())
                .setText(R.id.search_curreny,item.getCurrency())
                .setText(R.id.search_price,String.format("%.2f", Double.valueOf(item.getCurrentPrice())));

    }
}
