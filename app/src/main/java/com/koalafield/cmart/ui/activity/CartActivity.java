package com.koalafield.cmart.ui.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnItemMoveListener;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CartItemAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.presenter.cart.CartChangeItemPresenter;
import com.koalafield.cmart.presenter.cart.CartClearPresenter;
import com.koalafield.cmart.presenter.cart.CartListPresenter;
import com.koalafield.cmart.presenter.cart.ICartChangeItemPresenter;
import com.koalafield.cmart.presenter.cart.ICartClearPresenter;
import com.koalafield.cmart.ui.view.cart.ICartChangeCountView;
import com.koalafield.cmart.ui.view.cart.ICartClearView;
import com.koalafield.cmart.ui.view.cart.ICartListView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public class CartActivity extends TabBaseActivity implements ICartListView<List<CartDataBean>>,
        CartItemAdapter.CartItemCallBack,ICartClearView<BaseResponseBean>,ICartChangeCountView<SpecialResponseBean> {

    @BindView(R.id.clear_all)
    TextView clear_all;
    @BindView(R.id.cart_top_layout)
    FrameLayout cart_top_layout;
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
    private  long allAmount =0;
    private int allCount = 0;
    private ICartClearPresenter cartClearPresenter;
    private  boolean isAllSelect = true;
    private  List<CartDataBean> mCartBean ;

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
    protected void onResume() {
        super.onResume();
        Log.e("登录后是否走这里","**********************************************");
    }

    @Override
    public void onSucessCartFul(List<CartDataBean> data) {
        if (data != null && data.size() >0 ){
            Log.i("商品的数据:","购物车含有的数据="+data.size());
            clear_all.setEnabled(true);
            clear_all.setClickable(true);
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setSelect(true);
            }
            mCartBean = data;
            if (cartItemAdapter == null){
                cartItemAdapter = new CartItemAdapter(CartActivity.this,data);
                RecyclerViewHelper.initRecyclerViewV(CartActivity.this,goods_item_recycler,false,cartItemAdapter);
            }else {
                cartItemAdapter.cleanItems();
                cartItemAdapter.addItems(data);
            }
            cartItemAdapter.setCartItemCallBack(this);

        }
    }

    @Override
    public void onFailureCart(String message) {
        Log.e("查找列表失败","异常信息:"+message);
        if (!StringUtils.isEmpty(message) && message.equals("401")){
            //session去重新登录
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("type",2);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(CartActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadNoData() {
        empty_cart.setVisibility(View.VISIBLE);
        cart_top_layout.setVisibility(View.GONE);
        select.setImageResource(R.mipmap.un_select);
        select_num.setText("0");
        cart_curreny.setText("AUD:");
        select_amount.setText("0.00");
    }

    @OnClick({R.id.clear_all,R.id.select})
    public  void onDeleteAll(View v){
        switch (v.getId()){
            case R.id.clear_all: //全部清空
                if (clear_all.isClickable()){
                    cartClearPresenter = new CartClearPresenter(this);
                    cartClearPresenter.getData();
                }
                break;
            case R.id.select:  //是否全选
                if (isAllSelect){
                    select.setImageResource(R.mipmap.un_select);
                    for (int i = 0; i < mCartBean.size(); i++) {
                        mCartBean.get(i).setSelect(false);
                    }
                    cartItemAdapter.updateItems(mCartBean);
                }else {
                    select.setImageResource(R.mipmap.select);
                    for (int i = 0; i < mCartBean.size(); i++) {
                        mCartBean.get(i).setSelect(true);
                    }
                    cartItemAdapter.updateItems(mCartBean);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getAllPrice(double price) {
        cart_curreny.setText("AUD:");
        if (price > 0 || price > 0.00){
            select_amount.setText(String.valueOf(price));
        }else {
            select_amount.setText("0.00");
        }
    }

    @Override
    public void getAllCount(int count) {
        if (count > 0){
            select_num.setText(String.valueOf(count));
        }else {
            select_num.setText("0");
        }
    }

    @Override
    public void seletAll(boolean isSelect) {
        if(isSelect){
            select.setImageResource(R.mipmap.select);
            isAllSelect = true;
        }else {
            select.setImageResource(R.mipmap.un_select);
            isAllSelect = false;
        }
    }

    @Override
    public void cleatAll(boolean isNull) {
        cart_curreny.setText("AUD:");
        if (isNull){
            select.setImageResource(R.mipmap.un_select);
            select_num.setText("0");
            select_amount.setText("0.00");
        }
    }

    /**
     * 增减删除时调用的接口
     * @param count
     */
    @Override
    public void changeItemGoodsCount(int count,int contentId) {
        Map<String,String> params = new HashMap<>();
        params.put("count",String.valueOf(count));
        params.put("contentId",String.valueOf(contentId));
        ICartChangeItemPresenter presenter = new CartChangeItemPresenter(this);
        presenter.getChangeCountData(params);
    }

    @Override
    public void onClearSucessful(BaseResponseBean data) {
        if (data != null){
            if (data.getCode() ==200){
                Log.e("清空购物车","清除成功");
                cartItemAdapter.cleanItems();
                clear_all.setEnabled(false);
                clear_all.setClickable(false);
            }
        }
    }

    @Override
    public void onClearFailure(String message) {
        Log.e("清空购物车","清除失败信息:"+message);
    }

    @Override
    public void onChangeItemSucessful(SpecialResponseBean specialBean) {
        if (specialBean.getCode() == 200){
            Toast.makeText(CartActivity.this,"增或减成功",Toast.LENGTH_SHORT).show();
            cartItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onChangeItemFailure(String message) {
        if (!StringUtils.isEmpty(message) && message.equals("401")){
            //session去重新登录
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("type",2);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(CartActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    }
}
