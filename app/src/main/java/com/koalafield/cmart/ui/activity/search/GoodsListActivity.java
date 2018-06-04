package com.koalafield.cmart.ui.activity.search;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.BrandAdapter;
import com.koalafield.cmart.adapter.BrandCategryAdapter;
import com.koalafield.cmart.adapter.CategryGoodsAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.categry.BrandListBean;
import com.koalafield.cmart.bean.categry.CateBrandGoodsListBean;
import com.koalafield.cmart.bean.categry.CateListBean;
import com.koalafield.cmart.bean.categry.GoodsListBean;
import com.koalafield.cmart.presenter.cart.CartPresenter;
import com.koalafield.cmart.presenter.cart.ICartPresenter;
import com.koalafield.cmart.presenter.categry.CategryBrandPresenter;
import com.koalafield.cmart.presenter.categry.ICategryBrandPresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.goods.CartShoppingActivity;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.activity.goods.SearchActivity;
import com.koalafield.cmart.ui.view.cart.ICartVIew;
import com.koalafield.cmart.ui.view.categry.ICategryListView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.IndicatorView;
import com.koalafield.cmart.zoomswifresh.GZoomSwifrefresh;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.PUT;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class GoodsListActivity extends BaseActivity implements ICategryListView<CateBrandGoodsListBean>,ICartVIew<CartNumberBean> {

    private ICategryBrandPresenter mPresenter;
    private int mCateId;
    private int mBrandId;
    //头部布局
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_words)
    TextView search_words;
    @BindView(R.id.search_cart_num)
    TextView search_cart_num;
    @BindView(R.id.goods_car_img)
    ImageView goods_car_img;

    @BindView(R.id.collapsing_tool_bar_test_ctl)
    CollapsingToolbarLayout collapsing_tool_bar_test_ctl;

    //横排品牌布局
    @BindView(R.id.brand_list)
    RecyclerView brand_list;
    //横排品牌下的分类
    @BindView(R.id.categry_list)
    RecyclerView categry_list;
    //每个分类下的商品列表
    @BindView(R.id.goods_list)
    RecyclerView goods_list;

    @BindView(R.id.swiperefresh)
    GZoomSwifrefresh swiperefresh;




    @Override
    public int attchLayoutRes() {
        return R.layout.goods_list_categry;
    }

    @Override
    public void initDatas() {
        mCateId = getIntent().getIntExtra("cateId", -1);
        initRefreshLayout();
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (!StringUtils.isEmpty(tickets)){
            ICartPresenter mPresenter = new CartPresenter(GoodsListActivity.this);
            mPresenter.getData();
        }
    }

    @Override
    public void upDateViews() {
        mPresenter = new CategryBrandPresenter(this);
        Map<String, String> params = new HashMap<>();
        params.put("cateId", String.valueOf(mCateId));
        params.put("brandId", String.valueOf(mBrandId));
        mPresenter.setCategryBrandData(params);
        mPresenter.getData();
    }

    @OnClick({R.id.search_words,R.id.back,R.id.goods_car_img})
    public  void clickGoodsList(View view){
        switch (view.getId()){
            case R.id.search_words:
                startActivity(new Intent(GoodsListActivity.this, SearchActivity.class));
                break;
            case  R.id.back:
                finish();
                break;
            case R.id.goods_car_img:
                String tickets = ShareBankPreferenceUtils.getString("tickets", null);
                if (!StringUtils.isEmpty(tickets)){
                    startActivity(new Intent(GoodsListActivity.this, CartShoppingActivity.class));
                }else {
                    Intent intent = new Intent(GoodsListActivity.this, LoginActivity.class);
   //                 intent.putExtra("type",3);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private BrandAdapter mAdapter;
    private BrandCategryAdapter mCategryAdapter;
    private CategryGoodsAdapter goodsAdapter;
    private boolean isClickCategry = true;
    private boolean isClickBrand = true;

    @Override
    public void onCategryBrandSucessFul(CateBrandGoodsListBean data) {
        if (data != null) {
            if (isClickCategry) {
                final List<CateListBean> cateList = data.getCateList();
                if (cateList != null && cateList.size() > 0) {
                    for (int i = 0; i < cateList.size(); i++) {
                        if (mCateId == cateList.get(i).getId()){
                            cateList.get(i).setSelect(true);
                        }else {
                            cateList.get(i).setSelect(false);
                        }
                    }
                    collapsing_tool_bar_test_ctl.setVisibility(View.VISIBLE);
                    mAdapter = new BrandAdapter(this, cateList);
                    RecyclerViewHelper.initRecyclerViewH(GoodsListActivity.this, brand_list, false, mAdapter);
                    mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            isClickCategry = false;
                            isClickBrand = true;
                            mCateId = cateList.get(position).getId();
                            for (int i = 0; i < cateList.size(); i++) {
                                if (cateList.get(i).getId() == cateList.get(position).getId()) {
                                    cateList.get(i).setSelect(true);
                                } else {
                                    cateList.get(i).setSelect(false);
                                }
                            }
                            mAdapter.updateItems(cateList);
                            requestData();
                        }
                    });
                } else {
                    collapsing_tool_bar_test_ctl.setVisibility(View.GONE);
                }
            }
            //品牌下分类
            if (isClickBrand) {
                final List<BrandListBean> brandList = data.getBrandList();
                if (brandList != null && brandList.size() > 0) {
                    categry_list.setVisibility(View.VISIBLE);
                    for (int i = 0; i < brandList.size(); i++) {
                        if (i == 0) {
                            brandList.get(i).setSelect(true);
                        } else {
                            brandList.get(i).setSelect(false);
                        }
                    }
                    mCategryAdapter = new BrandCategryAdapter(this, brandList);
                    RecyclerViewHelper.initRecyclerViewH(GoodsListActivity.this, categry_list, false, mCategryAdapter);
                    mCategryAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            mBrandId = brandList.get(position).getId();
                            isClickBrand = false;
                            for (int i = 0; i < brandList.size(); i++) {
                                if (brandList.get(i).getId() == brandList.get(position).getId()) {
                                    brandList.get(i).setSelect(true);
                                } else {
                                    brandList.get(i).setSelect(false);
                                }
                            }
                            mCategryAdapter.updateItems(brandList);
                            requestData();
                        }
                    });
                } else {
                    categry_list.setVisibility(View.GONE);
                }
            }
        }

        //分类下的商品
        final List<GoodsListBean> goodsList = data.getGoodsList();
        if (goodsList != null && goodsList.size() > 0) {
            if (goodsAdapter == null) {
                goodsAdapter = new CategryGoodsAdapter(this, goodsList);
                RecyclerViewHelper.initRecyclerViewG(GoodsListActivity.this, goods_list, false, goodsAdapter, 2);
            } else {
                goodsAdapter.cleanItems();
                goodsAdapter.addItems(goodsList);
            }
            goodsAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    GoodsListBean goodsListBean = goodsList.get(position);
                    Intent intent = new Intent(GoodsListActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("contentId", goodsListBean.getId());
                    startActivity(intent);
                }
            });
            goodsAdapter.setRequestDataListener(new OnRequestDataListener() {
                @Override
                public void onLoadMore() {
                    Map<String, String> params = new HashMap<>();
                    params.put("cateId", String.valueOf(mCateId));
                    params.put("brandId", String.valueOf(mBrandId));
                    mPresenter.setCategryBrandData(params);
                    mPresenter.getMoreData();
                }
            });
        }
    }

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("cateId", String.valueOf(mCateId));
        params.put("brandId", String.valueOf(mBrandId));
        mPresenter.setCategryBrandData(params);
        mPresenter.getData();
    }

    @Override
    public void onCategryBrandFailure(String message,int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyData() {

    }

    @Override
    public void loadMoreData(CateBrandGoodsListBean data) {
        goodsAdapter.loadComplete();
        goodsAdapter.addItems(data.getGoodsList());
    }


    @Override
    public void loadNoMore() {
        goodsAdapter.loadComplete();
        goodsAdapter.noMoreData();
    }

    /**
     * 初始化刷新控件
     */
    private void initRefreshLayout() {
        swiperefresh.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE);
        swiperefresh.setOnRefreshListener(new GZoomSwifrefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
                cancleLoading();
            }
        });
    }
    /**取消加载*/
    public void cancleLoading() {
        if(swiperefresh.isRefreshing()){
            swiperefresh.setRefreshing(false);
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
    public void onNumberFailure(String message,int code) {
        Toast.makeText(GoodsListActivity.this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(GoodsListActivity.this, LoginActivity.class);
    //        intent.putExtra("type",3);
            startActivity(intent);
        }
    }
}
