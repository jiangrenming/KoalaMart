package com.koalafield.cmart.api;

import android.util.Log;

import com.google.gson.Gson;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.koalafield.cmart.AndoridApplication;
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
import com.koalafield.cmart.utils.AndoridSysUtils;
import com.koalafield.cmart.utils.ShareBankPreferenceUtils;
import com.koalafield.cmart.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
     * 微信登陆
     */
    public  static  Flowable<RegisterBean> getWXLogin(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.WXLogin(getHeaders(),setParams(params)))
                .map(getRegister());
    }
    /**
     * 地址列表
     */
    public  static  Flowable<List<AddressManagerBean>> getAddressList(){
        return apiSubscribe(AndoridApplication.apiService.getAddress(getHeaders()))
                .flatMap(getAddresses());
    }
    /**
     *用户反馈信息
     */
    public  static  Flowable<BaseResponseBean> addAdavices(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getUserAdavices(getHeaders(),setParams(params)))
                .map(addUserAdavice());
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
     * 获取短信验证码
     */
    public  static  Flowable<BaseResponseBean> getMessageCode(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getMessageCode(getHeaders(),setParams(params)))
                .map(getMessage());
    }
    /**
     * 修改个人信息
     */
    public  static  Flowable<BaseResponseBean> upDatePerson(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.upDatePersonInfo(getHeaders(),setParams(params)))
                .map(getPerson());
    }
    /**
     * 修改密码
     */
    public  static  Flowable<BaseResponseBean> changePwd(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.changePwd(getHeaders(),setParams(params)))
                .map(changePwd());
    }
    /**
     * 重置密码
     */
    public  static  Flowable<BaseResponseBean> resetPwd(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.resetPwd(getHeaders(),setParams(params)))
                .map(resetPwd());
    }
    /**
     * 获取国家代码
     */
    public  static  Flowable<List<CountryCode>> getCountryCode(){
        return apiSubscribe(AndoridApplication.apiService.getCountryCode(getHeaders()))
                .flatMap(getCountryCodes());
    }
    /**
     * 获取用户信息
     */
    public static Flowable<PersonInfos> getPersonInfos(){
        return apiSubscribe(AndoridApplication.apiService.getPersonInfos(getHeaders()))
                .map(getInfos());
    }

    /**
     * 取消订单
     */
    public static Flowable<BaseResponseBean> cancle_order(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.cancleOrder(getHeaders(),setParams(params)))
                .map(cancleOrder());
    }

    /**
     * 上传头像
     */
    public  static  Flowable<AvtorBean> changeAvtor(String path){
        return apiSubscribe(AndoridApplication.apiService.changeUserAvtor(getHeaders(),setAvtors(path)))
                .map(getAvtor());
    }


    /************************************订单结算********************************/

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

    /**
     * 确认订单
     * @param params
     * @return
     */
    public static Flowable<BaseResponseBean> getComfirmOrder(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.comfirmOrder(getHeaders(),setParams(params)))
                .map(confirm_order());
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
    public  static  Flowable<List<String>> getHotKeyWords(){
        return apiSubscribe(AndoridApplication.apiService.getHotDatas(getHeaders()))
                .flatMap(getHotWords());
    }
    /**
     * 搜索列表
     */
    public  static  Flowable<List<SearchListBean>> getSearchList(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getSearchList(getHeaders(),params))
                .flatMap(getsearchs());
    }
    /**
     * 获取分享数据
     */
    public static Flowable<ShareBean> getShareInfos(){
        return apiSubscribe(AndoridApplication.apiService.getShareInfos(getHeaders()))
                .map(getShare());
    }
    /******************************分类列表*******************************/
    /**
     * 获取分类列表一级分类
     */
    public  static  Flowable<List<CategryOneBean>> getCategryList(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getCategrys(getHeaders(),params))
                .flatMap(getCategry());
    }
    /**
     * 分类列表数据
     */
    public  static  Flowable<CateBrandGoodsListBean> getCategryLists(Map<String,String> params){
        return apiSubscribe(AndoridApplication.apiService.getCategryList(getHeaders(),params))
                .map(getCatgies());
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

    public  static  Flowable<BaseResponseBean> getCategryList(){
        return apiSubscribe(AndoridApplication.apiService.getCartDatas(getHeaders()))
                .map(getCartList());
    }
    /**
     * 改变购物车商品的数目，即增减删
     */
    public  static  Flowable<CartIdBean> changeCartCount(Map<String,String> params){
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
        String longitude = ShareBankPreferenceUtils.getString("Longitude", null);
        String latitude = ShareBankPreferenceUtils.getString("Latitude", null);
        if (!StringUtils.isEmpty(longitude)){
            lat = longitude;
        }
        if (!StringUtils.isEmpty(latitude)){
            let = latitude;
        }
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
     * 微信
     */
    private static Function<WeiXinToken,WeiXinToken> getWx() {
        return new Function<WeiXinToken, WeiXinToken>() {
            @Override
            public WeiXinToken apply(WeiXinToken response) throws Exception {
                return response;
            }
        };
    }
    private static Function<WXRegisterBean,WXRegisterBean> getWxUser() {
        return new Function<WXRegisterBean, WXRegisterBean>() {
            @Override
            public WXRegisterBean apply(WXRegisterBean response) throws Exception {
                return response;
            }
        };
    }
    /**
     * 注册,登陆
     * @return
     */
    private static Function<BaseResponseBean,RegisterBean> getRegister() {
        return new Function<BaseResponseBean, RegisterBean>() {
            @Override
            public RegisterBean apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (RegisterBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 获取分享数据
     * @return
     */
    private static Function<BaseResponseBean,ShareBean> getShare() {
        return new Function<BaseResponseBean, ShareBean>() {
            @Override
            public ShareBean apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (ShareBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 确认订单
     */
    private static Function<BaseResponseBean,BaseResponseBean> confirm_order() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                return  response;
            }
        };
    }
    /**
     * 确认订单
     */
    private static Function<BaseResponseBean,BaseResponseBean> cancleOrder() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                return  response;
            }
        };
    }
    /**
     * 用户信息
     * @return
     */
    private static Function<BaseResponseBean,PersonInfos> getInfos() {
        return new Function<BaseResponseBean, PersonInfos>() {
            @Override
            public PersonInfos apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (PersonInfos) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 结算
     */
    private static Function<BaseResponseBean,PayBean> getPay() {
        return new Function<BaseResponseBean, PayBean>() {
            @Override
            public PayBean apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean,OrderPrice> getChangePrice() {
        return new Function<BaseResponseBean, OrderPrice>() {
            @Override
            public OrderPrice apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean,CreateOrderBean> getOrder() {
        return new Function<BaseResponseBean, CreateOrderBean>() {
            @Override
            public CreateOrderBean apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean,SdkPayBean> getSdkpay() {
        return new Function<BaseResponseBean, SdkPayBean>() {
            @Override
            public SdkPayBean apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean,OrderdetailsBean> getOrderdetails() {
        return new Function<BaseResponseBean, OrderdetailsBean>() {
            @Override
            public OrderdetailsBean apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<CategryOneBean>>> getCategry() {
        return new Function<BaseResponseBean, Flowable<List<CategryOneBean>>>() {
            @Override
            public Flowable<List<CategryOneBean>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<HomeBanaerBean>>> getHomeBananer() {
        return new Function<BaseResponseBean, Flowable<List<HomeBanaerBean>>>() {
            @Override
            public Flowable<List<HomeBanaerBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<HomeBanaerBean>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 获取国家代码
     */
    private static Function<BaseResponseBean, Flowable<List<CountryCode>>> getCountryCodes() {
        return new Function<BaseResponseBean, Flowable<List<CountryCode>>>() {
            @Override
            public Flowable<List<CountryCode>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<CountryCode>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 获取主页商品分类
     */
    private static Function<BaseResponseBean, Flowable<List<GoodsCategryBean>>> getGoodsCategrys() {
        return new Function<BaseResponseBean, Flowable<List<GoodsCategryBean>>>() {
            @Override
            public Flowable<List<GoodsCategryBean>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<ToolsBarBean>>> getHomeToolsBars() {
        return new Function<BaseResponseBean, Flowable<List<ToolsBarBean>>>() {
            @Override
            public Flowable<List<ToolsBarBean>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<String>>> getHotWords() {
        return new Function<BaseResponseBean, Flowable<List<String>>>() {
            @Override
            public Flowable<List<String>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return  Flowable.fromArray((List<String>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 获取热门搜索关键词列表
     */
    private static Function<BaseResponseBean, Flowable<List<SearchListBean>>> getsearchs() {
        return new Function<BaseResponseBean, Flowable<List<SearchListBean>>>() {
            @Override
            public Flowable<List<SearchListBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return  Flowable.fromArray((List<SearchListBean>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 获取热门搜索关键词列表
     */
    private static Function<BaseResponseBean, CateBrandGoodsListBean> getCatgies() {
        return new Function<BaseResponseBean, CateBrandGoodsListBean>() {
            @Override
            public CateBrandGoodsListBean apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return (CateBrandGoodsListBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 获取购物和总数量
     */
    private static Function<BaseResponseBean,CartNumberBean> getCartNum() {
        return new Function<BaseResponseBean, CartNumberBean>() {
            @Override
            public CartNumberBean apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, BaseResponseBean> getCartList() {
        return new Function<BaseResponseBean,BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
               return response;
            }
        };
    }

    /**
     * 增减购物车商品数量
     */
    private static Function<BaseResponseBean,CartIdBean> changeCartCount() {
        return new Function<BaseResponseBean, CartIdBean>() {
            @Override
            public CartIdBean apply(BaseResponseBean response) throws Exception {
                if (response.getCode() == 200){
                    return (CartIdBean) response.getData();
                }
                return null;
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
    private static Function<BaseResponseBean,GoodsDetailsBean> getGoodsDetails() {
        return new Function<BaseResponseBean, GoodsDetailsBean>() {
            @Override
            public GoodsDetailsBean apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                return (GoodsDetailsBean) response.getData();
            }
        };
    }
    /**
     * 获取商品推荐
     */
    private static Function<BaseResponseBean, Flowable<List<GoodsRecoomendBean>>> getGoodsCommond() {
        return new Function<BaseResponseBean, Flowable<List<GoodsRecoomendBean>>>() {
            @Override
            public Flowable<List<GoodsRecoomendBean>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean,PersonNumber> getNumbers() {
        return new Function<BaseResponseBean,PersonNumber>() {
            @Override
            public PersonNumber apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                return (PersonNumber) response.getData();
            }
        };
    }

    /**
     * 获取收藏列表
     */
    private static Function<BaseResponseBean, Flowable<List<GoodsCollectionsBean>>> getGoodsCollect() {
        return new Function<BaseResponseBean, Flowable<List<GoodsCollectionsBean>>>() {
            @Override
            public Flowable<List<GoodsCollectionsBean>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<CommentDatas>>> getGoodsComments() {
        return new Function<BaseResponseBean, Flowable<List<CommentDatas>>>() {
            @Override
            public Flowable<List<CommentDatas>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<PurchaseOffBean>>> getPurchaseOff() {
        return new Function<BaseResponseBean, Flowable<List<PurchaseOffBean>>>() {
            @Override
            public Flowable<List<PurchaseOffBean>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<AddressManagerBean>>> getAddresses() {
        return new Function<BaseResponseBean, Flowable<List<AddressManagerBean>>>() {
            @Override
            public Flowable<List<AddressManagerBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<AddressManagerBean>) response.getData());
                }
                return  null;
            }
        };
    }
    /**
     * 用户反馈信息
     */
    private static Function<BaseResponseBean,BaseResponseBean> addUserAdavice() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                return response;
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
     * 获取短信验证码
     */
    private static Function<BaseResponseBean,BaseResponseBean> getMessage() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                return response;
            }
        };
    }
    /**
     * 修改个人信息
     */
    private static Function<BaseResponseBean,BaseResponseBean> getPerson() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                return response;
            }
        };
    }
    /**
     * 上传头像
     */
    private static Function<BaseResponseBean,AvtorBean> getAvtor() {
        return new Function<BaseResponseBean, AvtorBean>() {
            @Override
            public AvtorBean apply(BaseResponseBean response) throws Exception {
                Log.i("返回的数据:",response.getCode()+"");
                if (null !=  response && response.getCode() == 200){
                    return (AvtorBean) response.getData();
                }
                return  null;
            }
        };
    }
    /**
     * 重置密码
     */
    private static Function<BaseResponseBean,BaseResponseBean> resetPwd() {
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
    private static Function<BaseResponseBean,AddressManagerBean> getDetails() {
        return new Function<BaseResponseBean, AddressManagerBean>() {
            @Override
            public AddressManagerBean apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<DisCountBean>>> getDisCounts() {
        return new Function<BaseResponseBean, Flowable<List<DisCountBean>>>() {
            @Override
            public Flowable<List<DisCountBean>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<ScoreBean>>> getScores() {
        return new Function<BaseResponseBean, Flowable<List<ScoreBean>>>() {
            @Override
            public Flowable<List<ScoreBean>> apply(BaseResponseBean response) throws Exception {
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
    private static Function<BaseResponseBean, Flowable<List<OrderListBean>>> getOrders() {
        return new Function<BaseResponseBean, Flowable<List<OrderListBean>>>() {
            @Override
            public Flowable<List<OrderListBean>> apply(BaseResponseBean response) throws Exception {
                if ( null !=  response && response.getCode() == 200){
                    return Flowable.fromArray((List<OrderListBean>) response.getData());
                }
                return  null;
            }
        };
    }

    /**
     * 上传图片
     */
    private static Function<BaseResponseBean,BaseResponseBean> changePwd() {
        return new Function<BaseResponseBean, BaseResponseBean>() {
            @Override
            public BaseResponseBean apply(BaseResponseBean response) throws Exception {
                return response;
            }
        };
    }
    /***************************************post添加参数json格式转换**********************************************/
    private static RequestBody setParams(Map<String,String> params){
        return  RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(params));
    }
    /*****************************************post单个上传图片********************************************************************/

    private static List<MultipartBody.Part> setAvtors(String path) {
        File file = new File(path);//filePath 图片地址
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("avtor", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
        List<MultipartBody.Part> parts = builder.build().parts();
        return  parts;
    }
/*****************************************post多个上传图片********************************************************************/




    /************************************线程转换公共部分******************************************************/

    private static Flowable apiSubscribe(Flowable observable){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
