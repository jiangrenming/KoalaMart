package com.koalafield.cmart.ui.activity;

import android.content.Intent;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.presenter.cart.CartListPresenter;
import com.koalafield.cmart.ui.view.cart.ICartListView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public class CartActivity extends TabBaseActivity implements ICartListView<CartDataBean>{

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
    public void upDateViews() {

    }

    @Override
    public void onSucessCartFul(CartDataBean data) {

    }

    @Override
    public void onFailureCart(String message) {

    }

    @Override
    public void onLoadNoData() {

    }
}
