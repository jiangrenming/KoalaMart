package com.koalafield.cmart.api;

import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.bean.goods.CommentDatas;
import com.koalafield.cmart.bean.goods.GoodsCollectionsBean;
import com.koalafield.cmart.bean.goods.GoodsDetailsBean;
import com.koalafield.cmart.bean.goods.GoodsRecoomendBean;
import com.koalafield.cmart.bean.home.GoodsCategryBean;
import com.koalafield.cmart.bean.home.HomeBanaerBean;
import com.koalafield.cmart.bean.home.ToolsBarBean;
import com.koalafield.cmart.bean.order.CreateOrderBean;
import com.koalafield.cmart.bean.order.OrderListBean;
import com.koalafield.cmart.bean.order.OrderPrice;
import com.koalafield.cmart.bean.order.OrderdetailsBean;
import com.koalafield.cmart.bean.order.PayBean;
import com.koalafield.cmart.bean.order.SdkPayBean;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.bean.user.PersonNumber;
import com.koalafield.cmart.bean.user.PurchaseOffBean;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.bean.user.ScoreBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *
 * @author jiangrenming
 * @date 2018/1/3
 * 所有接口放置的地方
 */

public interface ApiService {

    /**********************************post 请求列表***********************************/
    //注册
    @POST("AppApi/Register")
    Flowable<SpecialResponseBean<RegisterBean>> getRegisterAccount(@HeaderMap Map<String,String> headrs, @Body RequestBody body);

