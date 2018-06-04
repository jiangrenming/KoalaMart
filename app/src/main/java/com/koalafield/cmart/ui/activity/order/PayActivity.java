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
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.PayAdapter;
import com.koalafield.cmart.adapter.PayChooseAdapter;
import com.koalafield.cmart.adapter.TimeRuleAdapter;
import com.koalafield.cmart.adapter.TimerAdapter;
import com.koalafield.cmart.adapter.TimerTypeAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.TimerBean;
import com.koalafield.cmart.bean.order.CreateOrderBean;
import com.koalafield.cmart.bean.order.Delivery;
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
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.activity.use.AddressManangerActivity;
import com.koalafield.cmart.ui.activity.use.DisCountActivity;
import com.koalafield.cmart.ui.view.order.ICreateOrderView;
import com.koalafield.cmart.ui.view.order.IPaySdkView;
import com.koalafield.cmart.ui.view.order.IPayView;
import com.koalafield.cmart.ui.view.order.IPriceView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.MyBoundScrollView;

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
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class PayActivity extends BaseActivity implements IPayView<PayBean>,PopupWindow.OnDismissListener,View.OnClickListener,
        IPriceView<OrderPrice>,ICreateOrderView<CreateOrderBean>,IPaySdkView<SdkPayBean>{


    //头部地区
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.back)
    ImageView back;
    //地址区
    @BindView(R.id.empty_adress)
    LinearLayout  empty_adress;
    @BindView(R.id.have_name_adress)
    LinearLayout  have_name_adress;
    @BindView(R.id.address_name)
    TextView address_name;
    @BindView(R.id.address_phone)
    TextView address_phone;
    @BindView(R.id.have_detail_adress)
    LinearLayout  have_detail_adress;
    @BindView(R.id.address_details)
    TextView address_details;
    @BindView(R.id.change_address)
    ImageView change_address;


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
    TextView  comfir_order;

    //打折区
    @BindView(R.id.order_curreny)
    TextView order_curreny;
    @BindView(R.id.order_total_amount)
    TextView order_total_amount;
    @BindView(R.id.order_tax_curreny)
    TextView order_tax_curreny;
    @BindView(R.id.tax_amount)
    TextView tax_amount;
  /*  @BindView(R.id.score_count)
    TextView score_count;*/
    @BindView(R.id.discount_content)
    TextView discount_content;
    @BindView(R.id.skip_discount)
    ImageView skip_discount;
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
        AddressManagerBean managerBean = ShareBankPreferenceUtils.getObject("addressId", AddressManagerBean.class);
        if (managerBean != null){
            empty_adress.setVisibility(View.GONE);
            have_name_adress.setVisibility(View.VISIBLE);
            have_detail_adress.setVisibility(View.VISIBLE);
            address_name.setText(managerBean.getContactname());
            address_phone.setText(managerBean.getContactphone());
            address_details.setText(managerBean.getCountry()+managerBean.getCity()+managerBean.getAddress());
        }else {
            empty_adress.setVisibility(View.VISIBLE);
            have_name_adress.setVisibility(View.GONE);
            have_detail_adress.setVisibility(View.GONE);
        }

         datas = getIntent().getStringExtra("payDatas");
        if (!StringUtils.isEmpty(datas)){
            IPayPresenter payPresenter = new PayPresenter(this);
            Map<String,String> params = new HashMap<>();
            params.put("scIds",datas);
            payPresenter.setParams(params);
        }

    }


    @OnClick({R.id.click_address,R.id.change_address,R.id.skip_discount,R.id.change_time,R.id.comfir_order})
    public  void addAddressClick(View view){
        switch (view.getId()){
            case  R.id.click_address:
            case R.id.change_address:
                Intent intent = new Intent(PayActivity.this, AddressManangerActivity.class);
                startActivityForResult(intent,10000);
                break;
            case R.id.skip_discount: //满减
               startActivityForResult(new Intent(PayActivity.this, DisCountActivity.class),10001);
                break;
            case R.id.change_time: //配送时间
                openWindow(view);
                break;
            case R.id.comfir_order: //提交订单
                if (addRessId <= 0){
                    Toast.makeText(PayActivity.this,"请选择地址",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (deliveryId <= 0){
                    Toast.makeText(PayActivity.this,"请选择配送时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (payId <= 0){
                    Toast.makeText(PayActivity.this,"请选择支付方式",Toast.LENGTH_SHORT).show();
                    return;
                }
                //创建订单
                Map<String,String> params = new HashMap<>();
                params.put("scIds",datas);
                params.put("deliveryId",String.valueOf(deliveryId));
                params.put("addressId",String.valueOf(addRessId));
                params.put("paymentId",String.valueOf(payId));
                params.put("couponCode ",disCountCode);
                params.put("bookTime","");
                params.put("remark","");
                params.put("isUserScore","false");
                ICreateOrderPresenter orderPresenter = new CreateOrderPresenter(this);
                orderPresenter.setParams(params);
                orderPresenter.getData();
                break;
            default:
                break;
        }
    }

    /**
     * 测试环境是01，正式环境是00
     */
    private String serVerMode = "01";
    @Override
    public void onCreateOrderData(CreateOrderBean data) {
        if (data != null){
            String orderNo = data.getOrderNo();
            if (!StringUtils.isEmpty(orderNo)){
                Map<String,String> params = new HashMap<>();
                IPaySdkPresenter mPresenter = new PaySdkPresenter(this);
                params.put("billCode",orderNo) ;
                params.put("paymentId",String.valueOf(payId)) ;
                mPresenter.setParams(params);
                mPresenter.getData();
            }
        }
    }
    @Override
    public void onPaySdkData(SdkPayBean data) {
        if (data != null){
            String transactionNo = data.getTransactionNo();
            if (!StringUtils.isEmpty(transactionNo)){
                UPPayAssistEx.startPay(PayActivity.this,null,null,transactionNo,serVerMode);
                finish();
            }
        }
    }

    @Override
    public void onPaySdkFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(PayActivity.this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOrderFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(PayActivity.this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    private int navigationHeight = 0;
    private void openWindow(View v) {
        //防止重复按按钮
        if (mPopuWindow != null && mPopuWindow.isShowing()) {
            return;
        }
        View view =  LayoutInflater.from(this).inflate(R.layout.customer_timer, null);
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

    private String date;
    private  int deliveryId = 0;
    private List<TimeInterval> timeIntervalList;
    private void setOnPopupViewClick(View view) {
            RecyclerView timer_type = view.findViewById(R.id.timer_type);
            RecyclerView time_select = view.findViewById(R.id.time_select);
            final RecyclerView time_categry = view.findViewById(R.id.time_categry);

            final TimerTypeAdapter typeAdapter = new TimerTypeAdapter(this,mDelivery);
            RecyclerViewHelper.initRecyclerViewG(this,timer_type,false,typeAdapter,3);

            timeIntervalList = mDelivery.get(0).getRuleList().get(0).getTimeIntervalList();
            final TimeRuleAdapter ruleAdapter = new TimeRuleAdapter(this,timeIntervalList);
            RecyclerViewHelper.initRecyclerViewV(this,time_categry,true,ruleAdapter);

            setTimer(time_select);

            deliveryId = mDelivery.get(0).getId();
            typeAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Delivery delivery = mDelivery.get(position);
                     deliveryId = delivery.getId();
                    for (int i = 0; i < mDelivery.size(); i++) {
                        if (deliveryId == mDelivery.get(i).getId()){
                            mDelivery.get(i).setTypeSelect(true);
                        }else {
                            mDelivery.get(i).setTypeSelect(false);
                        }
                    }
                    typeAdapter.updateItems(mDelivery);

                    List<Rule> ruleList = delivery.getRuleList();
                    if (ruleList != null && ruleList.size()>0){
                        timeIntervalList = ruleList.get(0).getTimeIntervalList();
                        if (ruleAdapter  != null){
                            ruleAdapter.updateItems(timeIntervalList);
                        }
                    }else {
                        ruleAdapter.cleanItems();
                    }
                }
            });

            ruleAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TimeInterval timeInterval = timeIntervalList.get(position);
                    disPopuWindow();
                    select_time.setText(date+" "+timeInterval.getStartTime()+"-"+timeInterval.getEndTime());
                    if (payId >0 && addRessId >0 && deliveryId  >0){
                        changePrice();
                    }
                }
            });

    }

    private  void setTimer(RecyclerView time_select){
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DATE);
        final List<TimerBean> timers= new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            TimerBean timerBean =new TimerBean();
            if (i == 0){
                timerBean.setSelect(true);
            }else {
                timerBean.setSelect(false);
            }
            day++;
            if (day > 30 || day > 31){
                day = 1;
                month++;
            }
            timerBean.setDate(year + "-" + month + "-" + day);
            timers.add(timerBean);
        }
        date = timers.get(0).getDate();
        final TimerAdapter timeAdapter = new TimerAdapter(this,timers);
        RecyclerViewHelper.initRecyclerViewV(this,time_select,false,timeAdapter);
        timeAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TimerBean timerBean = timers.get(position);
                date = timerBean.getDate();
                for (int i = 0; i < timers.size(); i++) {
                    if (timers.get(i).getDate() == timerBean.getDate()){
                        timers.get(i).setSelect(true);
                    }else {
                        timers.get(i).setSelect(false);
                    }
                }
                timeAdapter.updateItems(timers);
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this. getWindow().setAttributes(lp);
    }

    @Override
    public void upDateViews() {}

    private List<Delivery> mDelivery;
    private  int payId = 0;
    @Override
    public void onSubmitList(PayBean data) {
        if (data != null){
            int allCount  =0 ;
            List<ShoppingCart> shoppingCartDTOList = data.getShoppingCartDTOList();
            if (shoppingCartDTOList != null && shoppingCartDTOList.size() > 0){
                if (mPayAdapter == null){
                    mPayAdapter = new PayAdapter(this,shoppingCartDTOList);
                    RecyclerViewHelper.initRecyclerViewV(this,goods_pay,true,mPayAdapter);
                }else {
                    mPayAdapter.cleanItems();
                    mPayAdapter.addItems(shoppingCartDTOList);
                }
                for (int i = 0; i < shoppingCartDTOList.size(); i++) {
                    allCount += shoppingCartDTOList.get(i).getCount();
                }
            }
            count_goods.setText("共"+allCount+"件");
            actual_amount.setText("实际付款："+String.format("%.2f",data.getOrderPriceDTO().getTotalPriceAfterDiscount()));
            rmb_amount.setText("约合RMB:¥"+String.format("%.2f", data.getOrderPriceDTO().getTotalPriceAfterDiscount()*data.getOrderPriceDTO().getRate()));
            order_curreny.setText("AUD");
            order_total_amount.setText(String.format("%.2f",data.getOrderPriceDTO().getTotalPrice()));
            tax_amount.setText(String.format("%.2f",data.getOrderPriceDTO().getDeliveryPrice()));
           /* if (data.isAllowUseScore()){
                score_count.setVisibility(View.VISIBLE);
                score_count.setText(String.valueOf(data.getAvailableScore()));
            }else {
                score_count.setVisibility(View.GONE);
            }*/
            mDelivery = data.getDeliveryListDTO();
            if (mDelivery != null && mDelivery.size() >0){
                for (int i = 0; i < mDelivery.size(); i++) {
                    if (i == 0){
                        mDelivery.get(i).setTypeSelect(true);
                    }else {
                        mDelivery.get(i).setTypeSelect(false);
                    }
                }
            }
        }
        final  List<Payment>  mPayment = data.getPaymentDTOList();
        if (mPayment != null && mPayment.size() >0){
            for (int i = 0; i < mPayment.size(); i++) {
             /*   if (i == 0){
                    mPayment.get(i).setSelect(true);
                }else {
                    mPayment.get(i).setSelect(false);
                }*/
                mPayment.get(i).setSelect(false);
            }
  //          payId = mPayment.get(0).getId();
           final PayChooseAdapter chooseAdapter = new PayChooseAdapter(this,mPayment);
            RecyclerViewHelper.initRecyclerViewV(PayActivity.this,choose_pay,true,chooseAdapter);
            chooseAdapter.setmPayCallBack(new PayChooseAdapter.PayClickCallBack() {
               @Override
               public void onSucess(Payment item, boolean isSelect) {
                   for (int i = 0; i < mPayment.size(); i++) {
                       Payment payment = mPayment.get(i);
                       if (payment.getId() == item.getId()){
                           payment.setSelect(isSelect);
                       }else {
                           if (payment.isSelect()){
                               payment.setSelect(false);
                           }
                       }
                   }
                   chooseAdapter.updateItems(mPayment);
                   for (int i = 0; i < mPayment.size(); i++) {
                      if (mPayment.get(i).isSelect()){
                          payId = mPayment.get(i).getId();
                      }
                   }
                   //改变价格
                   if (payId >0 && addRessId >0 && deliveryId >0){
                       changePrice();
                   }
               }
           });
        }
    }

    @Override
    public void onSubmitFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(PayActivity.this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }    }


    private String disCountCode;
    private  int addRessId = 0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 10000){
                AddressManagerBean addressManagerBean = (AddressManagerBean) data.getSerializableExtra("address");
                if (addressManagerBean  != null){
                    empty_adress.setVisibility(View.GONE);
                    have_name_adress.setVisibility(View.VISIBLE);
                    have_detail_adress.setVisibility(View.VISIBLE);
                    address_name.setText(addressManagerBean.getContactname());
                    address_phone.setText(addressManagerBean.getContactphone());
                    address_details.setText(addressManagerBean.getCountry()+" "+addressManagerBean.getCity()+addressManagerBean.getAddress());
                    addRessId = addressManagerBean.getId();
                    if (payId >0 && addRessId >0 && deliveryId  >0){
                        changePrice();
                    }
                }
            }else if (requestCode == 10001){  //优惠卷
                DisCountBean discount = (DisCountBean) data.getSerializableExtra("counpon");
                if (discount != null){
                    discount_content.setText(String.valueOf(discount.getAmount()));
                    disCountCode = discount.getCode();
                }
            }else {
                if (data == null) {
                    return;
                }
                String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
                String str = data.getExtras().getString("pay_result");
                if (str.equalsIgnoreCase("success")) {

                    // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
                    // result_data结构见c）result_data参数说明
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
                } else if (str.equalsIgnoreCase("cancel")) {
                    msg = "用户取消了支付";
                }
                Toast.makeText(PayActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 变更价格
     */
    private void changePrice() {
        Map<String,String> params = new HashMap<>();
        params.put("scIds",datas);
        params.put("deliveryId",String.valueOf(deliveryId));
        params.put("addressId",String.valueOf(addRessId));
        params.put("paymentId",String.valueOf(payId));
        params.put("couponCode",disCountCode);
        mPricePresenter = new PricePresenter(this);
        mPricePresenter.setParams(params);
        mPricePresenter.getData();
    }

    @Override
    public void onDismiss() {
        disPopuWindow();
        setBackgroundAlpha(1);
    }
    private  void disPopuWindow(){
        if (null != mPopuWindow && mPopuWindow .isShowing()){
            mPopuWindow.dismiss();
        }
    }


    @Override
    public void onPriceData(OrderPrice data) {
        actual_amount.setText("实际付款："+String.format("%.2f",data.getTotalPriceAfterDiscount()));
        rmb_amount.setText("约合RMB:¥"+String.format("%.2f", data.getTotalPriceAfterDiscount()*data.getRate()));
        order_curreny.setText("AUD");
        order_total_amount.setText(String.format("%.2f",data.getTotalPrice()));
    }

    @Override
    public void onPriceFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(PayActivity.this, LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }



}
