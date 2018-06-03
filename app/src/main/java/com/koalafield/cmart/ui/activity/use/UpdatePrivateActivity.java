package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.text.TextUtils;
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
import com.koalafield.cmart.presenter.user.IUpdatePersonInfoPresenter;
import com.koalafield.cmart.presenter.user.UpdatePersonInfoPresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.view.user.IUpdatePersonView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 * 个人资料的修改
 */

public class UpdatePrivateActivity extends BaseActivity implements IUpdatePersonView<BaseResponseBean>{

    
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.private_name)
    EditText private_name;
    @BindView(R.id.comfirm)
    TextView comfirm;
    private  String type;

    private IUpdatePersonInfoPresenter infoPresenter;

    @Override
    public int attchLayoutRes() {
        return R.layout.update_private_activty;
    }

    @Override
    public void initDatas() {
         type = getIntent().getStringExtra("type");
        if (type.equals("1")){
            top_name.setText("设置昵称");
            private_name.setHint("请输入昵称");
        }else  if (type.equals("2")){
            top_name.setText("设置性别");
            private_name.setHint("请输入性别");
        }else if (type.equals("3")){
            top_name.setText("设置地区");
            private_name.setHint("请输入地区");
        }

        private_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (action == EditorInfo.IME_ACTION_DONE ||
                        action == EditorInfo.IME_ACTION_SEND ||
                        action == EditorInfo.IME_ACTION_NEXT ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!allowNext()) {
                        return true;
                    }
                    String value = private_name.getText().toString().trim();
                    if (!TextUtils.isEmpty(value)) {
                        skipPresenter(value);

                    } else {
                        Toast.makeText(UpdatePrivateActivity.this,"请输入数据",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void upDateViews() {
        infoPresenter = new UpdatePersonInfoPresenter(this);
    }
    @OnClick(R.id.comfirm)
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.comfirm:
                if (!allowNext()) {
                    return;
                }
                //接口调用
                String value = private_name.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    skipPresenter(value);
                } else {
                    Toast.makeText(UpdatePrivateActivity.this,"请输入数据",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void skipPresenter(String value){
        Map<String,String> mParams = new HashMap<String, String>();
        //调用接口
        if (type.equals("1")){
            mParams.put("nickname",value);
        }else if (type.equals("2")){
            mParams.put("gender",value);
        }else {
            mParams.put("country",value);
        }

        infoPresenter.setParams(mParams);
        infoPresenter.getData();
    }

    @Override
    public void onUpDatePersonFul(BaseResponseBean data) {
        if (data.getCode() == 200){
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("type",type);
            intent.putExtra("value",private_name.getText().toString().trim());
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void onUpDatePersonFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(UpdatePrivateActivity.this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }
}
