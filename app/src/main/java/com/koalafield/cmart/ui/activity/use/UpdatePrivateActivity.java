package com.koalafield.cmart.ui.activity.use;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 * 个人资料的修改
 */

public class UpdatePrivateActivity extends BaseActivity{

    
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.private_name)
    EditText private_name;
    @BindView(R.id.comfirm)
    TextView comfirm;
    private  String type;

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

                        //调用接口

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
    public void upDateViews() {}
    @OnClick(R.id.comfirm)
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.comfirm:
                if (!allowNext()) {
                    return;
                }
                //接口调用
                break;
            default:
                break;
        }
    }

}
