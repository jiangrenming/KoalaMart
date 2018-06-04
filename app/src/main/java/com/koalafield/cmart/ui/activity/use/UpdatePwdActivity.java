package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.presenter.user.ChangePwdPresenter;
import com.koalafield.cmart.presenter.user.IChangePwdPresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.view.user.IChangePwdView;
import com.koalafield.cmart.ui.view.user.IUpdatePersonView;
import com.koalafield.cmart.utils.RegaxUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class UpdatePwdActivity extends BaseActivity implements IChangePwdView<BaseResponseBean> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.new_pwd)
    EditText new_pwd;
    @BindView(R.id.comfirm_pwd)
    EditText comfirm_pwd;
    @BindView(R.id.update_comfirm)
    TextView update_comfirm;
    private IChangePwdPresenter pwdPresenter;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_updatepwd;
    }

    @Override
    public void initDatas() {
        top_name.setText("修改密码");
        new_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (new_pwd.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = new_pwd.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule) ||  !RegaxUtils.isPassword(vaule)) {
                        Toast.makeText(UpdatePwdActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(comfirm_pwd);
                    }
                    return true;
                }
                return false;
            }
        });
        comfirm_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (comfirm_pwd.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = comfirm_pwd.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule) ||  !RegaxUtils.isPassword(vaule)) {
                        Toast.makeText(UpdatePwdActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        if (isAllRight()){
                            String old_pwd = new_pwd.getText().toString().trim();
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("password",old_pwd);
                            params.put("newPassword",vaule);
                            pwdPresenter.setParams(params);
                            pwdPresenter.getData();
                        }else {
                            Toast.makeText(UpdatePwdActivity.this,"新旧密码不能相同",Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.back,R.id.update_comfirm})
    public void  onChangeClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.update_comfirm:
                if (isAllRight()){
                    String old_pwd = new_pwd.getText().toString().trim();
                    String vaule = comfirm_pwd.getText().toString().trim();
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("password",old_pwd);
                    params.put("newPassword",vaule);
                    pwdPresenter.setParams(params);
                    pwdPresenter.getData();
                }else {
                    Toast.makeText(UpdatePwdActivity.this,"新旧密码不能相同",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private  boolean isAllRight(){
        String pwd_one = new_pwd.getText().toString().trim();
        String comfirmpwd = comfirm_pwd.getText().toString().trim();
        if (StringUtils.isEmpty(pwd_one) ||  !RegaxUtils.isPassword(pwd_one)){
            return false;
        }
        if (StringUtils.isEmpty(comfirmpwd) || !RegaxUtils.isPassword(pwd_one) || pwd_one.equals(comfirmpwd)){
            return false;
        }
        return  true;
    }

    @Override
    public void upDateViews() {
        pwdPresenter = new ChangePwdPresenter(this);
    }

    @Override
    public void onChangePwdFul(BaseResponseBean data) {
        if (data != null &&data.getCode() == 200){
            Toast.makeText(this,"修改密码成功",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onChangePwdFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(this, LoginActivity.class);
    //        intent.putExtra("type",3);
            startActivity(intent);
        }
    }
}
