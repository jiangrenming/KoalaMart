package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
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

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.PersonInfos;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.view.user.IPersonInfosView;
import com.koalafield.cmart.utils.AndoridSysUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 * 个人资料
 */

public class PrivateActivity extends BaseActivity implements  PopupWindow.OnDismissListener,View.OnClickListener ,IPersonInfosView<PersonInfos>{


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.user_phone)
    RelativeLayout user_phone;
    @BindView(R.id.name)
    RelativeLayout name;
    @BindView(R.id.sex)
    RelativeLayout sex;
    @BindView(R.id.addres)
    RelativeLayout addres;

    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.phone_ava)
    ImageView phone_ava;
    @BindView(R.id.name_person)
    TextView name_person;
    @BindView(R.id.sex_person)
    TextView sex_person;
    @BindView(R.id.person_country)
    TextView person_country;


    private PopupWindow popupWindow;
    private int navigationHeight = 0;

    private static  final  int PHOTO_REQUEST_TAKEPHOTO = 1;

    @Override
    public int attchLayoutRes() {
        return R.layout.private_activity;
    }

    @Override
    public void initDatas() {
        top_name.setText("个人资料");
    }

    @Override
    public void upDateViews() {}

    @OnClick({R.id.back,R.id.user_phone,R.id.name,R.id.sex,R.id.addres})
    public  void  onButterKnifeClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.user_phone: //头像，底部弹窗
                openPopupWindow(v);
                break;
            case R.id.name:
                skipActivity("1");
                break;
            case  R.id.sex:
                skipActivity("2");
                break;
            case R.id.addres:
                skipActivity("3");
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
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

    //打开ActionSheet的方法
    private void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.take_phone_window_layout, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外退出
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        if (AndoridSysUtils.checkDeviceHasNavigationBar(this)){
            navigationHeight = AndoridSysUtils.getNavigationBarHeigh(this);
        }
        //设置显示的位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景透明度
        setBackgroundAlpha(0.5f);
    }

    private void setOnPopupViewClick(View view) {
        TextView tv_pick_phone, tv_pick_zone, tv_cancel;
        tv_pick_phone = (TextView) view.findViewById(R.id.tv_pick_phone);
        tv_pick_zone = (TextView) view.findViewById(R.id.tv_pick_zone);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_pick_phone.setOnClickListener(this);
        tv_pick_zone.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }
    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_pick_phone:
                openPictures();
                disPopuWindow();
                break;
            case R.id.tv_pick_zone:
                openCammer();
                disPopuWindow();
                break;
            case R.id.tv_cancel:
                disPopuWindow();
                break;
            default:
                break;
        }
    }

    private  void disPopuWindow(){
        if (null != popupWindow && popupWindow .isShowing()){
            popupWindow.dismiss();
        }
    }
    //打开系统照相机
    private void openCammer() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        } else {
            Log.e("CAMERA", "请确认已经插入SD卡");
        }
    }

    //打开系统相册
    private void openPictures() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if ( null != data){

            }
        }else if (requestCode == 2){

        }else if (requestCode == 1000){
            if (data != null){
                String type = data.getStringExtra("type");
                String value = data.getStringExtra("value");
                if (type .equals("1")){
                    name_person.setText(value);
                }else if (type .equals("2")){
                    sex_person.setText(value);
                }else if (type .equals("3")){
                    person_country.setText(value);
                }
            }
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
            person_country.setText(data.getCountry());
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
}
