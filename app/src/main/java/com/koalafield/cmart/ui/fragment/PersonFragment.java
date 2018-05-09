package com.koalafield.cmart.ui.fragment;

import com.koalafield.cmart.base.fragment.BaseFragment;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 * 个人中心界面
 */

public class PersonFragment extends BaseFragment{

    private  static  String mFrom ;

    public static PersonFragment newInstance(String from){
        mFrom = from;
        PersonFragment fragment = new PersonFragment();
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
