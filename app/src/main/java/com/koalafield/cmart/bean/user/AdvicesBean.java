package com.koalafield.cmart.bean.user;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/6/7.
 */

public class AdvicesBean implements Serializable{

    private String Content;
    private boolean isSelect;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
