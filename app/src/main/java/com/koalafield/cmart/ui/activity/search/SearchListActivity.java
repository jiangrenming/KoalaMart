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
import android.view.WindowManager;
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
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.bean.search.SearchListBean;
import com.koalafield.cmart.presenter.cart.CartPresenter;
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
import com.koalafield.cmart.ui.view.cart.ICartVIew;
import com.koalafield.cmart.ui.view.categry.ICategryTwoView;
import com.koalafield.cmart.ui.view.categry.ICategryView;
import com.koalafield.cmart.ui.view.search.ISearchView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.EmptyLayout;
import com.koalafield.cmart.widget.PriceUpDownView;
import com.koalafield.cmart.widget.SaleUpDownView;
import com.koalafield.cmart.widget.SpinerPopWindow;

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

public class SearchListActivity extends BaseActivity implements ICartVIew<CartNumberBean>,ISearchView<List<SearchListBean>>,ICategryView<List<CategryOneBean>> {

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
//                    intent.putExtra("type",3);
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
    //        intent.putExtra("type",3);
            startActivity(intent);
        }
    }

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
}
