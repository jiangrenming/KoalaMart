package com.koalafield.cmart.ui.fragment;

import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.ui.view.user.IDisCountListView;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/24.
 */

public class DisCountTimeFragment extends BaseFragment implements IDisCountListView<List<DisCountBean>> {


    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {


    }
    @Override
    public void onDisCountSucessFul(List<DisCountBean> data) {

    }

    @Override
    public void onDisCountFailure(String message) {

    }

    @Override
    public void loadDisCountEmptyData() {

    }

    @Override
    public void loadDisCountNoMoreData() {

    }

    @Override
    public void loadDisCountMoreData(List<DisCountBean> data) {

    }
}
