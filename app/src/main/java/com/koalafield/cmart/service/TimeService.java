package com.koalafield.cmart.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.koalafield.cmart.bean.event.UpdateEvent;
import com.koalafield.cmart.ui.activity.order.OrderDetailsActivity;
import com.koalafield.cmart.utils.AndroidTools;
import com.koalafield.cmart.utils.Constants;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class TimeService extends Service {

    private CountDownTimer mCodeTimer;
    private String time;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        time = intent.getStringExtra("time");
        long  mTime = Long.valueOf(time);
        // 第一个参数是总时间， 第二个参数是间隔
        mCodeTimer = new CountDownTimer(mTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 广播剩余时间
                Log.i("millisUntilFinished= ",millisUntilFinished+"");
                EventBus.getDefault().post(new UpdateEvent(Constants.IN_RUNNING, millisUntilFinished));
            }
            @Override
            public void onFinish() {
                // 广播倒计时结束
                EventBus.getDefault().post(new UpdateEvent(Constants.END_RUNNING, 0));
                // 停止服务
                stopSelf();
            }
        };
        // 开始倒计时
        mCodeTimer.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
