package com.koalafield.cmart.ui.activity;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chaychan.library.BottomBarLayout;
import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/6/4
 */

public class KoalaMartActivity extends BaseActivity {

    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.bbl)
    BottomBarLayout bbl;

    private List<Fragment> fragments = new ArrayList<>();
    @Override
    public int attchLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void upDateViews() {

    }
}
