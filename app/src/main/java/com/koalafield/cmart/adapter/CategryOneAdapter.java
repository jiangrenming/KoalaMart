package com.koalafield.cmart.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.categry.CategryOneBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/14
 * 一级列表
 */

public class CategryOneAdapter extends BaseQuickAdapter<CategryOneBean> {


    public CategryOneAdapter(Context context, List<CategryOneBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.categry_one_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, CategryOneBean item) {
        TextView name = holder.getView(R.id.categryOne_name);
        name.setText(item.getName());
    }
}
