package com.koalafield.cmart.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.GoodsCategryAdapter;
import com.koalafield.cmart.adapter.GoodsCategryThreeAdapter;
import com.koalafield.cmart.adapter.GoodsCategryTwoAdapter;
import com.koalafield.cmart.adapter.HomeGoodsAdapter;
import com.koalafield.cmart.adapter.PurchareOffAdapter;
import com.koalafield.cmart.adapter.ToolsBarAdapter;
import com.koalafield.cmart.bananer.MZBannerView;
import com.koalafield.cmart.bananer.MZHolderCreator;
import com.koalafield.cmart.bananer.MZViewHolder;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.home.GoodsCategryBean;
import com.koalafield.cmart.bean.home.HomeBanaerBean;
import com.koalafield.cmart.bean.home.ToolsBarBean;
import com.koalafield.cmart.presenter.goods.HotSearchPresenter;
import com.koalafield.cmart.presenter.home.HomeBananerPresenter;
import com.koalafield.cmart.presenter.home.HomeGoodsCategryPresenter;
import com.koalafield.cmart.presenter.home.HomeToolsBarPresenter;
import com.koalafield.cmart.presenter.home.IHomeGoodsCategryPresenter;
import com.koalafield.cmart.presenter.home.IHomeToolsBarPresenter;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.activity.goods.SearchActivity;
import com.koalafield.cmart.ui.activity.order.MartOrderActivity;
import com.koalafield.cmart.ui.activity.search.GoodsListActivity;
import com.koalafield.cmart.ui.activity.use.AboutUsActivity;
import com.koalafield.cmart.ui.activity.use.CollectionActivity;
import com.koalafield.cmart.ui.activity.use.DisCountActivity;
import com.koalafield.cmart.ui.activity.use.PurchareOffActivity;
import com.koalafield.cmart.ui.view.home.IBananerView;
import com.koalafield.cmart.ui.view.home.IGoodsCategryView;
import com.koalafield.cmart.ui.view.home.IHomeToolsView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.EmptyLayout;
import com.koalafield.cmart.widget.FullyLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public class HomeActivity extends TabBaseActivity implements IBananerView<List<HomeBanaerBean>>,
        IGoodsCategryView<List<GoodsCategryBean>>,IHomeToolsView<List<ToolsBarBean>>{


    @BindView(R.id.nomral_banner)
    MZBannerView banner;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.tools_bar)
    RecyclerView  tools_bar;
    @BindView(R.id.home_goods)
    RecyclerView home_goods;
    private HomeGoodsAdapter mAdapter;


    @Override
    public int attchLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initDatas() {
        //获取轮播图
        presenter = new HomeBananerPresenter(this);
        presenter.getData();
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        home_goods.setNestedScrollingEnabled(false);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        home_goods.setLayoutManager(linearLayoutManager);
        home_goods.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void upDateViews() {
        //获取导航栏
        IHomeToolsBarPresenter toolsBarPresenter = new HomeToolsBarPresenter(this);
        toolsBarPresenter.getData();

        //获取商品分类
        IHomeGoodsCategryPresenter goodsCategryPresenter = new HomeGoodsCategryPresenter(this);
        goodsCategryPresenter.getData();


    }

    @OnClick({R.id.search})
    public  void setHomeClick(View v){
        switch (v.getId()){
            case R.id.search: //搜索
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onSucessFul(final List<HomeBanaerBean> data) {
        if (data != null && data.size() >0 ){
            banner.setIndicatorVisible(true);
            banner.setIndicatorRes(R.drawable.unseletc_dot,R.drawable.select_dot);
            banner.setIndicatorAlign(MZBannerView.IndicatorAlign.BOTTOM);
            banner.setIndicatorPadding(10,0,0,10);
            banner.setDelayedTime(5000);
            banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
                @Override
                public void onPageClick(View view, int position) {
                    String typeName = data.get(position).getTypeName();
                    if (!StringUtils.isEmpty(typeName) && "URL".equalsIgnoreCase(typeName)){
                        Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                        intent.putExtra("type",3);
                        intent.putExtra("url",data.get(position).getUrl());
                        startActivity(intent);
                    }else {
                        int dataId = data.get(position).getDataId();
                        Intent intent = new Intent(HomeActivity.this,GoodsDetailActivity.class);
                        intent.putExtra("contentId",dataId);
                        startActivity(intent);
                    }
                }
            });

            banner.setPages(data, new MZHolderCreator<MZViewHolder>() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new NomralBannerViewHolder();
                }
            });
        }
    }

    @Override
    public void onFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGoodsCategrySucessFul( List<GoodsCategryBean> data) {
        if (data != null && data.size() >0){
            if (mAdapter == null){
                mAdapter = new HomeGoodsAdapter(this,data);
                home_goods.setAdapter(mAdapter);
            //    RecyclerViewHelper.initRecyclerViewV(this,home_goods,false,mAdapter);
            }else {
                mAdapter.cleanItems();
                mAdapter.addItems(data);
            }
        }
    }

    @Override
    public void onGoodsCategryFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onToolsBarSucessFul(final List<ToolsBarBean> data) {
        if (data != null && data.size() >0){
            ToolsBarAdapter toolsBarAdapter = new ToolsBarAdapter(this,data);
            RecyclerViewHelper.initRecyclerViewG(this,tools_bar,false,toolsBarAdapter,4);
            toolsBarAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ToolsBarBean toolsBarBean = data.get(position);
                    String tickets = ShareBankPreferenceUtils.getString("tickets", null);
                    if (toolsBarBean.getTypeName().equals("Category")){
                        Intent intent = new Intent(HomeActivity.this,CategryActivity.class);
                        startActivity(intent);
                    }else if (toolsBarBean.getTypeName().equals("CustomerServices")){

                    }else if (toolsBarBean.getTypeName().equals("Buyed")){
                        if (!StringUtils.isEmpty(tickets)){
                            Intent intent = new Intent(HomeActivity.this,PurchareOffActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(HomeActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }else if (toolsBarBean.getTypeName().equals("Coupon")){
                        if (!StringUtils.isEmpty(tickets)){
                            Intent intent = new Intent(HomeActivity.this,DisCountActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(HomeActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else  if ("Order".equals(toolsBarBean.getTypeName())){
                        if (!StringUtils.isEmpty(tickets)){
                            Intent intent = new Intent(HomeActivity.this,MartOrderActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(HomeActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else  if ("AllCategory".equals(toolsBarBean.getTypeName())){
                        Intent intent = new Intent(HomeActivity.this,CategryActivity.class);
                        startActivity(intent);
                    }else if ("FollowList".equals(toolsBarBean.getTypeName())){
                        if (!StringUtils.isEmpty(tickets)){
                            Intent intent = new Intent(HomeActivity.this,CollectionActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(HomeActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            });
        }

    }

    @Override
    public void onToolsBarFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    /**
     * 正常轮播图
     */
    private class NomralBannerViewHolder implements MZViewHolder<HomeBanaerBean>{
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.nomral_item,null);
            mImageView = (ImageView) view.findViewById(R.id.nomral_img);
            return view;
        }

        @Override
        public void onBind(Context context, int position, HomeBanaerBean data) {
            loadIntoUseFitWidth(context,data.getImg(),R.mipmap.default_img,mImageView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
    }
}
