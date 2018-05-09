/*
package com.koalafield.cmart.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.koalafield.cmart.R;
import com.koalafield.cmart.ui.fragment.CartFragment;
import com.koalafield.cmart.ui.fragment.CategryFragment;
import com.koalafield.cmart.ui.fragment.HomeFragment;
import com.koalafield.cmart.ui.fragment.PersonFragment;

*/
/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 *//*


public class DataGenerator {

    public static final int []mTabRes = new int[]{R.mipmap.home_tab, R.mipmap.shopper_grey,R.mipmap.shopcar_grey,R.mipmap.my_grey};
    public static final int []mTabResPressed = new int[]{R.mipmap.select_home_tab,R.mipmap.shopper_red,R.mipmap.shopcar_red,R.mipmap.my_red};
    public static final String []mTabTitle = new String[]{"商城","分类","购物车","个人中心"};

    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[4];
        fragments[0] = HomeFragment.newInstance(from);
        fragments[1] = CategryFragment.newInstance(from);
        fragments[2] = CartFragment.newInstance(from);
        fragments[3] = PersonFragment.newInstance(from);
        return fragments;
    }

    */
/**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     *//*

    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
*/
