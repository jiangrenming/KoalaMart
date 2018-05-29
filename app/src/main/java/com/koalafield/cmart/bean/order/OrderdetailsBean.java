package com.koalafield.cmart.bean.order;

import com.koalafield.cmart.bean.user.AddressManagerBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/29.
 */

public class OrderdetailsBean implements Serializable{

    List<Payment> PaymentDTOList;
    OrderPrice OrderPrice;
    OrderAdress AddressDTO;
    OrderDelivery Delivery;
    OrderDetailsInfo DetailsInfo;

    public List<Payment> getPaymentDTOList() {
        return PaymentDTOList;
    }

    public void setPaymentDTOList(List<Payment> paymentDTOList) {
        PaymentDTOList = paymentDTOList;
    }

    public com.koalafield.cmart.bean.order.OrderPrice getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(com.koalafield.cmart.bean.order.OrderPrice orderPrice) {
        OrderPrice = orderPrice;
    }

    public OrderAdress getAddressDTO() {
        return AddressDTO;
    }

    public void setAddressDTO(OrderAdress addressDTO) {
        AddressDTO = addressDTO;
    }

    public OrderDelivery getDelivery() {
        return Delivery;
    }

    public void setDelivery(OrderDelivery delivery) {
        Delivery = delivery;
    }

    public OrderDetailsInfo getDetailsInfo() {
        return DetailsInfo;
    }

    public void setDetailsInfo(OrderDetailsInfo detailsInfo) {
        DetailsInfo = detailsInfo;
    }
}
