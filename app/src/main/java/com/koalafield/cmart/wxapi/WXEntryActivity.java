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
		Log.d("errCode=",resp.errCode+"/"+"type="+resp.getType());
		switch (resp.errCode){
			case BaseResp.ErrCode.ERR_OK:
				if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){ //分享
					EventBus.getDefault().post(new LoginEvent(Constants.WX_SHARE,resp.errCode));
					finish();
				}else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH){
					SendAuth.Resp authResp = (SendAuth.Resp) resp;
					String code = authResp.code;
					Log.d("errCode==",resp.errCode+"");
					EventBus.getDefault().post(new LoginEvent(Constants.WX_LOGIN,code));
					finish();
				}
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				Toast.makeText(this,"拒绝授权登录",Toast.LENGTH_SHORT).show();
				finish();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				Toast.makeText(this,"取消登录",Toast.LENGTH_SHORT).show();
				finish();
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