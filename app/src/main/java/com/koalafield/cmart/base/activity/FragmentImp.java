package com.koalafield.cmart.base.activity;

import com.koalafield.cmart.base.fragment.BaseFragment;

/**
 * Created by jiangrenming on 2018/6/6.
 */

public interface FragmentImp {

    /**
     * 从一个Fragment 跳到另一个Fragment
     * @param tag
     * @param current
     */
    void jump(String tag, BaseFragment current);
    /**
     * 返回第一个Fragment
     */
    void home();


}
