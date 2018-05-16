package com.koalafield.cmart.ui.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CartItemAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.presenter.cart.CartListPresenter;
import com.koalafield.cmart.ui.view.cart.ICartListView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.util.List;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public class CartActivity extends TabBaseActivity implements ICartListView<List<CartDataBean>>{

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

    private CartItemAdapter cartItemAdapter;


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
    public void onSucessCartFul(List<CartDataBean> data) {

        if (data != null && data.size() >0 ){
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setSelect(true);
            }
            if (cartItemAdapter == null){
                cartItemAdapter = new CartItemAdapter(CartActivity.this,data);
                RecyclerViewHelper.initRecyclerViewV(CartActivity.this,goods_item_recycler,false,cartItemAdapter);
            }else {
                cartItemAdapter.cleanItems();
                cartItemAdapter.addItems(data);
            }

        }
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
