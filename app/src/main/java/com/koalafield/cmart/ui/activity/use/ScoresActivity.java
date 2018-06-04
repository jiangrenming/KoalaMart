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
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.ScoresAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.ScoreBean;
import com.koalafield.cmart.presenter.user.IScorePresenter;
import com.koalafield.cmart.presenter.user.ScorePresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.view.user.IScoresView;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import java.util.List;
import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/23
 * 买过商品列表
 */

public class ScoresActivity extends BaseActivity implements IScoresView<List<ScoreBean>> {


    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.score_swipe_refresh)
    SwipeRefreshLayout score_swipe_refresh;
    @BindView(R.id.score_list)
    RecyclerView score_list;
    @BindView(R.id.empty_score)
    LinearLayout empty_score;

    private ScoresAdapter scoreAdapter;
    private IScorePresenter scroePresenter;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_score;
    }

    @Override
    public void initDatas() {
        top_name.setText("积分历史");
        initSwipeRefresh();
    }

    @Override
    public void upDateViews() {
        scroePresenter = new ScorePresenter(this);
        scroePresenter.getData();
    }


    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (score_swipe_refresh != null) {
            SwipeRefreshHelper.enableRefresh(score_swipe_refresh,true);
            SwipeRefreshHelper.init(score_swipe_refresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    upDateViews();
                    SwipeRefreshHelper.controlRefresh(score_swipe_refresh,false);
                }
            });
        }
    }

    @Override
    public void onScoresSucessFul(List<ScoreBean> data) {
        if (data != null &&data.size() >0){
            if (scoreAdapter == null){
                scoreAdapter = new ScoresAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewV(this,score_list,true,scoreAdapter);
            }else {
                scoreAdapter.cleanItems();
                scoreAdapter.addItems(data);
            }
        }
        scoreAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                scroePresenter.getMoreData();
            }
        });

    }

    @Override
    public void onScoresFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(ScoresActivity.this, LoginActivity.class);
    //        intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    @Override
    public void loadScoresEmptyData() {
        SwipeRefreshHelper.controlRefresh(score_swipe_refresh,false);
        hideLoading();
        score_swipe_refresh.setVisibility(View.GONE);
        empty_score.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadScoresNoMoreData() {
        scoreAdapter.loadComplete();
        scoreAdapter.noMoreData();
    }

    @Override
    public void loadScoresMoreData(List<ScoreBean> data) {
        scoreAdapter.loadComplete();
        scoreAdapter.addItems(data);
    }

}
