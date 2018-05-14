package com.koalafield.cmart.ui.activity;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dl7.recycler.divider.DividerItemDecoration;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CategryOneAdapter;
import com.koalafield.cmart.adapter.CategryTwoAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.bean.categry.CategryTwoBean;
import com.koalafield.cmart.presenter.categry.CategryOnePresenter;
import com.koalafield.cmart.presenter.categry.ICategryPresenter;
import com.koalafield.cmart.ui.view.categry.ICategryView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.dl7.recycler.helper.RecyclerViewHelper.initRecyclerViewV;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public class CategryActivity extends TabBaseActivity implements ICategryView{


    @BindView(R.id.categry_one)
    RecyclerView categry_one;
    @BindView(R.id.categry_two)
    RecyclerView categry_two;
    @BindView(R.id.categry_img)
    ImageView categry_img;

    private  ICategryPresenter categryPresenter;
    private CategryOneAdapter categryOneAdapter;
    private List<CategryOneBean> one_list = new ArrayList<>();
    private CategryTwoAdapter categryTwoAdapter;


    @Override
    public int attchLayoutRes() {
        return R.layout.fragment_categry;
    }

    @Override
    public void initDatas() {
        Map<String,String> params = new HashMap<>();
        categryPresenter = new CategryOnePresenter(this);
        params.put("id","");
        categryPresenter.getCategryData(params);
        categryOneAdapter = new CategryOneAdapter(this,one_list);
        initRecyclerViewV(this,categry_one,false,categryOneAdapter);

        categryOneAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("点击的位置:",position+"");
                CategryOneBean item = categryOneAdapter.getItem(position);
                requestCategrtTwos(item.getId());
            }
        });
    }

    @Override
    public void upDateViews() {}



    @Override
    public void onSucessFul(Object data) {
        if (data != null){
            List<CategryOneBean> datas = (List<CategryOneBean>) data;
            Log.i("获取分类列表",datas.size()+"/第一个数据:"+datas.get(0).getName());
            if (datas != null &&datas.size() >0){
                categryOneAdapter.addItems(datas);
                int categrySuperId = datas.get(0).getId();
                requestCategrtTwos(categrySuperId);
            }
        }
    }

    @Override
    public void onFailure(String message) {
        Log.i("获取分类列表",message);
    }

    @Override
    public void onSucessTwo(Object data) {
        if (data != null){
            List<CategryTwoBean> categryTwo = (List<CategryTwoBean>) data;
            if (categryTwo != null && categryTwo.size() >0 ){
                if (null == categryTwoAdapter){
                    categryTwoAdapter = new CategryTwoAdapter(this,categryTwo);
                    initRecyclerViewV(this,categry_two,false,categryTwoAdapter);
                }else {
                    categryTwoAdapter.cleanItems();
                    categryTwoAdapter.addItems(categryTwo);
                }
            }
        }
    }

    private void requestCategrtTwos(int id){
        Map<String,String > params = new HashMap<>();
        params.put("id",String.valueOf(id));
        categryPresenter.getCategryTwoData(params);
    }

}
