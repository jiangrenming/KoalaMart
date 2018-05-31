package com.koalafield.cmart.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koalafield.cmart.R;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class SaleUpDownView extends LinearLayout implements View.OnClickListener {


    private TextView priceView;
    private boolean isUp;
    private SaleCallback callback;

    public SaleUpDownView(Context context) {
        this(context, null);
    }

    public SaleUpDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaleUpDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sale_up_down, this);
        priceView = (TextView) findViewById(R.id.sale_id);
        priceView.setOnClickListener(this);
    }

    private void setDrawable() {
        Drawable drawable = null;
        if (isUp) {
            isUp = false;
            drawable = getResources().getDrawable(R.mipmap.down_desc);
        } else {
            isUp = true;
            drawable = getResources().getDrawable(R.mipmap.up_desc);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        priceView.setCompoundDrawables(null, null, drawable, null);
        priceView.setCompoundDrawablePadding(8);
        priceView.setTextColor(Color.parseColor("#e02b4d"));
    }

    public  interface SaleCallback {
        void getSaleStatus(boolean isUp);
    }

    public void setSaleCallback(SaleCallback callback) {
        this.callback = callback;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.price_id:
                setDrawable();
                callback.getSaleStatus(isUp);
                break;
            default:
                break;
        }
    }
    public  void setClick(){
        Drawable drawable = getResources().getDrawable(R.mipmap.un_select_desc);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        priceView.setCompoundDrawables(null, null, drawable, null);
        priceView.setCompoundDrawablePadding(8);
        priceView.setTextColor(getResources().getColor(R.color.gray));
    }

}
