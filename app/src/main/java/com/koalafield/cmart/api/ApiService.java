package com.koalafield.cmart.api;

import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.bean.user.RegisterBean;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 *
 * @author jiangrenming
 * @date 2018/1/3
 * 所有接口放置的地方
 */

public interface ApiService {

    /**********************************get 请求列表************************************/

    /**********************************post 请求列表***********************************/
    //注册
    @POST("AppApi/Register")
    Flowable<BaseResponseBean<RegisterBean>> getRegisterAccount(@Body RequestBody body);

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
