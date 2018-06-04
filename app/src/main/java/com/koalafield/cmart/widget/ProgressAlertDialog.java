package com.koalafield.cmart.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.koalafield.cmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class ProgressAlertDialog extends AlertDialog{


    private Animation animation;
    ImageView refreshing_img;
    protected ProgressAlertDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public ProgressAlertDialog(Context context) {
        super(context,R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog_layout);
        refreshing_img = findViewById(R.id.refreshing_img);
        //点击imageview外侧区域，动画不会消失
        setCanceledOnTouchOutside(false);
        //加载动画资源
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.dialog_anim);
        //动画完成后，是否保留动画最后的状态，设为true
        animation.setFillAfter(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (animation != null){
            refreshing_img.startAnimation(animation);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        refreshing_img.clearAnimation();
    }
}
