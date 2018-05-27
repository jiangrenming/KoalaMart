package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.Rule;
import com.koalafield.cmart.bean.order.TimeInterval;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class TimeRuleAdapter extends BaseQuickAdapter<TimeInterval> {

    public TimeRuleAdapter(Context context, List<TimeInterval> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.time_rule_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, TimeInterval item) {
        holder.setText(R.id.time_start_rule, item.getStartTime()+"-"+item.getEndTime());

    }
}
