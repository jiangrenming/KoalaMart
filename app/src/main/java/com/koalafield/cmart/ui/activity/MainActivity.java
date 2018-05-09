package com.koalafield.cmart.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.utils.DataGenerator;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 */

public class MainActivity extends BaseActivity {


    @BindView(R.id.home_container)
    FrameLayout home_container;
    @BindView(R.id.bottom_tab_layout)
    TabLayout bottom_tab_layout;

    private Fragment[]mFragmensts;


    @Override
    public int attchLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initDatas() {
        mFragmensts = DataGenerator.getFragments("TabLayout Tab");
        bottom_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());
                // Tab 选中之后，改变各个Tab的状态
                for (int i=0;i<bottom_tab_layout.getTabCount();i++){
                    View view = bottom_tab_layout.getTabAt(i).getCustomView();
                    ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
                    TextView text = (TextView) view.findViewById(R.id.tab_content_text);
                    if(i == tab.getPosition()){ // 选中状态
                        icon.setImageResource(DataGenerator.mTabResPressed[i]);
                        text.setTextColor(getResources().getColor(android.R.color.black));
                    }else{// 未选中状态
                        icon.setImageResource(DataGenerator.mTabRes[i]);
                        text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        // 提供自定义的布局添加Tab
        for(int i=0;i<4;i++){
            bottom_tab_layout.addTab(bottom_tab_layout.newTab().setCustomView(DataGenerator.getTabView(this,i)));
        }
    }

    @Override
    public void upDateViews() {}


    private void onTabItemSelected(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;

            case 2:
                fragment = mFragmensts[2];
                break;
            case 3:
                fragment = mFragmensts[3];
                break;
            default:
                break;
        }
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }
}
