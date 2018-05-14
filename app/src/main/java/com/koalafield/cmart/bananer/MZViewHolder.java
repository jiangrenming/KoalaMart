package com.koalafield.cmart.bananer;

import android.content.Context;
import android.view.View;

/**
 * Created by jiangrenming on 2018/4/12.
 */

public interface MZViewHolder <T> {

    /**
     *  创建View
     * @param context
     * @return
     */
    View createView(Context context);
    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);
}
