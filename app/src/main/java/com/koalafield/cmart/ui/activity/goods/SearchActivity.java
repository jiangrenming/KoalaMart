package com.koalafield.cmart.ui.activity.goods;

import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.presenter.goods.HotSearchPresenter;
import com.koalafield.cmart.presenter.goods.IHotSearchPresenter;
import com.koalafield.cmart.ui.view.goods.IHotWordsView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/23
 */

public class SearchActivity extends BaseActivity implements IHotWordsView<String[]> {


    private IHotSearchPresenter hotSearchPresenter;

    @Override
    public int attchLayoutRes() {
        return 0;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void upDateViews() {
        hotSearchPresenter = new HotSearchPresenter(this);
        hotSearchPresenter.getData();
    }

    @Override
    public void onHotWordsSucessFul(String[] data) {

    }

    @Override
    public void onHotWordsNoData() {

    }

    @Override
    public void onHotWordsFailure(String message) {

    }
}
