package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.bean.categry.CategryTwoBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/14
 * 二级列表
 */

public class CategryTwoAdapter extends BaseQuickAdapter {

    public CategryTwoAdapter(Context context, List<CategryTwoBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.categrytwo_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {
        CategryTwoBean items = (CategryTwoBean) item;
        ImageView img = holder.getView(R.id.item_img);
        TextView name = holder.getView(R.id.categry_two_name);
        name.setText(items.getName());
        Glide.with(mContext).load(items.getImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(img);
    }


}
