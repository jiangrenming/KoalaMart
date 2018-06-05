package com.koalafield.cmart.presenter.user;

import android.content.Context;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.ui.view.user.IWXLoginView;
import com.koalafield.cmart.utils.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/5.
 */

public class WXLoginPresenter implements IWXLoginPresenter{

    private IWXLoginView wxLoginView;
    private  Map<String, String> params;

    private IWXAPI wxAPI;

    public WXLoginPresenter(IWXLoginView wxLoginView, Context context){

        this.wxLoginView = wxLoginView;
        if (wxAPI == null){
            wxAPI = WXAPIFactory.createWXAPI(context, Constants.APP_ID,true);
            wxAPI.registerApp(Constants.APP_ID);
        }

    }


    /**
     * 微信登陆(三个步骤)
     * 1.微信授权登陆
     * 2.根据授权登陆code 获取该用户token
     * 3.根据token获取用户资料
     * @param :activity
     */
    public void login(){
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = String.valueOf(System.currentTimeMillis());
        wxAPI.sendReq(req);
    }

    /**
     * 获取微信访问token
     */
    public void getAccessToken(String code){

    }



    @Override
    public void getData() {
        ApiManager.getWXLogin(params).subscribe(new SubScribeCallBack<RegisterBean>(new CallBack() {
            @Override
            public void onInit() {
                wxLoginView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    RegisterBean registerBean = (RegisterBean) data;
                    if (null != registerBean){
                        wxLoginView.onWXSucessFul(registerBean);
                    }else {
                        wxLoginView.onWXFailure("返回的数据null");
                    }
                }else {
                    wxLoginView.onWXFailure("返回的数据有误");
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                wxLoginView.hideLoading();
                wxLoginView.onWXFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                wxLoginView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
