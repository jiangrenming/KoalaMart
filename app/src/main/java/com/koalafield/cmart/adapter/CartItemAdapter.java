package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.bean.cart.CartItemBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/16
 */

public class CartItemAdapter extends BaseQuickAdapter<CartItemBean> {



    public CartItemAdapter(Context context, List<CartItemBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder holder, CartItemBean item) {

    }
}
