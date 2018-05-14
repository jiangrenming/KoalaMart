package com.koalafield.cmart.ui.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bananer.MZBannerView;
import com.koalafield.cmart.bananer.MZHolderCreator;
import com.koalafield.cmart.bananer.MZViewHolder;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.home.HomeBanaerBean;
import com.koalafield.cmart.presenter.home.HomeBananerPresenter;
import com.koalafield.cmart.ui.view.home.IBananerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public class HomeActivity extends TabBaseActivity implements IBananerView<List<HomeBanaerBean>>{


    @BindView(R.id.nomral_banner)
    MZBannerView banner;
    @BindView(R.id.search)
    ImageView search;

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
    public void upDateViews() {}

    @Override
    public void onSucessFul(List<HomeBanaerBean> data) {
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
        }
    }

    @Override
    public void onFailure(String message) {
        Log.i("加载异常",message);
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
            Glide.with(context).load(data.getImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(mImageView);
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
