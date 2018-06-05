package com.koalafield.cmart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.ui.activity.KoalaMartActivity;
import com.koalafield.cmart.ui.activity.MainActivity;

import java.util.Stack;

/**
 *
 * @author jiangrenming
 * @date 2018/4/19
 * actiivty的栈管理
 */

public class StackActivityManager {

    private static Stack<Activity> activityStack;
    private static StackActivityManager instance;
    public boolean isRemoving; // 正在删除栈中多个的activities

    /**
     * 实例化一个activity管理类
     *
     * @return
     */
    public static synchronized StackActivityManager getActivityManager() {
        if (instance == null) {
            instance = new StackActivityManager();
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    /**
     * 把activity加入到管理类堆栈底,如果已经存在堆栈中将推入栈顶
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (isRemoving)
            isRemoving = false;
        if (activity != null) {
            int i = activityStack.indexOf(activity);
            if (i != -1) {
                activityStack.add(activity);
            } else {
                activityStack.push(activity);
            }

        }
    }

    /**
     * 返回堆栈中activity的数量
     *
     * @return
     */
    public int getActivityNum() {
        return activityStack.size();
    }

    /**
     * 将activity推入栈顶
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        int i = activityStack.indexOf(activity);
        if (i != -1) {
            activityStack.remove(i);
            activityStack.push(activity);
        }
    }

    /**
     * 获得栈顶的activity
     * @return
     */
    public Activity getTopActivity() {
        if ((activityStack != null) && (activityStack.size() > 0)) {
            return (Activity) activityStack.lastElement();
        } else {
            return null;
        }
    }

    /**
     * 退出栈中指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            int i = activityStack.indexOf(activity);
            if (i != -1) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    /**
     * 移除
     * @param cls
     */
    public void removeExceptActivity(Class<?> cls) {
        int size = activityStack.size();
        isRemoving = true;
        for (int i = 0; i < size; i++) {
            Activity activity = getTopActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
            }
        }
    }

    /**
     * 清除掉除cls以外的其他类 全部退出的时候要使用removeAllActivity()
     * @param cls
     */
    public void removeAllActivityExceptOne(Class<?> cls) {
        int size = activityStack.size();
        isRemoving = true;
        for (int i = 0; i < size; i++) {
            Activity activity = getTopActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                continue;
            }
            removeActivity(activity);
        }
    }

    /**
     * 将栈中的activity自顶而下删掉，直到指定的Class
     * @param cls
     */
    public void removeActivityTo(Class<?> cls) {
        int size = activityStack.size();
        isRemoving = true;
        for (int i = 0; i < size; i++) {
            Activity activity = getTopActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            removeActivity(activity);
        }
    }

    /**
     * 计算栈中指定类(cls)对象的个数 add by chenkehui @2013.07.10
     */
    public int activityCount(Class<?> cls) {
        int count = 0;
        int size = activityStack.size();
        Activity activity = null;
        for (int i = 0; i < size; i++) {
            activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 退出所有activity
     */
    public void removeAllActivity() {
        int size = activityStack.size();
        isRemoving = true;
        for (int i = 0; i < size; i++) {
            Activity activity = getTopActivity();
            if (activity != null) {
                removeActivity(activity);
            }
        }
        System.gc();
    }

    /**
     * 是否存在
     * @return
     */
    public boolean containsActivity(Class<?> cls){
        int size = activityStack.size();
        for (int i=0; i<size; i++){
            if (activityStack.get(i).getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 回退到某个页面
     */
    public void backToActivity(Class<?> cls){

        int index = -1;
        int size = activityStack.size();
        Activity activity = null;
        for (int i=0; i<size; i++){
            activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                index = i;
            }
        }

        if(index != -1){
            System.out.println("回退删除");
            for(int i=index+1; i<size; i++){
                activity = activityStack.get(index+1);
                removeActivity(activity);
            }
        }
    }

    /**
     * 获取Activity索引
     * @return
     */
    public int getActivityIndex(Class<?> cls){
        int size = activityStack.size();
        for (int i=size-1; i>-1; i--){
            if (activityStack.get(i).getClass().equals(cls)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * activity的跳转
     * @param context
     */
    public  void goToMain(Context context){
        goToMain(context, 0);
    }


    public  void goToMain(Context context, int selectIndex){
        if(context instanceof BaseActivity){
            Activity activity = ((BaseActivity)context).getParent();
           /* if(activity != null && activity instanceof MainActivity){
                ((MainActivity)activity).setSelectedIndex(selectIndex);
                return;
            }*/
            if(activity != null && activity instanceof KoalaMartActivity){
                ((KoalaMartActivity)activity).changeFragment(selectIndex);
                return;
            }
        }
    //    Intent intent = new Intent(context, MainActivity.class);
    //    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     //   intent.putExtra(Constants.MAIN_SELECTED_INDEX, selectIndex + "");
     //   context.startActivity(intent);
    }
}
