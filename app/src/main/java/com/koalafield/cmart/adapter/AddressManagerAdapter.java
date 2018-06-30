package com.koalafield.cmart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.dl7.recycler.listener.OnRemoveDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.utils.StringUtils;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 * 地址管理适配器
 */

public class AddressManagerAdapter extends BaseQuickAdapter<AddressManagerBean> {

    public AddressManagerAdapter(Context context) {
        super(context);
    }

    public AddressManagerAdapter(Context context, List<AddressManagerBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.address_manager_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, final AddressManagerBean item) {
     //   final ImageView iv_select = holder.getView(R.id.iv_select); //默认地址
        LinearLayout iv_address_edit = holder.getView(R.id.iv_address_edit); //编辑
        LinearLayout iv_address_trash = holder.getView(R.id.iv_address_trash); //删除
        holder.setText(R.id.address_name, item.getContactname())
                .setText(R.id.address_phone, item.getContactphone())
                .setText(R.id.address_detail, item.getCountry()+" "+item.getProvince()+ " "+item.getCity()+" "+item.getArea()+" "+item.getAddress());
        final boolean selected = item.isSelected();
        /*if (selected){
            iv_select.setImageResource(R.mipmap.select);
        }else {
            iv_select.setImageResource(R.mipmap.un_select);
        }*/
        //默认选择
     /*   iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要将默认状态上传,调用接口同时刷新数据（将默认选中的数据永远放在第一位）
                if (selected){
                    iv_select.setImageResource(R.mipmap.un_select);
                    mSelectBack.checkSelect(item,false);
                }else {
                    iv_select.setImageResource(R.mipmap.select);
                    mSelectBack.checkSelect(item,true);
                }
            }
        });*/


        //编辑
        iv_address_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("跳转编辑",item.getAddress());
                mSelectBack.editAddress(item);
            }
        });
        //删除
        iv_address_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除接口调用，同时刷新数据
                mSelectBack.delAddress(item);
            }
        });
    }

    public  SelectAddressCallBack mSelectBack;
    public void SelectAddressCallBack(SelectAddressCallBack selectBack){
        this.mSelectBack = selectBack;
    }
    public interface SelectAddressCallBack{
 //       void checkSelect(AddressManagerBean item,boolean isSelect);
        void editAddress(AddressManagerBean item);
        void delAddress(AddressManagerBean item);
    }

    private AddressManagerBean addressManagerBean;
    public void setSelectItem(AddressManagerBean addressManagerBean){
        this.addressManagerBean = addressManagerBean;
    }
}
