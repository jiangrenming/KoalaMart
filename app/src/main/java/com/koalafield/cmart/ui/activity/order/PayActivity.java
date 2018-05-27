package com.koalafield.cmart.ui.activity.order;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
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
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.PayAdapter;
import com.koalafield.cmart.adapter.TimeRuleAdapter;
import com.koalafield.cmart.adapter.TimerAdapter;
import com.koalafield.cmart.adapter.TimerTypeAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.TimerBean;
import com.koalafield.cmart.bean.order.Delivery;
import com.koalafield.cmart.bean.order.PayBean;
import com.koalafield.cmart.bean.order.Rule;
import com.koalafield.cmart.bean.order.ShoppingCart;
import com.koalafield.cmart.bean.order.TimeInterval;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.presenter.order.IPayPresenter;
import com.koalafield.cmart.presenter.order.PayPresenter;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.activity.use.AddressManangerActivity;
import com.koalafield.cmart.ui.activity.use.DisCountActivity;
import com.koalafield.cmart.ui.view.order.IPayView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

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

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class PayActivity extends BaseActivity implements IPayView<PayBean>,PopupWindow.OnDismissListener,View.OnClickListener{

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
    @BindView(R.id.score_count)
    TextView score_count;
    @BindView(R.id.discount_content)
    TextView discount_content;
    @BindView(R.id.skip_discount)
    ImageView skip_discount;

    private PayAdapter mPayAdapter;


    private PopupWindow mPopuWindow;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_pay;
    }

    @Override
    public void initDatas() {
        AddressManagerBean managerBean = ShareBankPreferenceUtils.getObject("addressId", AddressManagerBean.class);
        if (managerBean != null){
            empty_adress.setVisibility(View.GONE);
            have_name_adress.setVisibility(View.VISIBLE);
            have_detail_adress.setVisibility(View.VISIBLE);
            address_name.setText(managerBean.getContactname());
            address_phone.setText(managerBean.getContactphone());
            address_details.setText(managerBean.getAddress());
        }else {
            empty_adress.setVisibility(View.VISIBLE);
            have_name_adress.setVisibility(View.GONE);
            have_detail_adress.setVisibility(View.GONE);
        }

        String datas = getIntent().getStringExtra("payDatas");
        if (!StringUtils.isEmpty(datas)){
            IPayPresenter payPresenter = new PayPresenter(this);
            Map<String,String> params = new HashMap<>();
            params.put("scIds",datas);
            payPresenter.setParams(params);
        }
        mPayAdapter = new PayAdapter(this);
        RecyclerViewHelper.initRecyclerViewV(this,goods_pay,true,mPayAdapter);
    }


    @OnClick({R.id.click_address,R.id.change_address,R.id.skip_discount,R.id.change_time})
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
            default:
                break;
        }
    }

    private int navigationHeight = 0;
    private void openWindow(View v) {
        //防止重复按按钮
        if (mPopuWindow != null && mPopuWindow.isShowing()) {
            return;
        }

        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.customer_timer, null);
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
    private void setOnPopupViewClick(View view) {
        RecyclerView timer_type = view.findViewById(R.id.timer_type);
        RecyclerView time_select = view.findViewById(R.id.time_select);
        final RecyclerView time_categry = view.findViewById(R.id.time_categry);

        final TimerTypeAdapter typeAdapter = new TimerTypeAdapter(this,mDelivery);
        RecyclerViewHelper.initRecyclerViewG(this,timer_type,false,typeAdapter,3);

        final List<TimeInterval> timeIntervalList = mDelivery.get(0).getRuleList().get(0).getTimeIntervalList();
        final TimeRuleAdapter ruleAdapter = new TimeRuleAdapter(this,timeIntervalList);
        RecyclerViewHelper.initRecyclerViewV(this,time_categry,true,ruleAdapter);

        setTimer(time_select);


        typeAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Delivery delivery = mDelivery.get(position);

                for (int i = 0; i < mDelivery.size(); i++) {
                    if (delivery.getId() == mDelivery.get(i).getId()){
                        mDelivery.get(i).setTypeSelect(true);
                    }else {
                        mDelivery.get(i).setTypeSelect(false);
                    }
                }
                typeAdapter.updateItems(mDelivery);

                List<Rule> ruleList = delivery.getRuleList();
                List<TimeInterval> intervalList = ruleList.get(0).getTimeIntervalList();
                if (ruleAdapter  != null){
                   ruleAdapter.updateItems(intervalList);
                }
            }
        });

        ruleAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TimeInterval timeInterval = timeIntervalList.get(position);
                disPopuWindow();
                select_time.setText(date+" "+timeInterval.getStartTime()+"-"+timeInterval.getEndTime());
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
            day++;
            if (day > 30 || day > 31){
                month++;
            }
            timerBean.setDate(year + "-" + month + "-" + day);
            if (i == 0){
                timerBean.setSelect(true);
            }else {
                timerBean.setSelect(false);
            }
            timers.add(timerBean);
        }
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
            /*case R.id.normal_time:
                break;
            case R.id.query_time:
                break;
            case R.id.quick_time:
                break;
            default:
                break;*/
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
    @Override
    public void onSubmitList(PayBean data) {
        if (data != null){
            int allCount  =0 ;
            List<ShoppingCart> shoppingCartDTOList = data.getShoppingCartDTOList();
            if (shoppingCartDTOList != null && shoppingCartDTOList.size() > 0){
                mPayAdapter.updateItems(shoppingCartDTOList);
            }
            for (int i = 0; i < shoppingCartDTOList.size(); i++) {
                allCount += shoppingCartDTOList.get(i).getCount();
            }
            count_goods.setText("共"+allCount+"件");
            actual_amount.setText("实际付款："+data.getOrderPriceDTO().getTotalPriceAfterDiscount());
            rmb_amount.setText("约合RMB:¥"+String.format("%.2f", data.getOrderPriceDTO().getTotalPriceAfterDiscount()*data.getOrderPriceDTO().getRate()));
            order_curreny.setText("AUD");
            order_total_amount.setText(String.valueOf(data.getOrderPriceDTO().getTotalPrice()));
            tax_amount.setText(String.valueOf(data.getOrderPriceDTO().getConsumptionTax()));
            if (data.isAllowUseScore()){
                score_count.setVisibility(View.VISIBLE);
                score_count.setText(String.valueOf(data.getAvailableScore()));
            }else {
                score_count.setVisibility(View.GONE);
            }
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

    }

    @Override
    public void onSubmitFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


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
                    address_details.setText(addressManagerBean.getAddress());
                }
            }else if (requestCode == 10001){  //优惠卷

            }
        }
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


}
