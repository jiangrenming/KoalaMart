package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.utils.RegaxUtils;
import com.koalafield.cmart.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 * 注册界面
 */

public class RegesterActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.register_phone)
    EditText register_phone;
    @BindView(R.id.register_code)
    EditText register_code;
    @BindView(R.id.register_pwd)
    EditText register_pwd;
    @BindView(R.id.register_pwd_comfirm)
    EditText register_pwd_comfirm;
    @BindView(R.id.register_btn)
    TextView register_btn;
    @BindView(R.id.register_comfirm)
    TextView register_comfirm;
    @BindView(R.id.already_account)
    TextView already_account;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_register;
    }
    @Override
    public void initDatas() {
        top_name.setText("手机注册");
        register_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (register_phone.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = register_phone.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule) || !RegaxUtils.isMobilePhone(vaule)) {
                        Toast.makeText(RegesterActivity.this,"手机格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(register_code);
                    }
                    return true;
                }
                return false;
            }
        });

        register_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action,
                                          KeyEvent event) {
                if (register_code.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String pwd = register_code.getText().toString().trim();
                    if (StringUtils.isEmpty(pwd)) {  //还未对密码格式做判断
                        Toast.makeText(RegesterActivity.this,"验证码为空",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(register_pwd);
                    }
                    return true;
                }
                return false;
            }
        });

        register_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action,
                                          KeyEvent event) {
                if (register_pwd.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String pwd = register_pwd.getText().toString().trim();
                    if (StringUtils.isEmpty(pwd) || !RegaxUtils.isPassword(pwd)) {
                        Toast.makeText(RegesterActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(register_pwd_comfirm);
                    }
                    return true;
                }
                return false;
            }
        });

        register_pwd_comfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action,
                                          KeyEvent event) {
                if (register_pwd_comfirm.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String pwd = register_pwd_comfirm.getText().toString().trim();
                    if (StringUtils.isEmpty(pwd) || !RegaxUtils.isSamePwd(register_pwd.getText().toString().trim(),pwd)) {  //还未对密码格式做判断
                        Toast.makeText(RegesterActivity.this,"前后密码不一致",Toast.LENGTH_SHORT).show();
                    } else {
                       if (isAllRight()){  //调用接口

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

    @OnClick({R.id.back,R.id.register_btn,R.id.register_comfirm,R.id.already_account})
    public  void onButterClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.register_btn: //短信验证码

                break;
            case  R.id.register_comfirm:
                if (isAllRight()){  //调用接口

                }else {
                    Toast.makeText(RegesterActivity.this,"填写数据有误，请仔细检查",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.already_account:
                finish();
                break;
            default:
                break;
        }
    }

    private  boolean isAllRight(){
        String pwd_two= register_pwd_comfirm.getText().toString().trim();
        String pwd_one = register_pwd.getText().toString().trim();
        String code = register_code.getText().toString().trim();
        String phone = register_phone.getText().toString().trim();
        if (StringUtils.isEmpty(phone) || !RegaxUtils.isMobilePhone(phone)){
            return false;
        }
        if (StringUtils.isEmpty(pwd_one) ||  !RegaxUtils.isPassword(pwd_one)){
            return false;
        }
        if (StringUtils.isEmpty(pwd_two) ||   !RegaxUtils.isSamePwd(register_pwd.getText().toString().trim(),pwd_two)){
            return false;
        }
        if (StringUtils.isEmpty(code)){
            return false;
        }
        return  true;
    }
}
