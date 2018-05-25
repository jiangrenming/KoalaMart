package com.koalafield.cmart.ui.activity.use;

import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.presenter.user.AddCountryPresenter;
import com.koalafield.cmart.presenter.user.AddressPresenter;
import com.koalafield.cmart.presenter.user.IAddCountryPresenter;
import com.koalafield.cmart.presenter.user.IAddressPresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.view.user.IAddAddressView;
import com.koalafield.cmart.ui.view.user.IEditAddressView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.RegaxUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 *
 * @author jiangrenming
 * @date 2018/5/24
 */

public class ChangeAddressActivity extends BaseActivity implements IAddAddressView<BaseResponseBean>,IEditAddressView<BaseResponseBean>,PopupWindow.OnDismissListener,View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;
    @BindView(R.id.castact_name)
    EditText castact_name;
    @BindView(R.id.castact_phone)
    EditText castact_phone;
    @BindView(R.id.select_country)
    LinearLayout select_country;
    @BindView(R.id.country_name)
    TextView country_name;
    @BindView(R.id.select_state)
    LinearLayout select_state;
    @BindView(R.id.state_name)
    TextView state_name;
    @BindView(R.id.et_suggestion)
    EditText et_suggestion;
    @BindView(R.id.select)
    ImageView select;
    @BindView(R.id.add_address)
    TextView add_address;
    private  int addressType;

    @Override
    public int attchLayoutRes() {
        return R.layout.address_create_layout;
    }

    @Override
    public void initDatas() {
         addressType = getIntent().getIntExtra("addressType", -1);
        if (addressType == 1){  //新增
            top_name.setText("新增地址");
            add_address.setText("新增收货地址");
        }else {  //编辑
            top_name.setText("编辑地址");
            add_address.setText("编辑收货地址");
            AddressManagerBean item = (AddressManagerBean) getIntent().getSerializableExtra("address");
            if (item != null){
                castact_name.setText(item.getContactname());
                castact_phone.setText(item.getContactphone());
                country_name.setText(item.getCountry());
                state_name.setText(item.getState());
                et_suggestion.setText(item.getAddress());
            }
        }
        castact_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action, KeyEvent event) {
                if (castact_name.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String vaule = castact_name.getText().toString().trim();
                    if (StringUtils.isEmpty(vaule)  || vaule.length() == 0) {
                        Toast.makeText(ChangeAddressActivity.this,"账户名称格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(castact_phone);
                    }
                    return true;
                }
                return false;
            }
        });

        castact_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action,
                                          KeyEvent event) {
                if (castact_phone.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String pwd = castact_phone.getText().toString();
                    if (StringUtils.isEmpty(pwd) || !RegaxUtils.isMobilePhone(pwd)||pwd.length() == 0) {  //还未对密码格式做判断
                        Toast.makeText(ChangeAddressActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(et_suggestion);
                    }
                    return true;
                }
                return false;
            }
        });

        et_suggestion.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action,
                                          KeyEvent event) {
                if (et_suggestion.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String suggestion = et_suggestion.getText().toString();
                    if (StringUtils.isEmpty(suggestion) ||suggestion.length() == 0 ) {  //还未对密码格式做判断
                        Toast.makeText(ChangeAddressActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    } else {
                        if (checkAllInfos()){
                            String phone = castact_phone.getText().toString().trim();
                            if (RegaxUtils.isMobilePhone(phone)){
                                if (addressType == 1){

                                }else {

                                }
                            }else {
                                Toast.makeText(ChangeAddressActivity.this,"手机号码格式不正确",Toast.LENGTH_SHORT).show();
                                castact_phone.setFocusable(true);
                                return true;
                            }
                        }else {
                            Toast.makeText(ChangeAddressActivity.this,"检验所填的收货人信息是否完整",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void upDateViews() {}

    @Override
    public void onAddAddressSucessFul(BaseResponseBean data) {}

    @Override
    public void onAddAddressFailure(String message) {}

    @Override
    public void onEditAddressSucessFul(BaseResponseBean data) {}

    @Override
    public void onEditAddressFailure(String message) {}


    @OnClick({R.id.select_country,R.id.select_state,R.id.add_address})
    public  void addressClick(View v){
        switch (v.getId()){
            case R.id.select_country:
                break;
            case  R.id.select_state:
                selectCity();
                break;
            case  R.id.add_address:
                if (checkAllInfos()){
                    String phone = castact_phone.getText().toString().trim();
                    if (RegaxUtils.isMobilePhone(phone)){
                        if (addressType == 1){
                            String name = castact_name.getText().toString().trim();
                            String country = country_name.getText().toString().trim();
                            String state = state_name.getText().toString().trim();
                            String suggestion = et_suggestion.getText().toString().trim();
                            Map<String,String > params = new HashMap<>();
                            params.put("country","Australia");
                            params.put("state",state);
                            params.put("city",country); //暂时未有
                            params.put("area",country);//暂时未有
                            params.put("address",suggestion);
                            params.put("contactname",name);
                            params.put("contactphone",phone);
                            IAddCountryPresenter add = new AddCountryPresenter(ChangeAddressActivity.this);
                            add.getParamsData(params);
                        }else {

                        }
                    }else {
                        Toast.makeText(this,"手机号码格式不正确",Toast.LENGTH_SHORT).show();
                        castact_phone.setFocusable(true);
                        return;
                    }
                }else {
                    Toast.makeText(this,"检验所填的收货人信息是否完整",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                break;
        }
    }

    private void selectCity() {


    }

    private  boolean checkAllInfos(){
        String name = castact_name.getText().toString().trim();
        if (StringUtils.isEmpty(name) || name.length() == 0){
            return false;
        }
        String phone = castact_phone.getText().toString().trim();
        if (StringUtils.isEmpty(phone) || phone.length() == 0){
            return false;
        }
        String country = country_name.getText().toString().trim();
        if (StringUtils.isEmpty(country) || country.length() == 0){
            return false;
        }
        String state = state_name.getText().toString().trim();
        if (StringUtils.isEmpty(state) || state.length() == 0){
            return false;
        }
        String suggestion = et_suggestion.getText().toString().trim();
        if (StringUtils.isEmpty(suggestion) || suggestion.length() == 0){
            return false;
        }
        return true;
    }

    private PopupWindow popupWindow;
    private int navigationHeight = 0;
    private boolean scrolling = false;
    final String cities[] = new String[] {
            "New South Wales",
            "Australian Capital Territory",
            "Victoria",
            "Western Australia",
            "South Australia",
            "Queensland",
            "Northern Territory",
            "Northern Tasmania"
    };

    private void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.select_citys, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外退出
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
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

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1f);
    }
    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this. getWindow().setAttributes(lp);
    }

    private void setOnPopupViewClick(View view) {
         TextView ib_cancle = view.findViewById(R.id.ib_cancle);
         TextView ib_confirm = view.findViewById(R.id.ib_confirm);
         final WheelView country = findViewById(R.id.id_province);
         ib_confirm.setOnClickListener(this);
         ib_cancle.setOnClickListener(this);
         country.setVisibleItems(3);
         country.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    updateCities(country, cities, newValue);
                }
            }
        });

        country.addScrollingListener( new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                updateCities(country, cities, country.getCurrentItem());
            }
        });
        country.setCurrentItem(1);
    }

    /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, String cities[], int index) {
        ArrayWheelAdapter<String> adapter =
                new ArrayWheelAdapter<String>(this, cities);
        adapter.setTextSize(16);
        city.setViewAdapter(adapter);
        city.setCurrentItem(cities.length / 2);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.ib_cancle :
                if (popupWindow != null &&popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                break;
            case R.id.ib_confirm:

                break;
            default:
                break;
        }
    }
}
