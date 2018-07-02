package com.koalafield.cmart.ui.activity.order;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.DisCountAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.presenter.user.CurennyDisCountPresenter;
import com.koalafield.cmart.presenter.user.ICurennyDisCountPresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.view.user.IDisCountListView;
import com.koalafield.cmart.utils.SwipeRefreshHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiangrenming on 2018/7/2.
 */

public class CounPonActivity extends BaseActivity implements IDisCountListView<List<DisCountBean>> {

    @BindView(R.id.time_discount)
    RecyclerView time_discount;
    @BindView(R.id.empty_discount)
    LinearLayout empty_discount;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_name)
    TextView top_name;

    private DisCountAdapter disCountAdapter;
    private ICurennyDisCountPresenter disCountPresenter;


    @Override
    public int attchLayoutRes() {
        return R.layout.activity_curenycoupon;
    }

    @Override
    public void initDatas() {
        top_name.setText("我的优惠劵");
        disCountAdapter = new DisCountAdapter(this);
        RecyclerViewHelper.initRecyclerViewV(this,time_discount,true,disCountAdapter);
        disCountPresenter = new CurennyDisCountPresenter(this);

    }

    @Override
    public void upDateViews() {
        disCountPresenter.getData();
    }

    @Override
    public void onDisCountSucessFul(final List<DisCountBean> data) {
        if (data != null ){
            disCountAdapter.updateItems(data);
            disCountAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("disCount",data.get(position));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onDisCountFailure(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (code == 401) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("type", 3);
            startActivity(intent);
        }
    }


    @OnClick(R.id.back)
    public  void curentBack(View view){
        switch (view.getId()){
            case  R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void loadDisCountEmptyData() {
        empty_discount.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadDisCountNoMoreData() {}

    @Override
    public void loadDisCountMoreData(List<DisCountBean> data) {}
}
