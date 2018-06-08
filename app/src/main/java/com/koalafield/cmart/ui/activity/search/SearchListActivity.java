package com.koalafield.cmart.ui.activity.search;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CategryOneAdapter;
import com.koalafield.cmart.adapter.CategryTwoAdapter;
import com.koalafield.cmart.adapter.SearchListAdapter;
import com.koalafield.cmart.adapter.TitleAdpater;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.Point;
import com.koalafield.cmart.bean.cart.CartIdBean;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.bean.categry.GoodsListBean;
import com.koalafield.cmart.bean.goods.GoodsItem;
import com.koalafield.cmart.bean.goods.SkuBean;
import com.koalafield.cmart.bean.search.SearchListBean;
import com.koalafield.cmart.presenter.cart.CartChangeItemPresenter;
import com.koalafield.cmart.presenter.cart.CartPresenter;
import com.koalafield.cmart.presenter.cart.ICartChangeItemPresenter;
import com.koalafield.cmart.presenter.cart.ICartPresenter;
import com.koalafield.cmart.presenter.categry.CategryOnePresenter;
import com.koalafield.cmart.presenter.categry.CategryTwoPresenter;
import com.koalafield.cmart.presenter.categry.ICategryPresenter;
import com.koalafield.cmart.presenter.categry.ICategryTwoPresenter;
import com.koalafield.cmart.presenter.search.ISearchPresenter;
import com.koalafield.cmart.presenter.search.SearchPresenter;
import com.koalafield.cmart.ui.activity.CategryActivity;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.activity.goods.CartShoppingActivity;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.activity.goods.SearchActivity;
import com.koalafield.cmart.ui.activity.order.PayActivity;
import com.koalafield.cmart.ui.view.cart.ICartChangeCountView;
import com.koalafield.cmart.ui.view.cart.ICartVIew;
import com.koalafield.cmart.ui.view.categry.ICategryTwoView;
import com.koalafield.cmart.ui.view.categry.ICategryView;
import com.koalafield.cmart.ui.view.search.ISearchView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.SelectSkuCalulator;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.EmptyLayout;
import com.koalafield.cmart.widget.MyGridView;
import com.koalafield.cmart.widget.PriceUpDownView;
import com.koalafield.cmart.widget.SaleUpDownView;
import com.koalafield.cmart.widget.SpinerPopWindow;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.PUT;

import static com.dl7.recycler.helper.RecyclerViewHelper.initRecyclerViewG;
import static com.dl7.recycler.helper.RecyclerViewHelper.initRecyclerViewV;

/**
 *
 * @author jiangrenming
 * @date 2018/5/31
 */

