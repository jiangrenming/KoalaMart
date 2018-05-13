package com.koalafield.cmart.ui.fragment;

import android.content.Intent;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 * 购物车界面
 */

public class CartFragment extends BaseFragment {



    @Override
    protected int attachLayoutRes() {
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (StringUtils.isEmpty(tickets)){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
        return R.layout.fragment_cart;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {

    }
}
