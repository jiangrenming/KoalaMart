package com.koalafield.cmart.ui.fragment;

import com.koalafield.cmart.base.fragment.BaseFragment;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 * 商城界面
 */

public class HomeFragment extends BaseFragment{


    private  static  String mFrom ;

    public static HomeFragment newInstance(String from){
        mFrom = from;
        HomeFragment fragment = new HomeFragment();
        return  fragment;
    }

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
}
