package com.koalafield.cmart.ui.activity;

import android.support.v7.widget.ViewUtils;
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
import com.koalafield.cmart.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 */

public class LoginActivity extends BaseActivity{

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

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void initDatas() {
        top_name.setText("手机登录");
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
    public void upDateViews() {}

    @OnClick({R.id.back,R.id.login,R.id.register,R.id.wx_login})
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.back: //返回首页
                break;
            case R.id.login:
                if (allRight()){  //登录接口调用

                }
                break;
            case  R.id.register:
                break;
            case R.id.wx_login:
                break;
            default:
                break;
        }
    }
    private boolean allRight(){
        String pwd = password.getText().toString();
        String account_name = account.getText().toString().trim();
        if (StringUtils.isEmpty(account_name)){
            return  false;
        }
        if (StringUtils.isEmpty(pwd)){
            return  false;
        }
        return  true;
    }
}
