package com.koalafield.cmart.ui.activity.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
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

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.BuildConfig;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.OrderAdapter;
import com.koalafield.cmart.adapter.PayChooseAdapter;
import com.koalafield.cmart.adapter.PayOrderChooseAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.HistoryContent;
import com.koalafield.cmart.bean.event.UpdateEvent;
import com.koalafield.cmart.bean.order.OrderAdress;
import com.koalafield.cmart.bean.order.OrderDetailsInfo;
import com.koalafield.cmart.bean.order.OrderPrice;
import com.koalafield.cmart.bean.order.OrderdetailsBean;
import com.koalafield.cmart.bean.order.Payment;
import com.koalafield.cmart.bean.order.SdkPayBean;
import com.koalafield.cmart.db.HistoryService;
import com.koalafield.cmart.db.IHistoryService;
import com.koalafield.cmart.presenter.order.CancleOrderPresenter;
import com.koalafield.cmart.presenter.order.ComfirmOrderPresenter;
import com.koalafield.cmart.presenter.order.ICancleOrderPresenter;
import com.koalafield.cmart.presenter.order.IComfirmOrderPresenter;
import com.koalafield.cmart.presenter.order.IOrderDetailsPresenter;
import com.koalafield.cmart.presenter.order.IPaySdkPresenter;
import com.koalafield.cmart.presenter.order.OrderDetailsPresenter;
import com.koalafield.cmart.presenter.order.PaySdkPresenter;
import com.koalafield.cmart.service.TimeService;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.use.PersonSettingActivity;
import com.koalafield.cmart.ui.view.order.ICancleOrderView;
import com.koalafield.cmart.ui.view.order.IComfirmOrderView;
import com.koalafield.cmart.ui.view.order.IOrderDetailsView;
import com.koalafield.cmart.ui.view.order.IPaySdkView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.AndroidTools;
import com.koalafield.cmart.utils.Constants;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.CommonDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author jiangrenming
 * @date 2018/5/29
 */

