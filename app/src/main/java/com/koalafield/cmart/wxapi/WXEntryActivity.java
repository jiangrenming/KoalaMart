package com.koalafield.cmart.wxapi;

import com.koalafield.cmart.utils.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


/**
 * @author jiangrenming
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{


	private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
		//将应用的appid注册到微信
		api.registerApp(Constants.APP_ID);
		try {
			boolean result =  api.handleIntent(getIntent(), this);
			if(!result){
				Log.d("WXEntryActivity =","参数不合法，未被SDK处理，退出");
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
		finish();
	}

	@Override
	public void onReq(BaseReq req) {
		Log.d("baseReq:", req.toString());
	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				break;
			case BaseResp.ErrCode.ERR_UNSUPPORT:
				break;
			default:
				break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		api.handleIntent(data,this);
	}


}