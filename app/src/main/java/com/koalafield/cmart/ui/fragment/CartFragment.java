package com.koalafield.cmart.ui.fragment;

import com.koalafield.cmart.base.fragment.BaseFragment;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 * 购物车界面
 */

public class CartFragment extends BaseFragment {

    private  static  String mFrom ;

    public static CartFragment newInstance(String from){
        mFrom = from;
        CartFragment fragment = new CartFragment();
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
