package com.koalafield.cmart.ui.activity.search;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.BrandAdapter;
import com.koalafield.cmart.adapter.BrandCategryAdapter;
import com.koalafield.cmart.adapter.CategryGoodsAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.categry.BrandListBean;
import com.koalafield.cmart.bean.categry.CateBrandGoodsListBean;
import com.koalafield.cmart.bean.categry.CateListBean;
import com.koalafield.cmart.bean.categry.GoodsListBean;
import com.koalafield.cmart.presenter.categry.CategryBrandPresenter;
import com.koalafield.cmart.presenter.categry.ICategryBrandPresenter;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.view.categry.ICategryListView;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.IndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class GoodsListActivity extends BaseActivity implements ICategryListView<CateBrandGoodsListBean> {

    private ICategryBrandPresenter mPresenter;
    private  int mCateId;
    private  int mBrandId;
    //头部布局
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_words)
    EditText search_words;
    @BindView(R.id.search_cart_num)
    TextView search_cart_num;

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

    @BindView(R.id.brand_swipe_refresh)
    SwipeRefreshLayout brand_swipe_refresh;


    @Override
    public int attchLayoutRes() {
        return R.layout.goods_list_categry;
    }

    @Override
    public void initDatas() {
        mCateId = getIntent().getIntExtra("cateId",-1);
    }

    @Override
    public void upDateViews() {
        mPresenter = new CategryBrandPresenter(this);
        Map<String,String> params = new HashMap<>();
        params.put("cateId",String.valueOf(mCateId));
        params.put("brandId",String.valueOf(mBrandId));
        mPresenter.setCategryBrandData(params);
        mPresenter.getData();
    }

    private  BrandAdapter mAdapter;
    private BrandCategryAdapter mCategryAdapter;
    private CategryGoodsAdapter goodsAdapter;
    private List<CateListBean> mCates;
    @Override
    public void onCategryBrandSucessFul(CateBrandGoodsListBean data) {
        if (data != null){
            final  List<CateListBean> cateList = data.getCateList();
            if (cateList != null && cateList.size() > 0){
                collapsing_tool_bar_test_ctl.setVisibility(View.VISIBLE);

                for (int i = 0; i < cateList.size(); i++) {
                    if (i == 0){
                        cateList.get(i).setSelect(true);
                    }else {
                        cateList.get(i).setSelect(false);
                    }
                }
                 mCates = cateList;
                 mAdapter = new BrandAdapter(this,mCates);
                 RecyclerViewHelper.initRecyclerViewH(GoodsListActivity.this,brand_list,false,mAdapter);
                 mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        for (int i = 0; i < mCates.size(); i++) {
                            if (mCates.get(i).getId() == mCates.get(position).getId()){
                                mCates.get(i).setSelect(true);
                            }else {
                                mCates.get(i).setSelect(false);
                            }
                        }
                        mAdapter.updateItems(mCates);
                        for (int i = 0; i < mCates.size(); i++) {
                            if (mCates.get(i).isSelect()){
                                mCateId = mCates.get(i).getId();
                            }
                        }
                        requestData();
                    }
                });
            }else {
                collapsing_tool_bar_test_ctl.setVisibility(View.GONE);
            }
             //品牌下分类
            final List<BrandListBean> brandList = data.getBrandList();
            if (brandList != null && brandList.size() >0){
                categry_list.setVisibility(View.VISIBLE);
                for (int i = 0; i < brandList.size(); i++) {
                    if (i == 0){
                        brandList.get(i).setSelect(true);
                    }else {
                        brandList.get(i).setSelect(false);
                    }
                }
                mCategryAdapter = new BrandCategryAdapter(this,brandList);
                RecyclerViewHelper.initRecyclerViewH(GoodsListActivity.this,categry_list,false,mCategryAdapter);
                mCategryAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        for (int i = 0; i < brandList.size(); i++) {
                            if (brandList.get(i).getId() == cateList.get(position).getId()){
                                brandList.get(i).setSelect(true);
                            }else {
                                brandList.get(i).setSelect(false);
                            }
                        }
                        mCategryAdapter.updateItems(brandList);
                        for (int i = 0; i < brandList.size(); i++) {
                            if (brandList.get(i).isSelect()){
                                mBrandId = brandList.get(i).getId();
                            }
                        }
                        requestData();
                    }
                });

            }else {
                categry_list.setVisibility(View.GONE);
            }
        }
        //分类下的商品
        final List<GoodsListBean> goodsList = data.getGoodsList();
        if (goodsList != null && goodsList.size() >0){
            goodsAdapter = new CategryGoodsAdapter(this,goodsList);
            RecyclerViewHelper.initRecyclerViewG(GoodsListActivity.this,goods_list,false,goodsAdapter,2);
            goodsAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    GoodsListBean goodsListBean = goodsList.get(position);
                    Intent intent = new Intent(GoodsListActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("contentId",goodsListBean.getId());
                    startActivity(intent);
                }
            });
        }else {
            brand_swipe_refresh.setVisibility(View.GONE);
            SwipeRefreshHelper.enableRefresh(brand_swipe_refresh,false);

        }
    }

    private void requestData(){
        Map<String,String> params = new HashMap<>();
        params.put("cateId",String.valueOf(mCateId));
        params.put("brandId",String.valueOf(mBrandId));
        mPresenter.setCategryBrandData(params);
        mPresenter.getData();
    }

    @Override
    public void onCategryBrandFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyData() {

    }

    @Override
    public void loadMoreData(CateBrandGoodsListBean data) {

    }


    @Override
    public void loadNoMore() {

    }
}
