package com.koalafield.cmart.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.koalafield.cmart.AndoridApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by jiangrenming on 2017/12/12.
 * 存储数据
 */

public class ShareBankPreferenceUtils {

    private static final String SHARE_PREFS_NAME = "com.android.koalamart";

    private static SharedPreferences getPreferences() {
        return AndoridApplication.getContext().getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void putBoolean(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public static void putString(String key, String value) {
        getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }
    public  static void clearData(String key){
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.remove(key);
        edit.commit();
    }
    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param object
     */
    public static  void setObject(String key, Object object) {
        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = getPreferences().edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取复杂对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static  <T> T getObject(String key, Class<T> clazz) {
        if (getPreferences().contains(key)) {
            String objectVal = getPreferences().getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
