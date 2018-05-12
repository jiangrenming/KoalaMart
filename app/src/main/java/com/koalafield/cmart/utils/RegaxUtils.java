package com.koalafield.cmart.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 * 正则判断
 */

public class RegaxUtils {

    /**
     * 匹配手机号
     * @param str
     * @return
     */
    public static boolean isMobilePhone(String str) {

        if (StringUtils.isEmpty(str)){
            String patternStr = "^1[3|4|5|7|8][0-9]\\d{8}$";
            Pattern p = Pattern.compile(patternStr);
            Matcher m = p.matcher(str);
            return m.matches();
        }
        return  false;
    }

    /**
     * 判断6-18位字母或数字的密码
     * @param password
     * @return
     */
    public  static  boolean isPassword(String password){
        if (!StringUtils.isEmpty(password)){
            String patternStr = "^[0-9a-zA-Z]{6,16}$";
            Pattern p = Pattern.compile(patternStr);
            Matcher m = p.matcher(password);
            return m.matches();
        }
        return  false;
    }
    /**
     * 判断2次密码是否一致
     */
    public  static  boolean isSamePwd(String pwd1,String pwd2){
        if (!StringUtils.isEmpty(pwd1)){
            if (pwd1.equals(pwd2)){
                return  true;
            }
        }
        return false;
    }
}
