package com.koalafield.cmart.bean;

import com.koalafield.cmart.db.dao.Column;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 */

public class User {


    @Column(name = "ID", primaryKey = true)
    private Long id;
    /** 用户名 */
    @Column(name = "USER_NAME")
    private String userName;
    /** 用户密码 */
    @Column(name = "PASSWORD")
    private String password;
    /**用户ID*/
    @Column(name = "USER_ID")
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
