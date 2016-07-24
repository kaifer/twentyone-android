package com.kaifer.fasttwentyone.api.models.res;

import java.sql.Timestamp;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class TokenRes {
    private String token;
    private Long userId;
    private Long expiredAt;

    public TokenRes() {
    }

    public TokenRes(String token, Long userId, Long expiredAt) {
        this.token = token;
        this.userId = userId;
        this.expiredAt = expiredAt;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public Timestamp getExpiredAt() {
        return new Timestamp(expiredAt);
    }
}