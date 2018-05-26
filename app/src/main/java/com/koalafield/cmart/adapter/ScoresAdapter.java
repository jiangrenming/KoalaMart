package com.koalafield.cmart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.user.PurchaseOffBean;
import com.koalafield.cmart.bean.user.ScoreBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/23
 */

public class ScoresAdapter extends BaseQuickAdapter<ScoreBean> {

    public ScoresAdapter(Context context, List<ScoreBean> data) {
        super(context, data);
    }

    public ScoresAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.score_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, ScoreBean item) {
        holder.setText(R.id.create_time,item.getCreatedTime())
                .setText(R.id.scores,String.valueOf(item.getScore()));
    }

}
