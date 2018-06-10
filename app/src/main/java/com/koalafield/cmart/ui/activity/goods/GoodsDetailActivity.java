package com.koalafield.cmart.ui.activity.goods;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.CommentAdapter;
import com.koalafield.cmart.adapter.GoodsDetailCommondAdapter;
import com.koalafield.cmart.adapter.SkuAdapter;
import com.koalafield.cmart.adapter.TitleAdpater;
import com.koalafield.cmart.bananer.MZBannerView;
import com.koalafield.cmart.bananer.MZHolderCreator;
import com.koalafield.cmart.bananer.MZViewHolder;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.bean.Point;
import com.koalafield.cmart.bean.cart.CartIdBean;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.goods.CommentDatas;
import com.koalafield.cmart.bean.goods.GoodsDetailsBean;
import com.koalafield.cmart.bean.goods.GoodsItem;
import com.koalafield.cmart.bean.goods.GoodsRecoomendBean;
import com.koalafield.cmart.bean.goods.SkuBean;
import com.koalafield.cmart.presenter.cart.CartChangeItemPresenter;
import com.koalafield.cmart.presenter.cart.CartPresenter;
import com.koalafield.cmart.presenter.cart.ICartChangeItemPresenter;
import com.koalafield.cmart.presenter.cart.ICartPresenter;
import com.koalafield.cmart.presenter.goods.GoodsCollectionDelPresenter;
import com.koalafield.cmart.presenter.goods.GoodsCollectionPresenter;
import com.koalafield.cmart.presenter.goods.GoodsCommondPresenter;
import com.koalafield.cmart.presenter.goods.GoodsDetailPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsCollectionDelPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsCollectionPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsCommondPresenter;
import com.koalafield.cmart.presenter.goods.IGoodsDetailPresenter;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.order.PayActivity;
import com.koalafield.cmart.ui.activity.search.GoodsListActivity;
import com.koalafield.cmart.ui.activity.search.SearchListActivity;
import com.koalafield.cmart.ui.view.cart.ICartChangeCountView;
import com.koalafield.cmart.ui.view.cart.ICartVIew;
import com.koalafield.cmart.ui.view.goods.IGoodsCollectionDeleteView;
import com.koalafield.cmart.ui.view.goods.IGoodsCollectionView;
import com.koalafield.cmart.ui.view.goods.IGoodsCommondlView;
import com.koalafield.cmart.ui.view.goods.IGoodsDetailView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.SelectSkuCalulator;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
import com.koalafield.cmart.widget.MyGridView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import static com.koalafield.cmart.ui.activity.TabBaseActivity.loadIntoUseFitWidth;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public class GoodsDetailActivity extends BaseActivity implements ICartVIew<CartNumberBean>,IGoodsCommondlView<List<GoodsRecoomendBean>>,
        IGoodsDetailView<GoodsDetailsBean>,IGoodsCollectionView<BaseResponseBean>,IGoodsCollectionDeleteView<BaseResponseBean>,
        ICartChangeCountView<CartIdBean>,PopupWindow.OnDismissListener,View.OnClickListener{

    //头部布局
    @BindView(R.id.goods_back)
    ImageView goods_back;
    @BindView(R.id.goods_cart_num)
    TextView goods_cart_num;
    //中间布局
    @BindView(R.id.goods_nomral_banner)
    MZBannerView goods_nomral_banner;
    @BindView(R.id.goods_detail_name)
    TextView goods_detail_name;
    @BindView(R.id.goods_detail_price)
    TextView goods_detail_price;
    @BindView(R.id.goods_detail_sale)
    TextView goods_detail_sale;
    @BindView(R.id.goods_detail_CYN_price)
    TextView goods_detail_CYN_price;
    //商品推荐
    @BindView(R.id.goods_commonds)
    RecyclerView goods_commonds;
    @BindView(R.id.webViews)
    WebView webViews;
    //底部布局
    @BindView(R.id.goods_collection)
    ImageView goods_collection;
    /*@BindView(R.id.goods_minus)
    ImageView goods_minus;
    @BindView(R.id.goods_number)
    EditText goods_number;
    @BindView(R.id.goods_add)
    ImageView goods_add;*/
    @BindView(R.id.pay_goods)
    LinearLayout pay_goods;
    @BindView(R.id.goods_car_img)
    ImageView goods_car_img;
    @BindView(R.id.judget_more)
    TextView judget_more;
    @BindView(R.id.comment_content)
    RecyclerView comment_content;
    @BindView(R.id.pay_buy)
    LinearLayout pay_buy;

    private  int contentId;
    private IGoodsDetailPresenter mGoodsDetailPresenter;
    private IGoodsCommondPresenter mGoodsCoomondPresenter;
    private List<String> imags = new ArrayList<>();
    private GoodsDetailCommondAdapter mAdapter;
    private boolean isSelect = false;
    private List<GoodsItem>  mColorList;
    private List<GoodsItem>  mSizeList;
    private List<GoodsItem>  mWeightList;
    private List<GoodsItem>  mTypeList;
    private List<GoodsItem>  mMaterialList;
    private   boolean openSelection;
    private  GoodsDetailsBean mBean;

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_goods_details;
    }

    @Override
    public void initDatas() {
        contentId = getIntent().getIntExtra("contentId",-1);

    }

    @Override
    public void upDateViews() {
        Map<String,String> params = new HashMap<>();
        params.put("id",String.valueOf(contentId));
        mGoodsDetailPresenter = new GoodsDetailPresenter(this);
        mGoodsDetailPresenter.getDetails(params);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (!StringUtils.isEmpty(tickets)){
            ICartPresenter mPresenter = new CartPresenter(GoodsDetailActivity.this);
            mPresenter.getData();
        }
    }

    private  int clickType ;
    @OnClick({R.id.pay_goods,/*R.id.goods_minus,R.id.goods_add,*/R.id.goods_collection,R.id.judget_more,R.id.goods_car_img,R.id.pay_buy,R.id.goods_back})
    public  void goodsClick(View view){
        String tickets = null;
        String num = null;
        Intent intent = null;
        switch (view.getId()){
            case R.id.pay_goods:
                 tickets = ShareBankPreferenceUtils.getString("tickets", null);
                if (!StringUtils.isEmpty(tickets)){
                    clickType = 1;
                    addCartData(view);
                }else {
                    intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    intent.putExtra("type",3);
                    startActivity(intent);
                }
                break;
            /*case R.id.goods_minus:
                 num = goods_number.getText().toString();
                if (!StringUtils.isEmpty(num)){
                    if (Integer.valueOf(num) <= 1){
                        Toast.makeText(GoodsDetailActivity.this,"商品数量已为最少，无法继续减少",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    goods_number.setText(String.valueOf(Integer.valueOf(num)-1));
                }
                break;
            case  R.id.goods_add:
                 num = goods_number.getText().toString();
                if (!StringUtils.isEmpty(num)){
                    goods_number.setText(String.valueOf(Integer.valueOf(num)+1));
                }
                break;*/
            case R.id.goods_collection:
                 tickets = ShareBankPreferenceUtils.getString("tickets", null);
                if (!StringUtils.isEmpty(tickets)){
                    if (!isSelect){
                          Map<String,String> params = new HashMap<>();
                          params.put("contentId",String.valueOf(mBean.getId()));
                          IGoodsCollectionPresenter collection = new GoodsCollectionPresenter(this);
                          collection.getDetails(params);
                    }else {
                        Map<String,String> params = new HashMap<>();
                        params.put("contentId",String.valueOf(mBean.getId()));
                        IGoodsCollectionDelPresenter collectionDel = new GoodsCollectionDelPresenter(this);
                        collectionDel.getDetails(params);
                    }
                }else {
                     intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    intent.putExtra("type",3);
                    startActivity(intent);
                }
                break;
            case  R.id.judget_more: //评论更多
                 intent = new Intent(GoodsDetailActivity.this, GoodsCommentActivity.class);
                intent.putExtra("contentId",contentId);
                startActivity(intent);
                overridePendingTransition(R.anim.comment_anim_out, R.anim.comment_anim_in);
                break;
            case R.id.goods_car_img:
                tickets = ShareBankPreferenceUtils.getString("tickets", null);
                if (!StringUtils.isEmpty(tickets)){
                    startActivity(new Intent(GoodsDetailActivity.this,CartShoppingActivity.class));
                }else {
                    intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    intent.putExtra("type",3);
                    startActivity(intent);
                }
                break;

            case R.id.pay_buy:
                tickets = ShareBankPreferenceUtils.getString("tickets", null);
                if (!StringUtils.isEmpty(tickets)){
                    clickType = 2;
                    addCartData(view);
                }else {
                     intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    intent.putExtra("type",3);
                    startActivity(intent);
                }
                break;
            case R.id.goods_back:
                finish();
                break;
            default:
                break;
        }
    }

    private int selectType = 0;
    private void addCartData(View view) {
        if (openSelection){
            openPopupWindow(view);
            selectType = 1;
        }else {
            selectType = 2;
            ICartChangeItemPresenter presenter = new CartChangeItemPresenter(this);
          //  String goods_num = goods_number.getText().toString();
            Map<String,String> addCarts = new HashMap<>();
            addCarts.put("count",String.valueOf(1));
            addCarts.put("contentId",String.valueOf(contentId));

            if (mWeightList != null && mWeightList.size() >0){
                addCarts.put("weight",mWeightList.get(0).getName());
            }else {
                addCarts.put("weight",null);

            }
            if (mTypeList != null && mTypeList.size() >0){
                addCarts.put("type",mTypeList.get(0).getName());
            }else {
                addCarts.put("type",null);

            }
            if (mColorList != null && mColorList.size() >0){
                addCarts.put("color",mColorList.get(0).getName());
            }else {
                addCarts.put("color",null);

            }
            if (mSizeList != null && mSizeList.size() >0){
                addCarts.put("size",mSizeList.get(0).getName());
            }else {
                addCarts.put("size",null);

            }
            if (mMaterialList != null && mMaterialList.size() >0){
                addCarts.put("material",mMaterialList.get(0).getName());
            }else {
                addCarts.put("material",null);

            }
            addCarts.put("tag","0");
            presenter.getChangeCountData(addCarts);
        }
    }

    private PopupWindow popupWindow;
    private int navigationHeight = 0;
    private void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.goods_color_layout, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外退出
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        if (AndoridSysUtils.checkDeviceHasNavigationBar(this)){
            navigationHeight = AndoridSysUtils.getNavigationBarHeigh(this);
        }
        //设置显示的位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景透明度
        setBackgroundAlpha(0.5f);
    }
    private TextView goods_short_name;
    private  ImageView goods_img;
    private  TextView curreny_;
    private  TextView curreny_price;
    private  ImageView goods_close;
    private MyGridView item_color,item_size,item_weight,item_type,item_materi;
    private  ImageView add_goods_num;
    private  TextView goods_pop_num;
    private  ImageView minus_pop_num;
    private  TextView add_pop_cart;
 //   private  TextView goods_type;
    private LinearLayout item_color_layout,item_size_layout,item_weight_layout,item_type_layout,item_materi_layout;
    private TitleAdpater color_adater,sizeAdapter,weightAdapter,typeAdapter,materAdapter;
    private List<GoodsItem> colorItems,sizeItems,weightItems,typeItems,matreItems;
    private List<SkuBean> skuBeanList = new ArrayList<>();

    private void setOnPopupViewClick(View view) {
        goods_short_name = view.findViewById(R.id.goods_short_name);
        goods_img = view.findViewById(R.id.goods_img);
        curreny_ = view.findViewById(R.id.curreny_);
        curreny_price = view.findViewById(R.id.curreny_price);
        goods_close = view.findViewById(R.id.goods_close);
        item_color  = view.findViewById(R.id.item_color);
        item_size  = view.findViewById(R.id.item_size);
        item_weight  = view.findViewById(R.id.item_weight);
        item_type  = view.findViewById(R.id.item_type);
  //      goods_type = view.findViewById(R.id.goods_type);
        item_materi  = view.findViewById(R.id.item_materi);
        item_color_layout = view.findViewById(R.id.item_color_layout);
        item_size_layout = view.findViewById(R.id.item_size_layout);
        item_weight_layout = view.findViewById(R.id.item_weight_layout);
        item_type_layout = view.findViewById(R.id.item_type_layout);
        item_materi_layout = view.findViewById(R.id.item_materi_layout);
        add_goods_num = view.findViewById(R.id.add_goods_num);
        goods_pop_num = view.findViewById(R.id.goods_pop_num);
        minus_pop_num = view.findViewById(R.id.minus_pop_num);
        add_pop_cart = view.findViewById(R.id.add_pop_cart);
        goods_close.setOnClickListener(this);
        add_goods_num.setOnClickListener(this);
        minus_pop_num.setOnClickListener(this);
        add_pop_cart.setOnClickListener(this);
        if (mBean != null){
            goods_short_name.setText(mBean.getName());
            Glide.with(GoodsDetailActivity.this).load(mBean.getImageList()[0]).placeholder(R.mipmap.default_img).error(R.mipmap.default_img).into(goods_img);
            curreny_.setText(mBean.getCurrency());
            curreny_price.setText(String.format("%.2f",  mBean.getCurrentPrice()));

            if (mBean.getColorList() != null && mBean.getColorList().size()>0){
                item_color_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < mBean.getColorList().size(); i++) {
                    mBean.getColorList().get(i).setState(1);
                }
                color_adater = new TitleAdpater(GoodsDetailActivity.this,mBean.getColorList());
                item_color.setAdapter(color_adater);
                item_color.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                color_adater.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = mBean.getColorList().get(position);
                        String color = goodsItem.getName();
                        String raisePrice = goodsItem.getRaisePrice();
                        switch (goodsItem.getState()){
                            case 0: //选中
                                colorItems = SelectSkuCalulator.clearAdapterStates(mBean.getColorList());
                                color_adater.notifyDataSetChanged();
                                break;
                            case  1 : //未选中
                                colorItems = SelectSkuCalulator.selectCalutor(mBean.getColorList(), position, 0);
                                color_adater.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
            if (mBean.getSizeList() != null && mBean.getSizeList().size() >0){
                item_size_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < mBean.getSizeList().size(); i++) {
                    mBean.getSizeList().get(i).setState(1);
                }
                sizeAdapter = new TitleAdpater(GoodsDetailActivity.this,mBean.getSizeList());
                item_size.setAdapter(sizeAdapter);
                item_size.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                sizeAdapter.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = mBean.getSizeList().get(position);
                        String size = goodsItem.getName();
                        String raisePrice = goodsItem.getRaisePrice();
                        switch (goodsItem.getState()){
                            case 0: //选中

                                sizeItems = SelectSkuCalulator.clearAdapterStates(mBean.getSizeList());
                                sizeAdapter.notifyDataSetChanged();
                                break;
                            case  1 : //未选中
                                sizeItems = SelectSkuCalulator.selectCalutor(mBean.getSizeList(), position, 0);
                                sizeAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }
                    }
                });
            }
            if (mBean.getWeightList() != null && mBean.getWeightList().size() >0){
                item_weight_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < mBean.getWeightList().size(); i++) {
                    mBean.getWeightList().get(i).setState(1);
                }
                weightAdapter = new TitleAdpater(GoodsDetailActivity.this,mBean.getWeightList());
                item_weight.setAdapter(weightAdapter);
                item_weight.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                weightAdapter.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = mBean.getWeightList().get(position);
                        String weight = goodsItem.getName();
                        String raisePrice = goodsItem.getRaisePrice();
                        switch (goodsItem.getState()){
                            case 0:
                                weightItems = SelectSkuCalulator.clearAdapterStates(mBean.getWeightList());
                                weightAdapter.notifyDataSetChanged();
                                break;
                            case  1 :
                                weightItems = SelectSkuCalulator.selectCalutor(mBean.getWeightList(), position, 0);
                                weightAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }
                    }
                });
            }
            if (mBean.getMaterialList() != null && mBean.getMaterialList().size() >0){
                item_materi_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < mBean.getMaterialList().size(); i++) {
                    mBean.getMaterialList().get(i).setState(1);
                }

                materAdapter = new TitleAdpater(GoodsDetailActivity.this,mBean.getMaterialList());
                item_materi.setAdapter(materAdapter);
                item_materi.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                materAdapter.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = mBean.getMaterialList().get(position);
                        String matres = goodsItem.getName();
                        String raisePrice = goodsItem.getRaisePrice();
                        switch (goodsItem.getState()){
                            case  0:

                                matreItems = SelectSkuCalulator.clearAdapterStates(mBean.getMaterialList());
                                materAdapter.notifyDataSetChanged();
                                break;
                            case  1 :

                                matreItems = SelectSkuCalulator.selectCalutor(mBean.getMaterialList(), position, 0);
                                materAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }
                    }
                });
            }
            if (mBean.getTypeList() != null && mBean.getTypeList().size() >0){
                item_type_layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < mBean.getTypeList().size(); i++) {
                    mBean.getTypeList().get(i).setState(1);
                }

                typeAdapter = new TitleAdpater(GoodsDetailActivity.this,mBean.getTypeList());
                item_type.setAdapter(typeAdapter);
                item_type.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                typeAdapter.setItemClickListener(new TitleAdpater.onItemClickListener() {
                    @Override
                    public void onItemClick(GoodsItem bean, int position) {
                        GoodsItem goodsItem = mBean.getTypeList().get(position);
                        String type = goodsItem.getName();
                        String raisePrice = goodsItem.getRaisePrice();
                        switch (goodsItem.getState()){
                            case 0:

                                typeItems = SelectSkuCalulator.clearAdapterStates(mBean.getTypeList());
                                typeAdapter.notifyDataSetChanged();
                                break;
                            case  1 :

                                typeItems = SelectSkuCalulator.selectCalutor(mBean.getTypeList(), position, 0);
                                typeAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }
                    }
                });

            }
        }
    }
    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;
        this. getWindow().setAttributes(lp);
    }

    /**
     * 购物车数量
     * @param data
     */
    @Override
    public void onSucessNumberful(CartNumberBean data) {
        if (data != null){
            if (data.getCount() <= 0){
                goods_cart_num.setVisibility(View.GONE);
            }else if (data.getCount() <= 99){
                goods_cart_num.setVisibility(View.VISIBLE);
                goods_cart_num.setText(String.valueOf(data.getCount()));
            }else {
                goods_cart_num.setVisibility(View.VISIBLE);
                goods_cart_num.setText(String.format(Locale.CHINA, "%d+", 99));
            }
        }
    }

    @Override
    public void onNumberFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 410){
            Intent intent = new Intent(GoodsDetailActivity.this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    /**
     * 商品详情的请求
     * @param data
     */
    private  List<CommentDatas> new_comments = new ArrayList<>();
    @Override
    public void onGoodsDetailsSucessFul(GoodsDetailsBean data) {
        if (data != null){
            mBean = data;
            Log.i("获取的数据为：",data.toString());
            String[] imageList = data.getImageList();
            for (int i = 0; i < imageList.length; i++) {
                imags.add(imageList[i]);
            }
            goods_nomral_banner.setIndicatorVisible(true);
            goods_nomral_banner.setIndicatorRes(R.drawable.unseletc_dot,R.drawable.select_dot);
            goods_nomral_banner.setIndicatorAlign(MZBannerView.IndicatorAlign.BOTTOM);
            goods_nomral_banner.setIndicatorPadding(10,0,0,10);
            goods_nomral_banner.setDelayedTime(5000);
            goods_nomral_banner.setPages(imags, new MZHolderCreator() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new NomralBannerViewHolder();
                }
            });
            String goods_name = data.getName();
            if (StringUtils.isEmpty(goods_name)){
                goods_detail_name.setText(data.getShortName());
            }
            goods_detail_name.setText(goods_name);
            goods_detail_price.setText(data.getCurrency() +data.getCurrentPrice());
            goods_detail_sale.setText("销量:"+data.getSalesCount());
            goods_detail_CYN_price.setText("CYN "+String.format("%.2f", data.getCurrencyRate()* data.getCurrentPrice()));
            mColorList = data.getColorList();
            mMaterialList = data.getMaterialList();
            mSizeList = data.getSizeList();
            mTypeList = data.getTypeList();
            mWeightList = data.getWeightList();
            if (!StringUtils.isEmpty(data.getContentHTML())){
                String html =  getHtmlData(data.getContentHTML());
                webViews.loadData(html, "text/html; charset=UTF-8", null);
            }
            String tickets = ShareBankPreferenceUtils.getString("tickets", null);
            if (!StringUtils.isEmpty(tickets)){
                if (data.isFavorite()){
                    goods_collection.setImageResource(R.mipmap.select_collection);
                }else {
                    goods_collection.setImageResource(R.mipmap.collection);
                }
                isSelect = data.isFavorite();
            }else {
                goods_collection.setImageResource(R.mipmap.collection);
            }
            openSelection = data.isOpenSelection();

            //获取评论内容前2条
            if (null != data.getCommentList() && data.getCommentList().size() >0 ){
                comment_content.setVisibility(View.VISIBLE);
                CommentAdapter commentAdapter = new CommentAdapter(this,data.getCommentList());
                RecyclerViewHelper.initRecyclerViewV(this,comment_content,true,commentAdapter);
            }else {
                comment_content.setVisibility(View.GONE);
            }
            //推荐商品
            mGoodsCoomondPresenter = new GoodsCommondPresenter(this);
            mGoodsCoomondPresenter.getCommondGoods(data.getId());
        }
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    public void onGoodsDetailsFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 410){
            Intent intent = new Intent(GoodsDetailActivity.this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    /**
     * 商品推荐的请求
     * @param data
     */
    @Override
    public void onGoodsCommondSucessFul(final List<GoodsRecoomendBean> data) {

        if (data != null &&data.size() >0){
            Log.i("返回推荐的数据",data.toString());
            if (mAdapter == null){
                mAdapter = new GoodsDetailCommondAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewH(this,goods_commonds,false,mAdapter);
            }else {
                mAdapter.cleanItems();
                mAdapter.addItems(data);
            }
            mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    int id = data.get(position).getId();
                    Intent intent = new Intent(GoodsDetailActivity.this,GoodsDetailActivity.class);
                    intent.putExtra("contentId",id);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onGoodsCommondFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 410){
            Intent intent = new Intent(GoodsDetailActivity.this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    //收藏商品
    @Override
    public void onGoodsCollectionsSucessFul(BaseResponseBean data) {
        if (data.getCode() == 200){
            Toast.makeText(GoodsDetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
            if (!isSelect){
                isSelect = true;
                goods_collection.setImageResource(R.mipmap.select_collection);
            }else {
                isSelect = false;
                goods_collection.setImageResource(R.mipmap.collection);
            }
        }
    }

    @Override
    public void onGoodsCollectionsFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 410){
            Intent intent = new Intent(GoodsDetailActivity.this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    @Override
    public void onGoodsCollectionDelSucessFul(BaseResponseBean data) {
        if (data.getCode() == 200){
            if (!isSelect){
                isSelect = true;
                goods_collection.setImageResource(R.mipmap.select_collection);
            }else {
                isSelect = false;
                goods_collection.setImageResource(R.mipmap.collection);
            }
            Toast.makeText(GoodsDetailActivity.this,"取消收藏成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGoodsCollectionDelFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 410){
            Intent intent = new Intent(GoodsDetailActivity.this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }    }

    //添加购物车

    @Override
    public void onChangeItemSucessful(CartIdBean data) {
        Toast.makeText(GoodsDetailActivity.this,"添加购物车成功",Toast.LENGTH_SHORT).show();
        if (popupWindow != null &&popupWindow.isShowing()){
            popupWindow.dismiss();
        }
        goods_cart_num.setVisibility(View.VISIBLE);
        if (selectType == 2){
            goods_cart_num.setText(String.valueOf(Integer.valueOf(goods_cart_num.getText().toString())+1));

        }else {
            goods_cart_num.setText(String.valueOf(Integer.valueOf(goods_cart_num.getText().toString())+Integer.valueOf(goods_pop_num.getText().toString())));

        }
        int[] loc = new int[2];
        pay_goods.getLocationInWindow(loc);
        playAnimation(loc);
        if (clickType ==2){
            //跳转到提交订单的页面
            Intent intent = new Intent(GoodsDetailActivity.this, PayActivity.class);
            intent.putExtra("payDatas",String.valueOf(data.getId()));
            startActivity(intent);
        }
    }

    @Override
    public void onChangeItemFailure(String message,int code) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if (code == 410){
            Intent intent = new Intent(GoodsDetailActivity.this,LoginActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }





    private String color,size,weight,type,matre;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goods_close: //关闭弹出窗
                if (popupWindow != null &&popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                break;
            case R.id.add_goods_num: //增加数量
                String count = goods_pop_num.getText().toString();
                int _count = Integer.valueOf(count);
                goods_pop_num.setText(String.valueOf(_count+1));
                break;
            case  R.id.minus_pop_num: //减少数量
                String minus_count = goods_pop_num.getText().toString();
                int minusCount = Integer.valueOf(minus_count);
                if (minusCount <= 1){
                    Toast.makeText(GoodsDetailActivity.this,"已经是最少数量，无法再减",Toast.LENGTH_SHORT).show();
                    return;
                }
                goods_pop_num.setText(String.valueOf(minusCount-1));
                break;
            case R.id.add_pop_cart: //添加购物车
                int select_count =0;
                if (skuBeanList != null && skuBeanList.size()>0){
                    skuBeanList.clear();
                }
                if (mColorList != null && mColorList .size() > 0){
                    select_count ++;
                    for (int i = 0; i < colorItems.size(); i++) {
                        if (colorItems.get(i).getState() == 0){
                            String  color_Name = colorItems.get(i).getName();
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setName(color_Name);
                            skuBean.setId(1);
                            skuBean.setPrice(colorItems.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (mSizeList != null && mSizeList .size() > 0){
                    select_count ++;
                    for (int i = 0; i < mSizeList.size(); i++) {
                        if (mSizeList.get(i).getState() == 0){
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setId(2);
                            skuBean.setName(mSizeList.get(i).getName());
                            skuBean.setPrice(mSizeList.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (mWeightList != null && mWeightList .size() > 0){
                    select_count ++;
                    for (int i = 0; i < mWeightList.size(); i++) {
                        if (mWeightList.get(i).getState() == 0){
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setId(3);
                            skuBean.setName(mWeightList.get(i).getName());
                            skuBean.setPrice(mWeightList.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (mTypeList != null && mTypeList .size() > 0){
                    select_count ++;
                    for (int i = 0; i < mTypeList.size(); i++) {
                        if (mTypeList.get(i).getState() == 0){
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setId(4);
                            skuBean.setName(mTypeList.get(i).getName());
                            skuBean.setPrice(mTypeList.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (mMaterialList != null && mMaterialList .size() > 0){
                    select_count ++;
                    for (int i = 0; i < mMaterialList.size(); i++) {
                        if (mMaterialList.get(i).getState() == 0){
                            SkuBean skuBean  = new SkuBean();
                            skuBean.setId(5);
                            skuBean.setName(mMaterialList.get(i).getName());
                            skuBean.setPrice(mMaterialList.get(i).getRaisePrice());
                            skuBeanList.add(skuBean);
                        }
                    }
                }
                if (null != skuBeanList && skuBeanList.size() > 0){
                    if (skuBeanList.size() == select_count){
                        for (int i = 0; i < skuBeanList.size(); i++) {
                            if (skuBeanList.get(i).getId() == 1){
                                color =  skuBeanList.get(i).getName();
                            }else if (skuBeanList.get(i).getId() == 2){
                                 size = skuBeanList.get(i).getName();
                            }else if (skuBeanList.get(i).getId() == 3){
                                weight = skuBeanList.get(i).getName();
                            }else if (skuBeanList.get(i).getId() ==4){
                                type = skuBeanList.get(i).getName();
                            }else if (skuBeanList.get(i).getId() == 5){
                                matre = skuBeanList.get(i).getName();
                            }
                        }
                        ICartChangeItemPresenter presenter = new CartChangeItemPresenter(this);
                        String goods_num = goods_pop_num.getText().toString();
                        Map<String,String> addCarts = new HashMap<>();
                        if (!StringUtils.isEmpty(goods_num)){
                            addCarts.put("count",goods_num);
                        }
                        addCarts.put("contentId",String.valueOf(contentId));
                        if (!StringUtils.isEmpty(weight)){
                            addCarts.put("weight",weight);
                        }else {
                            addCarts.put("weight",null);
                        }
                        if (!StringUtils.isEmpty(type)){
                            addCarts.put("type",type);
                        }else {
                            addCarts.put("type",null);
                        }
                        if (!StringUtils.isEmpty(color)){
                            addCarts.put("color",color);
                        }else {
                            addCarts.put("color",null);
                        }
                        if (!StringUtils.isEmpty(size)){
                            addCarts.put("size",size);
                        }else {
                            addCarts.put("size",null);
                        }
                        if (!StringUtils.isEmpty(matre)){
                            addCarts.put("material",matre);
                        }else {
                            addCarts.put("material",null);
                        }
                        addCarts.put("tag","0");
                        presenter.getChangeCountData(addCarts);
                    }else {
                        Toast.makeText(GoodsDetailActivity.this,"还有规格未选，请选择",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(GoodsDetailActivity.this,"请选择规格",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                break;

        }
    }

    /**
     * 正常轮播图
     */
    private class NomralBannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.nomral_item,null);
            mImageView = (ImageView) view.findViewById(R.id.nomral_img);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            loadIntoUseFitWidth(context,data,R.mipmap.default_img,mImageView);
        }
    }

    /**
     * 动画
     * @param position
     */
    public   void playAnimation(int[] position){
        final ImageView mImg = new ImageView(GoodsDetailActivity.this);
        mImg.setImageResource(R.mipmap.select);
        mImg.setScaleType(ImageView.ScaleType.MATRIX);
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.addView(mImg);
        int[] des = new int[2];
        goods_car_img.getLocationInWindow(des);
        /*动画开始位置，也就是物品的位置;动画结束的位置，也就是购物车的位置 */
        Point startPosition = new Point(position[0], position[1]);
        Point endPosition = new Point(des[0]+goods_car_img.getWidth()/2, des[1]+goods_car_img.getHeight()/2);
        int pointX = (startPosition.x + endPosition.x) / 2 + 100;
        int pointY = startPosition.y + 200;
        Point controllPoint = new Point(pointX, pointY);
        /**
         * 属性动画，依靠TypeEvaluator来实现动画效果，其中位移，缩放，渐变，旋转都是可以直接使用 *
         这里是自定义了TypeEvaluator， 我们通过point记录运动的轨迹，然后，物品随着轨迹运动，
         一旦轨迹发生变化，就会调用addUpdateListener这个方法，我们不断的获取新的位置，是物品移动
         */
        ValueAnimator valueAnimator = ValueAnimator.ofObject(
                new BizierEvaluator2(controllPoint),startPosition, endPosition);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                mImg.setX(point.x);
                mImg.setY(point.y);
            }
        });

        /** * 动画结束，移除掉小圆圈 */
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup rootView = (ViewGroup) GoodsDetailActivity.this.getWindow().getDecorView();
                rootView.removeView(mImg);
            }
        });
    }

    /** * 贝塞尔曲线（二阶抛物线） * controllPoint 是中间的转折点 * startValue 是起始的位置 * endValue 是结束的位置 */
    public class BizierEvaluator2 implements TypeEvaluator<Point> {
        private Point controllPoint;
        public BizierEvaluator2(Point controllPoint) {
            this.controllPoint = controllPoint;
        }
        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x);
            int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y);
            return new Point(x, y);
        }
    }


}
