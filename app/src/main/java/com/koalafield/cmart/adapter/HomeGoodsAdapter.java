package com.koalafield.cmart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.home.GoodItemBean;
import com.koalafield.cmart.bean.home.GoodsCategryBean;
import com.koalafield.cmart.ui.activity.goods.GoodsDetailActivity;
import com.koalafield.cmart.ui.activity.search.GoodsListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangrenming on 2018/6/8.
 */

public class HomeGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GoodsCategryBean> mData;
    private LayoutInflater mLayoutInflater;

    public static final int ImageGoodsArray = 0x00000111;
    public static final int GoodsPiece = 0x00000222;
    public static final int CategoryGoods = 0x00000333;
    public static final int NomralGoods = 0x00000444;

       private GoodsCategryThreeAdapter goodsCategryThree;



    public HomeGoodsAdapter(Context context, List<GoodsCategryBean> data) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getShowType().equals("ImageGoodsArray")){
            return  ImageGoodsArray;
        }else if ("GoodsPiece".equals(mData.get(position).getShowType())){
            return  GoodsPiece;
        }else if ("CategoryGoods".equals(mData.get(position).getShowType())){
            return  CategoryGoods;
        }
        return NomralGoods;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ImageGoodsArray:
                View view_one = mLayoutInflater.inflate(R.layout.item_goods_array, parent, false);
                return  new ImageArrayHolder(view_one);
            case GoodsPiece:
                View view_two = mLayoutInflater.inflate(R.layout.item_goods_piece, parent, false);
                return  new GoodsPieceHolder(view_two);
            case CategoryGoods:
                View view_three = mLayoutInflater.inflate(R.layout.item_categry_goods, parent, false);
                return  new CategryGoodsHolder(view_three);
            case NomralGoods:
                View view_four = mLayoutInflater.inflate(R.layout.item_goods_piece, parent, false);
                return  new GoodsPieceHolder(view_four);
            default:
                break;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GoodsCategryBean goodsCategryBean = mData.get(position);
        if (holder instanceof ImageArrayHolder){
            ((ImageArrayHolder) holder).name.setText(goodsCategryBean.getName());
            GoodsCategryAdapter   goodsCategryAdapter = new GoodsCategryAdapter(mContext,goodsCategryBean.getGoodsList());
            RecyclerViewHelper.initRecyclerViewH(mContext,((ImageArrayHolder) holder).categry_one_recycler,false,goodsCategryAdapter);
            goodsCategryAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    //跳入详情界面
                    Log.i("点击的位置0:",pos+"");
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("contentId",goodsCategryBean.getGoodsList().get(pos).getId());
                    mContext.startActivity(intent);
                }
            });
            //更多按钮
            ((ImageArrayHolder) holder).categry_one_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //传入分类id，跳转分类列表
                    Intent intent = new Intent(mContext, GoodsListActivity.class);
                    intent.putExtra("cateId", goodsCategryBean.getCategoryId());
                    mContext.startActivity(intent);
                }
            });
        }else if (holder instanceof  GoodsPieceHolder){
            ((GoodsPieceHolder) holder).goods_categry_two.setText(goodsCategryBean.getName());
            GoodsCategryTwoAdapter   goodsCategryTwoAdapter = new GoodsCategryTwoAdapter(mContext,goodsCategryBean.getGoodsList());
            RecyclerViewHelper.initRecyclerViewG(mContext,((GoodsPieceHolder) holder).categry_two_recycler,goodsCategryTwoAdapter,3);
            goodsCategryTwoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    Log.i("点击的位置1:",pos+"");
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("contentId",goodsCategryBean.getGoodsList().get(pos).getId());
                    mContext.startActivity(intent);
                }
            });
            ((GoodsPieceHolder) holder).goods_categry_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //传入分类id，跳转分类列表
                    Intent intent = new Intent(mContext, GoodsListActivity.class);
                    intent.putExtra("cateId",mData.get(position).getCategoryId());
                    mContext.startActivity(intent);
                }
            });
        }else  if (holder instanceof  CategryGoodsHolder){
            Glide.with(mContext).load(goodsCategryBean.getImg()).placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                    .into(((CategryGoodsHolder) holder).goods_categry_three);
            GoodsCategryThreeAdapter   goodsCategryThree = new GoodsCategryThreeAdapter(mContext,goodsCategryBean.getGoodsList());
            RecyclerViewHelper.initRecyclerViewG(mContext,((CategryGoodsHolder) holder).categry_three_recycler,goodsCategryThree,3);
            goodsCategryThree.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    Log.i("点击的位置2:",position+"");
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("contentId",goodsCategryBean.getGoodsList().get(pos).getId());
                    mContext.startActivity(intent);
                }
            });
            ((CategryGoodsHolder) holder).goods_categry_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsListActivity.class);
                    intent.putExtra("cateId",goodsCategryBean.getCategoryId());
                    mContext.startActivity(intent);
                }
            });

        }else {
            ((GoodsPieceHolder) holder).goods_categry_two.setText(goodsCategryBean.getName());
            GoodsCategryTwoAdapter goodsCategryTwoAdapter = new GoodsCategryTwoAdapter(mContext,goodsCategryBean.getGoodsList());
            RecyclerViewHelper.initRecyclerViewG(mContext,((GoodsPieceHolder) holder).categry_two_recycler,goodsCategryTwoAdapter,3);
            goodsCategryTwoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    Log.i("点击的位置1:",pos+"");
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("contentId",goodsCategryBean.getGoodsList().get(pos).getId());
                    mContext.startActivity(intent);
                }
            });
            ((GoodsPieceHolder) holder).goods_categry_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //传入分类id，跳转分类列表
                    Intent intent = new Intent(mContext, GoodsListActivity.class);
                    intent.putExtra("cateId",mData.get(position).getCategoryId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 :mData.size();
    }

    public class ImageArrayHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView categry_one_more;
        RecyclerView categry_one_recycler;
        ImageArrayHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.goods_one_name);
            categry_one_more = view.findViewById(R.id.categry_one_more);
            categry_one_recycler = view.findViewById(R.id.categry_one_recycler);
        }
    }

    public class GoodsPieceHolder extends RecyclerView.ViewHolder {

        TextView goods_categry_two;
        RecyclerView categry_two_recycler;

        GoodsPieceHolder(View view) {
            super(view);
            goods_categry_two = view.findViewById(R.id.goods_categry_two);
            categry_two_recycler = view.findViewById(R.id.categry_two_recycler);
        }
    }

    public class CategryGoodsHolder extends RecyclerView.ViewHolder {

        ImageView goods_categry_three;
        RecyclerView categry_three_recycler;

        CategryGoodsHolder(View view) {
            super(view);
            goods_categry_three = view.findViewById(R.id.goods_categry_three);
            categry_three_recycler = view.findViewById(R.id.categry_three_recycler);
        }
    }


    /**
     * 清除所有数据
     */
    public void cleanItems() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 在列表尾添加一串数据
     *
     * @param items
     */
    public void addItems(List<GoodsCategryBean> items) {
        _addItemList(mData.size(), items);
        int position = _calcPosition(mData.size());
        for (GoodsCategryBean item : items) {
            notifyItemInserted(position++);
        }
    }

    private void _addItemList(int position, List<GoodsCategryBean> items) {
        if (mData == null || mData.size() == 0) {
            mData = new ArrayList<>();
        }
        mData.addAll(position, items);
    }
    /**
     * 计算位置，算上头部
     * @param position
     * @return
     */
    private int _calcPosition(int position) {
        return position;
    }
}
