package com.koalafield.cmart.ui.activity;

import android.content.Intent;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public class CartActivity extends TabBaseActivity {

    @Override
    public int attchLayoutRes() {
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (StringUtils.isEmpty(tickets)){
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("type",2);
            startActivity(intent);
            finish();
        }
        return R.layout.fragment_cart;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void upDateViews() {

    }
}
