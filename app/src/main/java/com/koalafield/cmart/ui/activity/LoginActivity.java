package com.koalafield.cmart.ui.activity;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.event.SelectEvent;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.presenter.user.ILoginPresenter;
import com.koalafield.cmart.presenter.user.LoginPrsenter;
import com.koalafield.cmart.ui.activity.use.RegesterActivity;
import com.koalafield.cmart.ui.view.ILoginView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StackActivityManager;
import com.koalafield.cmart.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 */

public class LoginActivity extends BaseActivity<ILoginPresenter> implements ILoginView<RegisterBean>{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.wx_login)
    LinearLayout wx_login;

    private  int type;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void initDatas() {
        top_name.setText("手机登录");
        account.setText("15901774559");
        password.setText("123456");
        type = getIntent().getIntExtra("type",-1);
        account.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (account.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = account.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule)) {  //还未对账户格式做判断
                        Toast.makeText(LoginActivity.this,"账户名称格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(password);
                    }
                    return true;
                }
                return false;
            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action,
                                          KeyEvent event) {
                if (password.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String pwd = password.getText().toString();
                    if (StringUtils.isEmpty(pwd)) {  //还未对密码格式做判断
                        Toast.makeText(LoginActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        if (allRight()){  //登录接口调用

                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void upDateViews() {
        presenter = new LoginPrsenter(this);
    }

    @OnClick({R.id.back,R.id.login,R.id.register,R.id.wx_login})
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.back: //返回首页
                finish();
                StackActivityManager.getActivityManager().goToMain(this);
                break;
            case R.id.login:
                if (allRight()){  //登录接口调用
                    //登陆成功采用setResult回调回个人中心或购物车
                    presenter.setParams(params);
                    presenter.getData();
                }
                break;
            case  R.id.register: //注册
                startActivity(new Intent(this, RegesterActivity.class));
                break;
            case R.id.wx_login:
                break;
            default:
                break;
        }
    }

    private Map<String,String> params = new ArrayMap<>();
    private boolean allRight(){
        String pwd = password.getText().toString();
        String account_name = account.getText().toString().trim();
        if (StringUtils.isEmpty(account_name)){
            return  false;
        }
        if (StringUtils.isEmpty(pwd)){
            return  false;
        }
        params.put("mobile",account_name);
        params.put("password",pwd);
        return  true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
           StackActivityManager.getActivityManager().removeActivity(LoginActivity.this);
       //     finish();
            StackActivityManager.getActivityManager().goToMain(this);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSucessFul(RegisterBean data) {
        String ticket = data.getTicket();
        if (!StringUtils.isEmpty(ticket)){
            ShareBankPreferenceUtils.putString("tickets",ticket);
            if (type == 1){
               finish();
                StackActivityManager.getActivityManager().goToMain(this,4);
            }else if (type ==2){
                finish();
                StackActivityManager.getActivityManager().goToMain(this,3);
            }
        }
    }

    @Override
    public void onFailure(String message) {

    }
}
