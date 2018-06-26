package com.koalafield.cmart.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.TimerBean;
import com.koalafield.cmart.bean.order.LeftTimer;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class TimerAdapter extends BaseQuickAdapter<LeftTimer>{
    public TimerAdapter(Context context, List<LeftTimer> data) {
        super(context, data);
    }

    public TimerAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.time_select_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, LeftTimer item) {
        TextView name = holder.getView(R.id.select_time_item);
        LinearLayout layout = holder.getView(R.id.time_item);
        name.setText(item.getDateStr());
        if (item.isSelect()){
            layout.setBackgroundColor(mContext.getResources().getColor(R.color.gray_light));
            name.setTextColor(mContext.getResources().getColor(R.color.black));
            TextPaint tp = name.getPaint();
            tp.setFakeBoldText(true);
        }else {
            layout.setBackgroundColor(mContext.getResources().getColor(R.color.gray_light));
            name.setTextColor(mContext.getResources().getColor(R.color.black));
            TextPaint tp = name.getPaint();
            tp.setFakeBoldText(false);
        }
    }
}
