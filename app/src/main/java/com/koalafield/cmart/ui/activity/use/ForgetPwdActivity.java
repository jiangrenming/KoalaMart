package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.DragAndDropPermissions;
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
import com.koalafield.cmart.presenter.user.IMessageCodePresenter;
import com.koalafield.cmart.presenter.user.IResetPwdPresenter;
import com.koalafield.cmart.presenter.user.MessageCodePresenter;
import com.koalafield.cmart.presenter.user.ResetPwdPresenter;
import com.koalafield.cmart.ui.view.user.IMessageCodeView;
import com.koalafield.cmart.ui.view.user.IResetPwdView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.RegaxUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class ForgetPwdActivity extends BaseActivity implements IMessageCodeView<BaseResponseBean>,IResetPwdView<BaseResponseBean> {

    @BindView(R.id.forget_phone)
    EditText forget_phone;
    @BindView(R.id.country_id)
    TextView country_id;
    @BindView(R.id.forget_code)
    EditText forget_code;
    @BindView(R.id.forget_btn)
    TextView forget_btn;
    @BindView(R.id.new_pwd)
    EditText new_pwd;
    @BindView(R.id.forget_comfirm)
    TextView forget_comfirm;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;

    private IResetPwdPresenter resetPwdPresenter;
    @Override
    public int attchLayoutRes() {
        return R.layout.activity_forget;
    }

    @Override
    public void initDatas() {
        top_name.setText("重置密码");
        resetPwdPresenter = new ResetPwdPresenter(this);
    }

    @Override
    public void upDateViews() {
        forget_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (forget_phone.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = forget_phone.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule) || !RegaxUtils.isMobilePhone(vaule)) {
                        Toast.makeText(ForgetPwdActivity.this,"手机格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(forget_code);
                    }
                    return true;
                }
                return false;
            }
        });
        forget_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (forget_code.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = forget_code.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule)) {
                        Toast.makeText(ForgetPwdActivity.this,"短信验证码不能为空",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(new_pwd);
                    }
                    return true;
                }
                return false;
            }
        });
        new_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (new_pwd.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = new_pwd.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule)|| !RegaxUtils.isPassword(vaule)) {
                        Toast.makeText(ForgetPwdActivity.this,"密码不能为空或格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        //调用接口
                        if (isAllRight()){
                            String code = forget_code.getText().toString().trim();
                            String phone = forget_phone.getText().toString().trim();
                            Map<String,String> params = new HashMap();
                            params.put("mobile",phone);
                            params.put("code",code);
                            params.put("password",vaule);
                            resetPwdPresenter.getData();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.forget_btn,R.id.country_id,R.id.forget_comfirm,R.id.back})
    public void getCodeClick(View view){
        switch (view.getId()){
            case R.id.country_id:
                startActivityForResult(new Intent(ForgetPwdActivity.this,CountryIdActivity.class),10002);
                break;
            case R.id.forget_btn:
                String phone = forget_phone.getText().toString().trim();
                String countryId = country_id.getText().toString().trim();
                if (StringUtils.isEmpty(phone) || !RegaxUtils.isMobilePhone(phone) || StringUtils.isEmpty(countryId)){
                    return ;
                }
                //调用获取验证码的接口，成功时调用timeSchedule();
                IMessageCodePresenter messageCodePresenter = new MessageCodePresenter(this);
                Map<String,String> country_params = new HashMap<>();
                country_params.put("countryId",countryId);
                country_params.put("mobile",phone);
                country_params.put("flag",String.valueOf(2));
                messageCodePresenter.setParams(country_params);
                messageCodePresenter.getData();
                break;
            case R.id.forget_comfirm:
                if (isAllRight()){
                    String code = forget_code.getText().toString().trim();
                    String phones = forget_phone.getText().toString().trim();
                    String vaule = new_pwd.getText().toString().trim();
                    Map<String,String> params = new HashMap();
                    params.put("mobile",phones);
                    params.put("code",code);
                    params.put("password",vaule);
                    resetPwdPresenter.getData();
                }else {
                    Toast.makeText(ForgetPwdActivity.this,"填写数据不完整",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }


    private  boolean isAllRight(){
        String pwd_one = new_pwd.getText().toString().trim();
        String code = forget_code.getText().toString().trim();
        String phone = forget_phone.getText().toString().trim();
        if (StringUtils.isEmpty(phone) || !RegaxUtils.isMobilePhone(phone)){
            return false;
        }
        if (StringUtils.isEmpty(pwd_one) ||  !RegaxUtils.isPassword(pwd_one)){
            return false;
        }
        if (StringUtils.isEmpty(code)){
            return false;
        }
        return  true;
    }

    @Override
    public void onMessageCodeFul(BaseResponseBean data) {
        if (data != null && data.getCode() ==200){
            timeSchedule();
        }
    }

    @Override
    public void onMessageCodeFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理短信验证码倒计时
     */
    private  int remainSecond = 60;
    private Timer countDownTimer;
    private Handler handler = new Handler() {
        @Override
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void handleMessage(android.os.Message msg) {
            forget_btn.setText(remainSecond + "S");
            remainSecond--;
            if (remainSecond <= 0) {
                forget_btn.setEnabled(true);
                forget_btn.setText("获取验证码");
                forget_btn.setTextColor(AndoridSysUtils.getColorValueByResId(ForgetPwdActivity.this, R.color.white));
                forget_btn.setBackgroundDrawable(getDrawable(R.drawable.register_code));
                countDownTimer.cancel();// 取消
                remainSecond = 60;
            }

        }
    };

    private void timeSchedule() {
        countDownTimer = new Timer();
        countDownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(new Message());
            }
        }, 0, 1000);
        forget_btn.setEnabled(false);
        forget_btn.setTextColor(AndoridSysUtils.getColorValueByResId(ForgetPwdActivity.this, R.color.white));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 10002){
                if (data != null){
                    String countyrId = data.getStringExtra("countyrId");
                    country_id.setText(String.valueOf(countyrId));
                }
            }
        }
    }

    @Override
    public void onResetPwdFul(BaseResponseBean data) {
        if (data != null && data.getCode() ==200){
            Toast.makeText(this,data.getMsg(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onResetPwdFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
