package com.koalafield.cmart.adapter;

import android.content.Context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleHovlder titleHovlder = null;
        if (convertView == null){
            titleHovlder = new TitleHovlder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.color_select,null);
            titleHovlder.items_layout = convertView.findViewById(R.id.layout);
            titleHovlder.itemText = convertView.findViewById(R.id.itemText);
            convertView.setTag(titleHovlder);
        }
        titleHovlder = (TitleHovlder) convertView.getTag();
        GoodsItem goodsItem = mLists.get(position);
        Log.i("返回的数据为:",mLists.size()+"");
        /** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
        titleHovlder.itemText.setText(goodsItem.getName());
      /*switch (item.getState()) {
                // 选中
                case "0":
                    layout.setBackgroundResource(R.xml.shape2);
                    title.setTextColor(Color.WHITE);
                    break;
                // 未选中
                case "1":
                    layout.setBackgroundResource(R.xml.shape1);
                    title.setTextColor(Color.BLACK);
                    break;
                // 不可选
                case "2":
                    layout.setBackgroundResource(R.xml.shape1);
                    title.setTextColor(Color.parseColor("#999999"));
                    break;
                default:
                    break;
            }*/

        return convertView;
    }


    class TitleHovlder{
        private TextView itemText;
        private LinearLayout items_layout;
    }
}
