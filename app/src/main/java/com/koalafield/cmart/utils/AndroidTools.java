package com.koalafield.cmart.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.TitleAdpater;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.fragment.BaseFragment;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public class AndroidTools {


    /**
     * 格式化金额，保留2位小数点
     * @param amount
     * @return
     */
    public  static  String fromatAmount(Long amount){
        if (amount == null) {
            return "0.00";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return  decimalFormat.format(Double.valueOf((amount/100f)));
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        TitleAdpater adapter = (TitleAdpater) listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        listView.setLayoutParams(params);
    }
}
