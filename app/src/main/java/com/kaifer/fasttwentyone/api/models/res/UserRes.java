package com.kaifer.fasttwentyone.api.models.res;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class UserRes {
    private Long userId;
    private String userName;
    private Long score;
    private Long step;

    public UserRes(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public UserRes(Long userId, String userName, Long score, Long step) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
        this.step = step;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }
}
