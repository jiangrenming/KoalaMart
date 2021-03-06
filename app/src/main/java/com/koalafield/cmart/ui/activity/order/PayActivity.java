package com.koalafield.cmart.ui.activity.order;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
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
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.BuildConfig;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.PayAdapter;
import com.koalafield.cmart.adapter.PayChooseAdapter;
import com.koalafield.cmart.adapter.TimeRuleAdapter;
import com.koalafield.cmart.adapter.TimerAdapter;
import com.koalafield.cmart.adapter.TimerTypeAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.TimerBean;
import com.koalafield.cmart.bean.event.DisCountEvent;
import com.koalafield.cmart.bean.event.LoginEvent;
import com.koalafield.cmart.bean.order.CreateOrderBean;
import com.koalafield.cmart.bean.order.Delivery;
import com.koalafield.cmart.bean.order.LeftTimer;
import com.koalafield.cmart.bean.order.OrderPrice;
import com.koalafield.cmart.bean.order.PayBean;
import com.koalafield.cmart.bean.order.Payment;
import com.koalafield.cmart.bean.order.Rule;
import com.koalafield.cmart.bean.order.SdkPayBean;
import com.koalafield.cmart.bean.order.ShoppingCart;
import com.koalafield.cmart.bean.order.TimeInterval;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.presenter.order.CreateOrderPresenter;
import com.koalafield.cmart.presenter.order.ICreateOrderPresenter;
import com.koalafield.cmart.presenter.order.IPayPresenter;
import com.koalafield.cmart.presenter.order.IPaySdkPresenter;
import com.koalafield.cmart.presenter.order.IPricePresenter;
import com.koalafield.cmart.presenter.order.PayPresenter;
import com.koalafield.cmart.presenter.order.PaySdkPresenter;
import com.koalafield.cmart.presenter.order.PricePresenter;
import com.koalafield.cmart.presenter.user.IShareCallBackPresent;
import com.koalafield.cmart.presenter.user.ShareCallBackPresent;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.activity.use.AddressManangerActivity;
import com.koalafield.cmart.ui.activity.use.DisCountActivity;
import com.koalafield.cmart.ui.activity.use.ShareActivity;
import com.koalafield.cmart.ui.view.order.ICreateOrderView;
import com.koalafield.cmart.ui.view.order.IPaySdkView;
import com.koalafield.cmart.ui.view.order.IPayView;
import com.koalafield.cmart.ui.view.order.IPriceView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.Constants;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.MyBoundScrollView;

