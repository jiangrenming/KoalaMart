package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.Delivery;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class TimerTypeAdapter extends BaseQuickAdapter<Delivery> {


    public TimerTypeAdapter(Context context, List<Delivery> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.time_type_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, Delivery item) {
        LinearLayout time_layout = holder.getView(R.id.time_layout);
        TextView type_time = holder.getView(R.id.type_time);
        type_time.setText(item.getDeliveryName());
        if (item.isTypeSelect()){
            time_layout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            type_time.setTextColor(mContext.getResources().getColor(R.color.deviery));
        }else {
            time_layout.setBackgroundColor(mContext.getResources().getColor(R.color.gray_light));
            type_time.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }
}
