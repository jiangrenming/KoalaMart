package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.base.bean.SpecialResponseBean;
import com.koalafield.cmart.bean.user.PersonNumber;
import com.koalafield.cmart.ui.view.user.IPersonNumberView;

/**
 * Created by jiangrenming on 2018/5/23.
 */

public class PersonNumberPresenter implements IPersonNumberPresenter{

    private IPersonNumberView personNumberView;

    public PersonNumberPresenter(IPersonNumberView personNumberView){
        this.personNumberView = personNumberView;
    }



    @Override
    public void getData() {
        ApiManager.get_numbers().subscribe(new SubScribeCallBack<PersonNumber>(new CallBack() {
            @Override
            public void onInit() {
                personNumberView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    PersonNumber personNumber = (PersonNumber) data;
                    if (personNumber.getCode() == 200 || personNumber.getCode() == 0){
                        personNumberView.onPersonNumberSucessFul(personNumber);
                    } else  if (personNumber.getCode() == 401){
                        personNumberView.onPersonNumberFailure(String.valueOf(personNumber.getCode()));
                    }else {
                        personNumberView.onPersonNumberFailure(personNumber.getMsg());
                    }
                }else {
                    personNumberView.onPersonNumberFailure("返回的数据为NULL");

                }
            }

            @Override
            public void onFailure(Throwable t) {
                personNumberView.hideLoading();
                personNumberView.onPersonNumberFailure(t.getMessage());

            }

            @Override
            public void onCompleted() {
                personNumberView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }
}
