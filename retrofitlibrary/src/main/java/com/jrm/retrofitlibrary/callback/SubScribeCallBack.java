package com.jrm.retrofitlibrary.callback;

import android.util.Log;

import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;

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
        Log.i("返回的数据",t.toString());
        mCallBack.onSucess(t);
    }

    @Override
    public void onError(Throwable t) {
        ExceptionHandle.ResponeThrowable responeThrowable = ExceptionHandle.handleException(t);
        mCallBack.onFailure(responeThrowable);
    }

    @Override
    public void onComplete() {
        mCallBack.onCompleted();
    }
}