public class SearchListActivity extends BaseActivity implements ICartVIew<CartNumberBean>,ISearchView<List<SearchListBean>>,ICategryView<List<CategryOneBean>>
        ,ICartChangeCountView<CartIdBean>,PopupWindow.OnDismissListener,View.OnClickListener{

    @BindView(R.id.search_cart_num)
    TextView search_cart_num;
    @BindView(R.id.search_title)
    TextView search_title;
    @BindView(R.id.all_categry)
    TextView all_categry;
    @BindView(R.id.currentPrice)
    TextView currentPrice;
    @BindView(R.id.currentSale)
    TextView currentSale;
    @BindView(R.id.search_list)
    RecyclerView search_list;
    @BindView(R.id.search_swipe_refresh)
    SwipeRefreshLayout search_swipe_refresh;
    @BindView(R.id.empty_search)
    LinearLayout empty_search;
    @BindView(R.id.goods_car_img)
    ImageView goods_car_img;

    private ISearchPresenter mSearchList;
    private String title;
    private SearchListAdapter mAdapter;
    private boolean isUp = false;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_search_list;
    }

    @Override
    public void initDatas() {
        initSwipeRefresh();
         title = getIntent().getStringExtra("title");
        if (!StringUtils.isEmpty(title)){
            search_title.setText(title);
        }
        setDefaultDrawable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (!StringUtils.isEmpty(tickets)){
            ICartPresenter mPresenter = new CartPresenter(SearchListActivity.this);
            mPresenter.getData();
        }
    }

    @Override
    public void upDateViews() {
        mSearchList = new SearchPresenter(this);
        Map<String,String> params = new HashMap<>();
        params.put("text",title);
        params.put("ordercolumn",ordercolumn);
        params.put("desc",isUp+"");
        mSearchList.setParams(params);
        mSearchList.getData();
    }

    private  String ordercolumn = "CurrentPrice";
    @OnClick({R.id.all_categry,R.id.currentPrice,R.id.currentSale,R.id.goods_car_img})
    public  void changeClick(View view){
        switch (view.getId()){
            case R.id.all_categry:
                Map<String,String> params = new HashMap<>();
                ICategryPresenter  categryPresenter= new CategryOnePresenter(this);
                params.put("id","");
                categryPresenter.getCategryData(params);
                break;
            case R.id.currentPrice:
                setDrawable(1);
                ordercolumn = "CurrentPrice";
                break;
            case  R.id.currentSale:
                setDrawable(2);
                ordercolumn = "SalesCount";
                break;
            case R.id.goods_car_img:
                String tickets = ShareBankPreferenceUtils.getString("tickets", null);
                if (!StringUtils.isEmpty(tickets)){
                    startActivity(new Intent(SearchListActivity.this,CartShoppingActivity.class));
                }else {
                    Intent intent = new Intent(SearchListActivity.this, LoginActivity.class);
                    intent.putExtra("type",3);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
        Map<String,String> params = new HashMap<>();
        params.put("text",title);
        params.put("ordercolumn",ordercolumn);
        params.put("desc",isUp+"");
        mSearchList.setParams(params);
        mSearchList.getData();
    }

    private  void setDefaultDrawable(){
        Drawable drawable = getResources().getDrawable(R.mipmap.up_desc);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        currentPrice.setCompoundDrawables(null, null, drawable, null);
        currentPrice.setCompoundDrawablePadding(8);
        currentPrice.setTextColor(Color.parseColor("#e02b4d"));
        currentSale.setCompoundDrawables(null, null, null, null);
        currentSale.setTextColor(getResources().getColor(R.color.black));
    }

    private void setDrawable(int type) {
        Drawable drawable = null;
        if (isUp) {
            isUp = false;
            drawable = getResources().getDrawable(R.mipmap.up_desc);
        } else {
            isUp = true;
            drawable = getResources().getDrawable(R.mipmap.down_desc);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (type == 1){
            currentPrice.setCompoundDrawables(null, null, drawable, null);
            currentPrice.setCompoundDrawablePadding(8);
            currentPrice.setTextColor(Color.parseColor("#e02b4d"));
            currentSale.setCompoundDrawables(null, null, null, null);
            currentSale.setTextColor(getResources().getColor(R.color.black));
        }else {
            currentSale.setCompoundDrawables(null, null, drawable, null);
            currentSale.setCompoundDrawablePadding(8);
            currentSale.setTextColor(Color.parseColor("#e02b4d"));
            currentPrice.setCompoundDrawables(null, null, null, null);
            currentPrice.setTextColor(getResources().getColor(R.color.black));
        }
    }
    @Override
    public void onSucessNumberful(CartNumberBean data) {
        if (data != null){
            if (data.getCount() <= 0){
                search_cart_num.setVisibility(View.GONE);
            }else if (data.getCount() <= 99){
                search_cart_num.setVisibility(View.VISIBLE);
                search_cart_num.setText(String.valueOf(data.getCount()));
            }else {
                search_cart_num.setVisibility(View.VISIBLE);
                search_cart_num.setText(String.format(Locale.CHINA, "%d+", 99));
            }
        }
    }

    @Override
    public void onNumberFailure(String message,int  code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(SearchListActivity.this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    private List<GoodsItem> mColorList,mMaterialList,mTypeList,mSizeList,mWeightList;
    private SearchListBean mItem;
    private  int countType =  0;
    private View mView;
    @Override
    public void onSearchSucessFul(final List<SearchListBean> data) {
        if (data != null && data.size() >0){
            if (mAdapter == null){
                mAdapter = new SearchListAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewV(SearchListActivity.this,search_list,false,mAdapter);
            }else {
                mAdapter.cleanItems();
                mAdapter.addItems(data);
            }
            mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(SearchListActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("contentId",data.get(position).getId());
                    startActivity(intent);
                }
            });
            mAdapter.setRequestDataListener(new OnRequestDataListener() {
                @Override
                public void onLoadMore() {
                    Map<String,String> params = new HashMap<>();
                    params.put("text",title);
                    params.put("ordercolumn",ordercolumn);
                    params.put("desc",isUp+"");
                    mSearchList.setParams(params);
                    mSearchList.getMoreData();
                }
            });
            mAdapter.setmChooseCartCallBack(new SearchListAdapter.ChooseCartCallBack() {
                @Override
                public void choose(SearchListBean item, View view) {
                    mItem = item;
                    mView = view;
                    mColorList = item.getColorList();
                    mMaterialList = item.getMaterialList();
                    mSizeList = item.getSizeList();
                    mTypeList = item.getTypeList();
                    mWeightList = item.getWeightList();
                    if (item.isOpenSelection()){
                        countType = 1;
                        openPopupWindow(view,item);
                    }else {
                        countType = 2;
                        ICartChangeItemPresenter presenter = new CartChangeItemPresenter(SearchListActivity.this);
                        Map<String,String> addCarts = new HashMap<>();
                        addCarts.put("count",String.valueOf(1));
                        addCarts.put("contentId",String.valueOf(item.getId()));

                        if (mWeightList != null && mWeightList.size() >0){
                            addCarts.put("weight",mWeightList.get(0).getName());
                        }else {
                            addCarts.put("weight",null);
                        }
                        if (mTypeList != null && mTypeList.size() >0){
                            addCarts.put("type",mTypeList.get(0).getName());
                        }else {
                            addCarts.put("type",null);

                        }
                        if (mColorList != null && mColorList.size() >0){
                            addCarts.put("color",mColorList.get(0).getName());
                        }else {
                            addCarts.put("color",null);

                        }
                        if (mSizeList != null && mSizeList.size() >0){
                            addCarts.put("size",mSizeList.get(0).getName());
                        }else {
                            addCarts.put("size",null);

                        }
                        if (mMaterialList != null && mMaterialList.size() >0){
                            addCarts.put("material",mMaterialList.get(0).getName());
                        }else {
                            addCarts.put("material",null);

                        }
                        addCarts.put("tag","0");
                        presenter.getChangeCountData(addCarts);
                    }
                }
            });
        }
    }

    @Override
    public void onSearchFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyData() {
        hideLoading();
        SwipeRefreshHelper.enableRefresh(search_swipe_refresh, false);
        search_swipe_refresh.setVisibility(View.GONE);
        empty_search.setVisibility(View.VISIBLE);

    }

    @Override
    public void loadMoreData(List<SearchListBean> data) {
        mAdapter.loadComplete();
        mAdapter.addItems(data);
    }

    @Override
    public void loadNoMoreData() {
        mAdapter.loadComplete();
        mAdapter.noMoreData();
    }
    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (search_swipe_refresh != null) {
            SwipeRefreshHelper.enableRefresh(search_swipe_refresh,true);
            SwipeRefreshHelper.init(search_swipe_refresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    upDateViews();
                    SwipeRefreshHelper.controlRefresh(search_swipe_refresh,false);
                }
            });
        }
    }

    private  SpinerPopWindow<CategryOneBean> pop;
    @Override
    public void onSucessFul(List<CategryOneBean> data) {
        if (data != null && data.size() >0){
            pop = new SpinerPopWindow<CategryOneBean>(this,data);
            pop.showPopupWindow(all_categry);
        }
    }

    @Override
    public void onFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private PopupWindow popupWindow;
    private int navigationHeight = 0;
    private void openPopupWindow(View v,SearchListBean item) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.goods_color_layout, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外退出
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        if (AndoridSysUtils.checkDeviceHasNavigationBar(this)){
            navigationHeight = AndoridSysUtils.getNavigationBarHeigh(this);
        }
        //设置显示的位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view,item);
        //设置背景透明度
        setBackgroundAlpha(0.5f);
    }
    private TextView goods_short_name;
    private  ImageView goods_img;
    private  TextView curreny_;
    private  TextView curreny_price;
    private  ImageView goods_close;
    private MyGridView item_color,item_size,item_weight,item_type,item_materi;
    private  ImageView add_goods_num;
    private  TextView goods_pop_num;
    private  ImageView minus_pop_num;
    private  TextView add_pop_cart;
    private LinearLayout item_color_layout,item_size_layout,item_weight_layout,item_type_layout,item_materi_layout;
    private TitleAdpater color_adater,sizeAdapter,weightAdapter,typeAdapter,materAdapter;
    private List<GoodsItem> colorItems,sizeItems,weightItems,typeItems,matreItems;
    private List<SkuBean> skuBeanList = new ArrayList<>();

    private void setOnPopupViewClick(View view,final SearchListBean item) {
        goods_short_name = view.findViewById(R.id.goods_short_name);
        goods_img = view.findViewById(R.id.goods_img);
        curreny_ = view.findViewById(R.id.curreny_);
        curreny_price = view.findViewById(R.id.curreny_price);
        goods_close = view.findViewById(R.id.goods_close);
        item_color  = view.findViewById(R.id.item_color);
        item_size  = view.findViewById(R.id.item_size);
        item_weight  = view.findViewById(R.id.item_weight);
        item_type  = view.findViewById(R.id.item_type);
        item_materi  = view.findViewById(R.id.item_materi);
        item_color_layout = view.findViewById(R.id.item_color_layout);
        item_size_layout = view.findViewById(R.id.item_size_layout);
        item_weight_layout = view.findViewById(R.id.item_weight_layout);
        item_type_layout = view.findViewById(R.id.item_type_layout);
        item_materi_layout = view.findViewById(R.id.item_materi_layout);
        add_goods_num = view.findViewById(R.id.add_goods_num);
        goods_pop_num = view.findViewById(R.id.goods_pop_num);
        minus_pop_num = view.findViewById(R.id.minus_pop_num);
        add_pop_cart = view.findViewById(R.id.add_pop_cart);
        goods_close.setOnClickListener(this);
        add_goods_num.setOnClickListener(this);
        minus_pop_num.setOnClickListener(this);
        add_pop_cart.setOnClickListener(this);
        if (item != null){
            goods_short_name.setText(item.getName());
            Glide.with(SearchListActivity.this).load(item.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(goods_img);
            curreny_.setText(item.getCurrency());
            curreny_price.setText(String.format("%.2f",  item.getCurrentPrice()));

            if (item.getColorList() != null && item.getColorList().size()>0){
                item_color_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < item.getColorList().size(); i++) {
                    item.getColorList().get(i).setState(1);
                }
                color_adater = new TitleAdpater(SearchListActivity.this,item.getColorList());
                item_color.setAdapter(color_adater);
                item_color.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                color_adater.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = item.getColorList().get(position);
                        switch (goodsItem.getState()){
                            case 0: //选中
                                colorItems = SelectSkuCalulator.clearAdapterStates(item.getColorList());
                                color_adater.notifyDataSetChanged();
                                break;
                            case  1 : //未选中
                                colorItems = SelectSkuCalulator.selectCalutor(item.getColorList(), position, 0);
                                color_adater.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
            if (item.getSizeList() != null && item.getSizeList().size() >0){
                item_size_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < item.getSizeList().size(); i++) {
                    item.getSizeList().get(i).setState(1);
                }
                sizeAdapter = new TitleAdpater(SearchListActivity.this,item.getSizeList());
                item_size.setAdapter(sizeAdapter);
                item_size.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                sizeAdapter.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = item.getSizeList().get(position);
                        switch (goodsItem.getState()){
                            case 0: //选中

                                sizeItems = SelectSkuCalulator.clearAdapterStates(item.getSizeList());
                                sizeAdapter.notifyDataSetChanged();
                                break;
                            case  1 : //未选中
                                sizeItems = SelectSkuCalulator.selectCalutor(item.getSizeList(), position, 0);
                                sizeAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }
                    }
                });
            }
            if (item.getWeightList() != null && item.getWeightList().size() >0){
                item_weight_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < item.getWeightList().size(); i++) {
                    item.getWeightList().get(i).setState(1);
                }
                weightAdapter = new TitleAdpater(SearchListActivity.this,item.getWeightList());
                item_weight.setAdapter(weightAdapter);
                item_weight.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                weightAdapter.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = item.getWeightList().get(position);
                        switch (goodsItem.getState()){
                            case 0:
                                weightItems = SelectSkuCalulator.clearAdapterStates(item.getWeightList());
                                weightAdapter.notifyDataSetChanged();
                                break;
                            case  1 :
                                weightItems = SelectSkuCalulator.selectCalutor(item.getWeightList(), position, 0);
                                weightAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }
                    }
                });
            }
            if (item.getMaterialList() != null && item.getMaterialList().size() >0){
                item_materi_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < item.getMaterialList().size(); i++) {
                    item.getMaterialList().get(i).setState(1);
                }

                materAdapter = new TitleAdpater(SearchListActivity.this,item.getMaterialList());
                item_materi.setAdapter(materAdapter);
                item_materi.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                materAdapter.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = item.getMaterialList().get(position);
                        switch (goodsItem.getState()){
                            case  0:

                                matreItems = SelectSkuCalulator.clearAdapterStates(item.getMaterialList());
                                materAdapter.notifyDataSetChanged();
                                break;
                            case  1 :

                                matreItems = SelectSkuCalulator.selectCalutor(item.getMaterialList(), position, 0);
                                materAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }
                    }
                });
            }
            if (item.getTypeList() != null && item.getTypeList().size() >0){
                item_type_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < item.getTypeList().size(); i++) {
                    item.getTypeList().get(i).setState(1);
                }

                typeAdapter = new TitleAdpater(SearchListActivity.this,item.getTypeList());
                item_type.setAdapter(typeAdapter);
                item_type.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                typeAdapter.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = item.getTypeList().get(position);
                        String type = goodsItem.getName();
                        String raisePrice = goodsItem.getRaisePrice();
                        switch (goodsItem.getState()){
                            case 0:

                                typeItems = SelectSkuCalulator.clearAdapterStates(item.getTypeList());
                                typeAdapter.notifyDataSetChanged();
                                break;
                            case  1 :

                                typeItems = SelectSkuCalulator.selectCalutor(item.getTypeList(), position, 0);
                                typeAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }
                    }
                });

            }
        }
    }
    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this. getWindow().setAttributes(lp);
    }
    private String color,size,weight,type,matre;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goods_close: //关闭弹出窗
                if (popupWindow != null &&popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                break;
            case R.id.add_goods_num: //增加数量
                String count = goods_pop_num.getText().toString();
                int _count = Integer.valueOf(count);
                goods_pop_num.setText(String.valueOf(_count+1));
                break;
            case  R.id.minus_pop_num: //减少数量
                String minus_count = goods_pop_num.getText().toString();
                int minusCount = Integer.valueOf(minus_count);
                if (minusCount <= 1){
                    Toast.makeText(SearchListActivity.this,"已经是最少数量，无法再减",Toast.LENGTH_SHORT).show();
                    return;
                }
                goods_pop_num.setText(String.valueOf(minusCount-1));
                break;
            case R.id.add_pop_cart: //添加购物车
                int select_count = 0;
                if (skuBeanList != null && skuBeanList.size()>0){
                    skuBeanList.clear();
                }
                if (mColorList != null && mColorList .size() > 0){
                    select_count++;
                    for (int i = 0; i < colorItems.size(); i++) {
                        if (colorItems.get(i).getState() == 0){
                            String  color_Name = colorItems.get(i).getName();
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setName(color_Name);
                            skuBean.setId(1);
                            skuBean.setPrice(colorItems.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (mSizeList != null && mSizeList .size() > 0){
                    select_count++;
                    for (int i = 0; i < mSizeList.size(); i++) {
                        if (mSizeList.get(i).getState() == 0){
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setId(2);
                            skuBean.setName(mSizeList.get(i).getName());
                            skuBean.setPrice(mSizeList.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (mWeightList != null && mWeightList .size() > 0){
                    select_count++;
                    for (int i = 0; i < mWeightList.size(); i++) {
                        if (mWeightList.get(i).getState() == 0){
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setId(3);
                            skuBean.setName(mWeightList.get(i).getName());
                            skuBean.setPrice(mWeightList.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (mTypeList != null && mTypeList .size() > 0){
                    select_count++;
                    for (int i = 0; i < mTypeList.size(); i++) {
                        if (mTypeList.get(i).getState() == 0){
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setId(4);
                            skuBean.setName(mTypeList.get(i).getName());
                            skuBean.setPrice(mTypeList.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (mMaterialList != null && mMaterialList .size() > 0){
                    select_count++;
                    for (int i = 0; i < mMaterialList.size(); i++) {
                        if (mMaterialList.get(i).getState() == 0){
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setId(5);
                            skuBean.setName(mMaterialList.get(i).getName());
                            skuBean.setPrice(mMaterialList.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (null != skuBeanList && skuBeanList.size() > 0){
                    if ( skuBeanList.size() == select_count){
                        for (int i = 0; i < skuBeanList.size(); i++) {
                            if (skuBeanList.get(i).getId() == 1){
                                color =  skuBeanList.get(i).getName();
                            }else if (skuBeanList.get(i).getId() == 2){
                                size = skuBeanList.get(i).getName();
                            }else if (skuBeanList.get(i).getId() == 3){
                                weight = skuBeanList.get(i).getName();
                            }else if (skuBeanList.get(i).getId() ==4){
                                type = skuBeanList.get(i).getName();
                            }else if (skuBeanList.get(i).getId() == 5){
                                matre = skuBeanList.get(i).getName();
                            }
                        }
                        ICartChangeItemPresenter presenter = new CartChangeItemPresenter(this);
                        String goods_num = goods_pop_num.getText().toString();
                        Map<String,String> addCarts = new HashMap<>();
                        if (!StringUtils.isEmpty(goods_num)){
                            addCarts.put("count",goods_num);
                        }
                        addCarts.put("contentId",String.valueOf(mItem.getId()));
                        if (!StringUtils.isEmpty(weight)){
                            addCarts.put("weight",weight);
                        }else {
                            addCarts.put("weight",null);
                        }
                        if (!StringUtils.isEmpty(type)){
                            addCarts.put("type",type);
                        }else {
                            addCarts.put("type",null);
                        }
                        if (!StringUtils.isEmpty(color)){
                            addCarts.put("color",color);
                        }else {
                            addCarts.put("color",null);
                        }
                        if (!StringUtils.isEmpty(size)){
                            addCarts.put("size",size);
                        }else {
                            addCarts.put("size",null);
                        }
                        if (!StringUtils.isEmpty(matre)){
                            addCarts.put("material",matre);
                        }else {
                            addCarts.put("material",null);
                        }
                        addCarts.put("tag","0");
                        presenter.getChangeCountData(addCarts);
                    }else {
                        Toast.makeText(SearchListActivity.this,"还有规格未选择",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(SearchListActivity.this,"请选择规格",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                break;

        }
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

    @Override
    public void onChangeItemSucessful(CartIdBean data) {
        Toast.makeText(SearchListActivity.this,"添加购物车成功",Toast.LENGTH_SHORT).show();
        if (popupWindow != null &&popupWindow.isShowing()){
            popupWindow.dismiss();
        }
        search_cart_num.setVisibility(View.VISIBLE);
        if (countType == 1){
            search_cart_num.setText(String.valueOf(Integer.valueOf(search_cart_num.getText().toString())+Integer.valueOf(goods_pop_num.getText().toString())));
        }else {
            search_cart_num.setText(String.valueOf(Integer.valueOf(search_cart_num.getText().toString())+1));
        }
        int[] loc = new int[2];
        mView.getLocationInWindow(loc);
        playAnimation(loc);
    }

    @Override
    public void onChangeItemFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(SearchListActivity.this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }
    /**
     * 动画
     * @param position
     */
    public   void playAnimation(int[] position){
        final ImageView mImg = new ImageView(SearchListActivity.this);
        mImg.setImageResource(R.mipmap.select);
        mImg.setScaleType(ImageView.ScaleType.MATRIX);
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.addView(mImg);
        int[] des = new int[2];
        goods_car_img.getLocationInWindow(des);
        /*动画开始位置，也就是物品的位置;动画结束的位置，也就是购物车的位置 */
        Point startPosition = new Point(position[0], position[1]);
        Point endPosition = new Point(des[0]+goods_car_img.getWidth()/2, des[1]+goods_car_img.getHeight()/2);
        int pointX = (startPosition.x + endPosition.x) / 2 + 100;
        int pointY = startPosition.y + 200;
        Point controllPoint = new Point(pointX, pointY);
        /**
         * 属性动画，依靠TypeEvaluator来实现动画效果，其中位移，缩放，渐变，旋转都是可以直接使用 *
         这里是自定义了TypeEvaluator， 我们通过point记录运动的轨迹，然后，物品随着轨迹运动，
         一旦轨迹发生变化，就会调用addUpdateListener这个方法，我们不断的获取新的位置，是物品移动
         */
        ValueAnimator valueAnimator = ValueAnimator.ofObject(
                new SearchListActivity.BizierEvaluator2(controllPoint),startPosition, endPosition);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                mImg.setX(point.x);
                mImg.setY(point.y);
            }
        });

        /** * 动画结束，移除掉小圆圈 */
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup rootView = (ViewGroup) SearchListActivity.this.getWindow().getDecorView();
                rootView.removeView(mImg);
            }
        });
    }

    /** * 贝塞尔曲线（二阶抛物线） * controllPoint 是中间的转折点 * startValue 是起始的位置 * endValue 是结束的位置 */
    public class BizierEvaluator2 implements TypeEvaluator<Point> {
        private Point controllPoint;
        public BizierEvaluator2(Point controllPoint) {
            this.controllPoint = controllPoint;
        }
        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x);
            int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y);
            return new Point(x, y);
        }
    }
}
