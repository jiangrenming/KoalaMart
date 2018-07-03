package com.koalafield.cmart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.user.ShareDataBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/6/20
 */

public class ShareAdapter extends BaseQuickAdapter<ShareDataBean> {



    public ShareAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.item_share;
    }

    @Override
    protected void convert(BaseViewHolder holder, ShareDataBean item) {

        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int widthPixels = dm.widthPixels;
        int viewWidth = widthPixels/ (mData.size());

        View view = holder.getConvertView();
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.width = viewWidth;
        view.setLayoutParams(layoutParams);

        holder.setText(R.id.key,item.getName()).setText(R.id.value,item.getValue());
    }
}
