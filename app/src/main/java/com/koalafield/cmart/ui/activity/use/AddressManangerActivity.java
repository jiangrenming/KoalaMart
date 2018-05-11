package com.koalafield.cmart.ui.activity.use;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRemoveDataListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.AddressManagerAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 * 地址管理界面
 */

public class AddressManangerActivity extends BaseActivity {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;
    @BindView(R.id.rv_news_list)
    RecyclerView rv_news_list;
    @BindView(R.id.add_address)
    TextView add_address;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;

    private BaseQuickAdapter mAdapter;

    private List<AddressManagerBean> addresses = new ArrayList<>();

    @Override
    public int attchLayoutRes() {
        return R.layout.activty_address_manager;
    }

    @Override
    public void initDatas() {
        top_name.setText("收货地址");
        initSwipeRefresh();

        for (int i = 0; i < 4; i++) {
            AddressManagerBean addressManagerBean = new AddressManagerBean();
            addressManagerBean.setAddressId(i);
            addressManagerBean.setAddress("上海"+i);
            if (i == 0){
                addressManagerBean.setCheck(true);
            }else {
                addressManagerBean.setCheck(false);
            }
            addressManagerBean.setName("张三"+i);
            addressManagerBean.setPhone("17621216837");
            addresses.add(addressManagerBean);
        }
        mAdapter = new AddressManagerAdapter(this,addresses);
        RecyclerViewHelper.initRecyclerViewV(this, rv_news_list, false, mAdapter);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                //获取更多数据
            }
        });
        hideLoading();
        mAdapter.noMoreData(); //没有更多数据
        mAdapter.setRemoveDataListener(new OnRemoveDataListener() {
            @Override
            public void onRemove(int position) {
                //接口调用删除地址

            }
        });
    }

    @Override
    public void upDateViews() {}


    @OnClick({R.id.back,R.id.add_address})
    public  void addressOnClick(View v){

        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.add_address:
                break;
            default:
                break;
        }
    }


    @Override
    public void showLoading() {
        super.showLoading();
        if (mEmptyLayout  != null){
            SwipeRefreshHelper.enableRefresh(swipe_refresh, false);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mEmptyLayout  != null){
            SwipeRefreshHelper.enableRefresh(swipe_refresh, true);
            SwipeRefreshHelper.controlRefresh(swipe_refresh, false);
        }
    }

    @Override
    public void showNetError(EmptyLayout.OnRetryListener onRetryListener) {
        super.showNetError(onRetryListener);
        if (mEmptyLayout  != null){
            SwipeRefreshHelper.enableRefresh(swipe_refresh, false);
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (swipe_refresh != null) {
            SwipeRefreshHelper.init(swipe_refresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    upDateViews();
                }
            });
        }
    }
}
