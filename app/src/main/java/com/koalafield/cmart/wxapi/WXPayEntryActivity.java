package com.koalafield.cmart.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.koalafield.cmart.utils.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
;

/**
 *
 * @author jiangrenming
 * @date 2018/1/13
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

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
            switch(code) {
                case 0:
                    finish();
                    break;
                case -1:
                    finish();
                    break;
                case -2:
                    finish();
                    break;
                case -3:
                    finish();
                    break;
                case -4:
                    finish();
                    break;
                case -5:
                    finish();
                    break;
                case -6:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
