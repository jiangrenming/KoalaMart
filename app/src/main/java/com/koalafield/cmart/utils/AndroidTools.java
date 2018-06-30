package com.koalafield.cmart.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.TitleAdpater;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.fragment.BaseFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //获得adapter
        TitleAdpater adapter = (TitleAdpater) listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //计算总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //计算分割线高度
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        //给listview设置高度
        listView.setLayoutParams(params);
    }
    /**
     * 将系统时间转换成年月日 时分秒
     * @param time
     * @return
     */
    public static String formatMillisecondAllDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        String strDate = format.format(date);
        return strDate;
    }


    public static boolean isServiceRunning(Context context, String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> lists = am.getRunningServices(30);
        for (ActivityManager.RunningServiceInfo info : lists) {//判断服务
            if(info.service.getClassName().equals(serviceName)){
                Log.i("Service1进程", ""+info.service.getClassName());
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * dp转换成px
     */
    public static int dp2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
    /**
     * sp转换成px
     */
    public static int sp2px(Context context,float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    public static int px2sp(Context context,float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }
}
