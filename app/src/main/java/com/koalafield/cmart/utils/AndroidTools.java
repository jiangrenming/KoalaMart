package com.koalafield.cmart.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.fragment.BaseFragment;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public class AndroidTools {


    /**
     * 格式化金额，保留2位小数点
     * @param amount
     * @return
     */
    public  static  String fromatAmount(Long amount){
        if (amount == null) {
            return "0.00";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return  decimalFormat.format(Double.valueOf((amount/100f)));
    }


}
