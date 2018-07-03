package com.koalafield.cmart.adapter;

import android.content.Context;
import android.graphics.Paint;
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

public class BrandAdapter extends BaseQuickAdapter<CateListBean> {

    public BrandAdapter(Context context, List<CateListBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_brand;
    }

    @Override
    protected void convert(BaseViewHolder holder, CateListBean item) {

        TextView brand_name = holder.getView(R.id.brand_name);
        View brand_select = holder.getView(R.id.brand_select);
        brand_name.setText(item.getName());
        if (item.isSelect()){
          brand_select.setVisibility(View.VISIBLE);
          brand_select.setBackgroundColor(mContext.getResources().getColor(R.color.btn_red));
        }else {
            brand_select.setVisibility(View.INVISIBLE);
        }
    }


    private int selectId;
    public  void setSelect(int id){
        this.selectId = id;
    }
}
