package com.koalafield.cmart.ui.activity.use;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.presenter.user.AddCountryPresenter;
import com.koalafield.cmart.presenter.user.AddressPresenter;
import com.koalafield.cmart.presenter.user.EditCountryPresenter;
import com.koalafield.cmart.presenter.user.IAddCountryPresenter;
import com.koalafield.cmart.presenter.user.IAddressPresenter;
import com.koalafield.cmart.presenter.user.IEditCountryPresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.SplashActivity;
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
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.WheelViewAdapter;

import static com.koalafield.cmart.R.layout.country_layout;

/**
 * @author jiangrenming
 * @date 2018/5/24
 */

public class ChangeAddressActivity extends BaseActivity implements IAddAddressView<BaseResponseBean>, IEditAddressView<BaseResponseBean>, PopupWindow.OnDismissListener, View.OnClickListener {

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
    @BindView(R.id.area)
    EditText area;
    @BindView(R.id.add_address)
    TextView add_address;
    private int addressType;
    private AddressManagerBean item;

    @Override
    public int attchLayoutRes() {
        return R.layout.address_create_layout;
    }

    @Override
    public void initDatas() {
        addressType = getIntent().getIntExtra("addressType", -1);
        if (addressType == 1) {  //新增
            top_name.setText("新增地址");
            add_address.setText("新增收货地址");
        } else {  //编辑
            top_name.setText("编辑地址");
            add_address.setText("编辑收货地址");
            item = (AddressManagerBean) getIntent().getSerializableExtra("address");
            if (item != null) {
                castact_name.setText(item.getContactname());
                castact_phone.setText(item.getContactphone());
                country_name.setText(item.getCountry());
                state_name.setText(item.getState());
                area.setText(item.getArea());
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
                    if (StringUtils.isEmpty(vaule) || vaule.length() == 0) {
                        Toast.makeText(ChangeAddressActivity.this, "账户名称格式不正确", Toast.LENGTH_SHORT).show();
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
                    if (StringUtils.isEmpty(pwd) || !RegaxUtils.isMobilePhone(pwd) || pwd.length() == 0) {  //还未对密码格式做判断
                        Toast.makeText(ChangeAddressActivity.this, "电话格式不正确", Toast.LENGTH_SHORT).show();
                    } else {
                        setFocus(area);
                    }
                    return true;
                }
                return false;
            }
        });

