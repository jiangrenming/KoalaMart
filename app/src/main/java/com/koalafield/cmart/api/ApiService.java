package com.koalafield.cmart.api;

import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.bean.cart.CartIdBean;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.categry.CateBrandGoodsListBean;
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
import com.koalafield.cmart.bean.search.SearchListBean;
import com.koalafield.cmart.bean.user.AddressManagerBean;
import com.koalafield.cmart.bean.user.AvtorBean;
import com.koalafield.cmart.bean.user.CountryCode;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.bean.user.PersonInfos;
import com.koalafield.cmart.bean.user.PersonNumber;
import com.koalafield.cmart.bean.user.PurchaseOffBean;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.bean.user.ScoreBean;
import com.koalafield.cmart.bean.user.ShareBean;
import com.koalafield.cmart.bean.user.WXRegisterBean;
import com.koalafield.cmart.bean.user.WeiXinToken;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

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
    Flowable<BaseResponseBean<RegisterBean>> getRegisterAccount(@HeaderMap Map<String,String> headrs, @Body RequestBody body);

    //登陆
    @POST("AppApi/Login")
    Flowable<BaseResponseBean<RegisterBean>> getLoginAccount (@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //购物车商品数量的增减
    @POST("AppApi/ShoppingCartChange")
    Flowable<BaseResponseBean<CartIdBean>> changeGoodsCounts(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //购物车清空
    @POST("AppApi/ShoppingCartClearUp")
    Flowable<BaseResponseBean> clearCart(@HeaderMap Map<String,String> headrs);
    //商品详情
    @POST("AppApi/GoodsDetails")
    Flowable<BaseResponseBean<GoodsDetailsBean>> getGoodsDetails(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
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
    Flowable<BaseResponseBean<AddressManagerBean>> getDetailsAddress(@HeaderMap Map<String,String> headrs,@Body RequestBody body);
    //结算
    @POST("AppApi/Checkout")
    Flowable<BaseResponseBean<PayBean>> getPayMents(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //修改价格
    @POST("AppApi/CalculateOrderPrice")
    Flowable<BaseResponseBean<OrderPrice>> changePrices(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //生成订单
    @POST("AppApi/CreateOrder")
    Flowable<BaseResponseBean<CreateOrderBean>> createOrders(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //调起支付
    @POST("AppApi/CreatePay")
    Flowable<BaseResponseBean<SdkPayBean>> paySdk(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //短信验证码
    @POST("AppApi/SendValidate")
    Flowable<BaseResponseBean> getMessageCode(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //修改用户信息
    @POST("AppApi/UpdateInfo")
    Flowable<BaseResponseBean> upDatePersonInfo(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //重置密码
    @POST("AppApi/ResetPassword")
    Flowable<BaseResponseBean> resetPwd(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //修改密码
    @POST("AppApi/EditPassword")
    Flowable<BaseResponseBean> changePwd(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //确认收货
    @POST("AppApi/ConfirmReceive")
    Flowable<BaseResponseBean> comfirmOrder(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //微信登录
    @POST("AppApi/WechatOauth")
    Flowable<BaseResponseBean<RegisterBean>> WXLogin(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //取消订单
    @POST("AppApi/CancelBill")
    Flowable<BaseResponseBean> cancleOrder(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    @POST("AppApi/FeedBack")
    Flowable<BaseResponseBean> getUserAdavices(@HeaderMap Map<String,String> headrs, @Body RequestBody body);
    //修改头像，表单格式上传
    @Multipart
    @POST("AppApi/ChangeAvtor")
    Flowable<BaseResponseBean<AvtorBean>> changeUserAvtor(@HeaderMap Map<String,String> headrs,@Part List<MultipartBody.Part> partList);
    /***************************************************Get请求*******************************************/
    @GET("AppApi/CategoryList")
    Flowable<BaseResponseBean<List<CategryOneBean>>> getCategrys(@HeaderMap Map<String,String> headrs,@QueryMap Map<String, String> params);
    @GET("AppApi/BannerList")
    Flowable<BaseResponseBean<List<HomeBanaerBean>>> getIntegralMallBananer(@HeaderMap Map<String,String> headrs);
    @GET("AppApi/HomeCategoryGoods")
    Flowable<BaseResponseBean<List<GoodsCategryBean>>> getCategryGoodsData(@HeaderMap Map<String,String> headrs);
    @GET("AppApi/ShoppingCartList")
    Flowable<BaseResponseBean<List<CartDataBean>>> getCartDatas(@HeaderMap Map<String,String> headrs);
    @GET("AppApi/ShoppingCartTotalNumber")
    Flowable<BaseResponseBean<CartNumberBean>> getCartNumbers(@HeaderMap Map<String,String> headrs);
    //商品推荐
    @GET("AppApi/RecommendGoods/{goodsId}")
    Flowable<BaseResponseBean<List<GoodsRecoomendBean>>>  getGoodsReoomonds(@HeaderMap Map<String,String> headrs, @Path("goodsId") int goodsId);
    //收藏列表
    @GET("AppApi/FollowList/{pageIndex}")
    Flowable<BaseResponseBean<List<GoodsCollectionsBean>>> getCollectionList(@HeaderMap Map<String,String> headrs, @Path("pageIndex") int pageIndex);
    //评论列表
    @GET("AppApi/CommentList")
    Flowable<BaseResponseBean<List<CommentDatas>>> getCommentDatas(@HeaderMap Map<String,String> headrs,@QueryMap Map<String, String> params);
    //热门搜索词
    @GET("AppApi/HotSearchText")
    Flowable<BaseResponseBean<List<String>>> getHotDatas(@HeaderMap Map<String,String> headrs);
    //搜索列表分类
    @GET("AppApi/Search")
    Flowable<BaseResponseBean<List<SearchListBean>>> getSearchList(@HeaderMap Map<String,String> headrs,@QueryMap Map<String, String> params);
    //买过的商品
    @GET("AppApi/PurchaseOff/{pageIndex}")
    Flowable<BaseResponseBean<List<PurchaseOffBean>>> getPurchaseOffList(@HeaderMap Map<String,String> headrs, @Path("pageIndex") int pageIndex);
    //获取优惠券收藏等数量
    @GET("AppApi/CustomerInfoCount")
    Flowable<BaseResponseBean<PersonNumber>> getPersionNumbers(@HeaderMap Map<String,String> headrs);
    //用户地址列表
    @GET("AppApi/AddressList")
    Flowable<BaseResponseBean<List<AddressManagerBean>>> getAddress(@HeaderMap Map<String,String> headrs);
    //获取当前用户的优惠券
    @GET("AppApi/MyCoupon")
    Flowable<BaseResponseBean<List<DisCountBean>>> getDisCountsData(@HeaderMap Map<String,String> headrs, @QueryMap Map<String,String> params);
    //获取积分列表
    @GET("AppApi/ScoreList/{pageIndex}")
    Flowable<BaseResponseBean<List<ScoreBean>>> getScoreList(@HeaderMap Map<String,String> headrs, @Path("pageIndex") int pageIndex);
    //订单列表
    @GET("AppApi/BillList")
    Flowable<BaseResponseBean<List<OrderListBean>>> getOrderList(@HeaderMap Map<String,String> headrs, @QueryMap Map<String,String> params);
    @GET("AppApi/BillDetails")
    Flowable<BaseResponseBean<OrderdetailsBean>> getOrderDetials(@HeaderMap Map<String,String> headrs, @QueryMap Map<String,String> params);
    @GET("AppAPi/ToolsBar")
    Flowable<BaseResponseBean<List<ToolsBarBean>>> getHomeToolsBarData(@HeaderMap Map<String,String> headrs);
    @GET("AppApi/CateBrandGoodsList")
    Flowable<BaseResponseBean<CateBrandGoodsListBean>> getCategryList(@HeaderMap Map<String,String> headrs, @QueryMap Map<String,String> params);
    //获取国家代码
    @GET("AppApi/CountrySmsCode")
    Flowable<BaseResponseBean<List<CountryCode>>> getCountryCode(@HeaderMap Map<String,String> headrs);
    @GET("AppApi/CurrentInfo")
    Flowable<BaseResponseBean<PersonInfos>> getPersonInfos(@HeaderMap Map<String,String> headrs);
    //获取分享数据
    @GET("AppApi/SharePageData")
    Flowable<BaseResponseBean<ShareBean>> getShareInfos(@HeaderMap Map<String,String> headrs);

}
