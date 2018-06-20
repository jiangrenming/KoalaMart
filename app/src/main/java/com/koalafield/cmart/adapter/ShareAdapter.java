package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.user.ShareDataBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/6/20
 */

public class ShareAdapter extends BaseQuickAdapter<ShareDataBean> {



    public ShareAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_share;
    }

    @Override
    protected void convert(BaseViewHolder holder, ShareDataBean item) {
        holder.setText(R.id.key,item.getName()).setText(R.id.value,item.getValue());
    }
}