        area.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView view, int action,
                                          KeyEvent event) {
                if (area.getVisibility() == View.VISIBLE
                        && (action == EditorInfo.IME_ACTION_DONE
                        || action == EditorInfo.IME_ACTION_SEND
                        || action == EditorInfo.IME_ACTION_NEXT
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    String pwd = area.getText().toString();
                    if (StringUtils.isEmpty(pwd)) {  //还未对密码格式做判断
                        Toast.makeText(ChangeAddressActivity.this, "区域地址不能为空", Toast.LENGTH_SHORT).show();
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
                    if (StringUtils.isEmpty(suggestion) || suggestion.length() == 0) {  //还未对密码格式做判断
                        Toast.makeText(ChangeAddressActivity.this, "密码格式不正确", Toast.LENGTH_SHORT).show();
                    } else {
                        if (checkAllInfos()) {
                            String phone = castact_phone.getText().toString().trim();
                            if (RegaxUtils.isMobilePhone(phone)) {
                                String name = castact_name.getText().toString().trim();
                                String country = country_name.getText().toString().trim();
                                String state = state_name.getText().toString().trim();
                                String distince = area.getText().toString().trim();
                                Map<String, String> params = new HashMap<>();
                                params.put("country", "Australia");
                                params.put("state", "");
                                params.put("city", state == null ? "" : state); //暂时未有
                                params.put("area",distince== null ?"":distince);//暂时未有
                                params.put("address", suggestion == null ? "" : suggestion);
                                params.put("contactname", name == null ? "" : name);
                                params.put("contactphone", phone == null ? "" : phone);
                                if (addressType == 1) {
                                    IAddCountryPresenter add = new AddCountryPresenter(ChangeAddressActivity.this);
                                    add.getParamsData(params);
                                } else {
                                    IEditCountryPresenter edit = new EditCountryPresenter(ChangeAddressActivity.this);
                                    edit.getParamsData(params);
                                }
                            } else {
                                Toast.makeText(ChangeAddressActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                                castact_phone.setFocusable(true);
                                return true;
                            }
                        } else {
                            Toast.makeText(ChangeAddressActivity.this, "检验所填的收货人信息是否完整", Toast.LENGTH_SHORT).show();
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
    public void upDateViews() {
        initLocation();
    }

    @Override
    public void onAddAddressSucessFul(BaseResponseBean data) {
        if (data != null && data.getCode() == 200) {
            Toast.makeText(this, data.getMsg(), Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onAddAddressFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            skipLogin(this);
        }
    }

    @Override
    public void onEditAddressSucessFul(BaseResponseBean data) {
        if (data != null && data.getCode() == 200) {
            Toast.makeText(this, data.getMsg(), Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onEditAddressFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            skipLogin(this);
        }
    }


    @OnClick({R.id.select_country, R.id.select_state, R.id.add_address,R.id.back})
    public void addressClick(View v) {
        switch (v.getId()) {
            case R.id.select_country:
                break;
            case R.id.select_state:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    openPopupWindow(v);
                }
                break;
            case R.id.add_address:
                if (checkAllInfos()) {
                    String phone = castact_phone.getText().toString().trim();
                    if (RegaxUtils.isMobilePhone(phone)) {
                        String name = castact_name.getText().toString().trim();
                        String country = country_name.getText().toString().trim();
                        String state = state_name.getText().toString().trim();
                        String suggestion = et_suggestion.getText().toString().trim();
                        Map<String, String> params = new HashMap<>();
                        params.put("country", "Australia");
                        params.put("state", "");
                        params.put("city", state == null ? "" : state);
                        params.put("area", "");
                        params.put("address", suggestion == null ? "" : suggestion);
                        params.put("contactname", name == null ? "" : name);
                        params.put("contactphone", phone == null ? "" : phone);
                        if (addressType == 1) {
                            IAddCountryPresenter add = new AddCountryPresenter(ChangeAddressActivity.this);
                            add.getParamsData(params);
                        } else {
                            params.put("id", String.valueOf(item.getId()));
                            IEditCountryPresenter edit = new EditCountryPresenter(ChangeAddressActivity.this);
                            edit.getParamsData(params);
                        }
                    } else {
                        Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                        castact_phone.setFocusable(true);
                        return;
                    }
                } else {
                    Toast.makeText(this, "检验所填的收货人信息是否完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private boolean checkAllInfos() {
        String name = castact_name.getText().toString().trim();
        if (StringUtils.isEmpty(name) || name.length() == 0) {
            return false;
        }
        String phone = castact_phone.getText().toString().trim();
        if (StringUtils.isEmpty(phone) || phone.length() == 0) {
            return false;
        }
        String country = country_name.getText().toString().trim();
        if (StringUtils.isEmpty(country) || country.length() == 0) {
            return false;
        }
        String state = state_name.getText().toString().trim();
        if (StringUtils.isEmpty(state) || state.length() == 0) {
            return false;
        }
        String suggestion = et_suggestion.getText().toString().trim();
        if (StringUtils.isEmpty(suggestion) || suggestion.length() == 0) {
            return false;
        }
        return true;
    }

    private PopupWindow popupWindow;
    private int navigationHeight = 0;
    private boolean scrolling = false;
    final String cities[] = new String[]{
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
        if (AndoridSysUtils.checkDeviceHasNavigationBar(this)) {
            navigationHeight = AndoridSysUtils.getNavigationBarHeigh(this);
        }
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置显示的位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置背景透明度
        setBackgroundAlpha(0.5f);
    }

    @Override
    public void onDismiss() {
        disPopuWindow();
        setBackgroundAlpha(1f);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this.getWindow().setAttributes(lp);
    }

    private String statue;

    private void setOnPopupViewClick(View view) {
        TextView ib_cancle = view.findViewById(R.id.ib_cancle);
        TextView ib_confirm = view.findViewById(R.id.ib_confirm);
        final WheelView city = view.findViewById(R.id.id_province);
        CountryAdapter countryAdapter = new CountryAdapter(this);
        city.setViewAdapter(countryAdapter);
        city.setVisibleItems(5);
       // city.setWheelBackground(R.color.bg_white);
 //       city.setWheelForeground(R.color.gray);
        ib_confirm.setOnClickListener(this);
        ib_cancle.setOnClickListener(this);
        city.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    Log.i("滚动的值2", cities[oldValue] + "/新位置" + cities[newValue] + "/" + cities[city.getCurrentItem()]);
                    statue = cities[city.getCurrentItem()];
                }
            }
        });

        city.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                Log.i("滚动的值", cities[city.getCurrentItem()]);
                statue = cities[city.getCurrentItem()];
            }
        });
        city.setCurrentItem(1);
    }


    //  private boolean isSelect =false;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_cancle:
                disPopuWindow();
                break;
            case R.id.ib_confirm:
                if (!StringUtils.isEmpty(statue)) {
                    disPopuWindow();
                    state_name.setText(statue);
                }
                break;
           /* case R.id.select:
                if (isSelect){
                    select.setImageResource(R.mipmap.un_select);
                }else {
                    select.setImageResource(R.mipmap.select);
                }*/
            default:
                break;
        }
    }

    private void disPopuWindow() {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    private class CountryAdapter extends AbstractWheelTextAdapter {

        private Context mContext;

        protected CountryAdapter(Context context) {
            super(context);
            this.mContext = context;
        }

        @Override
        public View getItem(int index, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.country_layout, null);
                holder.name = convertView.findViewById(R.id.city_item_name);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            holder.name.setText(cities[index]);
            return convertView;
        }

        @Override
        public int getItemsCount() {
            return cities.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return cities[index];
        }

        private class ViewHolder {
            TextView name;
        }
    }
    /**
     * 获取经纬度
     */
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        locationClient.startLocation();
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    sb.append("定位成功" + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    String district = location.getDistrict();
                    String address = location.getAddress();
                    state_name.setText(location.getCity());
                    area.setText(district);
                    et_suggestion.setText(address);
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                Log.i("定位位置：",sb.toString());
            } else {
                Log.i("定位失败：",location.getErrorInfo());
            }
        }
    };
    /**
     * 销毁定位
     * @since 2.8.0
     * @author hongming.wang
     */
    private void destroyLocation(){
        if (null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }
}
