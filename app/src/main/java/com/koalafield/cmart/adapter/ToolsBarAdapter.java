package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.home.ToolsBarBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/30.
 */

public class ToolsBarAdapter extends BaseQuickAdapter<ToolsBarBean> {



    public ToolsBarAdapter(Context context, List<ToolsBarBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.layout_toolbar;
    }

    @Override
    protected void convert(BaseViewHolder holder, ToolsBarBean item) {
        ImageView  tool_type = holder.getView(R.id.tool_type);
        Glide.with(mContext).load(item.getImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(tool_type);
        holder.setText(R.id.toole_names,item.getName());
    }
}
