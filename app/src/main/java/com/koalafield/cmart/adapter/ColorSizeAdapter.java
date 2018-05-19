package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/18.
 */

public class ColorSizeAdapter extends BaseQuickAdapter {


    public ColorSizeAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.color_select;
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {

    }
}
