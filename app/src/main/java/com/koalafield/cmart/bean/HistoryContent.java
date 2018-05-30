package com.koalafield.cmart.bean;

import com.koalafield.cmart.db.dao.Column;

/**
 * Created by jiangrenming on 2018/5/30.
 */

public class HistoryContent {
    @Column(name = "ID", primaryKey = true)
    private Long id;
    /** 用户密码 */
    @Column(name = "CONTENT")
    private String historyContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHistoryContent() {
        return historyContent;
    }

    public void setHistoryContent(String historyContent) {
        this.historyContent = historyContent;
    }
}