import java.security.spec.ECField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class PayActivity extends BaseActivity implements IPayView<PayBean>, PopupWindow.OnDismissListener, View.OnClickListener,
        IPriceView<OrderPrice>, ICreateOrderView<CreateOrderBean>, IPaySdkView<SdkPayBean> {


    //头部地区
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.back)
    ImageView back;
    //地址区
    @BindView(R.id.empty_adress)
    LinearLayout empty_adress;
    @BindView(R.id.have_name_adress)
    LinearLayout have_name_adress;
    @BindView(R.id.address_name)
    TextView address_name;
    @BindView(R.id.address_phone)
    TextView address_phone;
    @BindView(R.id.have_detail_adress)
    LinearLayout have_detail_adress;
    @BindView(R.id.address_details)
    TextView address_details;
    @BindView(R.id.change_address)
    ImageView change_address;
    @BindView(R.id.name_address)
    LinearLayout name_address;


    //商品区
    @BindView(R.id.goods_pay)
    RecyclerView goods_pay;

    //配送时间区
    @BindView(R.id.change_time)
    TextView change_time;
    @BindView(R.id.select_time)
    TextView select_time;

    //金额区
    @BindView(R.id.count_goods)
    TextView count_goods;
    @BindView(R.id.actual_amount)
    TextView actual_amount;
    @BindView(R.id.rmb_amount)
    TextView rmb_amount;
    @BindView(R.id.comfir_order)
    TextView comfir_order;

    //打折区

    @BindView(R.id.order_total_amount)
    TextView order_total_amount;

    @BindView(R.id.tax_amount)
    TextView tax_amount;
    /*  @BindView(R.id.score_count)
      TextView score_count;*/
    @BindView(R.id.discount_content)
    TextView discount_content;

    @BindView(R.id.choose_pay)
    RecyclerView choose_pay;
    private PayAdapter mPayAdapter;
    private PopupWindow mPopuWindow;
    private IPricePresenter mPricePresenter;
    private String datas;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_pay;
    }

    @Override
    public void initDatas() {
        top_name.setText("确认订单");
        datas = getIntent().getStringExtra("payDatas");
        if (!StringUtils.isEmpty(datas)) {
            IPayPresenter payPresenter = new PayPresenter(this);
            Map<String, String> params = new HashMap<>();
            params.put("scIds", datas);
            payPresenter.setParams(params);
        }

        EventBus.getDefault().register(this);
    }


    @OnClick({R.id.click_address, R.id.change_address, R.id.change_time, R.id.comfir_order, R.id.back,R.id.discount_content})
    public void addAddressClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.click_address:
            case R.id.change_address:
                Intent intent = new Intent(PayActivity.this, AddressManangerActivity.class);
                startActivityForResult(intent, 10010);
                break;
            case R.id.change_time: //配送时间
                openWindow(view);
                break;
            case R.id.comfir_order: //提交订单
                if (addRessId <= 0) {
                    Toast.makeText(PayActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (deliveryId <= 0) {
                    Toast.makeText(PayActivity.this, "请选择配送时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (payId <= 0) {
                    Toast.makeText(PayActivity.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (allowNext()){
                    //创建订单
                    Map<String, String> params = new HashMap<>();
                    params.put("scIds", datas);
                    params.put("deliveryId", String.valueOf(deliveryId));
                    params.put("addressId", String.valueOf(addRessId));
                    params.put("paymentId", String.valueOf(payId));
                    params.put("couponCode ", disCountCode);
                    params.put("bookTime", select_time.getText().toString().trim());
                    params.put("timeId", StringUtils.isEmpty(timeId) ? "0" :timeId);
                    params.put("remark", "");
                    params.put("isUserScore", "false");
                    ICreateOrderPresenter orderPresenter = new CreateOrderPresenter(this);
                    orderPresenter.setParams(params);
                    orderPresenter.getData();
                }else {
                    Toast.makeText(this,"重复点击的时间间隔太短",Toast.LENGTH_SHORT).show();
                    return;
                }

                break;
            case R.id.discount_content:
                Intent intent1 = new Intent(this,CounPonActivity.class);
                startActivityForResult(intent1,10001);
                break;
            default:
                break;
        }
    }

    /**
     * 测试环境是01，正式环境是00
     */
    private String orderNo;
    @Override
    public void onCreateOrderData(CreateOrderBean data) {
        if (data != null) {
             orderNo = data.getOrderNo();
            if (!StringUtils.isEmpty(payName) && payName.equals("CashOnDelivery")) {
                //货到付款直接跳转到详情
                skipOrderActivity(orderNo);
            } else {
                if (!StringUtils.isEmpty(orderNo)) {
                    Map<String, String> params = new HashMap<>();
                    IPaySdkPresenter mPresenter = new PaySdkPresenter(this);
                    params.put("billCode", orderNo);
                    params.put("paymentId", String.valueOf(payId));
                    mPresenter.setParams(params);
                    mPresenter.getData();
                }
            }
        }
    }

    @Override
    public void onPaySdkData(SdkPayBean data) {
        if (data != null) {
            String transactionNo = data.getTransactionNo();
            Log.i("微信支付", payName + "/" + data.getPackage());
            if (!StringUtils.isEmpty(payName) && "WECHAT".equals(payName)) {
                onPayWX(data);
            } else if ("UnionPay".equals(payName)) {
                if (!StringUtils.isEmpty(transactionNo)) {
                    UPPayAssistEx.startPay(PayActivity.this, null, null, transactionNo, BuildConfig.BANK_URL);
           //         finish();
                }
            }
        }
    }

    private void onPayWX(SdkPayBean data) {
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
  //      finish();
    }

    @Override
    public void onPaySdkFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            skipLogin(this);
        }
    }

    @Override
    public void onCreateOrderFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            skipLogin(this);
        }
    }

    private int navigationHeight = 0;

    private void openWindow(View v) {
        //防止重复按按钮
        if (mPopuWindow != null && mPopuWindow.isShowing()) {
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.customer_timer, null);
        mPopuWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景
        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外退出
        mPopuWindow.setFocusable(true);
        mPopuWindow.setOutsideTouchable(true);
        //设置动画
        mPopuWindow.setAnimationStyle(R.style.PopupWindow);
        if (AndoridSysUtils.checkDeviceHasNavigationBar(this)) {
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

    private String date;
    private int deliveryId = 0;
    private TimeInterval timeInterval;
    private String timeId;
    private TextView time_quick;
    private String hintText;
    private  TimeRuleAdapter ruleAdapter;
    private RecyclerView time_categry,time_select;
    private List<Rule> ruleList;

    private void setOnPopupViewClick(View view) {

        RecyclerView timer_type = view.findViewById(R.id.timer_type); //横排配送方式
        time_select = view.findViewById(R.id.time_select);  //左侧时间表
         time_categry = view.findViewById(R.id.time_categry);  //右侧时间表
        time_quick = view.findViewById(R.id.time_quick);  //特殊配送方式
        TextView cancle_dievery = view.findViewById(R.id.cancle_dievery);
        TextView comfirm_dievery = view.findViewById(R.id.comfirm_dievery);
        final TimerTypeAdapter typeAdapter = new TimerTypeAdapter(this, mDelivery);
        RecyclerViewHelper.initRecyclerViewG(this, timer_type, false, typeAdapter, 3);

        for (int i = 0; i <mDelivery.size(); i++) {
           if (mDelivery.get(i).isTypeSelect()){
               ruleList = mDelivery.get(i).getRuleList();
               deliveryId = mDelivery.get(i).getId();
               for (int j = 0; j < ruleList.size(); j++) {
                    hintText = ruleList.get(j).getHintText();
                   if (StringUtils.isEmpty(hintText)) {
                       time_categry.setVisibility(View.VISIBLE);
                       time_select.setVisibility(View.VISIBLE);
                       time_quick.setVisibility(View.GONE);
                   } else {
                       time_select.setVisibility(View.GONE);
                       time_categry.setVisibility(View.GONE);
                       time_quick.setVisibility(View.VISIBLE);
                       time_quick.setText(hintText);
                   }
               }
           }
        }

        //左侧时间表
        setTimer(time_select,ruleList);

        cancle_dievery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackgroundAlpha(1);
                disPopuWindow();
            }
        });

        comfirm_dievery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isEmpty(hintText)) {
                    if (null == timeInterval) {
                        Toast.makeText(PayActivity.this, "请选择配送时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    disPopuWindow();
                    select_time.setText(hintText);
                }
            }
        });


        typeAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Delivery delivery = mDelivery.get(position);
                deliveryId = delivery.getId();
                for (int i = 0; i < mDelivery.size(); i++) {
                    if (deliveryId == mDelivery.get(i).getId()) {
                        mDelivery.get(i).setTypeSelect(true);
                    } else {
                        mDelivery.get(i).setTypeSelect(false);
                    }
                }
                typeAdapter.updateItems(mDelivery);
                upDate(delivery);
            }
        });
    }

    private  void upDate ( Delivery delivery){

        List<Rule> ruleList = delivery.getRuleList();
        if (ruleList != null && ruleList.size() >0){
            for (int i = 0; i < ruleList.size(); i++) {
                 hintText = ruleList.get(i).getHintText();
                if (StringUtils.isEmpty(hintText)){
                    time_categry.setVisibility(View.VISIBLE);
                    time_select.setVisibility(View.VISIBLE);
                    time_quick.setVisibility(View.GONE);
                    List<LeftTimer> availableChooseDateTime = ruleList.get(i).getAvailableChooseDateTime();
                    for (int j = 0; j < availableChooseDateTime.size(); j++) {
                        if (j == 0){
                            availableChooseDateTime.get(j).setSelect(true);
                        }else {
                            availableChooseDateTime.get(j).setSelect(false);
                        }
                    }
                }else {
                    time_select.setVisibility(View.GONE);
                    time_categry.setVisibility(View.GONE);
                    time_quick.setVisibility(View.VISIBLE);
                    time_quick.setText(hintText);
                }
            }
            if (time_categry.getVisibility() == View.VISIBLE && time_select.getVisibility() == View.VISIBLE){

                availableChooseDateTime = ruleList.get(0).getAvailableChooseDateTime();
                if (timeAdapter != null){

                    timeAdapter.updateItems(availableChooseDateTime);
                }
                timeList = availableChooseDateTime.get(0).getTimeList();
                date = availableChooseDateTime.get(0).getDateStr();
                if (ruleAdapter != null){
                   ruleAdapter.updateItems(timeList);
                }
            }
        }else {
            ruleAdapter.cleanItems();
        }
    }

    /**
     * 左侧/右侧的时间
     * @param time_select
     * @param ruleList
     */
    private TimerAdapter timeAdapter;
    private    List<LeftTimer> availableChooseDateTime;
    private    List<TimeInterval> timeList;

    private void setTimer(RecyclerView time_select,List<Rule> ruleList) {


        for (int i = 0; i < ruleList.size(); i++) {
            List<LeftTimer> availableChooseDateTime = ruleList.get(i).getAvailableChooseDateTime();
            for (int j = 0; j < availableChooseDateTime.size(); j++) {
                if (j == 0){
                    availableChooseDateTime.get(j).setSelect(true);
                }else {
                    availableChooseDateTime.get(j).setSelect(false);
                }
            }
        }

        //左侧的时间
        availableChooseDateTime = ruleList.get(0).getAvailableChooseDateTime();
        timeAdapter = new TimerAdapter(this, availableChooseDateTime);
        RecyclerViewHelper.initRecyclerViewV(this, time_select, false, timeAdapter);
        date = availableChooseDateTime.get(0).getDateStr();

        //右侧的时间
        timeList = availableChooseDateTime.get(0).getTimeList();
        ruleAdapter = new TimeRuleAdapter(this, timeList);
        RecyclerViewHelper.initRecyclerViewV(this, time_categry, false, ruleAdapter);

        timeAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LeftTimer leftTimer = availableChooseDateTime.get(position);
                date = leftTimer.getDateStr();
                for (int i = 0; i < availableChooseDateTime.size(); i++) {
                    if (availableChooseDateTime.get(i).getDateStr().equals(date)) {
                        availableChooseDateTime.get(i).setSelect(true);
                    } else {
                        availableChooseDateTime.get(i).setSelect(false);
                    }
                }
                if (timeAdapter != null){
                    timeAdapter.updateItems(availableChooseDateTime);
                }
                for (int i = 0; i <availableChooseDateTime.size() ; i++) {
                    if (availableChooseDateTime.get(i).isSelect()){
                        timeList = availableChooseDateTime.get(i).getTimeList();
                        if (ruleAdapter!= null){
                            ruleAdapter.updateItems(timeList);
                        }
                    }
                }
            }
        });

        ruleAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                timeInterval = timeList.get(position);
                if (timeInterval != null){
                    timeId = timeInterval.getTimeId();
                    Log.i("选择的时间为：",timeId+"");
                }
                disPopuWindow();
                if (timeInterval != null && StringUtils.isEmpty(timeInterval.getEndTime())){
                    select_time.setText(date + " " + timeInterval.getStartTime());
                }else {
                    select_time.setText(date + " " + timeInterval.getStartTime() + "-" + timeInterval.getEndTime());
                }
                if (deliveryId > 0 && payId >0) {
                    changePrice();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this.getWindow().setAttributes(lp);
    }

    @Override
    public void upDateViews() {
    }

    private List<Delivery> mDelivery;
    private int payId = 0;
    private String payName;

    @Override
    public void onSubmitList(PayBean data) {
        if (data != null) {
            int allCount = 0;
            List<ShoppingCart> shoppingCartDTOList = data.getShoppingCartDTOList();
            if (shoppingCartDTOList != null && shoppingCartDTOList.size() > 0) {
                if (mPayAdapter == null) {
                    mPayAdapter = new PayAdapter(this, shoppingCartDTOList);
                    RecyclerViewHelper.initRecyclerViewV(this, goods_pay, false, mPayAdapter);
                } else {
                    mPayAdapter.cleanItems();
                    mPayAdapter.addItems(shoppingCartDTOList);
                }
                for (int i = 0; i < shoppingCartDTOList.size(); i++) {
                    allCount += shoppingCartDTOList.get(i).getCount();
                }
            }
            count_goods.setText("共" + allCount + "件");
            actual_amount.setText("实际付款：" + data.getOrderPriceDTO().getCurrency()  + String.format("%.2f", data.getOrderPriceDTO().getTotalPrice()));
            rmb_amount.setText("约合RMB: ¥" + String.format("%.2f", data.getOrderPriceDTO().getTotalPriceAfterDiscount() * data.getOrderPriceDTO().getRate()));
            order_total_amount.setText(data.getOrderPriceDTO().getCurrency()+ String.format("%.2f", data.getOrderPriceDTO().getTotalGoodsPrice()));
            tax_amount.setText(data.getOrderPriceDTO().getCurrency() +String.format("%.2f", data.getOrderPriceDTO().getDeliveryPrice()));
           /* if (data.isAllowUseScore()){
                score_count.setVisibility(View.VISIBLE);
                score_count.setText(String.valueOf(data.getAvailableScore()));
            }else {
                score_count.setVisibility(View.GONE);
            }*/
            mDelivery = data.getDeliveryListDTO();
            if (mDelivery != null && mDelivery.size() > 0) {
                for (int i = 0; i < mDelivery.size(); i++) {
                    if (i == 0) {
                        mDelivery.get(i).setTypeSelect(true);
                    } else {
                        mDelivery.get(i).setTypeSelect(false);
                    }
                }
            }
        }
        final List<Payment> mPayment = data.getPaymentDTOList();
        if (mPayment != null && mPayment.size() > 0) {
            for (int i = 0; i < mPayment.size(); i++) {

                mPayment.get(i).setSelect(false);
            }
            final PayChooseAdapter chooseAdapter = new PayChooseAdapter(this, mPayment);
            RecyclerViewHelper.initRecyclerViewV(PayActivity.this, choose_pay, true, chooseAdapter);
            chooseAdapter.setmPayCallBack(new PayChooseAdapter.PayClickCallBack() {
                @Override
                public void onSucess(Payment item, boolean isSelect) {
                    for (int i = 0; i < mPayment.size(); i++) {
                        Payment payment = mPayment.get(i);
                        if (payment.getId() == item.getId()) {
                            payment.setSelect(isSelect);
                        } else {
                            if (payment.isSelect()) {
                                payment.setSelect(false);
                            }
                        }
                    }
                    chooseAdapter.updateItems(mPayment);
                    for (int i = 0; i < mPayment.size(); i++) {
                        if (mPayment.get(i).isSelect()) {
                            payId = mPayment.get(i).getId();
                            payName = mPayment.get(i).getPaymentName();
                        }
                    }
                    //改变价格
                    if (payId > 0 && deliveryId > 0) {
                        changePrice();
                    }
                }
            });
        }
    }

    @Override
    public void onSubmitFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            skipLogin(this);
        }
    }


    private String disCountCode;
    private int addRessId = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10010) {
                AddressManagerBean addressManagerBean = (AddressManagerBean) data.getSerializableExtra("address");
                if (addressManagerBean != null) {
                    empty_adress.setVisibility(View.GONE);
                    name_address.setVisibility(View.VISIBLE);
                    address_name.setText(addressManagerBean.getContactname());
                    address_phone.setText(addressManagerBean.getContactphone());
                    address_details.setText(addressManagerBean.getCountry() + " " + addressManagerBean.getCity() + addressManagerBean.getAddress());
                    addRessId = addressManagerBean.getId();
                }
            } else if (requestCode == 10001) {
                DisCountBean disCount = (DisCountBean) data.getSerializableExtra("disCount");
                if (disCount != null) {
                    discount_content.setText("满" + String.format("%.2f", disCount.getMinBillUseTotalPrice()) + "减" + String.format("%.2f", disCount.getAmount()));
                    disCountCode = disCount.getCode();
                    if (payId > 0 && deliveryId > 0) {
                        changePrice();
                    }
                }
            } else {
                if (data == null) {
                    return;
                }
                String msg = "";
                String str = data.getExtras().getString("pay_result");
                Log.i("微信支付",str);
                if (str.equalsIgnoreCase("success")) {
                    if (data.hasExtra("result_data")) {
                        String result = data.getExtras().getString("result_data");
                        try {
                            JSONObject resultJson = new JSONObject(result);
                            String sign = resultJson.getString("sign");
                            String dataOrg = resultJson.getString("data");
                            // 验签成功，显示支付结果
                            msg = "支付成功！";
                        } catch (JSONException e) {
                        }
                    }
                    // 结果result_data为成功时，去商户后台查询一下再展示成功
                    msg = "支付成功！";
                } else if (str.equalsIgnoreCase("fail")) {
                    msg = "支付失败！";
                    skipOrderActivity(orderNo);
                } else if (str.equalsIgnoreCase("cancel")) {
                    msg = "用户取消了支付";
                    skipOrderActivity(orderNo);
                }
                Toast.makeText(PayActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 支付失败，取消支付的操作
     * @param orderNo
     */
    private  void skipOrderActivity(String orderNo){
        //货到付款直接跳转到详情
        Intent intent = new Intent(PayActivity.this, OrderDetailsActivity.class);
        intent.putExtra("billNo", orderNo);
        startActivity(intent);
        finish();
    }

    /**
     * 变更价格
     */
    private void changePrice() {
        Map<String, String> params = new HashMap<>();
        params.put("scIds", datas);
        params.put("deliveryId", String.valueOf(deliveryId));
        params.put("addressId", String.valueOf(addRessId));
        params.put("paymentId", String.valueOf(payId));
        params.put("couponCode", disCountCode);
        params.put("timeId", StringUtils.isEmpty(timeId) ? "0" :timeId);
        mPricePresenter = new PricePresenter(this);
        mPricePresenter.setParams(params);
        mPricePresenter.getData();
    }

    @Override
    public void onDismiss() {
        disPopuWindow();
        setBackgroundAlpha(1);
    }

    private void disPopuWindow() {
        if (null != this && null != mPopuWindow && mPopuWindow.isShowing()) {
            try{
                mPopuWindow.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onPriceData(OrderPrice data) {
        actual_amount.setText("实际付款: " + data.getCurrency() + String.format("%.2f", data.getTotalPrice()));
        rmb_amount.setText("约合RMB:¥ " + String.format("%.2f", data.getTotalPriceAfterDiscount() * data.getRate()));
        order_total_amount.setText(data.getCurrency()+ String.format("%.2f", data.getTotalGoodsPrice()));
        tax_amount.setText(data.getCurrency() + String.format("%.2f", data.getDeliveryPrice()));

    }

    @Override
    public void onPriceFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            skipLogin(this);
        }
    }

    @Subscribe(threadMode  = ThreadMode.MAIN)
    public  void loginEvent(LoginEvent event){
        if (event != null){
            int mType = event.mType;
            Log.i("支付结果",mType+""+event.userAggree);
            if (mType == Constants.WX_PAY){
                int errCode = event.userAggree;
                if(errCode== BaseResp.ErrCode.ERR_OK){  //微信支付成功
                    finish();
                }else{  //失败或取消
                    skipOrderActivity(orderNo);
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
