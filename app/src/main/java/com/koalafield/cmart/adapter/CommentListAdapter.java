package com.koalafield.cmart.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.goods.CommentDatas;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/22
 */

public class CommentListAdapter extends BaseQuickAdapter<CommentDatas> {


    public CommentListAdapter(Context context, List<CommentDatas> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.comment_list_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentDatas item) {
       ImageView comment_img =  holder.getView(R.id.comment_img);
        holder.setText(R.id.comment_name,item.getCommmentUserName())
                .setText(R.id.where_from,item.getCountry())
                .setText(R.id.comment_contet,item.getContent())
                .setText(R.id.publish_time,item.getCreatedTime());
        Glide.with(mContext).load(item.getCommnetUserHeadImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(comment_img);
    }
}
