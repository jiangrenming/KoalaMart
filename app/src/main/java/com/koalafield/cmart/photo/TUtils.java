package com.koalafield.cmart.photo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.koalafield.cmart.R;

import java.util.List;

/**
 * 工具类
 * Author: JPH
 * Date: 2016/7/26 10:04
 */
public class TUtils {


    /**
     * 拍照前检查是否有相机
     **/
    public static void captureBySafely(Activity activity, Intent intentWap,int requestCode) throws TException {
        List result = activity.getPackageManager().queryIntentActivities(intentWap, PackageManager.MATCH_ALL);
        if (result.isEmpty()) {
            Toast.makeText(activity, activity.getResources().getText(R.string.tip_no_camera),
                    Toast.LENGTH_SHORT).show();
            throw new TException(TExceptionType.TYPE_NO_CAMERA);
        } else {
            startActivityForResult(activity, intentWap,requestCode);
        }
    }
    public static void startActivityForResult(Activity activity, Intent intentWap,int requestCode) {
        activity.startActivityForResult(intentWap, requestCode);
    }
}
