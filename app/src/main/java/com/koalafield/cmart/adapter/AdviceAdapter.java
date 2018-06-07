package com.koalafield.cmart.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.user.AdvicesBean;

import java.util.List;

/**
 * Created by jiangrenming on 2018/6/7.
 */

public class AdviceAdapter extends BaseQuickAdapter<AdvicesBean> {


    public AdviceAdapter(Context context, List<AdvicesBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_advices;
    }

    @Override
    protected void convert(BaseViewHolder holder, AdvicesBean item) {
         TextView advice_type_id = holder.getView(R.id.advice_type_id);
         LinearLayout advice_layout =  holder.getView(R.id.advice_layout);
        if (item.isSelect()){
            advice_layout.setBackgroundResource(R.drawable.select_user_response);
            advice_type_id.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            advice_layout.setBackgroundResource(R.drawable.user_response);
            advice_type_id.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        advice_type_id.setText(item.getContent());
    }
}
