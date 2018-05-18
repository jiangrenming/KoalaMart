package com.koalafield.cmart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.bean.cart.CartItemBean;
import com.koalafield.cmart.utils.StringUtils;

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
        final TextView goods_cart_number = holder.getView(R.id.goods_cart_number);
        TextView goods_cart_color = holder.getView(R.id.goods_cart_color);
        TextView goods_cart_size = holder.getView(R.id.goods_cart_size);
        Log.i("商品的数据:","数量="+item.getCount());
        TextView delete_item = holder.getView(R.id.delete_item);
        ImageView img = holder.getView(R.id.goods_cart_img);
        holder.setText(R.id.goods_cart_name,commodity.getName())
                .setText(R.id.goods_cart_curreny,commodity.getCurrency()+":")
                .setText(R.id.goods_cart_amount,commodity.getCurrentPrice())
                .setText(R.id.goods_cart_number,String.valueOf(item.getCount()));
         if (!StringUtils.isEmpty(item.getColor())){
             goods_cart_color.setText("颜色:"+item.getColor());
         }
        if (!StringUtils.isEmpty(item.getSize())){
            goods_cart_size.setText("尺寸:"+item.getSize());
        }
        Glide.with(mContext).load(commodity.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(img);
        isClearAll();
        if (item.isSelect()){
            iv_select.setImageResource(R.mipmap.select);
            addSelectList(item);
        }else {
            iv_select.setImageResource(R.mipmap.un_select);
            removeSelectList(item);
        }
        isAllSelect();  //是否全选
        changePrice();  //改变选中总金额
        changeCount();  //改变选中的总数量
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
                isAllSelect();  //是否全选
                changePrice();  //改变选中总金额
                changeCount();  //改变选中的总数量
            }
        });
        //减少
        goods_cart_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = goods_cart_number.getText().toString().trim();
                int minusCount  = Integer.valueOf(count);
                if (minusCount <= 1){
                    Toast.makeText(mContext,"已经处于最小量，无法继续减少...",Toast.LENGTH_SHORT).show();
                    return;
                }
                mCartItemCallBack.changeItemGoodsCount(-1,item.getContentId());
                goods_cart_number.setText(String.valueOf(minusCount-1));

            }
        });
        //增加
        goods_cart_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = goods_cart_number.getText().toString().trim();
                int addCount  = Integer.valueOf(count);
                mCartItemCallBack.changeItemGoodsCount(1,item.getContentId());
                goods_cart_number.setText(String.valueOf(addCount+1));

            }
        });
    }


    public  boolean checkSelect(CartDataBean item){
        if (selects != null && selects.size() >0){
            if (selects.contains(item)){
                return true;
            }
        }
        return  false;
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

        double amount =0;
        if (selects != null && selects.size() >0){
            for (int i = 0; i < selects.size(); i++) {
                amount += (selects.get(i).getCount()*(Double.parseDouble(selects.get(i).getCommodity().getCurrentPrice())));

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
        if (selects != null && selects.size() > 0 && selects.size() == allCount){
            mCartItemCallBack.seletAll(true);
        }else {
            mCartItemCallBack.seletAll(false);
        }
    }

    /**
     * 用于全部清除操作
     */
    public  void isClearAll(){
        if (mData .size() == 0){
            mCartItemCallBack.cleatAll(true);
        }else {
            mCartItemCallBack.cleatAll(false);
        }
    }


    public  interface CartItemCallBack{
        void getAllPrice(double price);
        void getAllCount(int count);
        void seletAll(boolean isSelect);
        void cleatAll(boolean isNull);
        void changeItemGoodsCount(int count,int contentId);
    }

    private CartItemCallBack mCartItemCallBack;
    public void setCartItemCallBack(CartItemCallBack cartItemCallBack){
        this.mCartItemCallBack = cartItemCallBack;
    }

}
