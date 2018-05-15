package com.koalafield.cmart.ui.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.event.CartEvent;
import com.koalafield.cmart.presenter.cart.CartListPresenter;
import com.koalafield.cmart.presenter.cart.CartPresenter;
import com.koalafield.cmart.presenter.cart.ICartListPresenter;
import com.koalafield.cmart.presenter.cart.ICartPresenter;
import com.koalafield.cmart.ui.view.cart.ICartVIew;
import com.koalafield.cmart.utils.Constants;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StackActivityManager;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.EmptyLayout;
import com.squareup.haha.perflib.Main;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 */

public class MainActivity extends TabActivity implements View.OnClickListener,ICartVIew<CartNumberBean>{

    private TabHost tabHost;
    @BindView(R.id.layout_1)
    FrameLayout layout1;
    @BindView(R.id.layout_2)
    FrameLayout layout2;
    @BindView(R.id.layout_3)
    FrameLayout layout3;
    @BindView(R.id.layout_4)
    FrameLayout layout4;
    @BindView(R.id.iv_index)
    TextView iv_index;
    @BindView(R.id.iv_category)
    TextView iv_category;
    @BindView(R.id.iv_shopcart)
    TextView iv_shopcart;
    @BindView(R.id.iv_user_center)
    TextView iv_user_center;
    @BindView(R.id.tv_cart_num)
    TextView tv_cart_num;
    private FrameLayout[] layoutArray = null;
    private Map<Integer, View[]> tabMap = null;
    private Intent mIntent;
    private ICartPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StackActivityManager.getActivityManager().addActivity(this);
        ButterKnife.bind(this);
        initDatas();
        initAction();
    }

    public void initDatas() {
        tabHost = findViewById(android.R.id.tabhost);
        layoutArray = new FrameLayout[]{layout1, layout2, layout3, layout4};
        tabMap = new HashMap<>();
        tabMap.put(1, new View[]{layout1, iv_index});
        tabMap.put(2, new View[]{layout2, iv_category});
        tabMap.put(3, new View[]{layout3, iv_shopcart});
        tabMap.put(4, new View[]{layout4, iv_user_center});
        mIntent = new Intent(this,HomeActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        overridePendingTransition(0, 0);
        tabHost.addTab(tabHost.newTabSpec("1").setIndicator("1").setContent(mIntent));
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator("2").setContent(new Intent(this, CategryActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator("3").setContent(new Intent(this, CartActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("4").setIndicator("4").setContent(new Intent(this, PersonActivity.class)));
        // 初始化按钮颜色
        initSelectTab();

    }

    private void initSelectTab() {
        int index = 1;
        String selectedIndex = this.getIntent().getStringExtra(Constants.MAIN_SELECTED_INDEX);
        if (selectedIndex != null && selectedIndex.length() > 0) {
            index = Integer.parseInt(selectedIndex);
        }
        setSelectedIndex(index);
    }

    private void initAction() {
        for (View view : layoutArray) {
            view.setOnClickListener(MainActivity.this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initSelectTab();
    }


    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (!StringUtils.isEmpty(tickets)){
            mPresenter = new CartPresenter(MainActivity.this);
            mPresenter.getData();
        }
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public  void getMessage(SelectEvent event){
        Log.i("返回的类型",event.getType());
        int currentPosition = 0;
        if (null != event){
            String type = event.getType();
            if ("cart".equals(type)){
                //跳转购物车
                currentPosition = 2 ;
            }else  if ("person".equals(type)){
                //个人中心
                currentPosition = 3;
            }else if ("home".equals(type)){
                currentPosition = 0;
            }
            changeFragment(currentPosition);
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("是否销毁","**********跳转界面为*********");

  //      EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < layoutArray.length; i++) {
            if (view == layoutArray[i]) {
                int selectIndex = i + 1;
                setSelectedIndex(selectIndex);
                break;
            }
        }
    }

    public void setSelectedIndex(int index) {
        setTabSelected(index);
        tabHost.setCurrentTabByTag(index + "");
    }

    private void setTabSelected(int index) {
        if (tabMap == null)
            return;
        for (Integer key : tabMap.keySet()) {
            boolean selected = false;
            if (key == index) {
                selected = true;
            }
            View[] viewArray = tabMap.get(key);
            for (View view : viewArray) {
                view.setSelected(selected);
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onSucessNumberful(CartNumberBean data) {
        if (data != null){
            if (data.getCount() <= 0){
                tv_cart_num.setVisibility(View.GONE);
            }else if (data.getCount() <= 99){
                tv_cart_num.setVisibility(View.VISIBLE);
                tv_cart_num.setText(String.valueOf(data.getCount()));
            }else {
                tv_cart_num.setVisibility(View.VISIBLE);
                tv_cart_num.setText(String.format(Locale.CHINA, "%d+", 99));
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
                tv_cart_num.setVisibility(View.VISIBLE);
                if (event.type ==0 ){   //--->代表购物车里总数量
                    tv_cart_num.setText(String.valueOf(event.nCount));
                }else if (event.type == 1 ){  //删除单个商品
                    String str = tv_cart_num.getText().toString();
                    int mCount = Integer.valueOf(str);
                    if (mCount <= 0){
                        tv_cart_num.setText(String.valueOf(0));
                        tv_cart_num.setVisibility(View.GONE);
                    }else {
                        tv_cart_num.setText(String.valueOf(mCount-event.nCount));
                    }
                }else if (event.type == 2){  //购物车里减
                    String str = tv_cart_num.getText().toString();
                    int mCount = Integer.valueOf(str);
                    tv_cart_num.setText(String.valueOf(mCount-event.nCount));
                }else  if (event.type == 3){  //购物车里的增
                    String str = tv_cart_num.getText().toString();
                    int mCount = Integer.valueOf(str);
                    tv_cart_num.setText(String.valueOf(mCount+event.nCount));
                }else  if (event.type == 4){  //添加购物车
                    String str = tv_cart_num.getText().toString();
                    int mCount = Integer.valueOf(str);
                    tv_cart_num.setText(String.valueOf(mCount+event.nCount));
                }
            }else {
                tv_cart_num.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onNumberFailure(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetError(EmptyLayout.OnRetryListener onRetryListener) {

    }
}
