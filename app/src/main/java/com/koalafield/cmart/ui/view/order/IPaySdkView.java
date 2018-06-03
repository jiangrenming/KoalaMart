package com.koalafield.cmart.ui.view.order;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface IPaySdkView<T> extends IBaseView {


    void onPaySdkData(T data);
    void onPaySdkFailure(String message,int code);

}
