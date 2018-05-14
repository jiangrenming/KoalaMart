package com.koalafield.cmart.ui.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.presenter.IBasePresenter;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public abstract class TabBaseActivity extends BaseActivity {


    protected long exitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!intercept()){
            return super.onKeyDown(keyCode, event);
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean intercept(){
        return true;
    }
}
