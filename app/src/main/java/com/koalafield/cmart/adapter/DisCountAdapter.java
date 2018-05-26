package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.user.DisCountBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/25
 */

public class DisCountAdapter extends BaseQuickAdapter<DisCountBean> {

    public DisCountAdapter(Context context) {
        super(context);
    }
    public DisCountAdapter(Context context, List<DisCountBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.discpunt_item_count;
    }

    @Override
    protected void convert(BaseViewHolder holder, DisCountBean item) {

    }
}
