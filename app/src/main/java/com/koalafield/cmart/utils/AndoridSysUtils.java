package com.koalafield.cmart.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    /**
     * 获取设备唯一标识符
     */
    public static String getUniqueId(Context context){
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }
    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }

}
