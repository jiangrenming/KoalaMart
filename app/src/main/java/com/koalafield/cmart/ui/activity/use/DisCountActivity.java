package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.OrderViewPagerAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.ui.activity.order.fragment.AllOrderFragment;
import com.koalafield.cmart.ui.activity.order.fragment.PayWaitFragment;
import com.koalafield.cmart.ui.fragment.DisCountExprideFragment;
import com.koalafield.cmart.ui.fragment.DisCountTimeFragment;
import com.koalafield.cmart.ui.view.user.IDisCountListView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.widget.CustomViewPager;
import com.koalafield.cmart.widget.IndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/5/24.
 */

public class DisCountActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.indicator_discount)
    IndicatorView indicator_discount;
    @BindView(R.id.discount_tab_one)
    TextView discount_tab_one;
    @BindView(R.id.discount_tab_two)
    TextView  discount_tab_two;
    @BindView(R.id.discount_viewpager)
    CustomViewPager discount_viewpager;

    private TextView[] TabArray = null;
    private ArrayList<Fragment> fragmentsList ;

    private int count = 0;


    @Override
    public int attchLayoutRes() {
        return R.layout.activty_discount;
    }

    @Override
    public void initDatas() {
        top_name.setText("优惠券");
        fragmentsList = new ArrayList<>();
        fragmentsList.add(new DisCountTimeFragment());
        fragmentsList.add(new DisCountExprideFragment());
        discount_viewpager.setOffscreenPageLimit(0);
        initTabColor(count);
        initViewPager();
    }

    @Override
    public void upDateViews() {}

    @OnClick(R.id.back)
    public  void bakcClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private void initViewPager() {
        discount_viewpager.setAdapter(new OrderViewPagerAdapter(getSupportFragmentManager(),fragmentsList));
        //切换viewpager
        discount_viewpager.setCurrentItem(count);
        discount_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator_discount.scroll(position, positionOffset);
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

    private void initTabColor(final int index) {
        if (TabArray == null) {
            TabArray = new TextView[]{discount_tab_one, discount_tab_two};
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
                    discount_viewpager.setCurrentItem(po, true);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ){
            DisCountBean discount = (DisCountBean) data.getSerializableExtra("counpon");
            Intent intent = new Intent();
            intent.putExtra("counpon",discount);
            setResult(RESULT_OK ,intent);
            finish();
        }
    }
}
