package com.koalafield.cmart.ui.fragment;

import com.koalafield.cmart.base.fragment.BaseFragment;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 * 分类界面
 */

public class CategryFragment extends BaseFragment{

    private  static  String mFrom ;

    public static CategryFragment newInstance(String from){
        mFrom = from;
        CategryFragment fragment = new CategryFragment();
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
