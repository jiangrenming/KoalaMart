package com.koalafield.cmart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.dl7.recycler.listener.OnRemoveDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.goods.GoodsCollectionsBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/22
 */

public class CollectionAdapter extends BaseQuickAdapter<GoodsCollectionsBean> {

    public CollectionAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.layout_collection;
    }

    @Override
    protected void convert(BaseViewHolder holder, final GoodsCollectionsBean item) {
        ImageView collection_img = holder.getView(R.id.collection_img);
        ImageView collection_del = holder.getView(R.id.collection_del);
        holder.setText(R.id.collection_name,item.getName())
                .setText(R.id.collection_curreny,item.getCurrency())
                .setText(R.id.collection_price,String.format("%.2f", Double.valueOf(item.getCurrentPrice())));
        Glide.with(mContext).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(collection_img);
        collection_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCollectionCallBack.onSucessCollect(item);
            }
        });
    }

    private DelCollectionCallBack mCollectionCallBack;

    public void setDelCollectionCallBack( DelCollectionCallBack  collectionCallBack){
        this.mCollectionCallBack = collectionCallBack;
    }

    public  interface  DelCollectionCallBack{
        void onSucessCollect(GoodsCollectionsBean item);
    }
}
