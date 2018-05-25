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

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRemoveDataListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.AddressManagerAdapter;
import com.koalafield.cmart.adapter.CommentAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.presenter.goods.GoodsCommentPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsCommentPresenter;
import com.koalafield.cmart.presenter.user.AddressPresenter;
import com.koalafield.cmart.presenter.user.DelCountryPresenter;
import com.koalafield.cmart.presenter.user.IAddressPresenter;
import com.koalafield.cmart.presenter.user.IDelCountryPresenter;
import com.koalafield.cmart.ui.view.user.IAddressListView;
import com.koalafield.cmart.ui.view.user.IDelAddressView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.utils.SwipeRefreshHelper;
import com.koalafield.cmart.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.internal.queue.MpscLinkedQueue;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 * 地址管理界面
 */

public class AddressManangerActivity extends BaseActivity implements IAddressListView<List<AddressManagerBean>>,IDelAddressView<BaseResponseBean>{

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
    @BindView(R.id.empty_address)
    LinearLayout empty_address;
    private IAddressPresenter addressPresenter;
    private  AddressManagerAdapter addressManagerAdapter;
    private List<AddressManagerBean> addresses;
    private  int pageIndex = 0;

    @Override
    public int attchLayoutRes() {
        return R.layout.activty_address_manager;
    }

    @Override
    public void initDatas() {
        top_name.setText("收货地址");
        initSwipeRefresh();

    }

    @Override
    public void upDateViews() {
        addressPresenter = new AddressPresenter(this);
        addressPresenter.setPrarms(pageIndex);
        addressPresenter.getData();
    }


    @OnClick({R.id.back,R.id.add_address})
    public  void addressOnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.add_address:
                Intent intent = new Intent(AddressManangerActivity.this,ChangeAddressActivity.class);
                intent.putExtra("addressType",1);
                startActivity(intent);
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
                    pageIndex = 0;
                    upDateViews();
                    SwipeRefreshHelper.controlRefresh(swipe_refresh,false);
                }
            });
        }
    }

    @Override
    public void onAddressSucessFul(final List<AddressManagerBean> data) {
        if (data != null && data.size() >0){
            pageIndex ++;
            final AddressManagerBean addressId = ShareBankPreferenceUtils.getObject("addressId", AddressManagerBean.class);
            for (int i = 0; i < data.size(); i++) {
                AddressManagerBean addressManagerBean = data.get(i);
                if (addressId != null){
                    if (addressId.getId() == addressManagerBean.getId()){
                        data.get(i).setSelected(true);
                    }else {
                        data.get(i).setSelected(false);
                    }
                }else {
                    data.get(i).setSelected(false);
                }

            }
            addresses = data;
            if (addressManagerAdapter == null){
                addressManagerAdapter = new AddressManagerAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewV(this,rv_news_list,true,addressManagerAdapter);
            }else {
                addressManagerAdapter.cleanItems();
                addressManagerAdapter.addItems(data);
            }
            addressManagerAdapter.setRequestDataListener(new OnRequestDataListener() {
                @Override
                public void onLoadMore() {
                    addressPresenter.setPrarms(pageIndex);
                    addressPresenter.getMoreData();
                }
            });
            //地址的变化
            addressManagerAdapter.SelectAddressCallBack(new AddressManagerAdapter.SelectAddressCallBack() {
                @Override
                public void checkSelect(AddressManagerBean item,boolean isSelect) {
                    if (isSelect){
                        ShareBankPreferenceUtils.setObject("addressId",item);
                    }else {
                        ShareBankPreferenceUtils.clearData("addressId");
                    }

                    for (int i = 0; i < addresses.size(); i++) {  //0,1
                        AddressManagerBean addressManagerBean = addresses.get(i);
                        if (addressManagerBean.getId() == item.getId()){ //1
                            addressManagerBean.setSelected(isSelect);
                        }else {
                            if (addressManagerBean.isSelected()){
                                addressManagerBean.setSelected(false);
                            }
                        }
                    }
                    addressManagerAdapter.updateItems(addresses);
                }

                @Override
                public void editAddress(AddressManagerBean item) {
                    Intent intent = new Intent(AddressManangerActivity.this,ChangeAddressActivity.class);
                    intent.putExtra("addressType",2);
                    intent.putExtra("address",item);
                    startActivity(intent);
                }

                @Override
                public void delAddress(AddressManagerBean item) {
                    if (item.isSelected()){
                        ShareBankPreferenceUtils.clearData("addressId");
                    }
                    if (addresses.contains(item)){
                        addresses.remove(item);
                    }
                    IDelCountryPresenter delAdapter = new DelCountryPresenter(AddressManangerActivity.this);
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Id",String.valueOf(item.getId()));
                    delAdapter.getParamsData(params);
                }
            });
        }
    }

    @Override
    public void onAddressFailure(String message) {
        Log.i("异常的信息:",message);
        Toast.makeText(AddressManangerActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadAddressEmptyData() {
        SwipeRefreshHelper.controlRefresh(swipe_refresh,false);
        hideLoading();
        swipe_refresh.setVisibility(View.GONE);
        empty_address.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadAddressNoMoreData() {
        addressManagerAdapter.loadComplete();
        addressManagerAdapter.noMoreData();
    }

    @Override
    public void loadAddressMoreData(List<AddressManagerBean> data) {
        addressManagerAdapter.loadComplete();
        addressManagerAdapter.addItems(data);
        pageIndex++;
    }

    @Override
    public void onDelAddressSucessFul(BaseResponseBean data) {
        if (data != null){
            if (data.getCode() ==200){
                addressManagerAdapter.updateItems(addresses);
            }
        }
    }

    @Override
    public void onDelAddressFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
