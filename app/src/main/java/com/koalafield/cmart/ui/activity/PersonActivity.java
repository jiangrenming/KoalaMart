package com.koalafield.cmart.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CollectionAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.event.LoginEvent;
import com.koalafield.cmart.bean.event.WxEvent;
import com.koalafield.cmart.bean.user.PersonInfos;
import com.koalafield.cmart.bean.user.PersonNumber;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.presenter.user.IInfosPresenter;
import com.koalafield.cmart.presenter.user.IPersonNumberPresenter;
import com.koalafield.cmart.presenter.user.InfosPresenter;
import com.koalafield.cmart.presenter.user.PersonNumberPresenter;
import com.koalafield.cmart.ui.activity.order.MartOrderActivity;
import com.koalafield.cmart.ui.activity.order.PayActivity;
import com.koalafield.cmart.ui.activity.use.AboutUsActivity;
import com.koalafield.cmart.ui.activity.use.AddressManangerActivity;
import com.koalafield.cmart.ui.activity.use.CollectionActivity;
import com.koalafield.cmart.ui.activity.use.DisCountActivity;
import com.koalafield.cmart.ui.activity.use.PersonSettingActivity;
import com.koalafield.cmart.ui.activity.use.PrivateActivity;
import com.koalafield.cmart.ui.activity.use.PurchareOffActivity;
import com.koalafield.cmart.ui.activity.use.ScoresActivity;
import com.koalafield.cmart.ui.activity.use.UserResponseActivity;
import com.koalafield.cmart.ui.view.user.IPersonInfosView;
import com.koalafield.cmart.ui.view.user.IPersonNumberView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.Constants;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StackActivityManager;
import com.koalafield.cmart.utils.StringUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public class PersonActivity extends TabBaseActivity implements View.OnClickListener, PopupWindow.OnDismissListener, IPersonNumberView<PersonNumber>
        , IPersonInfosView<PersonInfos> {

    @BindView(R.id.share)
    LinearLayout share;  //分享
    @BindView(R.id.advice)
    LinearLayout advice;  //意见反馈
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
    @BindView(R.id.old_buy)
    LinearLayout old;  //买过
    @BindView(R.id.discount)
    LinearLayout discount;  //优惠券
    @BindView(R.id.collection)
    LinearLayout collection;  //收藏
    @BindView(R.id.counp)
    LinearLayout counp;  //积分
    @BindView(R.id.address_manager)
    LinearLayout address_manager;  //地址
    @BindView(R.id.contact_custemer)
    LinearLayout contact;  //联系客服
    @BindView(R.id.service_infos)
    LinearLayout service_infos;  //服务
    @BindView(R.id.setting)
    ImageView set;  //设置
    @BindView(R.id.discount_num)
    TextView discount_num;
    @BindView(R.id.collection_num)
    TextView collection_num;
    @BindView(R.id.counp_num)
    TextView counp_num;

    private PopupWindow popupWindow;
    private int navigationHeight = 0;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
    private IWXAPI iwxapi;

    @Override
    public int attchLayoutRes() {
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        Log.i("返回的tickes", tickets + "");
        if (StringUtils.isEmpty(tickets)) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            finish();
        }
        return R.layout.fragment_person;
    }

    @Override
    public void initDatas() {
        //注册微信appid到微信平台
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        iwxapi.registerApp(Constants.APP_ID);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        Log.i("返回的tickes", tickets + "");
        if (!StringUtils.isEmpty(tickets)) {
            IInfosPresenter infosPresenter = new InfosPresenter(this);
            infosPresenter.getData();
        }
    }

    @Override
    public void upDateViews() {
        IPersonNumberPresenter personNumberPresenter = new PersonNumberPresenter(this);
        personNumberPresenter.getData();
    }

    @OnClick({R.id.share, R.id.person_av, R.id.order_infos, R.id.no_pay, R.id.pay_wait, R.id.wait_self, R.id.old_buy, R.id.discount,
            R.id.collection, R.id.address_manager, R.id.contact_custemer, R.id.service_infos, R.id.setting, R.id.counp, R.id.advice})
    public void onButterClick(View v) {
        switch (v.getId()) {
            case R.id.share: //进入朋友圈分享
             //   sendAuthCode();
                openSharePopupWindow(v);
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
            case R.id.old_buy:
                startActivity(new Intent(PersonActivity.this, PurchareOffActivity.class));
                break;
            case R.id.discount: //优惠券
                startActivity(new Intent(this, DisCountActivity.class));
                break;
            case R.id.collection: //收藏列表
                startActivity(new Intent(this, CollectionActivity.class));
                break;
            case R.id.counp: //积分列表
                startActivity(new Intent(this, ScoresActivity.class));
                break;
            case R.id.address_manager:  //地址管理
                startActivity(new Intent(this, AddressManangerActivity.class));
                break;
            case R.id.contact_custemer:  //联系客服
                openPopupWindow(v);
                break;
            case R.id.service_infos: //服务条款
                Intent intent = new Intent(PersonActivity.this, AboutUsActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.setting: //设置
                startActivity(new Intent(this, PersonSettingActivity.class));
                break;
            case R.id.advice: //意见反馈
                startActivity(new Intent(this, UserResponseActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 分享的弹出窗
     * @param v
     */
    private void openSharePopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.share_item, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外退出
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        if (AndoridSysUtils.checkDeviceHasNavigationBar(this)) {
            navigationHeight = AndoridSysUtils.getNavigationBarHeigh(this);
        }
        //设置显示的位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnSharePopupViewClick(view);
        //设置背景透明度
        setBackgroundAlpha(0.5f);
    }

    private LinearLayout  share_friend,share_cicle;

    private void setOnSharePopupViewClick(View view) {

        share_friend = view.findViewById(R.id.wx_friend);
        share_cicle = view.findViewById(R.id.wx_circle);

        share_friend.setOnClickListener(this);
        share_cicle.setOnClickListener(this);

    }


    /**
     * 分享朋友圈和朋友
     * @param friendsCircle
     */
    public void share(boolean friendsCircle){

        if (iwxapi != null && iwxapi.isWXAppInstalled()){
            Log.i("微信分享",friendsCircle+"");
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = "http://cmart.koalafield.com/Wechat/ShareApp";//分享url
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = "Cmart跨境超市";
            msg.description = "掌上超市，方便无限";
            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.weixinshare);
            msg.setThumbImage(thumb);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = friendsCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            iwxapi.sendReq(req);
        }else {
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    private void skipOrderActivity(int type) {
        Intent intent = new Intent(this, MartOrderActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    /**
     * 调用拨号功能
     *
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
        if (AndoridSysUtils.checkDeviceHasNavigationBar(this)) {
            navigationHeight = AndoridSysUtils.getNavigationBarHeigh(this);
        }
        //设置显示的位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景透明度
        setBackgroundAlpha(0.3f);
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
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_phone:
                //拨打电话
                String phone = custom_phone.getText().toString().trim();
                if (!StringUtils.isEmpty(phone)) {
                    call(phone);
                }
                disPopuWindow();
                break;
            case R.id.custom_cancel:
                disPopuWindow();
                break;
            case R.id.wx_circle:
                share(true);
                break;
            case  R.id.wx_friend:
                share(false);
                break;
            default:
                break;
        }
    }

    private void disPopuWindow() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

    @Override
    public void onPersonNumberSucessFul(PersonNumber data) {
        if (data != null) {
            int followCount = data.getFollowCount();
            if (followCount <= 99) {
                collection_num.setText(String.valueOf(followCount));
            } else {
                collection_num.setText(String.format(Locale.CHINA, "%d+", 99));
            }
            int couponCount = data.getCouponCount();
            if (couponCount <= 99) {
                discount_num.setText(String.valueOf(followCount));
            } else {
                discount_num.setText(String.format(Locale.CHINA, "%d+", 99));
            }
            int scoreCount = data.getScoreCount();
            if (scoreCount <= 99) {
                counp_num.setText(String.valueOf(scoreCount));
            } else {
                counp_num.setText(String.format(Locale.CHINA, "%d+", 99));
            }
        }
    }

    @Override
    public void onPersonNumberFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            Intent intent = new Intent(PersonActivity.this, LoginActivity.class);
            intent.putExtra("type", 3);
            startActivity(intent);
        }
    }

    @Override
    public void onInfosSucessFul(PersonInfos data) {
        if (data != null) {
            user_name.setText(data.getNickname());
            Glide.with(this).load(data.getAvatar()).placeholder(R.mipmap.mine).error(R.mipmap.mine).into(person_av);
        }
    }

    @Override
    public void onInfosFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            Intent intent = new Intent(PersonActivity.this, LoginActivity.class);
            intent.putExtra("type", 3);
            startActivity(intent);
        }
    }


    @Subscribe(threadMode  = ThreadMode.MAIN)
    public  void loginEvent(LoginEvent event){
        if (event != null){
            Log.i("微信分享成功",event.mType+"");
            int mType = event.mType;
            if (mType == Constants.WX_SHARE){
                int errCode = event.userAggree;
                if(errCode== BaseResp.ErrCode.ERR_USER_CANCEL||errCode==BaseResp.ErrCode.ERR_AUTH_DENIED){
                    Toast.makeText(PersonActivity.this,"取消分享",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PersonActivity.this,"分享成功",Toast.LENGTH_SHORT).show();
                }
                disPopuWindow();
            }
        }
    }

    @Subscribe(threadMode  = ThreadMode.MAIN)
    public  void WxEvent(WxEvent event){
        if (event != null){
            Log.i("微信登录成功",event.mRegister.getNickname()+"头像数据:"+event.mRegister.getAvatar());
            RegisterBean mRegister = event.mRegister;
            if (mRegister != null){
                String avatar = mRegister.getAvatar();
                String nickname = mRegister.getNickname();
                user_name.setText(nickname);
                Glide.with(this).load(avatar).placeholder(R.mipmap.mine).error(R.mipmap.mine).into(person_av);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
