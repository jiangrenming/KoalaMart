package com.koalafield.cmart.ui.activity.goods;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CommentAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.goods.CommentDatas;
import com.koalafield.cmart.presenter.goods.GoodsCommentPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsCommentPresenter;
import com.koalafield.cmart.ui.view.goods.IGoodsCommentView;
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

public class GoodsCommentActivity extends BaseActivity implements IGoodsCommentView<List<CommentDatas>>{


    @BindView(R.id .top_name)
    TextView top_name;
    @BindView(R.id .back)
    ImageView back;
    @BindView(R.id .comment_list)
    RecyclerView comment_list;
    @BindView(R.id.comment_swipe_refresh)
    SwipeRefreshLayout comment_swipe_refresh;
    @BindView(R.id.empty_comment)
    LinearLayout empty_comment;

    private   IGoodsCommentPresenter presenter;
    private int contentId;
    private CommentAdapter mAdapter;
    private  int pageIndex =0;

    @Override
    public int attchLayoutRes() {
        return R.layout.layout_comment;
    }

    @Override
    public void initDatas() {
        top_name.setText("评论列表");
        contentId = getIntent().getIntExtra("contentId",-1);
        initSwipeRefresh();
    }

    @Override
    public void upDateViews() {
        Map<String,String> params = new HashMap<>();
        params.put("pageIndex",String.valueOf(pageIndex));
        params.put("contentId",String.valueOf(contentId));
        presenter = new GoodsCommentPresenter(this);
        presenter.getComments(params);
    }

    @OnClick(R.id.back)
    public  void onCommentClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public void onGoodsCommentSucessFul(List<CommentDatas> data) {
        if (data != null && data.size() >0){
            pageIndex ++;
            if (mAdapter == null){
                mAdapter = new CommentAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewV(this,comment_list,true,mAdapter);
            }else {
                mAdapter.cleanItems();
                mAdapter.addItems(data);
            }
            mAdapter.setRequestDataListener(new OnRequestDataListener() {
                @Override
                public void onLoadMore() {
                    Map<String,String> params = new HashMap<>();
                    params.put("pageIndex",String.valueOf(pageIndex));
                    params.put("contentId",String.valueOf(contentId));
                    presenter.getMoreComments(params);
                }
            });
        }

    }

    @Override
    public void onGoodsCommentFailure(String message) {
        Log.i("评论的异常:",message);
    }

    @Override
    public void onLoadEmptyData() {
        SwipeRefreshHelper.controlRefresh(comment_swipe_refresh,false);
        hideLoading();
        comment_swipe_refresh.setVisibility(View.GONE);
        empty_comment.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadNoMoreData() {
        mAdapter.loadComplete();
        mAdapter.noMoreData();
    }

    @Override
    public void onLoadMoreData(List<CommentDatas> data) {
        Log.i("加载更多的数据",data.size()+"");
        mAdapter.loadComplete();
        mAdapter.addItems(data);
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (comment_swipe_refresh != null) {
            SwipeRefreshHelper.enableRefresh(comment_swipe_refresh,true);
            SwipeRefreshHelper.init(comment_swipe_refresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pageIndex = 0;
                    upDateViews();
                    SwipeRefreshHelper.controlRefresh(comment_swipe_refresh,false);
                }
            });
        }
    }
}
