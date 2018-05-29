package com.koalafield.cmart.bean.home;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/30.
 */

public class ToolsBarBean implements Serializable{

    private String DataId;
    private String Img;
    private String Name;
    private String TypeName;

    public String getDataId() {
        return DataId;
    }

    public void setDataId(String dataId) {
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
}
