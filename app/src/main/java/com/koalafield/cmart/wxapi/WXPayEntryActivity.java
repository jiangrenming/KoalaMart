package com.koalafield.cmart.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.event.LoginEvent;
import com.koalafield.cmart.utils.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
;import org.greenrobot.eventbus.EventBus;

/**
 *
 * @author jiangrenming
 * @date 2018/1/13
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {}


    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = baseResp.errCode;
            if (code == 0){
                Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
            }else if (code == -2){
                Toast.makeText(this,"取消支付",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
            }
            EventBus.getDefault().post(new LoginEvent(Constants.WX_PAY,code));
            finish();
        }
    }
}
