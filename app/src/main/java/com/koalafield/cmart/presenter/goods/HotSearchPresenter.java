package com.koalafield.cmart.presenter.goods;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.goods.IHotWordsView;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/23.
 */

public class HotSearchPresenter implements IHotSearchPresenter{

    private IHotWordsView hotWordsView;
    public HotSearchPresenter(IHotWordsView hotWordsView){
        this.hotWordsView = hotWordsView;
    }

    @Override
    public void getData() {
        ApiManager.getHotKeyWords().subscribe(new SubScribeCallBack<List<String>>(new CallBack() {
            @Override
            public void onInit() {
                hotWordsView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<String> words = (List<String>) data;
                    if (words != null && words.size() >0){
                        hotWordsView.onHotWordsSucessFul(words);
                    }else {
                        hotWordsView.onHotWordsNoData();
                    }
                }else {
                    hotWordsView.onHotWordsFailure("返回数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                hotWordsView.hideLoading();
                hotWordsView.onHotWordsFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                hotWordsView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }
}
