package com.koalafield.cmart.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.koalafield.cmart.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.WheelView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/25
 */

public class SelectCityPopuWidnow extends PopupWindow{


    private Context mContext;
    private View mView;
    @BindView(R.id.ib_cancle)
    TextView ib_cancle;
    @BindView(R.id.ib_confirm)
    TextView ib_confirm;
    @BindView(R.id.id_province)
    WheelView id_province;

    public SelectCityPopuWidnow(Context context) {
        super(context);
        mContext = context;
        initView();
        initData();
    }


    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.select_citys,null);
        ButterKnife.bind(this,mView);
    }

    @OnClick({R.id.ib_cancle,R.id.ib_confirm})
    public  void onSelectCityClick(View view){
        switch (view.getId()){
            case R.id.ib_cancle:
                if (isShowing()){
                    dismiss();
                }
                break;
            case  R.id.ib_confirm:
                break;
            default:
                break;
        }
    }
    private void initData() {


    }
}
