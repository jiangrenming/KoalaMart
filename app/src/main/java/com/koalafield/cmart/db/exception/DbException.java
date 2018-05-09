package com.koalafield.cmart.db.exception;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 */

public class DbException extends BaseException {

    private static final long serialVersionUID = 1L;

    public DbException() {
    }

    public DbException(String detailMessage) {
        super(detailMessage);
    }

    public DbException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DbException(Throwable throwable) {
        super(throwable);
    }
}
