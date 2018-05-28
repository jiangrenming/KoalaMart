package com.koalafield.cmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.goods.GoodsItem;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/18.
 */

public class SkuAdapter extends BaseQuickAdapter<GoodsItem> {


    public SkuAdapter(Context context, List<GoodsItem> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.color_select;
    }

    @Override
    protected void convert(BaseViewHolder holder, final GoodsItem item) {
        LinearLayout layout =  holder.getView(R.id.layout);
        TextView title = holder.getView(R.id.itemText);
        switch (item.getState()) {
            // 选中
            case 0:
                layout.setBackgroundResource(R.drawable.shape2);
                title.setTextColor(Color.WHITE);
                break;
            // 未选中
            case 1:
                layout.setBackgroundResource(R.drawable.shape1);
                title.setTextColor(Color.BLACK);
                break;
            // 不可选
            case 2:
                layout.setBackgroundResource(R.drawable.shape1);
                title.setTextColor(Color.parseColor("#999999"));
                break;
            default:
                break;
        }
        /** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
        title.setText(item.getName());
    }
}
