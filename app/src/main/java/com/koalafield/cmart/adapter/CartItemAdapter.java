package com.koalafield.cmart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.dl7.recycler.listener.OnRemoveDataListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.bean.cart.CartItemBean;
import com.koalafield.cmart.bean.event.CartEvent;
import com.koalafield.cmart.utils.AndroidTools;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.EmptyLayout;
import com.koalafield.cmart.widget.FrontViewToMove;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.factor;
import static android.R.attr.primaryContentAlpha;

/**
 *
 * @author jiangrenming
 * @date 2018/5/16
 */

public class CartItemAdapter extends BaseQuickAdapter<CartDataBean> {


    private List<CartDataBean> selects = new ArrayList<>();
    private RecyclerView mListView;

    public CartItemAdapter(Context context, List<CartDataBean> data, RecyclerView view) {
        super(context, data);
        mListView =view;
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
        LinearLayout type_weight_matre = holder.getView(R.id.type_weight_matre);

        LinearLayout color_size = holder.getView(R.id.color_size);
        TextView goods_cart_type = holder.getView(R.id.goods_cart_type);
        TextView goods_cart_weight = holder.getView(R.id.goods_cart_weight);
        TextView goods_cart_mater = holder.getView(R.id.goods_cart_mater);

        Log.i("商品的数据:","数量="+item.getCount());
        TextView delete_item = holder.getView(R.id.btn_delete);
        ImageView img = holder.getView(R.id.goods_cart_img);
       final LinearLayout frontView = holder.getView(R.id.id_front);
        //关键语句，使用自己写的类来对frontView的ontouch事件复写，实现视图滑动效果
        new FrontViewToMove(frontView, mListView,200);
        holder.setText(R.id.goods_cart_name,commodity.getName())
                .setText(R.id.goods_cart_curreny,commodity.getCurrency()+":")
                .setText(R.id.goods_cart_amount,commodity.getCurrentPrice())
                .setText(R.id.goods_cart_number,String.valueOf(item.getCount()));

        if (StringUtils.isEmpty(item.getColor()) && StringUtils.isEmpty(item.getSize())){
            color_size.setVisibility(View.GONE);
        }else {
            color_size.setVisibility(View.VISIBLE);
        }
         if (!StringUtils.isEmpty(item.getColor())){
             goods_cart_color.setVisibility(View.VISIBLE);
             goods_cart_color.setText("颜色:"+item.getColor());
         }else {
             goods_cart_color.setVisibility(View.GONE);
         }
        if (!StringUtils.isEmpty(item.getSize())){
            goods_cart_size.setVisibility(View.VISIBLE);
            goods_cart_size.setText("尺寸:"+item.getSize());
        }else {
            goods_cart_size.setVisibility(View.VISIBLE);
        }
        if (StringUtils.isEmpty(item.getType()) && StringUtils.isEmpty(item.getWeight()) && StringUtils.isEmpty(item.getMaterial())){
            type_weight_matre.setVisibility(View.GONE);
        }else {
            type_weight_matre.setVisibility(View.VISIBLE);
        }
        if (!StringUtils.isEmpty(item.getWeight())){
            goods_cart_weight.setVisibility(View.VISIBLE);
            goods_cart_weight.setText(item.getWeight());
        }else {
            goods_cart_weight.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(item.getType())){
            goods_cart_type.setVisibility(View.VISIBLE);
            goods_cart_type.setText(item.getType());
        }else {
            goods_cart_type.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(item.getMaterial())){
            goods_cart_mater.setVisibility(View.VISIBLE);
            goods_cart_mater.setText(item.getMaterial());
        }else {
            goods_cart_mater.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(commodity.getCoverImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(img);
        if (item.isSelect()){
            iv_select.setImageResource(R.mipmap.select);
            addSelectList(item);
        }else {
            iv_select.setImageResource(R.mipmap.un_select);
            removeSelectList(item);
        }
    //    isClearAll();    //是否全部删除
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
                    mCartItemCallBack.seletSigle(false,item);
                }else {
                    iv_select.setImageResource(R.mipmap.select);
                    addSelectList(item);
                    mCartItemCallBack.seletSigle(true,item);
                }
            }
        });
        //减少
        goods_cart_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = true;
                String count = goods_cart_number.getText().toString().trim();
                int minusCount  = Integer.valueOf(count);
                if (minusCount <= 1){
                    Toast.makeText(mContext,"已经处于最小量，无法继续减少...",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkSelect(item)){
                    isSelected = true;
                }else {
                    isSelected = false;
                }
                mCartItemCallBack.changeItemGoodsCount(-1,item,isSelected);
                EventBus.getDefault().post(new CartEvent(1,2));
            }
        });
        //增加
        goods_cart_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = true;
                String count = goods_cart_number.getText().toString().trim();
                int addCount  = Integer.valueOf(count);
                if (checkSelect(item)){
                    isSelected = true;
                }else {
                    isSelected = false;
                }
                mCartItemCallBack.changeItemGoodsCount(1,item,isSelected);
                EventBus.getDefault().post(new CartEvent(1,3));
            }
        });
        //删除单条目
        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = true;
                if (checkSelect(item)){
                    isSelected = true;
                    selects.remove(item);
                }else {
                    isSelected = false;
                }
                String count = goods_cart_number.getText().toString().trim();
                int addCount  = Integer.valueOf(count);
                EventBus.getDefault().post(new CartEvent(addCount,1));
                mCartItemCallBack.changeItemGoodsCount(0,item,isSelected);
                new FrontViewToMove(frontView, mListView,200).generateRevealAnimate(frontView,0);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCartItemCallBack.skipGoodsDeatils(item);
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

        double amount = 0;
        if (selects != null && selects.size() >0){
            for (int i = 0; i < selects.size(); i++) {
                amount += selects.get(i).getCount()*Double.parseDouble(selects.get(i).getCommodity().getCurrentPrice());
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
        int selectCount = 0;
        for (int i = 0; i <mData.size()  ; i++) {
            allCount += mData.get(i).getCount();
        }
        Log.i("总的商品数量:",allCount+"/被选中的商品数量:"+selects.size());
        if (selects != null && selects.size() > 0){
            for (int i = 0; i < selects.size(); i++) {
                selectCount += selects.get(i).getCount();
            }
        }
        if (allCount !=0 &&  selectCount!= 0 && allCount == selectCount){
            mCartItemCallBack.seletAll(true);
        }else {
            mCartItemCallBack.seletAll(false);
        }
    }

    /**
     * 用于全部清除操作
     */
    public  void isClearAll(){
        if (mData == null  || mData .size() == 0){
            selects.clear();
            mCartItemCallBack.cleatAll(true);
        }else {
            mCartItemCallBack.cleatAll(false);
        }
        notifyDataSetChanged();
    }


    public  interface CartItemCallBack{
        void getAllPrice(double price);
        void getAllCount(int count);
        void seletAll(boolean isSelect);
        void seletSigle(boolean isSelect,CartDataBean item);
        void cleatAll(boolean isNull);
        void changeItemGoodsCount(int count,CartDataBean item,boolean isSelect);
        void skipGoodsDeatils(CartDataBean item);
    }

    private CartItemCallBack mCartItemCallBack;
    public void setCartItemCallBack(CartItemCallBack cartItemCallBack){
        this.mCartItemCallBack = cartItemCallBack;
    }

}
