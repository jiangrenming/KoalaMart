package com.koalafield.cmart.adapter;

import android.content.Context;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.user.CountryCode;

import java.util.List;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class CityAdapter extends BaseQuickAdapter<CountryCode> {


    public CityAdapter(Context context, List<CountryCode> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.city_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, CountryCode item) {
        holder.setText(R.id.sectionTextView,item.getLetter())
                .setText(R.id.cityName,item.getCountry());
    }
}
