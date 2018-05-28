package com.koalafield.cmart.bean.order;

import com.koalafield.cmart.base.bean.SpecialResponseBean;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/28.
 */

public class SdkPayBean implements Serializable {

    private  String TransactionNo;

    public String getTransactionNo() {
        return TransactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        TransactionNo = transactionNo;
    }
}