public class OrderDetailsActivity extends BaseActivity implements IOrderDetailsView<OrderdetailsBean>,IPaySdkView<SdkPayBean> ,
        PopupWindow.OnDismissListener,IComfirmOrderView<BaseResponseBean>,ICancleOrderView<BaseResponseBean>{

    //头部布局
    @BindView(R.id.back)
    ImageView  back;
    @BindView(R.id.cancle_order)
    TextView cancle_order;
    @BindView(R.id.has_time)
    TextView has_time;
    @BindView(R.id.order_address_name)
    TextView order_address_name;
    @BindView(R.id.order_address_phone)
    TextView order_address_phone;
    @BindView(R.id.order_address_details)
    TextView order_address_details;

    //中间布局
    @BindView(R.id.order_detail_recy)
    RecyclerView order_detail_recy;
    @BindView(R.id.statue_txt)
    TextView statue_txt;

    //底部布局
    @BindView(R.id.order_tax)
    TextView order_tax;
    @BindView(R.id.order_discount)
    TextView order_discount;
    @BindView(R.id.order_all_price)
    TextView order_all_price;
    @BindView(R.id.order_num)
    TextView order_num;
    @BindView(R.id.order_time)
    TextView order_time;
    @BindView(R.id.devilery_time)
    TextView devilery_time;
    @BindView(R.id.devilery_style)
    TextView devilery_style;
    @BindView(R.id.once_pay)
    TextView once_pay;

    @BindView(R.id.pay_style)
    TextView pay_style;
    @BindView(R.id.pay_time)
    TextView pay_time;

    @BindView(R.id.devilery_style_layout)
    LinearLayout devilery_style_layout;
    @BindView(R.id.devilery_time_layout)
    LinearLayout devilery_time_layout;
    @BindView(R.id.pay_time_layout)
    LinearLayout pay_time_layout;
    @BindView(R.id.pay_style_layout)
    LinearLayout pay_style_layout;

    private  String billNo;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initDatas() {
        billNo = getIntent().getStringExtra("billNo");
        EventBus.getDefault().register(this);

    }

    @Override
    public void upDateViews() {
        IOrderDetailsPresenter detailsPresenter = new OrderDetailsPresenter(this);
        if (StringUtils.isEmpty(billNo)){
            billNo = "";
        }
        Map<String,String> params = new HashMap<>();
        params.put("billNo",billNo);
        detailsPresenter.setParams(params);
        detailsPresenter.getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private long mTime ;
    @Subscribe(threadMode  = ThreadMode.MAIN)
    public  void changeTime(UpdateEvent event){
        Log.i("获取的数据",event.mTime+"");
        if (event != null){
            if (event.mType.equals(Constants.IN_RUNNING)){
                  mTime = event.mTime;
                 String timer = AndroidTools.formatMillisecondAllDate(mTime);
                 Log.i("转换的时间为：",timer);
                // 正在倒计时
                has_time.setText("倒计时还剩:" + timer);
                if (limit_time != null){
                    limit_time.setText("请在"+timer+"后完成支付");
                }
            }else if (event.mType.equals(Constants.END_RUNNING)){
                has_time.setText("订单已过期");
                has_time.setTextColor(getResources().getColor(R.color.black));
                has_time.setBackgroundColor(getResources().getColor(R.color.gray));
                cancle_order.setVisibility(View.GONE);
                once_pay.setVisibility(View.GONE);
                statue_txt.setVisibility(View.GONE);
            }
        }
    }
    @OnClick({R.id.back,R.id.once_pay,R.id.cancle_order})
    public void orderDetaiClck(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case  R.id.once_pay:
                if (once_pay.getVisibility() == View.VISIBLE){
                    if (detailsInfo.getStatus() == 2){
                        //确认收货
                        if (allowNext()){
                            Map<String,String> params = new HashMap();
                            params.put("billNo",billNo);
                            IComfirmOrderPresenter comfirmOrderPresenter = new ComfirmOrderPresenter(this);
                            comfirmOrderPresenter.setParams(params);
                            comfirmOrderPresenter.getData();
                        }else {
                            Toast.makeText(this,"重复点击的时间间隔太短",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else {
                        if (payList != null && payList.size() > 0){
                            openWindow(view);
                        }else {
                            Toast.makeText(OrderDetailsActivity.this,"支付方式为空",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                break;
            case R.id.cancle_order:
                if (cancle_order.getVisibility() == View.VISIBLE){
                    new CommonDialog(this).builder().setTitle("取消订单").setMsg("订单取消后，交易将关闭").setNegativeButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String,String> params = new HashMap<>();
                            params.put("billNo",billNo);
                            ICancleOrderPresenter cancleOrderPresenter = new CancleOrderPresenter(OrderDetailsActivity.this);
                            cancleOrderPresenter.setParams(params);
                            cancleOrderPresenter.getData();
                        }
                    }).setPositiveButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
                }
                break;
            default:
                break;

        }
    }

    private int  paymentId;
    private int navigationHeight = 0;
    private PopupWindow mPopuWindow;
    private List<Payment> payList;
    private void openWindow(View v) {
        //防止重复按按钮
        if (mPopuWindow != null && mPopuWindow.isShowing()) {
            return;
        }
        View view =  LayoutInflater.from(this).inflate(R.layout.choose_pay, null);
        mPopuWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景
        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外退出
        mPopuWindow.setFocusable(true);
        mPopuWindow.setOutsideTouchable(true);
        //设置动画
        mPopuWindow.setAnimationStyle(R.style.PopupWindow);
        if (AndoridSysUtils.checkDeviceHasNavigationBar(this)){
            navigationHeight = AndoridSysUtils.getNavigationBarHeigh(this);
        }
        //设置显示的位置
        mPopuWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        mPopuWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景透明度
        setBackgroundAlpha(0.5f);
    }

    private TextView limit_time;
    private String payName;
    private void setOnPopupViewClick(View view) {
         RecyclerView payChoose = view.findViewById(R.id.choose_pay);
         limit_time = view.findViewById(R.id.limit_time);
        ImageView close = view.findViewById(R.id.close);
        TextView  comfirm_pay = view.findViewById(R.id.comfirm_pay);
        comfirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopuWindow != null &&mPopuWindow.isShowing()){
                    mPopuWindow.dismiss();
                }
                if (allowNext()){
                    Map<String,String> params = new HashMap<>();
                    IPaySdkPresenter mPresenter = new PaySdkPresenter(OrderDetailsActivity.this);
                    params.put("billCode",billNo) ;
                    params.put("paymentId",String.valueOf(paymentId)) ;
                    mPresenter.setParams(params);
                    mPresenter.getData();
                }else {
                    Toast.makeText(OrderDetailsActivity.this,"重复点击的时间间隔太短",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopuWindow != null && mPopuWindow.isShowing()){
                    mPopuWindow.dismiss();
                    setBackgroundAlpha(1.0f);
                }
            }
        });
        if (payList!= null && payList.size() > 0){
            final PayOrderChooseAdapter payAdapter = new PayOrderChooseAdapter(this,payList);
            RecyclerViewHelper.initRecyclerViewV(this,payChoose,true,payAdapter);
            payAdapter.setmPayCallBack(new PayOrderChooseAdapter.PayClickCallBack() {
                @Override
                public void onSucess(Payment item, boolean isSelect) {
                    for (int i = 0; i < payList.size(); i++) {
                        Payment payment = payList.get(i);
                        if (payment.getId() == item.getId()){
                            payment.setSelect(isSelect);
                        }else {
                            if (payment.isSelect()){
                                payment.setSelect(false);
                            }
                        }
                    }
                    payAdapter.updateItems(payList);
                    for (int i = 0; i < payList.size(); i++) {
                        if (payList.get(i).isSelect()){
                            paymentId = payList.get(i).getId();
                            payName = payList.get(i).getPaymentName();
                        }
                    }
                }

            });
        }

    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this. getWindow().setAttributes(lp);
    }

    private OrderDetailsInfo detailsInfo;
    @Override
    public void onOrderDetails(OrderdetailsBean data) {
        if (data != null){
             detailsInfo = data.getDetailsInfo();
            payList = data.getPaymentDTOList();
            if (detailsInfo != null && detailsInfo.getGoodsList() != null && detailsInfo.getGoodsList().size() >0){
                order_num.setText(detailsInfo.getBillNo());
                order_time.setText(detailsInfo.getCreatedTime());
                OrderAdapter mAdapter = new OrderAdapter(this,data.getDetailsInfo().getGoodsList());
                RecyclerViewHelper.initRecyclerViewV(OrderDetailsActivity.this,order_detail_recy,true,mAdapter);

                if (detailsInfo.getStatus() == 0){  //待付款
                    has_time.setVisibility(View.VISIBLE);
                    cancle_order.setVisibility(View.VISIBLE);
                    devilery_time_layout.setVisibility(View.VISIBLE);
                    devilery_style_layout.setVisibility(View.VISIBLE);
                    pay_style_layout.setVisibility(View.GONE);
                    pay_time_layout.setVisibility(View.GONE);
                    once_pay.setVisibility(View.VISIBLE);
                    devilery_time.setText(detailsInfo.getBookDeliveryTime());
                    devilery_style.setText(data.getDelivery().getDeliveryName());
                }else if (detailsInfo.getStatus() == 1){  //代发货
                    has_time.setVisibility(View.GONE);
                    cancle_order.setVisibility(View.GONE);
                    devilery_time_layout.setVisibility(View.VISIBLE);
                    devilery_style_layout.setVisibility(View.VISIBLE);
                    pay_style_layout.setVisibility(View.VISIBLE);
                    pay_time_layout.setVisibility(View.VISIBLE);
                    once_pay.setVisibility(View.GONE);
                    pay_time.setText(detailsInfo.getPayTime());
                    pay_style.setText(detailsInfo.getPaymentName());
                    devilery_time.setText(detailsInfo.getBookDeliveryTime());
                    devilery_style.setText(data.getDelivery().getDeliveryName());
                }else if (detailsInfo.getStatus() == 2){  //待收货
                    has_time.setVisibility(View.GONE);
                    once_pay.setVisibility(View.VISIBLE);
                    cancle_order.setVisibility(View.GONE);
                    once_pay.setText("确认收货");
                    devilery_time_layout.setVisibility(View.VISIBLE);
                    devilery_style_layout.setVisibility(View.VISIBLE);
                    pay_style_layout.setVisibility(View.VISIBLE);
                    pay_time_layout.setVisibility(View.VISIBLE);
                    pay_time.setText(detailsInfo.getPayTime());
                    pay_style.setText(detailsInfo.getPaymentName());
                    devilery_time.setText(detailsInfo.getBookDeliveryTime());
                    devilery_style.setText(data.getDelivery().getDeliveryName());
                }else if(detailsInfo.getStatus() == 4){  //已完成
                    has_time.setVisibility(View.GONE);
                    cancle_order.setVisibility(View.GONE);
                    once_pay.setVisibility(View.GONE);
                    devilery_time_layout.setVisibility(View.VISIBLE);
                    devilery_style_layout.setVisibility(View.VISIBLE);
                    pay_style_layout.setVisibility(View.VISIBLE);
                    pay_time_layout.setVisibility(View.VISIBLE);
                    pay_time.setText(detailsInfo.getPayTime());
                    pay_style.setText(detailsInfo.getPaymentName());
                    devilery_time.setText(detailsInfo.getBookDeliveryTime());
                    devilery_style.setText(data.getDelivery().getDeliveryName());
                }
                statue_txt.setText(detailsInfo.getStatusText());
            }
            OrderAdress addressDTO = data.getAddressDTO();
            if (addressDTO != null){
                order_address_name.setText(addressDTO.getContactname());
                order_address_phone.setText(addressDTO.getContactphone());
                order_address_details.setText(addressDTO.getCountry()+" "+addressDTO.getCity() +addressDTO.getAddress());
            }
            OrderPrice orderPrice = data.getOrderPrice();
            if (orderPrice != null){
                order_tax.setText("AUD "+String.format("%.2f",orderPrice.getDeliveryPrice()));
                if (orderPrice.getCouponPrice() == 0 || orderPrice.getCouponPrice() == 0.00){
                    order_discount.setVisibility(View.GONE);
                }else {
                    order_discount.setVisibility(View.VISIBLE);
                    order_discount.setText("AUD "+String.format("%.2f",orderPrice.getCouponPrice()));
                }
                order_all_price.setText("AUD "+String.format("%.2f",orderPrice.getTotalPrice()));
            }
            if (!AndroidTools.isServiceRunning(this,"com.koalafield.cmart.service.TimeService")){
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Intent intent = new Intent(OrderDetailsActivity.this,TimeService.class);
                        intent.putExtra("time",detailsInfo.getExpirePayTimeStamp());
                        startService(intent);
                    }
                }.start();
            }
        }
    }

    @Override
    public void onOrderDetailsFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(OrderDetailsActivity.this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    @Override
    public void onPaySdkData(SdkPayBean data) {
        if (data != null){
            String transactionNo = data.getTransactionNo();
            if (!StringUtils.isEmpty(payName) && "WECHAT".equals(payName)){
                onPayWX(data);
            }else if ("UnionPay".equals(payName)){
                if (!StringUtils.isEmpty(transactionNo)){
                    UPPayAssistEx.startPay(OrderDetailsActivity.this,null,null,transactionNo, BuildConfig.BANK_URL);
                    finish();
                }
            }else if ("CashOnDelivery".equals(payName)){  //货到付款

            }
        }
    }
    private  void onPayWX(SdkPayBean data){
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this, data.getAppId());
        msgApi.registerApp(data.getAppId());
        PayReq req = new PayReq();
        req.appId = data.getAppId();
        req.partnerId = data.getPartnerId();
        req.prepayId = data.getPrepayId();
        req.nonceStr = data.getNonceStr();
        req.timeStamp = data.getTimeStamp();
        req.sign = data.getPaySign();
        req.signType = data.getSignType();
        req.packageValue = data.getPackage();
        msgApi.sendReq(req);
    }
    @Override
    public void onPaySdkFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(OrderDetailsActivity.this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    @Override
    public void onDismiss() {
        if (null != mPopuWindow && mPopuWindow .isShowing()){
            mPopuWindow.dismiss();
        }
        setBackgroundAlpha(1.0f);
    }

    @Override
    public void onComfirmOrderList(BaseResponseBean data) {
        if (data != null && data.getCode() == 200){
            Toast.makeText(this,"确认收货成功",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onFailureComfirmOrder(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }

    }

    @Override
    public void onSucessCancleOrder(BaseResponseBean data) {
        if (data != null && data.getCode() == 200){
            Toast.makeText(this,"取消订单成功",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onFailureCancleOrder(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }
}
