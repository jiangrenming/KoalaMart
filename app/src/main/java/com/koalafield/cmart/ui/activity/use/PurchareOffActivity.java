package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CollectionAdapter;
import com.koalafield.cmart.adapter.PurchareOffAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.PurchaseOffBean;
import com.koalafield.cmart.presenter.cart.ICartClearPresenter;
import com.koalafield.cmart.presenter.user.IPurcashOffPresenter;
import com.koalafield.cmart.presenter.user.PurcashOffPresenter;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.view.user.IPurchaseOffView;
import com.koalafield.cmart.utils.SwipeRefreshHelper;

import java.util.List;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/23
 * 买过商品列表
 */

public class PurchareOffActivity extends BaseActivity implements IPurchaseOffView<List<PurchaseOffBean>>{


    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.puroff_swipe_refresh)
    SwipeRefreshLayout puroff_swipe_refresh;
    @BindView(R.id.puroff_list)
    RecyclerView puroff_list;
    @BindView(R.id.empty_puroff)
    LinearLayout empty_puroff;


    private PurchareOffAdapter mPurchareAdapter;
    private  IPurcashOffPresenter purcashOffPresenter;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_purshare;
    }

    @Override
    public void initDatas() {
        top_name.setText("买过列表");
        initSwipeRefresh();
    }

    @Override
    public void upDateViews() {
         purcashOffPresenter = new PurcashOffPresenter(this);
         purcashOffPresenter.getData();
    }

    @Override
    public void onPurchaseOffSucessFul(final List<PurchaseOffBean> data) {
        if (data != null &&data.size() >0){
            if (mPurchareAdapter == null){
                mPurchareAdapter = new PurchareOffAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewV(this,puroff_list,true,mPurchareAdapter);
            }else {
                mPurchareAdapter.cleanItems();
                mPurchareAdapter.addItems(data);
            }
        }
        mPurchareAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                purcashOffPresenter.getMoreData();
            }
        });
        mPurchareAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PurchareOffActivity.this, GoodsDetailActivity.class);
                intent.putExtra("contentId",data.get(position).getId());
                startActivity(intent);
            }
        });
        mPurchareAdapter.setAddCartCallBack(new PurchareOffAdapter.AddCartCallBack() {
            @Override
            public void onSucessCollect(PurchaseOffBean item) {
                //加入购物车

            }
        });
    }

    @Override
    public void onPurchaseOffFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadPurchaseOffEmptyData() {
        SwipeRefreshHelper.controlRefresh(puroff_swipe_refresh,false);
        hideLoading();
        puroff_swipe_refresh.setVisibility(View.GONE);
        empty_puroff.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadPurchaseOffNoMoreData() {
        mPurchareAdapter.loadComplete();
        mPurchareAdapter.noMoreData();
    }

    @Override
    public void loadPurchaseOffMoreData(List<PurchaseOffBean> data) {
        mPurchareAdapter.loadComplete();
        mPurchareAdapter.addItems(data);
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (puroff_swipe_refresh != null) {
            SwipeRefreshHelper.enableRefresh(puroff_swipe_refresh,true);
            SwipeRefreshHelper.init(puroff_swipe_refresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    upDateViews();
                    SwipeRefreshHelper.controlRefresh(puroff_swipe_refresh,false);
                }
            });
        }
    }
}
