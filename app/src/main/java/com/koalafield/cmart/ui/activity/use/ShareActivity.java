package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.ShareAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.event.LoginEvent;
import com.koalafield.cmart.bean.user.ShareBean;
import com.koalafield.cmart.bean.user.ShareDataBean;
import com.koalafield.cmart.presenter.user.IShareCallBackPresent;
import com.koalafield.cmart.presenter.user.ISharePresenter;
import com.koalafield.cmart.presenter.user.ShareCallBackPresent;
import com.koalafield.cmart.presenter.user.SharePresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.PersonActivity;
import com.koalafield.cmart.ui.view.user.IShareCallView;
import com.koalafield.cmart.ui.view.user.IShareView;
import com.koalafield.cmart.utils.Constants;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/6/20.
 */

public class ShareActivity extends BaseActivity implements IShareView<ShareBean>,IShareCallView<BaseResponseBean>{

    @BindView(R.id.share_infos)
    RecyclerView share_infos;
    @BindView(R.id.share_img)
    ImageView share_img;
    @BindView(R.id.share_txt1)
    TextView share_txt1;
/*    @BindView(R.id.share_txt2)
    TextView share_txt2;*/
    @BindView(R.id.wx_friend)
    LinearLayout wx_friend;
    @BindView(R.id.wx_circle)
    LinearLayout wx_circle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;
    private IWXAPI iwxapi;
    private String code;
    @Override
    public int attchLayoutRes() {
        return R.layout.activity_share;
    }

    @Override
    public void initDatas() {
        top_name.setText("推荐/分享有礼");
        //注册微信appid到微信平台
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        iwxapi.registerApp(Constants.APP_ID);
        EventBus.getDefault().register(this);
        code = getIntent().getStringExtra("code");
    }

    @Override
    public void upDateViews() {
        ISharePresenter sharePresenter = new SharePresenter(this);
        sharePresenter.getData();
    }

    @OnClick({R.id.back,R.id.wx_friend,R.id.wx_circle})
    public  void shareClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case  R.id.wx_friend:
                share(false);
                break;
            case R.id.wx_circle:
                share(true);
                break;
            default:
                break;
        }
    }
    /**
     * 分享朋友圈和朋友
     * @param friendsCircle
     */
    public void share(boolean friendsCircle){

        if (iwxapi != null && iwxapi.isWXAppInstalled()){
            Log.i("微信分享",friendsCircle+"");
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = "http://cmart.koalafield.com/Wechat/ShareApp?inviteCode="+code;//分享url
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = "Cmart跨境超市";
            msg.description = "掌上超市，方便无限";
            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.weixinshare);
            msg.setThumbImage(thumb);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = friendsCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            iwxapi.sendReq(req);
        }else {
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSucessFul(ShareBean data) {
        Glide.with(this).load(data.getImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(share_img);
        share_txt1.setText(data.getText1()+"\n"+data.getText2());
       // share_txt2.setText(data.getText2());
        List<ShareDataBean> itemList = data.getItemList();
        if (itemList != null && itemList.size() >0){
            ShareAdapter mAdapter = new ShareAdapter(this,itemList);
            RecyclerViewHelper.initRecyclerViewG(this,share_infos,mAdapter,4);
        }
    }

    @Override
    public void onFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            skipLogin(this);
        }
    }

    @Subscribe(threadMode  = ThreadMode.MAIN)
    public  void loginEvent(LoginEvent event){
        if (event != null){
            int mType = event.mType;
            if (mType == Constants.WX_SHARE){
                int errCode = event.userAggree;
                if(errCode== BaseResp.ErrCode.ERR_USER_CANCEL||errCode==BaseResp.ErrCode.ERR_AUTH_DENIED){
                    Toast.makeText(ShareActivity.this,"取消分享",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ShareActivity.this,"分享成功",Toast.LENGTH_SHORT).show();
                    Map<String ,String> params = new HashMap<>();
                    params.put("type","ShareApp");
                    IShareCallBackPresent shareCallBackPresent = new ShareCallBackPresent(this);
                    shareCallBackPresent.setParams(params);
                    shareCallBackPresent.getData();
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onShareSucessFul(BaseResponseBean data) {
        Log.i("分成功后的回调",data.getMsg());
    }

    @Override
    public void onShareFailure(String message, int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 401){
            Intent intent = new Intent(this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }
}
