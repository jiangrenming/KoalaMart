package com.koalafield.cmart.bean.goods;

import com.koalafield.cmart.base.bean.SpecialResponseBean;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public class GoodsDetailsBean extends SpecialResponseBean {

    private List<GoodsItem> ColorList;
    private List<GoodsItem> SizeList;

    private List<GoodsItem> MaterialList;
    private List<GoodsItem> TypeList;

    private List<GoodsItem> WeightList;
    private String[] ImageList;
    private  int Id;
    private String Name;
    private  String ShortName;
    //商品详情
    private  String ContentHTML;
    //有关评论的
    private boolean CanComment;
    private int  Comment;
    private  int Following;
    private  int Viewed;
    private String PublishTime;
    private String GoodType;
    private boolean FreeDelivery;
    private int GiveScore;
    private int Availablescore;

    private String LinkGoods;
    private  String Sku;
    //是否是会员
    private  String DiscountType;
    private   String Discount;
    private int MinPurchase;
    private int MaxBookingCustomer;
    //货币相关
    private String Currency;
    private double CurrencyRate;
    private double CurrentPrice;
    private double OriginalPrice;
    //销量
    private boolean Favorite;
    private String SalesCount ;

    public List<GoodsItem> getColorList() {
        return ColorList;
    }

    public void setColorList(List<GoodsItem> colorList) {
        ColorList = colorList;
    }

    public List<GoodsItem> getSizeList() {
        return SizeList;
    }

    public void setSizeList(List<GoodsItem> sizeList) {
        SizeList = sizeList;
    }

    public List<GoodsItem> getMaterialList() {
        return MaterialList;
    }

    public void setMaterialList(List<GoodsItem> materialList) {
        MaterialList = materialList;
    }

    public List<GoodsItem> getTypeList() {
        return TypeList;
    }

    public void setTypeList(List<GoodsItem> typeList) {
        TypeList = typeList;
    }

    public List<GoodsItem> getWeightList() {
        return WeightList;
    }

    public void setWeightList(List<GoodsItem> weightList) {
        WeightList = weightList;
    }

    public String[] getImageList() {
        return ImageList;
    }

    public void setImageList(String[] imageList) {
        ImageList = imageList;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getContentHTML() {
        return ContentHTML;
    }

    public void setContentHTML(String contentHTML) {
        ContentHTML = contentHTML;
    }

    public boolean isCanComment() {
        return CanComment;
    }

    public void setCanComment(boolean canComment) {
        CanComment = canComment;
    }

    public int getComment() {
        return Comment;
    }

    public void setComment(int comment) {
        Comment = comment;
    }

    public int getFollowing() {
        return Following;
    }

    public void setFollowing(int following) {
        Following = following;
    }

    public int getViewed() {
        return Viewed;
    }

    public void setViewed(int viewed) {
        Viewed = viewed;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public String getGoodType() {
        return GoodType;
    }

    public void setGoodType(String goodType) {
        GoodType = goodType;
    }

    public boolean isFreeDelivery() {
        return FreeDelivery;
    }

    public void setFreeDelivery(boolean freeDelivery) {
        FreeDelivery = freeDelivery;
    }

    public int getGiveScore() {
        return GiveScore;
    }

    public void setGiveScore(int giveScore) {
        GiveScore = giveScore;
    }

    public int getAvailablescore() {
        return Availablescore;
    }

    public void setAvailablescore(int availablescore) {
        Availablescore = availablescore;
    }

    public String getLinkGoods() {
        return LinkGoods;
    }

    public void setLinkGoods(String linkGoods) {
        LinkGoods = linkGoods;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public int getMinPurchase() {
        return MinPurchase;
    }

    public void setMinPurchase(int minPurchase) {
        MinPurchase = minPurchase;
    }

    public int getMaxBookingCustomer() {
        return MaxBookingCustomer;
    }

    public void setMaxBookingCustomer(int maxBookingCustomer) {
        MaxBookingCustomer = maxBookingCustomer;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public double getCurrencyRate() {
        return CurrencyRate;
    }

    public void setCurrencyRate(double currencyRate) {
        CurrencyRate = currencyRate;
    }

    public double getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        CurrentPrice = currentPrice;
    }

    public double getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        OriginalPrice = originalPrice;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public String getSalesCount() {
        return SalesCount;
    }

    public void setSalesCount(String salesCount) {
        SalesCount = salesCount;
    }
}
