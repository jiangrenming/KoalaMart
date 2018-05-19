package com.koalafield.cmart.ui.activity.goods;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.koalafield.cmart.R;
import com.koalafield.cmart.adapter.GoodsDetailCommondAdapter;
import com.koalafield.cmart.bananer.MZBannerView;
import com.koalafield.cmart.bananer.MZHolderCreator;
import com.koalafield.cmart.bananer.MZViewHolder;
import com.koalafield.cmart.base.activity.BaseActivity;
import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;
import com.koalafield.cmart.bean.Point;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.goods.GoodsDetailsBean;
import com.koalafield.cmart.bean.goods.GoodsItem;
import com.koalafield.cmart.bean.goods.GoodsRecoomendBean;
import com.koalafield.cmart.bean.home.HomeBanaerBean;
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
import com.koalafield.cmart.ui.activity.HomeActivity;
import com.koalafield.cmart.ui.activity.LoginActivity;
import com.koalafield.cmart.ui.activity.MainActivity;
import com.koalafield.cmart.ui.view.cart.ICartChangeCountView;
import com.koalafield.cmart.ui.view.cart.ICartVIew;
import com.koalafield.cmart.ui.view.goods.IGoodsCollectionDeleteView;
import com.koalafield.cmart.ui.view.goods.IGoodsCollectionView;
import com.koalafield.cmart.ui.view.goods.IGoodsCommondlView;
import com.koalafield.cmart.ui.view.goods.IGoodsDetailView;
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.AndroidTools;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;
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
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.koalafield.cmart.R.id.goods_car_img;
import static com.koalafield.cmart.ui.activity.TabBaseActivity.loadIntoUseFitWidth;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public class GoodsDetailActivity extends BaseActivity implements ICartVIew<CartNumberBean>,IGoodsCommondlView<List<GoodsRecoomendBean>>,
        IGoodsDetailView<GoodsDetailsBean>,IGoodsCollectionView<BaseResponseBean>,IGoodsCollectionDeleteView<BaseResponseBean>,
        ICartChangeCountView<SpecialResponseBean>,PopupWindow.OnDismissListener{

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
    @BindView(R.id.goods_minus)
    ImageView goods_minus;
    @BindView(R.id.goods_number)
    EditText goods_number;
    @BindView(R.id.goods_add)
    ImageView goods_add;
    @BindView(R.id.pay_goods)
    LinearLayout pay_goods;
    @BindView(R.id.goods_car_img)
    ImageView goods_car_img;

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

    @Override
    public int attchLayoutRes() {
        return R.layout.activity_goods_details;
    }

    @Override
    public void initDatas() {
        contentId = getIntent().getIntExtra("contentId",-1);
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        if (!StringUtils.isEmpty(tickets)){
            ICartPresenter mPresenter = new CartPresenter(GoodsDetailActivity.this);
            mPresenter.getData();
        }
    }

    @Override
    public void upDateViews() {
        Map<String,String> params = new HashMap<>();
        params.put("id",String.valueOf(contentId));
        mGoodsDetailPresenter = new GoodsDetailPresenter(this);
        mGoodsDetailPresenter.getDetails(params);

    }


    @OnClick({R.id.pay_goods,R.id.goods_minus,R.id.goods_add,R.id.goods_collection})
    public  void goodsClick(View view){
        String tickets = null;
        String num = null;
        switch (view.getId()){
            case R.id.pay_goods:
                 tickets = ShareBankPreferenceUtils.getString("tickets", null);
                if (!StringUtils.isEmpty(tickets)){
                    if (openSelection){
                        openPopupWindow(view);
                    }else {
                        ICartChangeItemPresenter presenter = new CartChangeItemPresenter(this);
                        String goods_num = goods_number.getText().toString();
                        Map<String,String> addCarts = new HashMap<>();
                        if (!StringUtils.isEmpty(goods_num)){
                            addCarts.put("count",goods_num);
                        }
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

                }else {
                    startActivity(new Intent(GoodsDetailActivity.this, LoginActivity.class));
                }
                break;
            case R.id.goods_minus:
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
                break;
            case R.id.goods_collection:
                 tickets = ShareBankPreferenceUtils.getString("tickets", null);
                if (!StringUtils.isEmpty(tickets)){
                    if (!isSelect){
                          Map<String,String> params = new HashMap<>();
                          params.put("contentId",String.valueOf(contentId));
                          IGoodsCollectionPresenter presenter = new GoodsCollectionPresenter(this);
                          presenter.getDetails(params);
                    }else {
                        Map<String,String> params = new HashMap<>();
                        params.put("contentId",String.valueOf(contentId));
                        IGoodsCollectionDelPresenter presenter = new GoodsCollectionDelPresenter(this);
                        presenter.getDetails(params);
                    }
                }else {
                    Toast.makeText(GoodsDetailActivity.this,"请先登陆",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                break;
        }
    }

    /**
     *
     */
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
    private   RecyclerView item_title;
    private TextView add_goods_num;
    private  TextView goods_pop_num;
    private  TextView minus_pop_num;
    private  TextView add_pop_cart;

    private void setOnPopupViewClick(View view) {


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
    public void onNumberFailure(String message) {
        Log.i("获取购物车总数量:","异常:"+message);
    }

    /**
     * 商品详情的请求
     * @param data
     */
    @Override
    public void onGoodsDetailsSucessFul(GoodsDetailsBean data) {
        if (data != null){
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
    public void onGoodsDetailsFailure(String message) {
        Log.i("获取商品详情:","异常:"+message);
    }

    /**
     * 商品推荐的请求
     * @param data
     */
    @Override
    public void onGoodsCommondSucessFul(List<GoodsRecoomendBean> data) {

        if (data != null &&data.size() >0){
            if (mAdapter == null){
                mAdapter = new GoodsDetailCommondAdapter(this,data);
                RecyclerViewHelper.initRecyclerViewH(this,goods_commonds,false,mAdapter);
            }else {
                mAdapter.cleanItems();
                mAdapter.addItems(data);
            }
        }
    }

    @Override
    public void onGoodsCommondFailure(String message) {
        Log.i("获取商品推荐:","异常:"+message);
    }

    //收藏商品
    @Override
    public void onGoodsCollectionsSucessFul(BaseResponseBean data) {
        if (data.getCode() == 200){
            isSelect = true;
            goods_collection.setImageResource(R.mipmap.select_collection);
            Toast.makeText(GoodsDetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGoodsCollectionsFailure(String message) {
        Toast.makeText(GoodsDetailActivity.this,message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onGoodsCollectionDelSucessFul(BaseResponseBean data) {
        if (data.getCode() == 200){
            isSelect = false;
            goods_collection.setImageResource(R.mipmap.collection);
            Toast.makeText(GoodsDetailActivity.this,"取消收藏成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGoodsCollectionDelFailure(String message) {
        Toast.makeText(GoodsDetailActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    //添加购物车

    @Override
    public void onChangeItemSucessful(SpecialResponseBean data) {
        if (data.getCode() == 200){
            Toast.makeText(GoodsDetailActivity.this,"添加购物车成功",Toast.LENGTH_SHORT).show();
            goods_cart_num.setVisibility(View.VISIBLE);
            goods_cart_num.setText(String.valueOf(Integer.valueOf(goods_cart_num.getText().toString())+Integer.valueOf(goods_number.getText().toString())));
            int[] loc = new int[2];
            pay_goods.getLocationInWindow(loc);
            playAnimation(loc);
        }
    }

    @Override
    public void onChangeItemFailure(String message) {
        Toast.makeText(GoodsDetailActivity.this,message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
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
