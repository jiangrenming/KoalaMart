package com.koalafield.cmart.ui.activity.order;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.OrderViewPagerAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.ui.activity.order.fragment.AllOrderFragment;
import com.koalafield.cmart.ui.activity.order.fragment.PayWaitFragment;
import com.koalafield.cmart.ui.activity.order.fragment.WaitJusticeFragment;
import com.koalafield.cmart.ui.activity.order.fragment.WaitReceiverFragment;
import com.koalafield.cmart.ui.activity.order.fragment.WaitSendFragment;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.widget.IndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 * 我的订单
 */

public class MartOrderActivity extends BaseActivity {


    @BindView(R.id.indicator)
    IndicatorView indicator;
    @BindView(R.id.tab_one)
    TextView tab_one;
    @BindView(R.id.tab_two)
    TextView tab_two;
    @BindView(R.id.tab_three)
    TextView tab_three;
    @BindView(R.id.tab_four)
    TextView tab_four;
    @BindView(R.id.tab_five)
    TextView tab_five;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.back)
    ImageView back;
    private  int count;
    private TextView[] TabArray = null;
    private ArrayList<Fragment> fragmentsList ;


    @Override
    public int attchLayoutRes() {
        return R.layout.activity_mart_order;
    }

    @Override
    public void initDatas() {
        top_name.setText("我的订单");

        fragmentsList = new ArrayList<>();
        fragmentsList.add(new AllOrderFragment());
        fragmentsList.add(new PayWaitFragment());
        fragmentsList.add(new WaitSendFragment());
        fragmentsList.add(new WaitReceiverFragment());
        fragmentsList.add(new WaitJusticeFragment());

        //viewpager设置缓存页面
        mViewPager.setOffscreenPageLimit(2);
        Intent intent = getIntent();
        if (intent != null) {
            count = intent.getIntExtra("type", -1);
            initTabColor(count);
        }
        initViewPager();
    }

    @Override
    public void upDateViews() {}

    private void initTabColor(final int index) {
        if (TabArray == null) {
            TabArray = new TextView[]{tab_one, tab_two, tab_three, tab_four, tab_five};
        }
        for (int i = 0; i < TabArray.length; i++) {
            if (i == index) {
                TabArray[i].setTextColor(AndoridSysUtils.getColorValueByResId(this, R.color.text_red2));
            } else {
                TabArray[i].setTextColor(AndoridSysUtils.getColorValueByResId(this,R.color.text_black));
            }
            TabArray[i].setTag(i);
            TabArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int po = Integer.parseInt(view.getTag().toString());
                    mViewPager.setCurrentItem(po, true);
                }
            });
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new OrderViewPagerAdapter(getSupportFragmentManager(),fragmentsList));
        //切换viewpager
        mViewPager.setCurrentItem(count);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.scroll(position, positionOffset);
            }
            @Override
            public void onPageSelected(int position) {
                count = position;
                initTabColor(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });


    }

    @OnClick(R.id.back)
    public  void orderClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

}
