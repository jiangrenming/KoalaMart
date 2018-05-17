package com.koalafield.cmart.bean.home;

import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;

/**
 * Created by jiangrenming on 2018/5/14.
 */

public class HomeBanaerBean extends SpecialResponseBean {

    private int DataId;
    private String Img;
    private String Name;
    private String TypeName;

    public int getDataId() {
        return DataId;
    }

    public void setDataId(int dataId) {
        DataId = dataId;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    @Override
    public String toString() {
        return "HomeBanaerBean{" +
                "DataId=" + DataId +
                ", Img='" + Img + '\'' +
                ", Name='" + Name + '\'' +
                ", TypeName='" + TypeName + '\'' +
                '}';
    }
}
