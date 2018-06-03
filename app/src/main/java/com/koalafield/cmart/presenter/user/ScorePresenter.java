package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.PurchaseOffBean;
import com.koalafield.cmart.bean.user.ScoreBean;
import com.koalafield.cmart.ui.view.user.IPurchaseOffView;
import com.koalafield.cmart.ui.view.user.IScoresView;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public class ScorePresenter implements IScorePresenter{

    private IScoresView scoreView;
    private  int pageIndex = 0;
    public ScorePresenter(IScoresView scoreView){
        this.scoreView = scoreView;
    }

    @Override
    public void getData() {
        pageIndex = 0;
        ApiManager.getScoreList(pageIndex).subscribe(new SubScribeCallBack<List<ScoreBean>>(new CallBack() {
            @Override
            public void onInit() {
                scoreView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                List<ScoreBean> collectionsBeen = (List<ScoreBean>) data;
                if (collectionsBeen != null && collectionsBeen.size() > 0){
                    scoreView.onScoresSucessFul(collectionsBeen);
                    pageIndex++;
                }else {
                    scoreView.loadScoresEmptyData();
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                scoreView.hideLoading();
                scoreView.onScoresFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                scoreView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        ApiManager.getScoreList(pageIndex).subscribe(new SubScribeCallBack<List<ScoreBean>>(new CallBack() {
            @Override
            public void onInit() {
                scoreView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                List<ScoreBean> collectionsBeen = (List<ScoreBean>) data;
                if (collectionsBeen != null && collectionsBeen.size() > 0){
                    scoreView.loadScoresMoreData(collectionsBeen);
                    pageIndex++;
                }else {
                    scoreView.loadScoresNoMoreData();
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                scoreView.hideLoading();
                scoreView.onScoresFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                scoreView.hideLoading();
            }
        }));
    }
}
