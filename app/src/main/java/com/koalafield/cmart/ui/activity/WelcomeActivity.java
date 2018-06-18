package com.koalafield.cmart.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.TipPagerAdapter;
import com.koalafield.cmart.utils.PermissionsUtil;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by jiangrenming on 2018/6/13.
 */

public class WelcomeActivity extends Activity  implements PermissionsUtil.IPermissionsCallback{

    @BindView(R.id.vp_tip)
    ViewPager vp_tip;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;

    private int  mCurrentIndex = 0 ; //当前小圆点的位置
    private int [] images = {R.mipmap.welcome,R.mipmap.welcome1,R.mipmap.welcome2,R.mipmap.welcome3};
    private  List<ImageView> imageList = new ArrayList<>();

    private PermissionsUtil permissionsUtil;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFirst = ShareBankPreferenceUtils.getBoolean("isFirst", true);
        if (!isFirst){
            skipActivity();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 沉浸状态栏
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 无标题
        setContentView(R.layout.activity_welcome_layout);
        ButterKnife.bind(this);
        initDatas();
        getPermissoin();
    }

    private void getPermissoin() {
        permissionsUtil = PermissionsUtil
                .with(this)
                .requestCode(1)
                .isDebug(true)//开启log
                .permissions(PERMISSIONS)
                .request();
    }

    public void initDatas() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[i]);
            imageList.add(imageView);
        }
        for (int i = 0; i < images.length ; i++) {
            ImageView dot = new ImageView(this);
            if (i == mCurrentIndex) {
                dot.setImageResource(R.drawable.cirle_blue);//设置当前页的圆点
            } else {
                dot.setImageResource(R.drawable.cirle_gary);//其余页的圆点
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                layoutParams.leftMargin = 10 ;//设置圆点边距
            }
            dot.setLayoutParams(layoutParams);
            ll_container.addView(dot) ;//将圆点添加到容器中
        }

        TipPagerAdapter adapter = new TipPagerAdapter(imageList);
        vp_tip.setAdapter(adapter);
        vp_tip.setOffscreenPageLimit(5);
        vp_tip.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
            //根据监听的页面改变当前页对应的小圆点
                mCurrentIndex = position;
                for (int i = 0; i < ll_container.getChildCount(); i++) {
                    ImageView imageView = (ImageView) ll_container.getChildAt(i);
                    if (i == position) {
                        imageView.setImageResource(R.drawable.cirle_blue);
                    } else {
                        imageView.setImageResource(R.drawable.cirle_gary);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        vp_tip.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float endX;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();

                        break;
                    case MotionEvent.ACTION_UP:
                        endX = motionEvent.getX();
                        //获取屏幕的宽度
                        int width = getNoHasVirtualKey();
                        //根据滑动的距离来切换界面
                        if (mCurrentIndex == ll_container.getChildCount()-1 && startX - endX >= (width / 5)) {
                            skipActivity();//切换界面
                        }
                        break;
                }
                return false;
            }
        });
    }

    private  void skipActivity(){
        startActivity(new Intent(this,SplashActivity.class));
        finish();
    }

    public int getNoHasVirtualKey() {
            int height = getWindowManager().getDefaultDisplay().getHeight();
            return height;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //需要调用onRequestPermissionsResult
        permissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //监听跳转到权限设置界面后再回到应用
        permissionsUtil.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPermissionsGranted(int requestCode, String... permission){
    }

    @Override
    public void onPermissionsDenied(int requestCode, String... permission) {
    }

}
