package com.koalafield.cmart.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.R;
import com.koalafield.cmart.base.bean.TipsBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.koalafield.cmart.R.layout.tip_layout;

/**
 * Created by jiangrenming on 2018/6/13.
 */

public class TipPagerAdapter extends PagerAdapter {

   /* private List<View> mViewList = new ArrayList<>();
    private  List<TipsBean> dots ;
    private Context mContext;*/
   private List<ImageView> imageList;
    public TipPagerAdapter(List<ImageView> imageList) {
        this.imageList = imageList;
    }

    /**
     * 获取ViewPage页数量
     * @return
     */
    @Override
    public int getCount() {
        return imageList.size();
    }

    /**
     * 判断类型是否符合
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 实例化一个页卡
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       /* View view = View.inflate(mContext, R.layout.tip_layout, null);
        ImageView img = view.findViewById(R.id.img);
        img.setImageResource(dots.get(position).getImg());
        mViewList.add(position,view);*/
        container.addView(imageList.get(position));
        return imageList.get(position);
    }

    /**
     * 销毁一个页卡
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageList.get(position));
    }
}
