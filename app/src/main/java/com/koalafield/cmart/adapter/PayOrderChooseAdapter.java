package com.koalafield.cmart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.order.Payment;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/28
 */

public class PayOrderChooseAdapter extends BaseQuickAdapter<Payment> {


    public PayOrderChooseAdapter(Context context, List<Payment> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.order_choose_pay_item;
    }

    @Override
    protected void convert(BaseViewHolder holder, final Payment item) {
        ImageView pay_img = holder.getView(R.id.pay_img);
        ImageView close = holder.getView(R.id.close);
        TextView comfirm_pay = holder.getView(R.id.comfirm_pay);
        holder.setText(R.id.pay_name,item.getDisplayName());
        Glide.with(mContext).load(item.getIcon()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(pay_img);
        final ImageView iv_balance_select = holder.getView(R.id.iv_balance_select);
        if (item.isSelect()){
            iv_balance_select.setImageResource(R.mipmap.select);
        }else {
            iv_balance_select.setImageResource(R.mipmap.un_select);
        }
        iv_balance_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isSelect()){
                    iv_balance_select.setImageResource(R.mipmap.un_select);
                    mPayCallBack.onSucess(item,false);
                }else {
                    iv_balance_select.setImageResource(R.mipmap.select);
                    mPayCallBack.onSucess(item,true);
                }
            }
        });
        comfirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayCallBack.paySdk(item);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayCallBack.onClosePopu();
            }
        });
    }

    public  PayClickCallBack mPayCallBack;
    public  void setmPayCallBack(PayClickCallBack payCallBack){
        this.mPayCallBack = payCallBack;
    }
    public  interface  PayClickCallBack{
        void onSucess(Payment item, boolean isSelect);
        void paySdk(Payment item);
        void onClosePopu();
    }
}
