package com.koalafield.cmart.api;

import android.util.Log;

import com.google.gson.Gson;
import com.koalafield.cmart.AndoridApplication;
import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;
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
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 */

public class ApiManager {


    /*********************************个人中心**************************************/
    /**
     * 注册
     */
    public static Flowable<RegisterBean> getRegsterInfos(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getRegisterAccount(getHeaders(),setParams(params)))
                .map(getRegister());
    }
    /**
     * 登陆
     */
    public  static  Flowable<RegisterBean> getLoginInfos(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getLoginAccount(getHeaders(),setParams(params)))
                .map(getRegister());
    }

    /**
     * 地址列表
     */
    public  static  Flowable<List<AddressManagerBean>> getAddressList(int pageIndex){
        return apiSubscribe(AndoridApplication.apiService.getAddress(getHeaders(),pageIndex))
                .flatMap(getAddresses());
    }

    /**
     * 新增地址列表
     */
    public  static  Flowable<BaseResponseBean> addAddresses(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.addAddress(getHeaders(),setParams(params)))
                .map(addAdress());
    }
    /**
     *修改地址列表
     */
    public  static  Flowable<BaseResponseBean> editAddresses(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.editAddress(getHeaders(),setParams(params)))
                .map(addAdress());
    }
    /**
     * 删除地址
     */
    public  static  Flowable<BaseResponseBean> delAddresses(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.delAddress(getHeaders(),setParams(params)))
                .map(delAdress());
    }

    /**
     * 地址详情
     */
    public  static  Flowable<AddressManagerBean> getAddressDetails(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getDetailsAddress(getHeaders(),setParams(params)))
                .flatMap(getDetails());
    }

    /**
     * 获取当前用户的优惠券
     */
    public  static  Flowable<List<DisCountBean>> getDisCountList(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getDisCountsData(getHeaders(),params))
                .flatMap(getDisCounts());
    }
    /**
     * 获取积分列表
     */
    public  static  Flowable<List<ScoreBean>> getScoreList(int pageIndex){
        return apiSubscribe(AndoridApplication.apiService.getScoreList(getHeaders(),pageIndex))
                .flatMap(getScores());
    }
    /**
     * 订单列表
     */
    public  static  Flowable<List<OrderListBean>> getOrderList(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getOrderList(getHeaders(),params))
                .flatMap(getOrders());
    }
    /**
     * 订单详情
     */
    public  static  Flowable<OrderdetailsBean> getOrderDetails(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getOrderDetials(getHeaders(),params))
                .map(getOrderdetails());
    }

    /**
     * 结算
     */
    public static Flowable<PayBean> getPays(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getPayMents(getHeaders(),setParams(params)))
                .map(getPay());
    }
    /**
     * 价格变动
     */
    public static Flowable<OrderPrice> getPrice(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.changePrices(getHeaders(),setParams(params)))
                .map(getChangePrice());
    }
    /**
     * 提交订单
     */
    public static Flowable<CreateOrderBean> createOrders(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.createOrders(getHeaders(),setParams(params)))
                .map(getOrder());
    }
    /**
     * 调起Sdk
     */
    public static Flowable<SdkPayBean> createSdkPay(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.paySdk(getHeaders(),setParams(params)))
                .map(getSdkpay());
    }
    /*********************************首页****************************************/

    /**
     * 获取轮播图
     * @return
     */
    public  static  Flowable<List<HomeBanaerBean>> getHomeBananerList(){
        return apiSubscribe(AndoridApplication.apiService.getIntegralMallBananer(getHeaders()))
                .flatMap(getHomeBananer());
    }
    /**
     * 获取首页导航栏
     */
    public  static  Flowable<List<ToolsBarBean>> getHomeToolsBar(){
        return apiSubscribe(AndoridApplication.apiService.getHomeToolsBarData(getHeaders()))
                .flatMap(getHomeToolsBars());
    }

    /**
     * 获取首页商品列表
     */
    public  static  Flowable<List<GoodsCategryBean>> getHomeGoodsCategry(){
        return apiSubscribe(AndoridApplication.apiService.getCategryGoodsData(getHeaders()))
                .flatMap(getGoodsCategrys());
    }

    /**
     * 热门搜索
     */
    public  static  Flowable<String[]> getHotKeyWords(){
        return apiSubscribe(AndoridApplication.apiService.getHotDatas(getHeaders()))
                .flatMap(getGoodsCategrys());
    }

    /******************************分类列表*******************************/
    /**
     * 获取分类列表一级分类
     */
    public  static  Flowable<List<CategryOneBean>> getCategryList(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getCategrys(getHeaders(),params))
                .flatMap(getCategry());
    }
    /******************************购物车***********************************/

    /**
     * 获取购物车总数量
     */
    public static Flowable<CartNumberBean> getCartNums(){
        return apiSubscribe(AndoridApplication.apiService.getCartNumbers(getHeaders()))
                .map(getCartNum());
    }

    /**
     * 获取购物车列表
     */

    public  static  Flowable<SpecialResponseBean> getCategryList(){
        return apiSubscribe(AndoridApplication.apiService.getCartDatas(getHeaders()))
                .map(getCartList());
    }
    /**
     * 改变购物车商品的数目，即增减删
     */
    public  static  Flowable<BaseResponseBean> changeCartCount(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.changeGoodsCounts(getHeaders(),setParams(params)))
                .map(changeCartCount());
    }
    /**
     * 清空购物车数据
     */

    public  static  Flowable<BaseResponseBean> clearCartGoods(){
        return apiSubscribe(AndoridApplication.apiService.clearCart(getHeaders()))
                .map(getClearCart());
    }
    /*******************************商品**********************************************************/

    /**
     * 商品详情
     * @param params
     * @return
     */
    public static Flowable<GoodsDetailsBean> getGoodsDetailsInfos(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getGoodsDetails(getHeaders(),setParams(params)))
                .map(getGoodsDetails());
    }

    /**
     * 商品推荐
     * @param goodsId
     * @return
     */
    public  static  Flowable<List<GoodsRecoomendBean>> getGoodsRecommends(int goodsId){
        return apiSubscribe(AndoridApplication.apiService.getGoodsReoomonds(getHeaders(),goodsId))
                .flatMap(getGoodsCommond());
    }
    /**
     * 添加收藏
     */

    public  static  Flowable<BaseResponseBean> goods_collections(Map<String,String>params){
        return apiSubscribe(AndoridApplication.apiService.getGoodsCollection(getHeaders(),setParams(params)))
                .map(addCollcetion());
    }

    /**
     * 取消收藏
     */

    public  static  Flowable<BaseResponseBean> goods_delete_collections(Map<String,String>params){
        return apiSubscribe(AndoridApplication.apiService.getGoodsCollectionDelete(getHeaders(),setParams(params)))
                .map(addCollcetion());
    }

    /**
     * 收藏列表
     */
    public  static  Flowable<List<GoodsCollectionsBean>> getGoodsCollection(int pageIndex){
        return apiSubscribe(AndoridApplication.apiService.getCollectionList(getHeaders(),pageIndex))
                .flatMap(getGoodsCollect());
    }

    /**
     * 评论列表
     */
    public  static  Flowable<List<CommentDatas>> getGoodsComment(Map<String, String> params){
        return apiSubscribe(AndoridApplication.apiService.getCommentDatas(getHeaders(),params))
                .flatMap(getGoodsComments());
    }
    /**
     * 买过的商品列表
     */
    public  static  Flowable<List<PurchaseOffBean>> getGoodsPurchaseOff(int pageIndex){
        return apiSubscribe(AndoridApplication.apiService.getPurchaseOffList(getHeaders(),pageIndex))
                .flatMap(getPurchaseOff());
    }

    /**
     *收藏，优惠卷等数量
     */
    public  static  Flowable<PersonNumber> get_numbers(){
        return apiSubscribe(AndoridApplication.apiService.getPersionNumbers(getHeaders()))
                .map(getNumbers());
    }

    /*****************************添加头部*****************************************/

    private static  Map<String,String> getHeaders(){
        //设置头部
        String lat = ""; //经度
        String let = "";  //w纬度
        String uniqueId = AndoridSysUtils.getUniqueId(AndoridApplication.getContext()); //唯一标识符
        String version = AndoridSysUtils.getVersion(AndoridApplication.getContext(), AndoridApplication.getContext().getPackageName()); //版本号
        String deviceType ="2"; //设备类型
        String tickets = ShareBankPreferenceUtils.getString("tickets", null);
        Map<String,String> headers = new HashMap<>();
        StringBuilder sb  =new StringBuilder();
        sb.append(version).append(";").append(lat).append(",").append(let).append(";").append(uniqueId).append(";").append(deviceType);
        headers.put("E-Agent",sb.toString());
        if (!StringUtils.isEmpty(tickets)){
            headers.put("ticket",tickets);
        }else {
            headers.put("ticket","");
        }
        return  headers;
    }


    /***************************************数据转换器******************************************/
    /**
     * 注册,登陆
     * @return
     */
    private static Function<SpecialResponseBean,RegisterBean> getRegister() {
        return new Function<SpecialResponseBean, RegisterBean>() {
            @Override
            public RegisterBean apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (RegisterBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 结算
     */
    private static Function<SpecialResponseBean,PayBean> getPay() {
        return new Function<SpecialResponseBean, PayBean>() {
            @Override
            public PayBean apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (PayBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 价格变动
     */
    private static Function<SpecialResponseBean,OrderPrice> getChangePrice() {
        return new Function<SpecialResponseBean, OrderPrice>() {
            @Override
            public OrderPrice apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (OrderPrice) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 生成订单
     */
    private static Function<SpecialResponseBean,CreateOrderBean> getOrder() {
        return new Function<SpecialResponseBean, CreateOrderBean>() {
            @Override
            public CreateOrderBean apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (CreateOrderBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 调起Sdk
     */
    private static Function<SpecialResponseBean,SdkPayBean> getSdkpay() {
        return new Function<SpecialResponseBean, SdkPayBean>() {
            @Override
            public SdkPayBean apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据2:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (SdkPayBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 订单详情
     */
    private static Function<SpecialResponseBean,OrderdetailsBean> getOrderdetails() {
        return new Function<SpecialResponseBean, OrderdetailsBean>() {
            @Override
            public OrderdetailsBean apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (OrderdetailsBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     *父分类列表
     */
    private static Function<SpecialResponseBean, Flowable<List<CategryOneBean>>> getCategry() {
        return new Function<SpecialResponseBean, Flowable<List<CategryOneBean>>>() {
            @Override
            public Flowable<List<CategryOneBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<CategryOneBean>) response.getData());
                }
                return  null;
            }
        };
    }

    /**
     * 获取主页轮播图
     */
    private static Function<SpecialResponseBean, Flowable<List<HomeBanaerBean>>> getHomeBananer() {
        return new Function<SpecialResponseBean, Flowable<List<HomeBanaerBean>>>() {
            @Override
            public Flowable<List<HomeBanaerBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<HomeBanaerBean>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 获取主页商品分类
     */
    private static Function<SpecialResponseBean, Flowable<List<GoodsCategryBean>>> getGoodsCategrys() {
        return new Function<SpecialResponseBean, Flowable<List<GoodsCategryBean>>>() {
            @Override
            public Flowable<List<GoodsCategryBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<GoodsCategryBean>) response.getData());
                }
                return  null;
            }
        };
    }

    /**
     * 获取主页导航栏
     */
    private static Function<SpecialResponseBean, Flowable<List<ToolsBarBean>>> getHomeToolsBars() {
        return new Function<SpecialResponseBean, Flowable<List<ToolsBarBean>>>() {
            @Override
            public Flowable<List<ToolsBarBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<ToolsBarBean>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 获取热门搜索关键词
     */
    private static Function<SpecialResponseBean, Flowable<String []>> getHotWords() {
        return new Function<SpecialResponseBean, Flowable<String []>>() {
            @Override
            public Flowable<String []> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return (Flowable<String[]>) response.getData();
                }
                return  null;
            }
        };
    }

    /**
     * 获取购物和总数量
     */
    private static Function<SpecialResponseBean,CartNumberBean> getCartNum() {
        return new Function<SpecialResponseBean, CartNumberBean>() {
            @Override
            public CartNumberBean apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (CartNumberBean) response.getData();
                }
                return  null;
            }
        };
    }

    /**
     * 获取购物车列表
     */
    private static Function<SpecialResponseBean, SpecialResponseBean> getCartList() {
        return new Function<SpecialResponseBean,SpecialResponseBean>() {
            @Override
            public SpecialResponseBean apply(SpecialResponseBean response) throws Exception {
               return response;
            }
        };
    }

    /**
     * 增减购物车商品数量
     */
    private static Function<BaseResponseBean,BaseResponseBean> changeCartCount() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                return response;
            }
        };
    }

    /**
     * 清空购物车
     * @return
     */
    private static Function<BaseResponseBean,BaseResponseBean> getClearCart() {
        return new Function<BaseResponseBean,BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                return response;
            }
        };
    }
    /**
     * 商品详情
     */
    private static Function<SpecialResponseBean,GoodsDetailsBean> getGoodsDetails() {
        return new Function<SpecialResponseBean, GoodsDetailsBean>() {
            @Override
            public GoodsDetailsBean apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                return (GoodsDetailsBean) response.getData();
            }
        };
    }
    /**
     * 获取商品推荐
     */
    private static Function<SpecialResponseBean, Flowable<List<GoodsRecoomendBean>>> getGoodsCommond() {
        return new Function<SpecialResponseBean, Flowable<List<GoodsRecoomendBean>>>() {
            @Override
            public Flowable<List<GoodsRecoomendBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<GoodsRecoomendBean>) response.getData());
                }
                return  null;
            }
        };
    }

    /**
     * 收藏，取消，商品
     * @return
     */
    private static Function<BaseResponseBean,BaseResponseBean> addCollcetion() {
        return new Function<BaseResponseBean,BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                return response;
            }
        };
    }

    /**
     * 获取收藏等数量
     */
    private static Function<SpecialResponseBean,PersonNumber> getNumbers() {
        return new Function<SpecialResponseBean,PersonNumber>() {
            @Override
            public PersonNumber apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                return (PersonNumber) response.getData();
            }
        };
    }

    /**
     * 获取收藏列表
     */
    private static Function<SpecialResponseBean, Flowable<List<GoodsCollectionsBean>>> getGoodsCollect() {
        return new Function<SpecialResponseBean, Flowable<List<GoodsCollectionsBean>>>() {
            @Override
            public Flowable<List<GoodsCollectionsBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<GoodsCollectionsBean>) response.getData());
                }
                return  null;
            }
        };
    }


    /**
     * 获取评论列表
     */
    private static Function<SpecialResponseBean, Flowable<List<CommentDatas>>> getGoodsComments() {
        return new Function<SpecialResponseBean, Flowable<List<CommentDatas>>>() {
            @Override
            public Flowable<List<CommentDatas>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<CommentDatas>) response.getData());
                }
                return  null;
            }
        };
    }

    /**
     * 获取买过的列表
     */
    private static Function<SpecialResponseBean, Flowable<List<PurchaseOffBean>>> getPurchaseOff() {
        return new Function<SpecialResponseBean, Flowable<List<PurchaseOffBean>>>() {
            @Override
            public Flowable<List<PurchaseOffBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<PurchaseOffBean>) response.getData());
                }
                return  null;
            }
        };
    }


    /**
     * 地址列表
     */
    private static Function<SpecialResponseBean, Flowable<List<AddressManagerBean>>> getAddresses() {
        return new Function<SpecialResponseBean, Flowable<List<AddressManagerBean>>>() {
            @Override
            public Flowable<List<AddressManagerBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<AddressManagerBean>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 新增修改地址
     */
    private static Function<BaseResponseBean,BaseResponseBean> addAdress() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                return response;
            }
        };
    }
    /**
     * 删除地址
     */
    private static Function<BaseResponseBean,BaseResponseBean> delAdress() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                return response;
            }
        };
    }

    /**
     * 获取地址详情
     */
    private static Function<SpecialResponseBean,AddressManagerBean> getDetails() {
        return new Function<SpecialResponseBean, AddressManagerBean>() {
            @Override
            public AddressManagerBean apply(SpecialResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (AddressManagerBean) response.getData();
                }
                return  null;
            }
        };
    }

    /**
     * 获取用户优惠券
     */
    private static Function<SpecialResponseBean, Flowable<List<DisCountBean>>> getDisCounts() {
        return new Function<SpecialResponseBean, Flowable<List<DisCountBean>>>() {
            @Override
            public Flowable<List<DisCountBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<DisCountBean>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 获取积分列表
     */
    private static Function<SpecialResponseBean, Flowable<List<ScoreBean>>> getScores() {
        return new Function<SpecialResponseBean, Flowable<List<ScoreBean>>>() {
            @Override
            public Flowable<List<ScoreBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<ScoreBean>) response.getData());
                }
                return  null;
            }
        };
    }

    /**
     * 获取订单列表
     */
    private static Function<SpecialResponseBean, Flowable<List<OrderListBean>>> getOrders() {
        return new Function<SpecialResponseBean, Flowable<List<OrderListBean>>>() {
            @Override
            public Flowable<List<OrderListBean>> apply(SpecialResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<OrderListBean>) response.getData());
                }
                return  null;
            }
        };
    }
    /***************************************post添加参数json格式转换**********************************************/
    private static RequestBody setParams(Map<String,String> params){
        return  RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(params));
    }



  /*  *//**
     * 获取首页的bananer图片
     * @return
     *//*
    public static Flowable<List<ProductBannerBean>> getIntegralMall(){
        return apiSubscribe(MyApplication.apiService.getIntegralMallBananer())
                .flatMap(getMall());
    }


    *//**
     * 获取首页的分类主题
     *//*
    public  static  Flowable<List<UbProductMainListBean>> getHomeCategryData(Map<String,String> params){
        return apiSubscribe(MyApplication.apiService.getHomeCategry(params))
                .flatMap(getCategry());
    }

    *//**
     * 获取首页推荐列表
     *//*
    public static Flowable<List<ProductListBean>> getHomeRecommend(Map<String,String> params){
        return apiSubscribe(MyApplication.apiService.getHomeRecommend(setParams(params)))
                .flatMap(getRecommend());
    }

    *//**
     * 获取购物车列表数据
     *//*
    public  static  Flowable<List<ShopCartItemBean>> getCartNumber(){
        return apiSubscribe(MyApplication.apiService.getCartShopping())
                .flatMap(getCarts());

    }

    *//**
     * 添加加盟商收藏
     *//*
    public  static  Flowable<Object> getAddShopers(Map<String,String> params){
        return apiSubscribe(MyApplication.apiService.getAddShopers(setParams(params)))
                .flatMap(addShoper());

    }

    *//**
     * 加盟商下拉列表
     *//*
    public  static  Flowable<List<SpinnerListBean>> getSelectShop(Map<String,String> params){
        return apiSubscribe(MyApplication.apiService.getSelectShops(setParams(params)))
                .flatMap(selectShops());

    }


    *//**
     * 联盟商家轮播图
     * @return
     *//*
    public  static  Flowable<List<ProductBannerBean>> getUnioBananers(){
        return apiSubscribe(MyApplication.apiService.getUnioBananers())
                .flatMap(getUnioBananer());

    }
    *//**
     * 联盟商家分类列表
     *//*
    public  static  Flowable<List<SellerCategoryBean>> getUnioCategry(){
        return apiSubscribe(MyApplication.apiService.getUnioCategry())
                .flatMap(getUnioCategrys());

    }

    *//**
     * 联盟商家列表
     *//*
    public  static  Flowable<List<SellerBean>> getUnioList(Map<String,String> params){
        return apiSubscribe(MyApplication.apiService.getUnioList(params))
                .flatMap(getUnioLists());
    }

    *//**
     * 联盟商家具体分类入口
     *//*
    public  static  Flowable<List<SellerBean>> getUnioCategryIn(Map<String,String> params){
        return apiSubscribe(MyApplication.apiService.getUnioCategryIn(params))
                .flatMap(getUnioLists());
    }

    *//**
     * 附近联盟商家
     *//*
    public  static  Flowable<List<SellerBean>> getUnioNearByShopers(Map<String,String> params){
        return apiSubscribe(MyApplication.apiService.getUnioNearByShopers(params))
                .flatMap(getUnioLists());
    }

    *//***************************************post添加参数json格式转换**********************************************//*


    private static RequestBody setParams(Map<String,String> params){
        String userToken = GlobalInfo.userToken;
        if(TextUtils.isEmpty(userToken)){
            userToken = "\"\"";
        }
        params.put("token",userToken);
        return  RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(params));
    }

    *//***************************************数据转换器******************************************//*

    *//**
     * 联盟商家轮播图
     * @return
     *//*

    private static Function<BaseResponseBean, Flowable<List<ProductBannerBean>>> getUnioBananer() {
        return new Function<BaseResponseBean, Flowable<List<ProductBannerBean>>>() {
            @Override
            public Flowable<List<ProductBannerBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<ProductBannerBean>) response.getData());
                }
                return  null;
            }
        };

    }
    *//**
     * 联盟商家分类列表
     *//*
    private static Function<BaseResponseBean, Flowable<List<SellerCategoryBean>>> getUnioCategrys() {
        return new Function<BaseResponseBean, Flowable<List<SellerCategoryBean>>>() {
            @Override
            public Flowable<List<SellerCategoryBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<SellerCategoryBean>) response.getData());
                }
                return  null;
            }
        };
    }

    *//**
     * 联盟商家列表/具体分类入口
     *//*
    private static Function<BaseResponseBean, Flowable<List<SellerBean>>> getUnioLists() {
        return new Function<BaseResponseBean, Flowable<List<SellerBean>>>() {
            @Override
            public Flowable<List<SellerBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<SellerBean>) response.getData());
                }
                return  null;
            }
        };
    }


    *//**
     * 积分商城bananer图片的转换
     * @return
     *//*
    private static Function<BaseResponseBean, Flowable<List<ProductBannerBean>>> getMall() {

        return new Function<BaseResponseBean, Flowable<List<ProductBannerBean>>>() {
            @Override
            public Flowable<List<ProductBannerBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<ProductBannerBean>) response.getData());
                }
                return  null;
            }
        };

    }

    *//**
     * 获取首页主题分类
     * @return
     *//*

    private static Function<BaseResponseBean, Flowable<List<UbProductMainListBean>>> getRecommend() {
        return new Function<BaseResponseBean, Flowable<List<UbProductMainListBean>>>() {
            @Override
            public Flowable<List<UbProductMainListBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<UbProductMainListBean>) response.getData());
                }
                return  null;
            }
        };
    }

    *//**
     * 获取首页推荐商品
     *//*
    private static Function<BaseResponseBean, Flowable<List<ProductListBean>>> getCategry() {
        return new Function<BaseResponseBean, Flowable<List<ProductListBean>>>() {
            @Override
            public Flowable<List<ProductListBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<ProductListBean>) response.getData());
                }
                return  null;
            }
        };
    }

    *//**
     * 添加加盟商收藏
     *//*
    private static Function<BaseResponseBean,Object> addShoper() {

        return  new Function<BaseResponseBean, Object>() {
            @Override
            public Object apply(BaseResponseBean baseResponseBean) throws Exception {
                if (null != baseResponseBean){
                    return  baseResponseBean;
                }
                return  null;
            }
        };
    }

    *//**
     * 加盟商下拉列表
     *//*

    private static Function<BaseResponseBean, Flowable<List<SpinnerListBean>>> selectShops() {

        return new Function<BaseResponseBean, Flowable<List<SpinnerListBean>>>() {
            @Override
            public Flowable<List<SpinnerListBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<SpinnerListBean>) response.getData());
                }
                return  null;
            }
        };
    }
    *//**
     * 购物车数量
     * @return
     *//*
    private static  Function<BaseResponseBean,Flowable<List<ShopCartItemBean>>> getCarts(){
        return new Function<BaseResponseBean, Flowable<List<ShopCartItemBean>>>() {
            @Override
            public Flowable<List<ShopCartItemBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<ShopCartItemBean>) response.getData());
                }
                return  null;
            }
        };
    }*/

    /************************************线程转换公共部分******************************************************/

    private static Flowable apiSubscribe(Flowable observable){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
