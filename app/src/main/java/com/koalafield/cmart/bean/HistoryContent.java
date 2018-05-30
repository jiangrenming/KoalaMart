package com.koalafield.cmart.bean;

import com.koalafield.cmart.db.dao.Column;
import com.koalafield.cmart.db.dao.Table;

/**
 * Created by jiangrenming on 2018/5/30.
 */
@Table(name="T_HOSTORY_KEY")
public class HistoryContent {

    @Column(name = "ID", primaryKey = true)
    private Long id;
    /** 用户密码 */
    @Column(name = "CONTENT")
    private String content;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
