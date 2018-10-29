package com.pq.api.type;

/**
 * fund
 * com.hn.fund.dto;
 *
 * @author liken
 * @date 15/3/13
 */
public enum ServiceErrorCode {

    SERVER_ERROR("0000"),
    RED_MAX_LIMITED("0001"),
    RED_MIN_LIMITED("0002"),
    PURCHASE_EXCEED_LIMIT("0005"),
    CARD_MUST_AUTH("0003"),
    CARD_NOT_AUTH("0004"),
    ACCOUNT_DISABLED("0009");

    private String errorCode;

    private ServiceErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
