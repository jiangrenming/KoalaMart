package com.koalafield.cmart.ui.activity.order;

import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
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
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.OrderAdapter;
import com.koalafield.cmart.adapter.PayChooseAdapter;
import com.koalafield.cmart.adapter.PayOrderChooseAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.order.OrderAdress;
import com.koalafield.cmart.bean.order.OrderDetailsInfo;
import com.koalafield.cmart.bean.order.OrderPrice;
import com.koalafield.cmart.bean.order.OrderdetailsBean;
import com.koalafield.cmart.bean.order.Payment;
import com.koalafield.cmart.bean.order.SdkPayBean;
import com.koalafield.cmart.presenter.order.IOrderDetailsPresenter;
import com.koalafield.cmart.presenter.order.IPaySdkPresenter;
import com.koalafield.cmart.presenter.order.OrderDetailsPresenter;
import com.koalafield.cmart.presenter.order.PaySdkPresenter;
import com.koalafield.cmart.ui.view.order.IOrderDetailsView;
import com.koalafield.cmart.ui.view.order.IPaySdkView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.unionpay.UPPayAssistEx;

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

public class OrderDetailsActivity extends BaseActivity implements IOrderDetailsView<OrderdetailsBean>,IPaySdkView<SdkPayBean> ,PopupWindow.OnDismissListener{

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

    @OnClick({R.id.back,R.id.once_pay})
    public void orderDetaiClck(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case  R.id.once_pay:
                if (once_pay.getVisibility() == View.VISIBLE){
                    if (payList != null && payList.size() > 0){
                        openWindow(view);
                    }else {
                        Toast.makeText(OrderDetailsActivity.this,"支付方式为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
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

    private void setOnPopupViewClick(View view) {
        RecyclerView payChoose = view.findViewById(R.id.choose_pay);
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
                        }
                    }
                }

                @Override
                public void paySdk(Payment item) {
                    if (mPopuWindow != null &&mPopuWindow.isShowing()){
                        mPopuWindow.dismiss();
                    }
                    Map<String,String> params = new HashMap<>();
                    IPaySdkPresenter mPresenter = new PaySdkPresenter(OrderDetailsActivity.this);
                    params.put("billCode",billNo) ;
                    params.put("paymentId",String.valueOf(paymentId)) ;
                    mPresenter.setParams(params);
                    mPresenter.getData();
                }

                @Override
                public void onClosePopu() {
                    setBackgroundAlpha(1);
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

    @Override
    public void onOrderDetails(OrderdetailsBean data) {
        if (data != null){
            OrderDetailsInfo detailsInfo = data.getDetailsInfo();
            payList = data.getPaymentDTOList();
            if (detailsInfo != null && detailsInfo.getGoodsList() != null && detailsInfo.getGoodsList().size() >0){
                order_num.setText(detailsInfo.getBillNo());
                order_time.setText(detailsInfo.getCreatedTime());
                OrderAdapter mAdapter = new OrderAdapter(this,data.getDetailsInfo().getGoodsList());
                RecyclerViewHelper.initRecyclerViewV(OrderDetailsActivity.this,order_detail_recy,true,mAdapter);

                if (detailsInfo.getStatus() == 0){  //待付款
                    has_time.setVisibility(View.VISIBLE);
                    devilery_time_layout.setVisibility(View.GONE);
                    devilery_style_layout.setVisibility(View.GONE);
                    pay_style_layout.setVisibility(View.GONE);
                    pay_time_layout.setVisibility(View.GONE);
                    once_pay.setVisibility(View.VISIBLE);
                }else if (detailsInfo.getStatus() == 1){  //代发货
                    has_time.setVisibility(View.GONE);
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
                    once_pay.setVisibility(View.GONE);
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
                order_tax.setText(String.format("%.2f",orderPrice.getDeliveryPrice()));
                order_discount.setText(String.format("%.2f",orderPrice.getCouponPrice()));
                order_all_price.setText(String.format("%.2f",orderPrice.getTotalPrice()));
            }

        }
    }

    @Override
    public void onOrderDetailsFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private String serVerMode = "01";
    @Override
    public void onPaySdkData(SdkPayBean data) {
        if (data != null){
            String transactionNo = data.getTransactionNo();
            if (!StringUtils.isEmpty(transactionNo)){
                UPPayAssistEx.startPay(OrderDetailsActivity.this,null,null,transactionNo,serVerMode);
            }
        }
    }

    @Override
    public void onPaySdkFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDismiss() {
        if (null != mPopuWindow && mPopuWindow .isShowing()){
            mPopuWindow.dismiss();
        }
        setBackgroundAlpha(1.0f);
    }
}
