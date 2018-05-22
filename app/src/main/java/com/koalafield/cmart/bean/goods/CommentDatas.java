package com.koalafield.cmart.bean.goods;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/22.
 */

public class CommentDatas implements Serializable{

    private String CommmentUserName;
    private int ContentId;
    private  String CommnetUserHeadImg;
    private String Content;
    private String CreatedTime;
    private String Vote;
    private String Country;

    public String getCommmentUserName() {
        return CommmentUserName;
    }

    public void setCommmentUserName(String commmentUserName) {
        CommmentUserName = commmentUserName;
    }

    public int getContentId() {
        return ContentId;
    }

    public void setContentId(int contentId) {
        ContentId = contentId;
    }

    public String getCommnetUserHeadImg() {
        return CommnetUserHeadImg;
    }

    public void setCommnetUserHeadImg(String commnetUserHeadImg) {
        CommnetUserHeadImg = commnetUserHeadImg;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getVote() {
        return Vote;
    }

    public void setVote(String vote) {
        Vote = vote;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    @Override
    public String toString() {
        return "CommentDatas{" +
                "CommmentUserName='" + CommmentUserName + '\'' +
                ", ContentId=" + ContentId +
                ", CommnetUserHeadImg='" + CommnetUserHeadImg + '\'' +
                ", Content='" + Content + '\'' +
                ", CreatedTime='" + CreatedTime + '\'' +
                ", Vote='" + Vote + '\'' +
                ", Country='" + Country + '\'' +
                '}';
    }
}
