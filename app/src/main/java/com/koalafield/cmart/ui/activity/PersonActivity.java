package com.koalafield.cmart.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.ui.activity.order.MartOrderActivity;
import com.koalafield.cmart.ui.activity.use.AddressManangerActivity;
import com.koalafield.cmart.ui.activity.use.PersonSettingActivity;
import com.koalafield.cmart.ui.activity.use.PrivateActivity;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.Constants;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public class PersonActivity extends TabBaseActivity implements View.OnClickListener,PopupWindow.OnDismissListener{

    @BindView(R.id.share)
    ImageView share;  //分享
    @BindView(R.id.person_av)
    ImageView person_av;  //头像
    @BindView(R.id.user_name)
    TextView user_name;  //用户名
    @BindView(R.id.order_infos)
    LinearLayout order_infos;  //订单
    @BindView(R.id.no_pay)
    LinearLayout no_pay;  //待付款
    @BindView(R.id.pay_wait)
    LinearLayout pay_wait;  //待发货
    @BindView(R.id.wait_self)
    LinearLayout wait_self;  //待收货
    @BindView(R.id.old)
    ImageView old;  //买过
    @BindView(R.id.discount)
    ImageView discount;  //优惠券
    @BindView(R.id.collection)
    ImageView collection;  //收藏
    @BindView(R.id.address_manager)
    LinearLayout address_manager;  //地址
    @BindView(R.id.contact)
    ImageView contact;  //联系客服
    @BindView(R.id.service)
    ImageView service;  //服务
    @BindView(R.id.set)
    ImageView set;  //设置

    private PopupWindow popupWindow;
    private int navigationHeight = 0;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;


    @Override
    public int attchLayoutRes() {
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (StringUtils.isEmpty(tickets)) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("type",1);
            startActivity(intent);
            finish();
        }
        return R.layout.fragment_person;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void upDateViews() {

    }

    @OnClick({R.id.share, R.id.person_av, R.id.order_infos, R.id.no_pay, R.id.pay_wait, R.id.wait_self, R.id.old, R.id.discount,
            R.id.collection, R.id.address_manager, R.id.contact, R.id.service, R.id.set})
    public void onButterClick(View v) {
        switch (v.getId()) {
            case R.id.share: //进入朋友圈分享
                break;
            case R.id.person_av: //头像进入个人资料
                startActivity(new Intent(this, PrivateActivity.class));
                break;
            case R.id.order_infos:  //全部订单
                skipOrderActivity(Constants.ALL);
                break;
            case R.id.no_pay:
                skipOrderActivity(Constants.PAY_WAIT);
                break;
            case R.id.pay_wait:
                skipOrderActivity(Constants.WAIT_SEND);
                break;
            case R.id.wait_self:
                skipOrderActivity(Constants.WAIT_RECEIVER);
                break;
            case R.id.old:
                break;
            case R.id.discount:
                break;
            case R.id.collection:
                break;
            case R.id.address_manager:  //地址管理
                startActivity(new Intent(this, AddressManangerActivity.class));
                break;
            case R.id.contact:  //联系客服
                openPopupWindow(v);
                break;
            case R.id.service:
                break;
            case R.id.set: //设置
                startActivity(new Intent(this, PersonSettingActivity.class));
                break;
            default:
                break;
        }
    }

    private void skipOrderActivity(int type) {
        Intent intent = new Intent(this, MartOrderActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    /**
     * 调用拨号功能
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
    }

    //打开ActionSheet的方法
    private void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.customer_layout, null);
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

    private TextView custom_phone, custom_cancel;
    private void setOnPopupViewClick(View view) {
        custom_phone = (TextView) view.findViewById(R.id.custom_phone);
        custom_cancel = (TextView) view.findViewById(R.id.custom_cancel);
        custom_phone.setOnClickListener(this);
        custom_cancel.setOnClickListener(this);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this. getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_phone:
                //拨打电话
                String phone = custom_phone.getText().toString().trim();
                if (!StringUtils.isEmpty(phone)){
                    call(phone);
                }
                disPopuWindow();
                break;
            case R.id.custom_cancel:
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
    public void onDismiss() {
        setBackgroundAlpha(1);
    }


}
