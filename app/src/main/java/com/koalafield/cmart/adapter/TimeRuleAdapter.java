package com.koalafield.cmart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.Rule;
import com.koalafield.cmart.bean.order.TimeInterval;
import com.koalafield.cmart.utils.StringUtils;

import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class TimeRuleAdapter extends BaseQuickAdapter<TimeInterval> {

    StringBuffer sb ;
    public TimeRuleAdapter(Context context, List<TimeInterval> data) {
        super(context, data);
        sb = null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.time_rule_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, TimeInterval item) {
        sb = new StringBuffer();
        sb.append(item.getStartTime());
        if (!StringUtils.isEmpty(item.getEndTime())){
            sb.append("-").append(item.getEndTime());
        }
        TextView  time_start_price = holder.getView(R.id.time_start_price);
        if (StringUtils.isEmpty(item.getPrice()) || item.getPrice().equals("0")|| item.getPrice().equals("0.00") ){
            time_start_price.setVisibility(View.GONE);
        }else {
            time_start_price.setVisibility(View.VISIBLE);
            time_start_price.setText("AUD "+item.getPrice());
        }
        holder.setText(R.id.time_start_rule, sb.toString());
    }
}