    //登陆
    @POST("AppApi/Login")
    Flowable<SpecialResponseBean<RegisterBean>> getLoginAccount (@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //购物车商品数量的增减
    @POST("AppApi/ShoppingCartChange")
    Flowable<SpecialResponseBean> changeGoodsCounts(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //购物车清空
    @POST("AppApi/ShoppingCartClearUp")
    Flowable<SpecialResponseBean> clearCart(@HeaderMap Map<String,String> headrs);
    //商品详情
    @POST("AppApi/GoodsDetails")
    Flowable<SpecialResponseBean<GoodsDetailsBean>> getGoodsDetails(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //添加收藏
    @POST("AppApi/AddFollow")
    Flowable<BaseResponseBean> getGoodsCollection(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //取消收藏
    @POST("AppApi/DeleteFollow")
    Flowable<BaseResponseBean> getGoodsCollectionDelete(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //新增地址
    @POST("AppApi/AddAddress")
    Flowable<BaseResponseBean> addAddress(@HeaderMap Map<String,String> headrs,@Body RequestBody body);
    //修改地址
    @POST("AppApi/EditAddress")
    Flowable<BaseResponseBean> editAddress(@HeaderMap Map<String,String> headrs,@Body RequestBody body);
    //删除地址
    @POST("AppApi/DeleteAddress")
    Flowable<BaseResponseBean> delAddress(@HeaderMap Map<String,String> headrs,@Body RequestBody body);
    //用户地址详情
    @POST("AppApi/AddressDetail")
    Flowable<SpecialResponseBean<AddressManagerBean>> getDetailsAddress(@HeaderMap Map<String,String> headrs,@Body RequestBody body);
    //结算
    @POST("AppApi/Checkout")
    Flowable<SpecialResponseBean<PayBean>> getPayMents(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //修改价格
    @POST("AppApi/CalculateOrderPrice")
    Flowable<SpecialResponseBean<OrderPrice>> changePrices(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //生成订单
    @POST("AppApi/CreateOrder")
    Flowable<SpecialResponseBean<CreateOrderBean>> createOrders(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //调起支付
    @POST("AppApi/CreatePay")
    Flowable<SpecialResponseBean<SdkPayBean>> paySdk(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    /***************************************************Get请求*******************************************/
    @GET("AppApi/CategoryList")
    Flowable<SpecialResponseBean<List<CategryOneBean>>> getCategrys(@HeaderMap Map<String,String> headrs,@QueryMap Map<String, String> params);
    @GET("AppApi/BannerList")
    Flowable<SpecialResponseBean<List<HomeBanaerBean>>> getIntegralMallBananer(@HeaderMap Map<String,String> headrs);
    @GET("AppApi/HomeCategoryGoods")
    Flowable<SpecialResponseBean<List<GoodsCategryBean>>> getCategryGoodsData(@HeaderMap Map<String,String> headrs);
    @GET("AppApi/ShoppingCartList")
    Flowable<SpecialResponseBean<List<CartDataBean>>> getCartDatas(@HeaderMap Map<String,String> headrs);
    @GET("AppApi/ShoppingCartTotalNumber")
    Flowable<SpecialResponseBean<CartNumberBean>> getCartNumbers(@HeaderMap Map<String,String> headrs);
    //商品推荐
    @GET("AppApi/RecommendGoods/{goodsId}")
    Flowable<SpecialResponseBean<List<GoodsRecoomendBean>>>  getGoodsReoomonds(@HeaderMap Map<String,String> headrs, @Path("goodsId") int goodsId);
    //收藏列表
    @GET("AppApi/FollowList/{pageIndex}")
    Flowable<SpecialResponseBean<List<GoodsCollectionsBean>>> getCollectionList(@HeaderMap Map<String,String> headrs, @Path("pageIndex") int pageIndex);
    //评论列表
    @GET("AppApi/CommentList")
    Flowable<SpecialResponseBean<List<CommentDatas>>> getCommentDatas(@HeaderMap Map<String,String> headrs,@QueryMap Map<String, String> params);
    //热门搜索词
    @GET("AppApi/HotSearchText")
    Flowable<SpecialResponseBean<String[]>> getHotDatas(@HeaderMap Map<String,String> headrs);
    //买过的商品
    @GET("AppApi/PurchaseOff/{pageIndex}")
    Flowable<SpecialResponseBean<List<PurchaseOffBean>>> getPurchaseOffList(@HeaderMap Map<String,String> headrs, @Path("pageIndex") int pageIndex);
    //获取优惠券收藏等数量
    @GET("AppApi/CustomerInfoCount")
    Flowable<SpecialResponseBean<PersonNumber>> getPersionNumbers(@HeaderMap Map<String,String> headrs);
    //用户地址列表
    @GET("AppApi/AddressList/{pageIndex}")
    Flowable<SpecialResponseBean<List<AddressManagerBean>>> getAddress(@HeaderMap Map<String,String> headrs, @Path("pageIndex") int pageIndex);
    //获取当前用户的优惠券
    @GET("AppApi/MyCoupon")
    Flowable<SpecialResponseBean<List<DisCountBean>>> getDisCountsData(@HeaderMap Map<String,String> headrs, @QueryMap Map<String,String> params);
    //获取积分列表
    @GET("AppApi/ScoreList/{pageIndex}")
    Flowable<SpecialResponseBean<List<ScoreBean>>> getScoreList(@HeaderMap Map<String,String> headrs, @Path("pageIndex") int pageIndex);
    //订单列表
    @GET("AppApi/BillList")
    Flowable<SpecialResponseBean<List<OrderListBean>>> getOrderList(@HeaderMap Map<String,String> headrs, @QueryMap Map<String,String> params);
    @GET("AppApi/BillDetails")
    Flowable<SpecialResponseBean<OrderdetailsBean>> getOrderDetials(@HeaderMap Map<String,String> headrs, @QueryMap Map<String,String> params);
    @GET("AppAPi/ToolsBar")
    Flowable<SpecialResponseBean<List<ToolsBarBean>>> getHomeToolsBarData(@HeaderMap Map<String,String> headrs);

    /* *//**
     * 首页bananer
     * @return
     *//*
    @GET("app/ubstore/banner/list")
    Flowable<BaseResponseBean<List<ProductBannerBean>>> getIntegralMallBananer();

    *//**
     * 首页主题分类
     *//*
    @GET("app/ubstore/franchisee/activity/list")
    Flowable<BaseResponseBean<List<UbProductMainListBean>>> getHomeCategry(@QueryMap Map<String, String> params);

    *//**
     * 购物车列表
     *//*
     @GET("app/user/shopcart/list")
    Flowable<BaseResponseBean<List<ShopCartItemBean>>> getCartShopping();


    *//**
     * 联盟商家轮播图
     * @return
     *//*
    @GET("app/union_shop/banner")
    Flowable<BaseResponseBean<List<ProductBannerBean>>> getUnioBananers();


    *//**
     * 联盟商家分类列表
     *//*
    @GET("app/union_shop/category")
    Flowable<BaseResponseBean<List<SellerCategoryBean>>> getUnioCategry();

    *//**
     * 联盟商家列表
     *//*
    @GET("app/union_shop/list")
    Flowable<BaseResponseBean<List<SellerBean>>> getUnioList(@QueryMap Map<String, String> params);

    *//**
     * 联盟商家具体分类入口
     * @return
     *//*
    @GET("app/union_shop/category_detail")
    Flowable<BaseResponseBean<List<SellerBean>>> getUnioCategryIn(@QueryMap Map<String, String> params);
    *//**
     * 附近联盟商家
     *//*
    @GET("app/union_shop/nearby_list")
    Flowable<BaseResponseBean<List<SellerBean>>> getUnioNearByShopers(@QueryMap Map<String, String> params);

    *//**********************************post 请求列表***********************************//*
    *//**
     * 首页推荐商品
     *//*
    @POST("app/ubstore/franchisee/product/recommend")
    Flowable<BaseResponseBean<List<ProductListBean>>> getHomeRecommend(@Body RequestBody body);

    *//**
     * 添加加盟商收藏
     *//*
    @POST("app/franchisee/favor/add")
    Flowable<BaseResponseBean<Object>> getAddShopers(@Body RequestBody body);

    *//**
     *首页加盟商下拉店铺列表
     *//*
    @POST("app/ubstore/franchisee/select/list")
    Flowable<BaseResponseBean<List<SpinnerListBean>>> getSelectShops(@Body RequestBody body);
*/
}
