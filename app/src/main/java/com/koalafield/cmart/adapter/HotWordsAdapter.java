package com.koalafield.cmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/30.
 */

public class HotWordsAdapter extends BaseQuickAdapter<String> {


    public HotWordsAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_hot_words;
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        TextView hot_itemText = holder.getView(R.id.hot_itemText);
        LinearLayout hot_layout = holder.getView(R.id.hot_layout);
        hot_layout.setBackgroundResource(R.drawable.shape1);
        hot_itemText .setTextColor(Color.BLACK);
        hot_itemText.setText(item);
    }
}
