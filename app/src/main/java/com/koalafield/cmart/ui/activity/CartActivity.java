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
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CartItemAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.bean.cart.CartIdBean;
import com.koalafield.cmart.bean.event.CartEvent;
import com.koalafield.cmart.presenter.cart.CartChangeItemPresenter;
import com.koalafield.cmart.presenter.cart.CartClearPresenter;
import com.koalafield.cmart.presenter.cart.CartListPresenter;
import com.koalafield.cmart.presenter.cart.ICartChangeItemPresenter;
import com.koalafield.cmart.presenter.cart.ICartClearPresenter;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.activity.order.PayActivity;
import com.koalafield.cmart.ui.activity.use.PersonSettingActivity;
import com.koalafield.cmart.ui.view.cart.ICartChangeCountView;
import com.koalafield.cmart.ui.view.cart.ICartClearView;
import com.koalafield.cmart.ui.view.cart.ICartListView;
import com.koalafield.cmart.utils.AndroidTools;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StackActivityManager;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.CommonDialog;

import org.greenrobot.eventbus.EventBus;

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
        CartItemAdapter.CartItemCallBack,ICartClearView<BaseResponseBean>,ICartChangeCountView<CartIdBean> {

    @BindView(R.id.clear_all)
    ImageView clear_all;
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

    }

    @Override
    public void upDateViews() {}

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("登录后是否走这里","**********************************************");
        presenter = new CartListPresenter(CartActivity.this);
        presenter.getData();
    }

    private String currenyCD;
    @Override
    public void onSucessCartFul(List<CartDataBean> data) {
        if (data != null && data.size() >0 ){
            Log.i("商品的数据:","购物车含有的数据="+data.size());
            clear_all.setEnabled(true);
            clear_all.setClickable(true);
            select.setClickable(true);
            select.setEnabled(true);
            empty_cart.setVisibility(View.GONE);
            cart_top_layout.setVisibility(View.VISIBLE);
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setSelect(true);
                currenyCD = data.get(i).getCommodity().getCurrency();
            }
            mCartBean = data;
            cartItemAdapter = new CartItemAdapter(CartActivity.this,data,goods_item_recycler);
            RecyclerViewHelper.initRecyclerViewV(CartActivity.this,goods_item_recycler,false,cartItemAdapter);
            cartItemAdapter.setCartItemCallBack(this);
        }else{
            clear_all.setEnabled(false);
            clear_all.setClickable(false);
        }
    }

    @Override
    public void onFailureCart(String message,int code) {
        Log.e("查找列表失败","异常信息:"+message);
        Toast.makeText(CartActivity.this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
           skipLogin(this);
        }
    }

    @Override
    public void onLoadNoData() {
        empty_cart.setVisibility(View.VISIBLE);
        cart_top_layout.setVisibility(View.GONE);
        select.setImageResource(R.mipmap.un_select);
        select.setClickable(false);
        select.setEnabled(false);
        clear_all.setEnabled(false);
        clear_all.setClickable(false);
        select_num.setText("0");
        cart_curreny.setText("AUD");
        select_amount.setText("0.00");
    }

    @OnClick({R.id.clear_all,R.id.select,R.id.pay_goods})
    public  void onDeleteAll(View v){
        switch (v.getId()){
            case R.id.clear_all: //全部清空
                if (clear_all.isClickable()){
                    new CommonDialog(this).builder().setTitle("清空购物车").setMsg("确定清空购物车吗？").setNegativeButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartClearPresenter = new CartClearPresenter(CartActivity.this);
                            cartClearPresenter.getData();
                        }
                    }).setPositiveButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
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
            case R.id.pay_goods: //结算
                    if (allowNext()){
                        StringBuilder sb  = new StringBuilder();
                        if (mCartBean != null && mCartBean.size() >0 ){
                            for (int i = 0; i < mCartBean.size(); i++) {
                                if (mCartBean.get(i).isSelect()){
                                    sb.append(mCartBean.get(i).getId()).append(",");
                                }
                            }
                        String data = sb.substring(0, sb.length() - 1);
                        Log.i("数据为：",data);
                        Intent payIntent = new Intent(CartActivity.this, PayActivity.class);
                        payIntent.putExtra("payDatas",data);
                        startActivity(payIntent);
                    }else {
                        Toast.makeText(this,"重复点击太快",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getAllPrice(double price) {
        Long.valueOf((long) (price*100));
        cart_curreny.setText(currenyCD);
        if (price > 0  && price > 0.00){
            select_amount.setText(String.format("%.2f", price));
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
    public void seletSigle(boolean isSelect, CartDataBean item) {
        for (int i = 0; i <mCartBean.size() ; i++) {
            if (mCartBean.get(i).equals(item)){
                mCartBean.get(i).setSelect(isSelect);
            }
        }
        cartItemAdapter.updateItems(mCartBean);
    }

    @Override
    public void cleatAll(boolean isNull) {
        cart_curreny.setText("AUD");
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
    public void changeItemGoodsCount(int count,CartDataBean item,boolean isSelect) {
        if (count == 0){
            if (mCartBean != null && mCartBean.size() >0){
                for (int i = 0; i < mCartBean.size() ; i++) {
                    if (item.equals(mCartBean.get(i))){
                        mCartBean.remove(item);
                    }
                }
            }
        }else {
            if (mCartBean != null &&mCartBean.size() >0){
                for (int i = 0; i < mCartBean.size() ; i++) {
                    if (item.equals(mCartBean.get(i))){
                        mCartBean.get(i).setCount(item.getCount()+count);
                        mCartBean.get(i).setSelect(isSelect);
                    }
                }
            }
        }

        Map<String,String> params = new HashMap<>();
        params.put("count",String.valueOf(count));
        params.put("contentId",String.valueOf(item.getContentId()));
        params.put("color",item.getColor());
        params.put("size",item.getSize());
        params.put("weight",item.getWeight());
        params.put("material",item.getMaterial());
        params.put("type",item.getType());
        params.put("tag","0");
        ICartChangeItemPresenter presenter = new CartChangeItemPresenter(this);
        presenter.getChangeCountData(params);

    }

    @Override
    public void skipGoodsDeatils(CartDataBean item) {
        Intent intent = new Intent(CartActivity.this,GoodsDetailActivity.class);
        intent.putExtra("contentId",item.getContentId());
        startActivity(intent);
    }

    @Override
    public void onClearSucessful(BaseResponseBean data) {
        if (data != null){
            int mCount = 0;
            if (data.getCode() ==200){
                Log.e("清空购物车","清除成功");
                cartItemAdapter.cleanItems();
                cartItemAdapter.isClearAll();
                for (int i = 0; i < mCartBean.size(); i++) {
                    mCount += mCartBean.get(i).getCount();
                }
                EventBus.getDefault().post(new CartEvent(mCount,5));
                clear_all.setEnabled(false);
                clear_all.setClickable(false);
                empty_cart.setVisibility(View.VISIBLE);
                cart_top_layout.setVisibility(View.GONE);
                select.setClickable(false);
                select.setEnabled(false);
            }
        }
    }

    @Override
    public void onClearFailure(String message,int code) {
        Toast.makeText(CartActivity.this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            skipLogin(this);
        }
    }

    @Override
    public void onChangeItemSucessful(CartIdBean responseBean) {
   //     Toast.makeText(CartActivity.this,"增或减成功",Toast.LENGTH_SHORT).show();
        cartItemAdapter.updateItems(mCartBean);
        if (mCartBean == null  || mCartBean.size() == 0){
            empty_cart.setVisibility(View.VISIBLE);
            cart_top_layout.setVisibility(View.GONE);
            select.setClickable(false);
            select.setEnabled(false);
            clear_all.setEnabled(false);
            clear_all.setClickable(false);
            cartItemAdapter.isClearAll();
        }else {
            empty_cart.setVisibility(View.GONE);
            cart_top_layout.setVisibility(View.VISIBLE);
            select.setClickable(true);
            select.setEnabled(true);
            clear_all.setEnabled(true);
            clear_all.setClickable(true);
        }
    }

    @Override
    public void onChangeItemFailure(String message,int code) {
        Toast.makeText(CartActivity.this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            skipLogin(this);
        }
    }
}
