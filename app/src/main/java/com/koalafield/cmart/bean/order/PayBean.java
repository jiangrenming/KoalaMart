package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public class PayBean implements Serializable{

    private boolean AllowUseScore;
    private int AvailableScore;
    private  int TotalWeight;

    private List<Payment> PaymentDTOList;

    private List<Delivery> DeliveryListDTO;

    private List<ShoppingCart>ShoppingCartDTOList;

    private OrderPrice OrderPriceDTO;


    public boolean isAllowUseScore() {
        return AllowUseScore;
    }

    public void setAllowUseScore(boolean allowUseScore) {
        AllowUseScore = allowUseScore;
    }

    public int getAvailableScore() {
        return AvailableScore;
    }

    public void setAvailableScore(int availableScore) {
        AvailableScore = availableScore;
    }

    public int getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        TotalWeight = totalWeight;
    }

    public List<Payment> getPaymentDTOList() {
        return PaymentDTOList;
    }

    public void setPaymentDTOList(List<Payment> paymentDTOList) {
        PaymentDTOList = paymentDTOList;
    }

    public List<Delivery> getDeliveryListDTO() {
        return DeliveryListDTO;
    }

    public void setDeliveryListDTO(List<Delivery> deliveryListDTO) {
        DeliveryListDTO = deliveryListDTO;
    }

    public List<ShoppingCart> getShoppingCartDTOList() {
        return ShoppingCartDTOList;
    }

    public void setShoppingCartDTOList(List<ShoppingCart> shoppingCartDTOList) {
        ShoppingCartDTOList = shoppingCartDTOList;
    }

    public OrderPrice getOrderPriceDTO() {
        return OrderPriceDTO;
    }

    public void setOrderPriceDTO(OrderPrice orderPriceDTO) {
        OrderPriceDTO = orderPriceDTO;
    }
}
