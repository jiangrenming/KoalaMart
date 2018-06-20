package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.koalafield.cmart.R;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.AvtorBean;
import com.koalafield.cmart.bean.user.PersonInfos;
import com.koalafield.cmart.photo.CameraCore;
import com.koalafield.cmart.photo.PhotoCallback;
import com.koalafield.cmart.presenter.user.AvtorPresenter;
import com.koalafield.cmart.presenter.user.IAvtorPresenter;
import com.koalafield.cmart.presenter.user.IInfosPresenter;
import com.koalafield.cmart.presenter.user.InfosPresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.view.user.IAvtorView;
import com.koalafield.cmart.ui.view.user.IPersonInfosView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.PhotoSelectUtils;
import com.koalafield.cmart.utils.RealPathFromUriUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 * 个人资料
 */

public class PrivateActivity extends BaseActivity implements IPersonInfosView<PersonInfos>,IAvtorView<AvtorBean> , PhotoCallback {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.user_phone)
    RelativeLayout user_phone;
    @BindView(R.id.name)
    RelativeLayout name;
    @BindView(R.id.sex)
    RelativeLayout sex;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.phone_ava)
    ImageView phone_ava;
    @BindView(R.id.name_person)
    TextView name_person;
    @BindView(R.id.sex_person)
    TextView sex_person;



    private PopupWindow popupWindow;
    private int navigationHeight = 0;

    private static  final  int PHOTO_REQUEST_TAKEPHOTO = 1;


    private PhotoSelectUtils api;


    @Override
    public int attchLayoutRes() {
        return R.layout.private_activity;
    }

    @Override
    public void initDatas() {
        top_name.setText("个人资料");
    }

    @Override
    public void upDateViews() {
        IInfosPresenter infosPresenter = new InfosPresenter(this);
        infosPresenter.getData();
    }

    @OnClick({R.id.back,R.id.user_phone,R.id.name,R.id.sex})
    public  void  onButterKnifeClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.user_phone: //头像，底部弹窗
                api = new PhotoSelectUtils(this,false,this);
                api.getPhoto();
                break;
            case R.id.name:
                skipActivity("1");
                break;
            case  R.id.sex:
                skipActivity("2");
                break;
            default:
                break;
        }
    }

    private void skipActivity(String type){
        Intent intent = new Intent(PrivateActivity.this,UpdatePrivateActivity.class);
        intent.putExtra("type",type);
        startActivityForResult(intent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     if (requestCode == 1000){
            if (data != null){
                String type = data.getStringExtra("type");
                String value = data.getStringExtra("value");
                if (type .equals("1")){
                    name_person.setText(value);
                }else if (type .equals("2")){
                    if (value.equals("male")){
                        sex_person.setText("男");
                    }else {
                        sex_person.setText("女");
                    }
                }
            }
        }else {
            api.onResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onInfosSucessFul(PersonInfos data) {
        if (data != null){
            name_person.setText(data.getNickname());
            if (data.getGender().equals("male")){
                sex_person.setText("男");
            }else {
                sex_person.setText("女");
            }
            Glide.with(this).load(data.getAvatar()).placeholder(R.mipmap.mine).error(R.mipmap.mine).into(phone_ava);
         }
    }

    @Override
    public void onInfosFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    @Override
    public void onSucessFul(AvtorBean data) {
        String avatar = data.getAvatar();
        if (!StringUtils.isEmpty(avatar)){
            Glide.with(this).load(avatar).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(phone_ava);
        }
    }

    @Override
    public void onFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    @Override
    public void onSuccess(String filePath, int requestId) {
        Log.i("filePath=",filePath+"/requestId="+requestId);
        if (!StringUtils.isEmpty(filePath)){
            IAvtorPresenter avtor = new AvtorPresenter(this);
            avtor.setParams(filePath);
            avtor.getData();
        }
    }

    @Override
    public void onFail(String message, int requestId) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
