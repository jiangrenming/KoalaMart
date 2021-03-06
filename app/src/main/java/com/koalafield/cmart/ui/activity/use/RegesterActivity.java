package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
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
import com.koalafield.cmart.bean.event.SelectEvent;
import com.koalafield.cmart.bean.user.CountryCode;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.presenter.RegisterPresent;
import com.koalafield.cmart.presenter.IRegsterPresent;
import com.koalafield.cmart.presenter.user.CountryCodePresenter;
import com.koalafield.cmart.presenter.user.ICountryCodePresenter;
import com.koalafield.cmart.presenter.user.IMessageCodePresenter;
import com.koalafield.cmart.presenter.user.MessageCodePresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.activity.PersonActivity;
import com.koalafield.cmart.ui.view.IRegesterView;
import com.koalafield.cmart.ui.view.user.ICountryCodeView;
import com.koalafield.cmart.ui.view.user.IMessageCodeView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.RegaxUtils;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StackActivityManager;
import com.koalafield.cmart.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 * 注册界面
 */

public class RegesterActivity extends BaseActivity<IRegsterPresent> implements IRegesterView<RegisterBean>,
        IMessageCodeView<BaseResponseBean>, ICountryCodeView<List<CountryCode>> {


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
    @BindView(R.id.country_id)
    TextView country_id;
    @BindView(R.id.register_invacation_code)
    EditText register_invacation_code;


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
                        setFocus(register_invacation_code);
                    }
                    return true;
                }
                return false;
            }
        });


        register_invacation_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action,
                                          KeyEvent event) {
                if (register_invacation_code.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String pwd = register_invacation_code.getText().toString().trim();
                    if (StringUtils.isEmpty(pwd)) {  //还未对密码格式做判断
                        Toast.makeText(RegesterActivity.this,"邀请码为空",Toast.LENGTH_SHORT).show();
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
                           Log.i("是否进入","**********调用注册接口***********");
                           Map<String,String> params = new HashMap<>();
                           params.put("mobile",register_phone.getText().toString().trim());
                           params.put("password",register_pwd.getText().toString().trim());
                           params.put("code",register_code.getText().toString().trim());
                           params.put("inviteCode",register_invacation_code.getText().toString().trim());
                           presenter.setParams(params);
                           presenter.getData();
                       }
                    }
                    return true;
                }
                return false;
            }
        });
        ICountryCodePresenter presenter = new CountryCodePresenter(this);
        presenter.getData();
    }

    @Override
    public void upDateViews() {
        presenter = new RegisterPresent(this);
    }

    @OnClick({R.id.back,R.id.register_btn,R.id.register_comfirm,R.id.already_account,R.id.country_id})
    public  void onButterClick(View v){

        switch (v.getId()){
            case R.id.back:
               finish();
                break;
            case R.id.register_btn: //短信验证码
                String phone = register_phone.getText().toString().trim();
                String countryId = country_id.getText().toString().trim();
                if (StringUtils.isEmpty(phone) || !RegaxUtils.isMobilePhone(phone) || StringUtils.isEmpty(countryId)){
                    return ;
                }
                //调用获取验证码的接口，成功时调用timeSchedule();
                IMessageCodePresenter messageCodePresenter = new MessageCodePresenter(this);
                Map<String,String> country_params = new HashMap<>();
                country_params.put("countryId",countryId);
                country_params.put("mobile",phone);
                country_params.put("flag",String.valueOf(0));
                messageCodePresenter.setParams(country_params);
                messageCodePresenter.getData();
                break;
            case  R.id.register_comfirm:
                if (isAllRight()){  //调用接口
                    Log.i("是否进入","**********调用注册接口***********");
                    Map<String,String> params = new HashMap<>();
                    params.put("mobile",register_phone.getText().toString().trim());
                    params.put("password",register_pwd.getText().toString().trim());
                    params.put("code",register_code.getText().toString().trim());
                    params.put("inviteCode",register_invacation_code.getText().toString().trim());
                    presenter.setParams(params);
                    presenter.getData();
                }else {
                    Toast.makeText(RegesterActivity.this,"填写数据有误，请仔细检查",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.already_account:
                finish();
                break;
            case  R.id.country_id: //选择区号
                startActivityForResult(new Intent(RegesterActivity.this,CountryIdActivity.class),10001);
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
        String invacation_code = register_invacation_code.getText().toString().trim();
        if (StringUtils.isEmpty(phone) || !RegaxUtils.isMobilePhone(phone)){
            return false;
        }
        if (StringUtils.isEmpty(invacation_code)){
            return false;
        }
        if (StringUtils.isEmpty(pwd_one) ||  !RegaxUtils.isPassword(pwd_one)){
            return false;
        }
        if (StringUtils.isEmpty(pwd_two) ||  !RegaxUtils.isSamePwd(register_pwd.getText().toString().trim(),pwd_two)){
            return false;
        }
        if (StringUtils.isEmpty(code)){
            return false;
        }
        return  true;
    }

    @Override
    public void onSucessFul(RegisterBean data) {
        if (null != data){
            //返回的ticket字段
            String ticket = data.getTicket();
            Log.i("返回的字段",ticket);
            if (!StringUtils.isEmpty(ticket)){
                ShareBankPreferenceUtils.putString("tickets",ticket);
                finish();
                StackActivityManager.getActivityManager().goToMain(this,4);
            }
        }
    }

    @Override
    public void onFailure(String message) {
        Log.i("返回的异常码：",message);
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
            register_btn.setText(remainSecond + "S");
            remainSecond--;
            if (remainSecond <= 0) {
                register_btn.setEnabled(true);
                register_btn.setText("获取验证码");
                register_btn.setTextColor(AndoridSysUtils.getColorValueByResId(RegesterActivity.this, R.color.blue));
                register_btn.setBackgroundDrawable(getDrawable(R.drawable.register_code));
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
        register_btn.setEnabled(false);
        register_btn.setTextColor(AndoridSysUtils.getColorValueByResId(RegesterActivity.this, R.color.blue));
    }

    @Override
    public void onMessageCodeFul(BaseResponseBean data) {
        if (data != null && data.getCode()  ==200){
            timeSchedule();
        }
    }

    @Override
    public void onMessageCodeFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 10001){
                if (data != null){
                    String countyrId = data.getStringExtra("countyrId");
                    country_id.setText(countyrId);
                }
            }
        }
    }

    @Override
    public void onCountryCodeFul(List<CountryCode> data) {
        if (data != null && data.size() >0){
            String codeStr = data.get(0).CodeStr;
            country_id.setText(codeStr);
        }
    }

    @Override
    public void onCountryCodeFailure(String message) {}
}
