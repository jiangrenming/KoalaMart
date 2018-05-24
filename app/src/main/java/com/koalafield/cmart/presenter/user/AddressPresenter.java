package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.ui.view.user.IAddressListView;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/24
 */

public class AddressPresenter implements IAddressPresenter {


    private IAddressListView addressListView;
    private  int pageIndex = 0;

    public AddressPresenter(IAddressListView addressListView){
        this.addressListView = addressListView;
    }



    @Override
    public void getData() {
        ApiManager.getAddressList(pageIndex).subscribe(new SubScribeCallBack<List<AddressManagerBean>>(new CallBack() {
            @Override
            public void onInit() {
                addressListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<AddressManagerBean> addressManagerBean = (List<AddressManagerBean>) data;
                    if (addressManagerBean != null && addressManagerBean.size() >0){
                        addressListView.onAddressSucessFul(addressManagerBean);
                        pageIndex++;
                    }else {
                        addressListView.loadAddressEmptyData();
                    }
                }else {
                    addressListView.onAddressFailure("返回的数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                addressListView.hideLoading();
                addressListView.onAddressFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                addressListView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        ApiManager.getAddressList(pageIndex).subscribe(new SubScribeCallBack<List<AddressManagerBean>>(new CallBack() {
            @Override
            public void onInit() {
                addressListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<AddressManagerBean> addressManagerBean = (List<AddressManagerBean>) data;
                    if (addressManagerBean != null && addressManagerBean.size() >0){
                        addressListView.loadAddressMoreData(addressManagerBean);
                        pageIndex++;
                    }else {
                        addressListView.loadAddressNoMoreData();
                    }
                }else {
                    addressListView.onAddressFailure("返回的数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                addressListView.hideLoading();
                addressListView.onAddressFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                addressListView.hideLoading();
            }
        }));
    }
}
