package com.koalafield.cmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.goods.GoodsItem;
import com.koalafield.cmart.widget.MyGridView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/21
 */

public class TitleAdpater extends BaseAdapter {

    private Context mContext;
    private List<GoodsItem> mLists;
    public TitleAdpater(Context context,List<GoodsItem> datas){
        this.mContext =context;
        this.mLists = datas;
    }

    @Override
    public int getCount() {
        return mLists.size() == 0 ? 0 :mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mLists == null ? null :mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            TitleHovlder titleHovlder = null;
            if (convertView == null){
                titleHovlder = new TitleHovlder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.color_select,null);
                titleHovlder.items_layout = convertView.findViewById(R.id.layout);
                titleHovlder.itemText = convertView.findViewById(R.id.itemText);
                convertView.setTag(titleHovlder);
            }
            titleHovlder = (TitleHovlder) convertView.getTag();
            final GoodsItem goodsItem = mLists.get(position);
            Log.i("返回的数据为:",mLists.size()+"");
            /** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
            titleHovlder.itemText.setText(goodsItem.getName());
           switch (goodsItem.getState()) {
                     // 选中
                    case 0:
                        titleHovlder.items_layout.setBackgroundResource(R.drawable.shape2);
                        titleHovlder.itemText .setTextColor(Color.WHITE);
                        break;
                    // 未选中
                    case 1:
                        titleHovlder.items_layout.setBackgroundResource(R.drawable.shape1);
                        titleHovlder.itemText .setTextColor(Color.BLACK);
                        break;
                    // 不可选
                    case 2:
                        titleHovlder.items_layout.setBackgroundResource(R.drawable.shape1);
                        titleHovlder.itemText .setTextColor(Color.parseColor("#999999"));
                        break;
                    default:
                        break;
                }
            titleHovlder.items_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        if (goodsItem.getState() != 2) {
                            itemClickListener.onItemClick(goodsItem, position);
                        }
                    }
                }
            });

        return convertView;
    }


    class TitleHovlder{
        private TextView itemText;
        private LinearLayout items_layout;
    }
    public onItemClickListener itemClickListener;// 接口回调
    public interface onItemClickListener {
         void onItemClick(GoodsItem bean, int position);
    }

    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
