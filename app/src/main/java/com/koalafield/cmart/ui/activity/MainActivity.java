package com.koalafield.cmart.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.home.TabEntity;
import com.koalafield.cmart.ui.fragment.CartFragment;
import com.koalafield.cmart.ui.fragment.CategryFragment;
import com.koalafield.cmart.ui.fragment.HomeFragment;
import com.koalafield.cmart.ui.fragment.PersonFragment;
import com.koalafield.cmart.widget.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 */

public class MainActivity extends BaseActivity {


    @BindView(R.id.home_container)
    FrameLayout home_container;
    @BindView(R.id.bottom_nav)
    BottomBarLayout bottom_nav;

    private List<TabEntity> tabEntityList;
    private String[] tabText = {"商场","分类","购物车","个人中心"};
    private int[] normalIcon = {R.mipmap.home,R.mipmap.play,R.mipmap.buy,R.mipmap.mine};
    private int[] selectIcon = {R.mipmap.home1,R.mipmap.play1,R.mipmap.buy1,R.mipmap.mine1};
    private int normalTextColor = Color.parseColor("#999999");
    private int selectTextColor = Color.parseColor("#fa6e51");



    @Override
    public int attchLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void initDatas() {
        tabEntityList = new ArrayList<>();
        for (int i=0;i< tabText.length;i++){
            TabEntity item = new TabEntity();
            item.setText(tabText[i]);
            item.setNormalIconId(normalIcon[i]);
            item.setSelectIconId(selectIcon[i]);
            if(i== 2){
                item.setShowPoint(true);
                item.setNewsCount(0);
            }else{
                item.setShowPoint(false);
            }
            tabEntityList.add(item);
        }
        bottom_nav.setNormalTextColor(normalTextColor);
        bottom_nav.setSelectTextColor(selectTextColor);
        bottom_nav.setTabList(tabEntityList);

        //默认是首页
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,new HomeFragment()).commit();
        bottom_nav.setOnItemClickListener(new BottomBarLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:  //商城
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,new HomeFragment()).commit();
                        break;
                    case 1:  //分类
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,new CategryFragment()).commit();
                        break;
                    case 2:   //购物车
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,new CartFragment()).commit();
                        break;
                    case 3:   //个人中心
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_container,new PersonFragment()).commit();

                    default:
                        break;
                }
            }
        });


    }

    @Override
    public void upDateViews() {}

}
