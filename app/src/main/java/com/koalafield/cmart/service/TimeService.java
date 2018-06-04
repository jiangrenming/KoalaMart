package com.koalafield.cmart.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.koalafield.cmart.utils.Constants;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class TimeService extends Service {

    private CountDownTimer mCodeTimer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int mTime = intent.getIntExtra("time",-1);
        // 第一个参数是总时间， 第二个参数是间隔
        mCodeTimer = new CountDownTimer(mTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 广播剩余时间
                broadcastUpdate(Constants.IN_RUNNING, millisUntilFinished / 1000 + "");
            }
            @Override
            public void onFinish() {
                // 广播倒计时结束
                broadcastUpdate(Constants.END_RUNNING);
                // 停止服务
                stopSelf();
            }
        };
        // 开始倒计时
        mCodeTimer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    // 发送广播
    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    // 发送带有数据的广播
    private void broadcastUpdate(final String action, String time) {
        final Intent intent = new Intent(action);
        intent.putExtra("time", time);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
