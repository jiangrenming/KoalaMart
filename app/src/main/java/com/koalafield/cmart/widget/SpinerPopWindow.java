package com.koalafield.cmart.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CategryOneAdapter;
import com.koalafield.cmart.adapter.CategryTwoAdapter;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.presenter.categry.CategryTwoPresenter;
import com.koalafield.cmart.presenter.categry.ICategryTwoPresenter;
import com.koalafield.cmart.ui.activity.search.GoodsListActivity;
import com.koalafield.cmart.ui.view.categry.ICategryTwoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miao on 2017/9/29.
 * 自定义PopupWindow 实现下拉列表效果
 */

public  class SpinerPopWindow<T> extends PopupWindow implements ICategryTwoView<List<CategryOneBean>> {

    private List<T> datas;
    private Context mContext;
    private LayoutInflater mInflater;
    private RecyclerView categry_one;
    private RecyclerView categry_two;
    private Activity mActivity;
    private ImageView categry_img;

    public SpinerPopWindow(Context context, List<T> list){
        this.mContext=context;
        this.datas = list;
         mInflater = LayoutInflater.from(mContext);
         mActivity= (Activity) context;
         initPopup();
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopup(){
        View view = mInflater.inflate(R.layout.popuwindow_categry, null);
        setContentView(view);
        //设置PopupWindow宽高
        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        setWidth(display.getWidth());
        setHeight(display.getHeight());
        //设置背景
        ColorDrawable dw = new ColorDrawable(0x60000000);
        setBackgroundDrawable(dw);
        setOutsideTouchable(true);
        setFocusable(true);
        categry_one =  view.findViewById(R.id.categry_one);
        categry_two =  view.findViewById(R.id.categry_two);
        categry_img = view.findViewById(R.id.categry_img);
        initOneData();
    }

    private CategryOneAdapter categryOneAdapter;
    private CategryTwoAdapter categryTwoAdapter;

    private void initOneData() {
        if (datas != null && datas.size() >0){
            List<CategryOneBean> data = (List<CategryOneBean>) datas;
            if (categryOneAdapter == null){
                categryOneAdapter = new CategryOneAdapter(mContext,data);
                RecyclerViewHelper.initRecyclerViewV(mContext,categry_one,false,categryOneAdapter);
            }else {
                categryOneAdapter.cleanItems();
                categryOneAdapter.addItems(data);
            }
            int categrySuperId = data.get(0).getId();
            for (int i = 0; i < data.size(); i++) {
                CategryOneBean categryOneBean = data.get(i);
                if (i == 0){
                    categryOneBean.setSelect(true);
                }else {
                    categryOneBean.setSelect(false);
                }
            }
            Glide.with(mContext).load(data.get(0).getImg()).asBitmap().
                    placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(categry_img);
            requestCategrtTwos(categrySuperId);
            categryOneAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    CategryOneBean item = categryOneAdapter.getItem(position);
                    List<CategryOneBean> data = categryOneAdapter.getData();
                    for (int i = 0; i < data.size(); i++) {
                        if (item.getId() == data.get(i).getId()){
                            data.get(i).setSelect(true);
                        }else {
                            data.get(i).setSelect(false);
                        }
                    }
                    categryOneAdapter.updateItems(data);
                    Glide.with(mContext).load(item.getImg()).asBitmap().
                            placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(categry_img);
                    requestCategrtTwos(item.getId());
                }
            });
        }
    }

    private void requestCategrtTwos(int id){
        Map<String,String > params = new HashMap<>();
        params.put("id",String.valueOf(id));
        ICategryTwoPresenter categryTwoPresenter = new CategryTwoPresenter(this);
        categryTwoPresenter.getCategryTwoData(params);
    }

    @Override
    public void onSucessTwoFul(final List<CategryOneBean> data) {
        if (data != null && data.size() > 0) {
            categry_two.setVisibility(View.VISIBLE);
            if (null == categryTwoAdapter) {
                categryTwoAdapter = new CategryTwoAdapter(mContext,data);
                RecyclerViewHelper.initRecyclerViewG(mContext, categry_two,categryTwoAdapter,3);
            } else {
                categryTwoAdapter.cleanItems();
                categryTwoAdapter.addItems(data);
            }
            categryTwoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (mContext != null && isShowing()){
                        dismiss();
                    }
                    //跳转界面
                    Intent intent =new Intent(mContext, GoodsListActivity.class);
                    intent.putExtra("cateId",data.get(position).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onFailure(String message,int code) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadNoTwoFul() {
        categry_two.setVisibility(View.GONE);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    /**
     * 设置PopupWindow的位置
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 2);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetError(EmptyLayout.OnRetryListener onRetryListener) {

    }
}
