package com.koalafield.cmart.presenter.goods;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.goods.CommentDatas;
import com.koalafield.cmart.ui.view.goods.IGoodsCommentView;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/22
 */

public class GoodsCommentPresenter implements IGoodsCommentPresenter{

    private IGoodsCommentView mCommentView;
    private int pageIndex = 0;

    public GoodsCommentPresenter(IGoodsCommentView commentView){
        this.mCommentView = commentView;
    }


    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {
 }

    @Override
    public void getComments( Map<String,String> params) {
        ApiManager.getGoodsComment(params).subscribe(new SubScribeCallBack<List<CommentDatas>>(new CallBack() {
            @Override
            public void onInit() {
                mCommentView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data !=null){
                    List<CommentDatas> commentDatases = (List<CommentDatas>) data;
                    if (commentDatases != null && commentDatases.size() >0 ){
                        mCommentView.onGoodsCommentSucessFul(commentDatases);
                        pageIndex++;
                    }else {
                        mCommentView.onLoadEmptyData();
                    }
                }else {
                    mCommentView.onGoodsCommentFailure("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mCommentView.hideLoading();
                mCommentView.onGoodsCommentFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mCommentView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreComments(Map<String,String> params) {
        ApiManager.getGoodsComment(params).subscribe(new SubScribeCallBack<List<CommentDatas>>(new CallBack() {
            @Override
            public void onInit() {
                mCommentView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data !=null){
                    List<CommentDatas> commentDatases = (List<CommentDatas>) data;
                    if (commentDatases != null && commentDatases.size() >0 ){
                        mCommentView.onLoadMoreData(commentDatases);
                        pageIndex++;
                    }else {
                        mCommentView.onLoadNoMoreData();
                    }
                }else {
                    mCommentView.onGoodsCommentFailure("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mCommentView.hideLoading();
                mCommentView.onGoodsCommentFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mCommentView.hideLoading();
            }
        }));
    }
}
