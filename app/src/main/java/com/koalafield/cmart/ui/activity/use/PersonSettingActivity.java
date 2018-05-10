package com.koalafield.cmart.ui.activity.use;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 * 设置界面
 */

public class PersonSettingActivity extends BaseActivity {

    @BindView(R.id.login_pwd)
    RelativeLayout login_pwd;
    @BindView(R.id.clear)
    RelativeLayout clear;
    @BindView(R.id.about_us)
    RelativeLayout about_us;
    @BindView(R.id.help_centre)
    RelativeLayout help_centre;
    @BindView(R.id.login_out)
    TextView login_out;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_person_setting;
    }

    @Override
    public void initDatas() {
        top_name.setText("设置");
    }

    @Override
    public void upDateViews() {}

    @OnClick({R.id.back,R.id.login_pwd,R.id.clear,R.id.about_us,R.id.help_centre,R.id.login_out})
    public  void onSettingClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.login_pwd:
                break;
            case R.id.clear:
                break;
            case R.id.about_us:
                break;
            case R.id.help_centre:
                break;
            case R.id.login_out:
                break;
            default:
                break;
        }
    }
}
