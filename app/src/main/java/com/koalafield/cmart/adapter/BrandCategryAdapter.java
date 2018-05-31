package com.koalafield.cmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.categry.BrandListBean;
import com.koalafield.cmart.bean.categry.CateListBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class BrandCategryAdapter extends BaseQuickAdapter<BrandListBean> {


    public BrandCategryAdapter(Context context, List<BrandListBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_brand_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, BrandListBean item) {
        TextView brand_name_item = holder.getView(R.id.brand_name_item);
        holder.setText(R.id.brand_name_item,item.getName());
        if (item.isSelect()){
            brand_name_item.setBackgroundResource(R.color.btn_red);
            brand_name_item.setTextColor(Color.WHITE);
        }else {
            brand_name_item.setBackgroundResource(R.color.white);
            brand_name_item.setTextColor(Color.BLACK);
        }
    }

}
