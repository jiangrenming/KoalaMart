package com.jrm.retrofitlibrary.callback;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Subscriber的封装
 * @author jiangrenming
 * @date 2018/1/8
 */

public class SubScribeCallBack<T> implements Subscriber<T>{

    private CallBack mCallBack;

    public  SubScribeCallBack(CallBack callBack){
        this.mCallBack = callBack;
    }


    @Override
    public void onSubscribe(Subscription s) {
        mCallBack.onInit();
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        mCallBack.onSucess(t);
    }

    @Override
    public void onError(Throwable t) {
        mCallBack.onFailure(t);
    }

    @Override
    public void onComplete() {
        mCallBack.onCompleted();
    }
}
