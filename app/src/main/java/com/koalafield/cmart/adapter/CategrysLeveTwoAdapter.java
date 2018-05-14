package com.koalafield.cmart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.koalafield.cmart.bean.categry.CategryTwoBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/14
 */

public class CategrysLeveTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<CategryTwoBean> list;

    public CategrysLeveTwoAdapter(Context mContext, List<CategryTwoBean> categryTwoBeen){
        this.mContext =mContext;
        this.list = categryTwoBeen;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
