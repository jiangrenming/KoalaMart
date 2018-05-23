package com.koalafield.cmart.presenter.goods;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.goods.IHotWordsView;

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
        ApiManager.getHotKeyWords().subscribe(new SubScribeCallBack<String[]>(new CallBack() {
            @Override
            public void onInit() {
                hotWordsView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    String[] words = (String[]) data;
                    if (words != null && words.length >0){
                        hotWordsView.onHotWordsSucessFul(words);
                    }else {
                        hotWordsView.onHotWordsNoData();
                    }
                }else {
                    hotWordsView.onHotWordsFailure("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hotWordsView.hideLoading();
                hotWordsView.onHotWordsFailure(t.getMessage());
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
