package com.koalafield.cmart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.bean.cart.CartItemBean;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.primaryContentAlpha;

/**
 *
 * @author jiangrenming
 * @date 2018/5/16
 */

public class CartItemAdapter extends BaseQuickAdapter<CartDataBean> {


    private List<CartDataBean> selects = new ArrayList<>();

    public CartItemAdapter(Context context, List<CartDataBean> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.cart_item_layout;
    }

    @Override
    protected void convert(BaseViewHolder holder, final CartDataBean item) {
        CartItemBean commodity = item.getCommodity();
        final ImageView iv_select = holder.getView(R.id.iv_select);
        ImageView goods_cart_minus = holder.getView(R.id.goods_cart_minus);
        ImageView goods_cart_add = holder.getView(R.id.goods_cart_add);
        TextView goods_cart_number = holder.getView(R.id.goods_cart_number);
        TextView delete_item = holder.getView(R.id.delete_item);
        ImageView img = holder.getView(R.id.goods_cart_img);
        holder.setText(R.id.goods_cart_name,commodity.getName())
                .setText(R.id.goods_cart_curreny,commodity.getCurrency())
                .setText(R.id.goods_cart_amount,commodity.getCurrentPrice())
                .setText(R.id.goods_cart_number,String.valueOf(item.getCount()))
                .setText(R.id.goods_cart_color,item.getColor())
                .setText(R.id.goods_cart_size,item.getSize());
        Glide.with(mContext).load(commodity.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(img);

        //是否选中
        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelect(item)){
                    iv_select.setImageResource(R.mipmap.un_select);
                    removeSelectList(item);
                }else {
                    iv_select.setImageResource(R.mipmap.select);
                    addSelectList(item);
                }
            }
        });
    }


    public  boolean checkSelect(CartDataBean item){
        if (selects != null && selects.size() >0){
            if (!selects.contains(item)){
                return false;
            }
        }
        return  true;
    }


    private  void addSelectList(CartDataBean item){
        if (selects == null){
            selects = new ArrayList<>();
        }
        if (!selects.contains(item)){
            selects.add(item);
        }
    }

    private  void removeSelectList(CartDataBean item){
        if (selects == null){
            selects = new ArrayList<>();
        }
        if (selects.contains(item)){
            selects.remove(item);
        }
    }

    /**
     * 选中的总价
     */
    public void changePrice(){

        long amount =0;
        if (selects != null && selects.size() >0){
            for (int i = 0; i < selects.size(); i++) {
                amount += (selects.get(i).getCount()*Long.parseLong(selects.get(i).getCommodity().getCurrentPrice()));
            }
        }
        mCartItemCallBack.getAllPrice(amount);
    }

    /**
     * 选中的总数目
     */
    public void changeCount(){
        int count = 0;
        if (selects != null && selects.size() >0){
            for (int i = 0; i < selects.size(); i++) {
                count += selects.get(i).getCount();
            }
        }
        mCartItemCallBack.getAllCount(count);
    }

    /**
     * 全选是否选中
     */
    public  void isAllSelect(){
        int allCount =0;
        for (int i = 0; i <mData.size()  ; i++) {
            allCount += mData.get(i).getCount();
        }
        if (selects.size() > 0 && selects.size() == allCount){
            mCartItemCallBack.seletAll(true);
        }else {
            mCartItemCallBack.seletAll(false);
        }
    }


    public  interface CartItemCallBack{
        void getAllPrice(long price);
        void getAllCount(int count);
        void seletAll(boolean isSelect);
    }

    private CartItemCallBack mCartItemCallBack;
    public void setCartItemCallBack(CartItemCallBack cartItemCallBack){
        this.mCartItemCallBack = cartItemCallBack;
    }

}
