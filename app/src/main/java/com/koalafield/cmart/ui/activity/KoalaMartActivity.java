package com.koalafield.cmart.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.event.CartEvent;
import com.koalafield.cmart.presenter.cart.CartPresenter;
import com.koalafield.cmart.presenter.cart.ICartPresenter;
import com.koalafield.cmart.ui.fragment.CartFragment;
import com.koalafield.cmart.ui.fragment.CategryFragment;
import com.koalafield.cmart.ui.fragment.HomeFragment;
import com.koalafield.cmart.ui.fragment.PersonFragment;
import com.koalafield.cmart.ui.view.cart.ICartVIew;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import static com.koalafield.cmart.R.id.tv_cart_num;

/**
 *
 * @author jiangrenming
 * @date 2018/6/4
 */

public class KoalaMartActivity extends BaseActivity implements ICartVIew<CartNumberBean> {

    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;

    private List<Fragment> fragments = new ArrayList<>();
    private ICartPresenter mPresenter;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void initDatas() {

        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        CategryFragment categryFragment = new CategryFragment();
        fragments.add(categryFragment);
        CartFragment cartFragment = new CartFragment();
        fragments.add(cartFragment);
        PersonFragment personFragment = new PersonFragment();
        fragments.add(personFragment);
        changeFragment(0); //默认显示第一页
        EventBus.getDefault().register(this);

    }

    public void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, fragments.get(currentPosition));
        transaction.commit();
    }
    @Override
    public void upDateViews() {

        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                Log.i("MainActivity", "position: " + currentPosition);
                changeFragment(currentPosition);
                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.mipmap.home);//更换为原来的图标
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (!StringUtils.isEmpty(tickets)){
            mPresenter = new CartPresenter(KoalaMartActivity.this);
            mPresenter.getData();
        }
    }

    @Override
    public void onSucessNumberful(CartNumberBean data) {
        if (data != null){
            if (data.getCount() <= 0){
                mBottomBarLayout.setUnread(2, 0);
            }else if (data.getCount() >0){
                mBottomBarLayout.setUnread(2, data.getCount());
            }
        }
    }

    /**
     * 接收购物车数量的变动
     * @param event
     */
    @Subscribe(threadMode  = ThreadMode.MAIN)
    public  void getCartNUmber(CartEvent event){
        if (event != null){
            if (event.nCount >0 ){
                if (event.type ==0 ){   //--->代表购物车里总数量
                    mBottomBarLayout.setUnread(2, event.nCount);
                }else if (event.type == 1 ){  //删除单个商品
                    int unReadNum = mBottomBarLayout.getBottomItem(2).getUnReadNum();
                    if (unReadNum <= 0){
                        mBottomBarLayout.setUnread(2, 0);
                    }else {
                        mBottomBarLayout.setUnread(2, unReadNum-event.nCount);
                    }
                }else if (event.type == 2){  //购物车里减
                    int unReadNum = mBottomBarLayout.getBottomItem(2).getUnReadNum();
                    mBottomBarLayout.setUnread(2, unReadNum-event.nCount);
                }else  if (event.type == 3){  //购物车里的增
                    int unReadNum = mBottomBarLayout.getBottomItem(2).getUnReadNum();
                    mBottomBarLayout.setUnread(2, unReadNum+event.nCount);
                }else  if (event.type == 4){  //添加购物车
                    int unReadNum = mBottomBarLayout.getBottomItem(2).getUnReadNum();
                    mBottomBarLayout.setUnread(2, unReadNum+event.nCount);
                }else if (event.type ==5){  //全部清除
                    int unReadNum = mBottomBarLayout.getBottomItem(2).getUnReadNum();
                    mBottomBarLayout.setUnread(2, unReadNum-event.nCount);
                    mBottomBarLayout.setUnread(2, 0);
                }
            }else {
                mBottomBarLayout.setUnread(2, 0);
            }
        }
    }


    @Override
    public void onNumberFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("是否销毁","**********跳转界面为*********");
        EventBus.getDefault().unregister(this);
    }
}
