package com.koalafield.cmart.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import com.koalafield.cmart.ui.activity.use.DisCountActivity;
import com.koalafield.cmart.ui.activity.use.PurchareOffActivity;
import com.koalafield.cmart.ui.view.home.IBananerView;
import com.koalafield.cmart.ui.view.home.IGoodsCategryView;
import com.koalafield.cmart.ui.view.home.IHomeToolsView;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.EmptyLayout;

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
    @BindView(R.id.goods_one_name)
    TextView goods_one_name;
    @BindView(R.id.categry_one_more)
    TextView categry_one_more;
    @BindView(R.id.categry_one_recycler)
    RecyclerView categry_one_recycler;
    @BindView(R.id.goods_categry_two)
    TextView goods_categry_two;
    @BindView(R.id.categry_two_recycler)
    RecyclerView categry_two_recycler;
    @BindView(R.id.goods_categry_three)
    ImageView goods_categry_three;
    @BindView(R.id.categry_three_recycler)
    RecyclerView categry_three_recycler;

    private GoodsCategryAdapter goodsCategryAdapter;
    private GoodsCategryTwoAdapter goodsCategryTwoAdapter;
    private GoodsCategryThreeAdapter goodsCategryThree;

    @Override
    public int attchLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initDatas() {
        //获取轮播图
        presenter = new HomeBananerPresenter(this);
        presenter.getData();

    }

    @Override
    public void upDateViews() {
        //获取商品分类
        IHomeGoodsCategryPresenter goodsCategryPresenter = new HomeGoodsCategryPresenter(this);
        goodsCategryPresenter.getData();

        //获取导航栏
        IHomeToolsBarPresenter toolsBarPresenter = new HomeToolsBarPresenter(this);
        toolsBarPresenter.getData();
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
            banner.setPages(data, new MZHolderCreator() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new NomralBannerViewHolder();
                }
            });
            banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
                @Override
                public void onPageClick(View view, int position) {
                    int dataId = data.get(position).getDataId();
                    Intent intent = new Intent(HomeActivity.this,GoodsDetailActivity.class);
                    intent.putExtra("contentId",dataId);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onFailure(String message) {
        Log.i("加载异常","异常信息为:"+message);
    }

    @Override
    public void onGoodsCategrySucessFul( List<GoodsCategryBean> data) {
        if (data != null && data.size() >0){
            for (int i = 0; i < data.size(); i++) {
                 String showType = data.get(i).getShowType();
                final  GoodsCategryBean goodsCategryBean = data.get(i);
                 int categryId = goodsCategryBean.getCategoryId();
                if (!StringUtils.isEmpty(showType) && "ImageGoodsArray".equals(showType)){
                    goods_one_name.setText(goodsCategryBean.getName());
                    if (goodsCategryAdapter == null ){
                        goodsCategryAdapter = new GoodsCategryAdapter(HomeActivity.this,goodsCategryBean.getGoodsList());
                        RecyclerViewHelper.initRecyclerViewH(HomeActivity.this,categry_one_recycler,false,goodsCategryAdapter);
                    }else {
                        goodsCategryAdapter.cleanItems();
                        goodsCategryAdapter.addItems(goodsCategryBean.getGoodsList());
                    }
                    goodsCategryAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //跳入详情界面
                            Log.i("点击的位置0:",position+"");
                            Intent intent = new Intent(HomeActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("contentId",goodsCategryBean.getGoodsList().get(position).getId());
                            startActivity(intent);
                        }
                    });
                    //更多按钮
                    categry_one_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //传入分类id，跳转分类列表
                        }
                    });
                }else if ("GoodsPiece".equals(showType)){
                    goods_categry_two.setText(goodsCategryBean.getName());
                    if (goodsCategryTwoAdapter == null ){
                        goodsCategryTwoAdapter = new GoodsCategryTwoAdapter(HomeActivity.this,goodsCategryBean.getGoodsList());
                        RecyclerViewHelper.initRecyclerViewG(HomeActivity.this,categry_two_recycler,goodsCategryTwoAdapter,3);
                    }else {
                        goodsCategryTwoAdapter.cleanItems();
                        goodsCategryTwoAdapter.addItems(goodsCategryBean.getGoodsList());
                    }
                    goodsCategryTwoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.i("点击的位置1:",position+"");
                            Intent intent = new Intent(HomeActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("contentId",goodsCategryBean.getGoodsList().get(position).getId());
                            startActivity(intent);
                        }
                    });
                    goods_categry_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //传入分类id，跳转分类列表
                        }
                    });
                }else if ("CategoryGoods".equals(showType)){
                    loadIntoUseFitWidth(HomeActivity.this,goodsCategryBean.getImg(),R.mipmap.default_img,goods_categry_three);
                    if (goodsCategryThree == null ){
                        goodsCategryThree = new GoodsCategryThreeAdapter(HomeActivity.this,goodsCategryBean.getGoodsList());
                        RecyclerViewHelper.initRecyclerViewG(HomeActivity.this,categry_three_recycler,goodsCategryThree,3);
                    }else {
                        goodsCategryThree.cleanItems();
                        goodsCategryThree.addItems(goodsCategryBean.getGoodsList());
                    }
                    goodsCategryThree.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.i("点击的位置2:",position+"");
                            Intent intent = new Intent(HomeActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("contentId",goodsCategryBean.getGoodsList().get(position).getId());
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onGoodsCategryFailure(String message) {
        Log.i("获取商品分类数据失败:","异常信息"+message);
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
                    if (toolsBarBean.getTypeName().equals("Category")){
                        Intent intent = new Intent(HomeActivity.this,CategryActivity.class);
                        startActivity(intent);
                    }else if (toolsBarBean.getTypeName().equals("CustomerServices")){

                    }else if (toolsBarBean.getTypeName().equals("Buyed")){
                        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
                        if (!StringUtils.isEmpty(tickets)){
                            Intent intent = new Intent(HomeActivity.this,PurchareOffActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(HomeActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }else if (toolsBarBean.getTypeName().equals("Coupon")){
                        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
                        if (!StringUtils.isEmpty(tickets)){
                            Intent intent = new Intent(HomeActivity.this,DisCountActivity.class);
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
