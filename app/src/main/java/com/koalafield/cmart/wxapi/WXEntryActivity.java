package com.koalafield.cmart.wxapi;

import com.google.gson.Gson;
import com.koalafield.cmart.bean.event.LoginEvent;
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
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;


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
			Log.i("savedInstanceState"," sacvsa"+api.handleIntent(getIntent(), this));
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
		Log.d("baseResp:",resp.toString());
		Toast.makeText(this,resp.errCode,Toast.LENGTH_SHORT).show();
		if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){ //分享
			EventBus.getDefault().post(new LoginEvent(Constants.WX_SHARE,resp.errCode));
		}else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH){
			SendAuth.Resp authResp = (SendAuth.Resp) resp;
			String code = "";
			if (authResp.errCode == BaseResp.ErrCode.ERR_OK){ //用户同意
				code = authResp.code;
			}
			EventBus.getDefault().post(new LoginEvent(Constants.WX_LOGIN,authResp.errCode,code));
		}
		finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		api.handleIntent(data,this);
	}


}