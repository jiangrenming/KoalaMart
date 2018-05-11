package com.koalafield.cmart.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

/**
 *
 * @author jiangrenming
 * @date 2018/5/10
 */

public class AndoridSysUtils {

    /**
     * 判断手机底部是否存在导航栏
     * @param activity
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBar(Context activity) {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    /**
     * 计算手机底部导航栏的高度
     */
    public static  int getNavigationBarHeigh(Context context){
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationHeight = context.getResources().getDimensionPixelSize(resourceId);
        return  navigationHeight;
    }

    /**
     * 获取应用版本号
     */
    public  static  String getVersion(Context context,String packageName){
        if (null == context || TextUtils.isEmpty(packageName)){
            return null;
        }
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo == null ? null :packageInfo.versionName;
        }catch (Exception e){
                 e.printStackTrace();
        }
        return  null;
    }

    /**
     * 获取应用内部版本号
     */
    public  static  int getVersionCode(Context context,String packageName){
        if (null == context || TextUtils.isEmpty(packageName)){
            return 0;
        }
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo == null ? null :packageInfo.versionCode;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  0;
    }

    public static int getColorValueByResId(Context context, int resId){
        Resources resource = (Resources) context.getResources();
        return resource.getColor(resId);
    }
}
