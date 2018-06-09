package com.koalafield.cmart.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CategryOneAdapter;
import com.koalafield.cmart.adapter.CategryTwoAdapter;
import com.koalafield.cmart.base.fragment.BaseFragment;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.presenter.categry.CategryOnePresenter;
import com.koalafield.cmart.presenter.categry.CategryTwoPresenter;
import com.koalafield.cmart.presenter.categry.ICategryPresenter;
import com.koalafield.cmart.presenter.categry.ICategryTwoPresenter;
import com.koalafield.cmart.ui.activity.CategryActivity;
import com.koalafield.cmart.ui.activity.search.GoodsListActivity;
import com.koalafield.cmart.ui.view.categry.ICategryTwoView;
import com.koalafield.cmart.ui.view.categry.ICategryView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.dl7.recycler.helper.RecyclerViewHelper.initRecyclerViewG;
import static com.dl7.recycler.helper.RecyclerViewHelper.initRecyclerViewV;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 * 分类界面
 */

public class CategryFragment extends BaseFragment implements ICategryView<List<CategryOneBean>>
        ,ICategryTwoView<List<CategryOneBean>> {


    @BindView(R.id.categry_one)
    RecyclerView categry_one;
    @BindView(R.id.categry_two)
    RecyclerView categry_two;
    @BindView(R.id.categry_img)
    ImageView categry_img;

    private ICategryPresenter categryPresenter;
    private CategryOneAdapter categryOneAdapter;
    private List<CategryOneBean> one_list = new ArrayList<>();
    private CategryTwoAdapter categryTwoAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_categry;
    }

    @Override
    protected void initViews() {}

    private void requestCategrtTwos(int id){
        Map<String,String > params = new HashMap<>();
        params.put("id",String.valueOf(id));
        ICategryTwoPresenter categryTwoPresenter = new CategryTwoPresenter(this);
        categryTwoPresenter.getCategryTwoData(params);
    }

    @Override
    protected void updateViews() {
        Map<String,String> params = new HashMap<>();
        categryPresenter = new CategryOnePresenter(this);
        params.put("id","");
        categryPresenter.getCategryData(params);
        categryOneAdapter = new CategryOneAdapter(mContext,one_list);
        initRecyclerViewV(mContext,categry_one,false,categryOneAdapter);

        categryOneAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("点击的位置:",position+"");
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

    @Override
    public void onSucessFul(List<CategryOneBean> data) {
        if (data != null && data.size() >0){
            one_list = data;
            categryOneAdapter.updateItems(data);
            int categrySuperId = data.get(0).getId();
            for (int i = 0; i < data.size(); i++) {
                CategryOneBean categryOneBean = data.get(i);
                if (i == 0){
                    categryOneBean.setSelect(true);
                }else {
                    categryOneBean.setSelect(false);
                }
            }
            Glide.with(this).load(data.get(0).getImg()).asBitmap().
                    placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(categry_img);
            requestCategrtTwos(categrySuperId);
        }
    }

    @Override
    public void onFailure(String message, int code) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadNoTwoFul() {
        categry_two.setVisibility(View.GONE);
    }

    @Override
    public void onSucessTwoFul(final List<CategryOneBean> data) {
        Log.i("获取分类列表",data.size()+"/第一个数据:"+data.get(0).getName());
        if (data != null && data.size() > 0) {
            categry_two.setVisibility(View.VISIBLE);
            if (null == categryTwoAdapter) {
                categryTwoAdapter = new CategryTwoAdapter(mContext,data);
                initRecyclerViewG(mContext, categry_two,  categryTwoAdapter,3);
            } else {
                categryTwoAdapter.cleanItems();
                categryTwoAdapter.addItems(data);
            }
            categryTwoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //跳转界面
                    Intent intent = new Intent(mContext, GoodsListActivity.class);
                    intent.putExtra("cateId",data.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }
}
