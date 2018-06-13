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
    /* @BindView(R.id.select)
     ImageView select;*/
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
                                Map<String, String> params = new HashMap<>();
                                params.put("country", "Australia");
                                params.put("state", "");
                                params.put("city", state == null ? "" : state); //暂时未有
                                params.put("area", "");//暂时未有
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
            Intent intent = new Intent(ChangeAddressActivity.this, LoginActivity.class);
            intent.putExtra("type", 3);
            startActivity(intent);
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
            Intent intent = new Intent(ChangeAddressActivity.this, LoginActivity.class);
            intent.putExtra("type", 3);
            startActivity(intent);
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
}
