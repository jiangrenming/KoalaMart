package com.koalafield.cmart.ui.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.presenter.cart.CartListPresenter;
import com.koalafield.cmart.ui.view.cart.ICartListView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public class CartActivity extends TabBaseActivity implements ICartListView<CartDataBean>{

    @BindView(R.id.clear_all)
    TextView clear_all;
    @BindView(R.id.cart_top)
    FrameLayout cart_top;
    @BindView(R.id.goods_item)
    RecyclerView goods_item_recycler;
    @BindView(R.id.empty_cart)
    LinearLayout empty_cart;
    @BindView(R.id.select)
    ImageView select;
    @BindView(R.id.select_num)
    TextView select_num;
    @BindView(R.id.cart_curreny)
    TextView cart_curreny;
    @BindView(R.id.select_amount)
    TextView select_amount;
    @BindView(R.id.pay_goods)
    LinearLayout pay_goods;


    @Override
    public int attchLayoutRes() {
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (StringUtils.isEmpty(tickets)){
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("type",2);
            startActivity(intent);
            finish();
        }
        return R.layout.fragment_cart;
    }

    @Override
    public void initDatas() {
        presenter = new CartListPresenter(CartActivity.this);
        presenter.getData();
    }

    @Override
    public void upDateViews() {}

    @Override
    public void onSucessCartFul(CartDataBean data) {

    }

    @Override
    public void onFailureCart(String message) {

    }

    @Override
    public void onLoadNoData() {
        empty_cart.setVisibility(View.VISIBLE);
        cart_top.setVisibility(View.GONE);
    }
}
