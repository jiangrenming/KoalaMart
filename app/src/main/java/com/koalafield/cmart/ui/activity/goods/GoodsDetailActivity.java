package com.koalafield.cmart.ui.activity.goods;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.GoodsDetailCommondAdapter;
import com.koalafield.cmart.bananer.MZBannerView;
import com.koalafield.cmart.bananer.MZHolderCreator;
import com.koalafield.cmart.bananer.MZViewHolder;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.goods.GoodsDetailsBean;
import com.koalafield.cmart.bean.goods.GoodsRecoomendBean;
import com.koalafield.cmart.bean.home.HomeBanaerBean;
import com.koalafield.cmart.presenter.cart.CartPresenter;
import com.koalafield.cmart.presenter.cart.ICartPresenter;
import com.koalafield.cmart.presenter.goods.GoodsCommondPresenter;
import com.koalafield.cmart.presenter.goods.GoodsDetailPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsCommondPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsDetailPresenter;
import com.koalafield.cmart.ui.activity.HomeActivity;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.view.cart.ICartVIew;
import com.koalafield.cmart.ui.view.goods.IGoodsCommondlView;
import com.koalafield.cmart.ui.view.goods.IGoodsDetailView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.AndroidTools;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

import static com.koalafield.cmart.ui.activity.TabBaseActivity.loadIntoUseFitWidth;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public class GoodsDetailActivity extends BaseActivity implements ICartVIew<CartNumberBean>,IGoodsCommondlView<List<GoodsRecoomendBean>>,
        IGoodsDetailView<GoodsDetailsBean>{

    //头部布局
    @BindView(R.id.goods_back)
    ImageView goods_back;
    @BindView(R.id.goods_cart_num)
    TextView goods_cart_num;
    //中间布局
    @BindView(R.id.goods_nomral_banner)
    MZBannerView goods_nomral_banner;
    @BindView(R.id.goods_detail_name)
    TextView goods_detail_name;
    @BindView(R.id.goods_detail_price)
    TextView goods_detail_price;
    @BindView(R.id.goods_detail_sale)
    TextView goods_detail_sale;
    @BindView(R.id.goods_detail_CYN_price)
    TextView goods_detail_CYN_price;
    //商品推荐
    @BindView(R.id.goods_commonds)
    RecyclerView goods_commonds;
    @BindView(R.id.webViews)
    WebView webViews;
    //底部布局
    @BindView(R.id.goods_collection)
    ImageView goods_collection;
    @BindView(R.id.goods_minus)
    ImageView goods_minus;
    @BindView(R.id.goods_number)
    EditText goods_number;
    @BindView(R.id.goods_add)
    ImageView goods_add;
    @BindView(R.id.pay_goods)
    LinearLayout pay_goods;

    private  int contentId;
    private IGoodsDetailPresenter mGoodsDetailPresenter;
    private IGoodsCommondPresenter mGoodsCoomondPresenter;
    private List<String> imags = new ArrayList<>();
    private GoodsDetailCommondAdapter mAdapter;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_goods_details;
    }

    @Override
    public void initDatas() {
        contentId = getIntent().getIntExtra("contentId",-1);
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (!StringUtils.isEmpty(tickets)){
            ICartPresenter mPresenter = new CartPresenter(GoodsDetailActivity.this);
            mPresenter.getData();
        }
    }

    @Override
    public void upDateViews() {
        Map<String,String> params = new HashMap<>();
        params.put("id",String.valueOf(contentId));
        mGoodsDetailPresenter = new GoodsDetailPresenter(this);
        mGoodsDetailPresenter.getDetails(params);

    }

    /**
     * 购物车数量
     * @param data
     */
    @Override
    public void onSucessNumberful(CartNumberBean data) {
        if (data != null){
            if (data.getCount() <= 0){
                goods_cart_num.setVisibility(View.GONE);
            }else if (data.getCount() <= 99){
                goods_cart_num.setVisibility(View.VISIBLE);
                goods_cart_num.setText(String.valueOf(data.getCount()));
            }else {
                goods_cart_num.setVisibility(View.VISIBLE);
                goods_cart_num.setText(String.format(Locale.CHINA, "%d+", 99));
            }
        }
    }

    @Override
    public void onNumberFailure(String message) {
        Log.i("获取购物车总数量:","异常:"+message);
    }

    /**
     * 商品详情的请求
     * @param data
     */
    @Override
    public void onGoodsDetailsSucessFul(GoodsDetailsBean data) {
        if (data != null){
            String[] imageList = data.getImageList();
            for (int i = 0; i < imageList.length; i++) {
                imags.add(imageList[i]);
            }
            goods_nomral_banner.setIndicatorVisible(true);
            goods_nomral_banner.setIndicatorRes(R.drawable.unseletc_dot,R.drawable.select_dot);
            goods_nomral_banner.setIndicatorAlign(MZBannerView.IndicatorAlign.BOTTOM);
            goods_nomral_banner.setIndicatorPadding(10,0,0,10);
            goods_nomral_banner.setDelayedTime(5000);
            goods_nomral_banner.setPages(imags, new MZHolderCreator() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new NomralBannerViewHolder();
                }
            });
            String goods_name = data.getName();
            if (StringUtils.isEmpty(goods_name)){
                goods_detail_name.setText(data.getShortName());
            }
            goods_detail_name.setText(goods_name);
            goods_detail_price.setText(data.getCurrency() +data.getCurrentPrice());
            goods_detail_sale.setText("销量:"+data.getSalesCount());
            goods_detail_CYN_price.setText("CYN "+String.format("%.2f", data.getCurrencyRate()* data.getCurrentPrice()));

            mGoodsCoomondPresenter = new GoodsCommondPresenter(this);
            mGoodsCoomondPresenter.getCommondGoods(data.getId());
        }
    }

    @Override
    public void onGoodsDetailsFailure(String message) {
        Log.i("获取商品详情:","异常:"+message);
    }

    /**
     * 商品推荐的请求
     * @param data
     */
    @Override
    public void onGoodsCommondSucessFul(List<GoodsRecoomendBean> data) {
        if (data != null &&data.size() >0){
            if (mAdapter == null){
                mAdapter = new GoodsDetailCommondAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewH(this,goods_commonds,false,mAdapter);
            }else {
                mAdapter.cleanItems();
                mAdapter.addItems(data);
            }
        }
    }

    @Override
    public void onGoodsCommondFailure(String message) {
        Log.i("获取商品推荐:","异常:"+message);
    }

    /**
     * 正常轮播图
     */
    private class NomralBannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.nomral_item,null);
            mImageView = (ImageView) view.findViewById(R.id.nomral_img);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            loadIntoUseFitWidth(context,data,R.mipmap.default_img,mImageView);
        }
    }
}
