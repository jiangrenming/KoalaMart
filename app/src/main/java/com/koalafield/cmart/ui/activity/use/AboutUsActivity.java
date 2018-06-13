package com.koalafield.cmart.ui.activity.use;

import android.graphics.Bitmap;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.koalafield.cmart.BuildConfig;
import com.koalafield.cmart.R;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.widget.ProgressAlertDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;

    private ProgressAlertDialog dialog;
    private  int type ;
    private String url;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_aboutus;
    }

    @Override
    public void initDatas() {
         type = getIntent().getIntExtra("type", -1);
        if (type == 1){
            top_name.setText("服务条款");
        }else if (type == 3){
            url = getIntent().getStringExtra("url");
        }else {
            top_name.setText("关于我们");
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setBlockNetworkLoads(false);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (dialog == null) {
                    dialog=  new ProgressAlertDialog(AboutUsActivity.this);
                    dialog.show();
                    webView.setEnabled(false);// 当加载网页的时候将网页进行隐藏
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
                if (dialog != null){
                    dialog.dismiss();
                    dialog = null;
                    webView.setEnabled(true);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (type == 3){
                    top_name.setText(title);
                }
            }
        });
    }

    @Override
    public void upDateViews() {
        if (type == 1){
            webView.loadUrl(BuildConfig.POST_URL+"wechat/Privacy");
        }else if (type == 3){
            webView.loadUrl(url);
        }else {
            webView.loadUrl(BuildConfig.POST_URL+"wechat/AboutUs");
        }
    }

    @OnClick(R.id.back)
    public void aboutUsClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
