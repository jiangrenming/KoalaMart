package com.koalafield.cmart.adapter;

import android.content.Context;
import android.view.View;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.categry.BrandListBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class BrandAdapter extends BaseQuickAdapter<BrandListBean> {

    public BrandAdapter(Context context, List<BrandListBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_brand;
    }

    @Override
    protected void convert(BaseViewHolder holder, BrandListBean item) {
        View brand_select = holder.getView(R.id.brand_select);
        holder.setText(R.id.brand_name,item.getName());
        if (item.isSelect()){
            brand_select.setVisibility(View.VISIBLE);
            brand_select.setBackgroundColor(R.color.btn_red);
        }else {
            brand_select.setVisibility(View.INVISIBLE);
        }
    }
}
