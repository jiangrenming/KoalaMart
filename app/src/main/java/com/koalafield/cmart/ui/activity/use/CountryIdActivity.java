package com.koalafield.cmart.ui.activity.use;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dl7.recycler.divider.DividerItemDecoration;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CityAdapter;
import com.koalafield.cmart.adapter.ContactAdapter;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.user.CountryCode;
import com.koalafield.cmart.presenter.user.CountryCodePresenter;
import com.koalafield.cmart.presenter.user.ICountryCodePresenter;
import com.koalafield.cmart.ui.view.user.ICountryCodeView;
import com.koalafield.cmart.widget.LetterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jiangrenming on 2018/6/3.
 */

public class CountryIdActivity extends BaseActivity implements ICountryCodeView<List<CountryCode>>{




    @BindView(R.id.contact_list)
    RecyclerView contact_list;
   /* @BindView(R.id.letter_view)
    LetterView letter_view;
    private LinearLayoutManager layoutManager;*/
    private ContactAdapter adapter;
 //   private List<CountryCode> contactNames = new ArrayList<>();

    @Override
    public int attchLayoutRes() {
        return R.layout.layout_country;
    }

    @Override
    public void initDatas() {
        ICountryCodePresenter presenter = new CountryCodePresenter(this);
        presenter.getData();
    }

    @Override
    public void upDateViews() {
    }

    @Override
    public void onCountryCodeFul(final List<CountryCode> data) {
        if (data != null && data.size() >0){
            CityAdapter adapter = new CityAdapter(this,data);
            RecyclerViewHelper.initRecyclerViewV(this,contact_list,true,adapter);
            adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(CountryIdActivity.this,data.get(position).getCountry(),Toast.LENGTH_SHORT).show();
                    CountryCode countryCode = data.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("countyrId",countryCode.getCodeStr());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });

        }
    }

    @Override
    public void onCountryCodeFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
