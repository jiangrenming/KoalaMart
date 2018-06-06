package com.koalafield.cmart.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.koalafield.cmart.base.fragment.BaseFragment;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/6/6
 */

public abstract class BaseFragmentActivity extends BaseActivity implements FragmentImp{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //处理异常退出恢复情况
        if (savedInstanceState != null) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null && fragments.size() > 0) {
                boolean showFlag = false;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                for (int i = fragments.size() - 1; i >= 0; i--) {
                    Fragment fragment = fragments.get(i);
                    if (fragment != null) {
                        if (!showFlag) {
                            ft.show(fragments.get(i));
                            showFlag = true;
                        } else {
                            ft.hide(fragments.get(i));
                        }
                    }
                }
                ft.commit();
            }
        }
    }

    @Override
    public void home() {
        while (getSupportFragmentManager().getBackStackEntryCount() != 1) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void jump(String tag, BaseFragment current) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment target = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (target == null) {
            transaction.hide(current)
                    .add(target, tag)
                    .addToBackStack(null)
                    .commit();
        } else {
            transaction.hide(current).show(target).commit();
        }
    }
}
