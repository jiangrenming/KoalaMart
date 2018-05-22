package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRemoveDataListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CollectionAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.bean.goods.GoodsCollectionsBean;
import com.koalafield.cmart.presenter.goods.GoodsCollectionDelPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsCollectionDelPresenter;
import com.koalafield.cmart.presenter.user.CollectionPresenter;
import com.koalafield.cmart.presenter.user.ICollectionPresenter;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.view.goods.IGoodsCollectionDeleteView;
import com.koalafield.cmart.ui.view.user.ICollectionView;
import com.koalafield.cmart.utils.SwipeRefreshHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/22
 */

public class CollectionActivity extends BaseActivity implements ICollectionView<List<GoodsCollectionsBean>>,IGoodsCollectionDeleteView<BaseResponseBean> {

    @BindView(R.id.collection_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.collection_list)
    RecyclerView collection_list;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.empty_collection)
    LinearLayout empty_collection;
    private CollectionAdapter collectionAdapter;
    private  ICollectionPresenter collectionPresenter;
    private GoodsCollectionsBean mCollections;


    @Override
    public int attchLayoutRes() {
        return R.layout.activity_collection;
    }

    @Override
    public void initDatas() {
        top_name.setText("收藏列表");
        initSwipeRefresh();
    }

    @Override
    public void upDateViews() {
        collectionPresenter = new CollectionPresenter(this);
        collectionPresenter.getData();
    }

    @OnClick(R.id.back)
    public  void onCollectionClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (swipeRefreshLayout != null) {
            SwipeRefreshHelper.enableRefresh(swipeRefreshLayout,true);
            SwipeRefreshHelper.init(swipeRefreshLayout, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    upDateViews();
                    SwipeRefreshHelper.controlRefresh(swipeRefreshLayout,false);
                }
            });
        }
    }

    @Override
    public void onCollectionSucessFul(final List<GoodsCollectionsBean> data) {
        if (data != null && data.size() >0){
            if (collectionAdapter == null){
                collectionAdapter = new CollectionAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewV(this,collection_list,true,collectionAdapter);
            }else {
                collectionAdapter.cleanItems();
                collectionAdapter.addItems(data);
            }

            collectionAdapter.setRequestDataListener(new OnRequestDataListener() {
                @Override
                public void onLoadMore() {
                    collectionPresenter.getMoreData();
                }
            });
            collectionAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.i("点击的位置：",position+"");
                    GoodsCollectionsBean goodsCollectionsBean = data.get(position);
                    Intent intent = new Intent(CollectionActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("contentId",goodsCollectionsBean.getId());
                    startActivity(intent);
                }
            });
            //删除收藏
            collectionAdapter.setDelCollectionCallBack(new CollectionAdapter.DelCollectionCallBack() {
                @Override
                public void onSucessCollect(GoodsCollectionsBean item) {
                    mCollections = item;
                    Map<String,String> params = new HashMap<>();
                    params.put("contentId",String.valueOf(item.getId()));
                    IGoodsCollectionDelPresenter collectionDel = new GoodsCollectionDelPresenter(CollectionActivity.this);
                    collectionDel.getDetails(params);
                }
            });
        }
    }

    @Override
    public void onCollectionFailure(String message) {
        Log.i("收藏的异常:",message);
    }

    @Override
    public void loadCollectionEmptyData() {
        SwipeRefreshHelper.controlRefresh(swipeRefreshLayout,false);
        hideLoading();
        swipeRefreshLayout.setVisibility(View.GONE);
        empty_collection.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadCollectionNoMoreData() {
        collectionAdapter.loadComplete();
        collectionAdapter.noMoreData();
    }


    @Override
    public void loadCollectionMoreData(List<GoodsCollectionsBean> data) {
        Log.i("加载更多的数据:",data.size()+"");
        collectionAdapter.loadComplete();
        collectionAdapter.addItems(data);
    }

    @Override
    public void onGoodsCollectionDelSucessFul(BaseResponseBean data) {
        if (data != null && data.getCode() ==200){
            Toast.makeText(CollectionActivity.this,"取消收藏成功",Toast.LENGTH_SHORT).show();
            collectionAdapter.removeItem(mCollections);
        }
    }

    @Override
    public void onGoodsCollectionDelFailure(String message) {

    }
}
