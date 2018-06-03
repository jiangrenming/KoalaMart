package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.ui.view.user.IAddressDetailView;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/24
 */

public class AddressDetaPresenter implements IAddressDetaPresenter{

    private IAddressDetailView detailView;


    public AddressDetaPresenter(IAddressDetailView detailView){
        this.detailView = detailView;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void getDetailData(Map<String, String> params) {
        ApiManager.getAddressDetails(params).subscribe(new SubScribeCallBack<AddressManagerBean>(new CallBack() {
            @Override
            public void onInit() {
                detailView.showLoading();;
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    AddressManagerBean datas = (AddressManagerBean) data;
                    if (datas != null){
                        detailView.onAddressDetailSucessFul(datas);
                    }
                }else {
                    detailView.onAddressDetailFailure("返回数据为null",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                detailView.hideLoading();
                detailView.onAddressDetailFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                detailView.hideLoading();
            }
        }));
    }
}
