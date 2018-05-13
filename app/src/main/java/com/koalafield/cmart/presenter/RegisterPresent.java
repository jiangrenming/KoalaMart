package com.koalafield.cmart.presenter;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.base.presenter.IBasePresenter;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.ui.view.IRegesterView;
import com.koalafield.cmart.widget.EmptyLayout;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 * 注册的p层接口实现
 */

public class RegisterPresent implements IRegsterPresent {

    private IRegesterView mRegesterView;
    private  Map<String,String> mParams;

    public RegisterPresent(IRegesterView regesterView){
        this.mRegesterView = regesterView;
    }




    @Override
    public void getData() {
        ApiManager.getRegsterInfos(mParams).subscribe(new SubScribeCallBack<RegisterBean>(new CallBack() {
            @Override
            public void onInit() {
                mRegesterView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    RegisterBean registerBean  = (RegisterBean) data;
                    if (null != registerBean){
                        mRegesterView.onSucessFul(registerBean);
                    }else {
                        mRegesterView.onFailure("返回的数据为空");
                    }
                }else {
                    mRegesterView.onFailure("数据为Null");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mRegesterView.hideLoading();
                mRegesterView.onFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mRegesterView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(Map<String, String> params) {
        mParams = params;
    }
}
