package com.koalafield.cmart.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.dl7.tag.TagView;
import com.koalafield.cmart.R;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 * 欢迎页
 */

public class SplashActivity extends Activity {

    @BindView(R.id.tag_skip)
    TagView mTagSkip;
    private boolean mIsSkip = false;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 沉浸状态栏
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 无标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initDatas();
        upDateViews();
        initLocation();

    }

    /**
     * 获取经纬度
     */

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
                    ShareBankPreferenceUtils.putString("Longitude",String.valueOf(location.getLongitude()));
                    ShareBankPreferenceUtils.putString("Latitude",String.valueOf(location.getLatitude()));
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                Log.i("定位位置：",sb.toString());
            } else {
                Log.i("定位失败",location.getErrorInfo());
            }
        }
    };

    public void initDatas() {
        mTagSkip.setTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int pos, String text, int mode) {
                _doSkip();
            }
        });
    }


    public void upDateViews() {
         countdown(3).subscribe(new Consumer<Integer>() {
             @Override
             public void accept(Integer integer) throws Exception {
                 mTagSkip.setText("跳过 " + integer);
                 if (integer == 0){
                     _doSkip();
                 }
             }
         });
    }



    private void _doSkip() {
        if (!mIsSkip) {
            mIsSkip = true;
            finish();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
            ShareBankPreferenceUtils.putBoolean("isFirst",false);
        }
    }
    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }


    /**
     * 倒计时
     * @param time
     * @return
     */
    public static Observable<Integer> countdown(int time) {
        if (time < 0) {
            time = 0;
        }
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long increaseTime) throws Exception {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


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
