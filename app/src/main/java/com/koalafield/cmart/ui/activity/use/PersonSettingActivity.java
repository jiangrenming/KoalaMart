package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.HistoryContent;
import com.koalafield.cmart.db.HistoryService;
import com.koalafield.cmart.db.IHistoryService;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.activity.PersonActivity;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StackActivityManager;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.CommonDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 * 设置界面
 */

public class PersonSettingActivity extends BaseActivity  {

    @BindView(R.id.login_pwd)
    RelativeLayout login_pwd;
    @BindView(R.id.clear)
    RelativeLayout clear;
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
        String versionName = AndoridSysUtils.getVersion(this, this.getPackageName());
        if (StringUtils.isEmpty(versionName)){
            versionName = "1.1.0";
        }
        version.setText("系统版本号："+versionName);
    }

    @Override
    public void upDateViews() {}

    @OnClick({R.id.back,R.id.login_pwd,R.id.clear,R.id.login_out})
    public  void onSettingClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.login_pwd:
                //修改登录密码
                startActivity(new Intent(this,UpdatePwdActivity.class));
                break;
            case R.id.clear:
                new CommonDialog(this).builder().setTitle("清理缓存").setMsg("清理缓存之后，本地数据将不存在").setNegativeButton("清理", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareBankPreferenceUtils.clearData("tickets");
                        ShareBankPreferenceUtils.clearData("isFirst");
                        IHistoryService service = new HistoryService(PersonSettingActivity.this);
                        List<HistoryContent> allHistory = service.findAllHistory();
                        if (allHistory != null && allHistory.size() >0){
                            service.deletAllKeys();
                        }
                    }
                }).setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
                break;

            case R.id.login_out:  //退出登录
                new CommonDialog(this).builder().setTitle("退出").setMsg("退出后将删除数据").setNegativeButton("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //退出登陆后返回主页面，同时改变购物车界面数目显示
                        StackActivityManager.getActivityManager().goToMain(PersonSettingActivity.this,1);
                        ShareBankPreferenceUtils.clearData("tickets");
                        finish();
                    }
                }).setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            default:
                break;
        }
    }
}
