package com.kaifer.fasttwentyone.api.models.res;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class ScoreRes {
    private Long userId;
    private String userName;
    private Long score;
    private Long step;

    public ScoreRes(Long userId, String userName, Long score, Long step) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
        this.step = step;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getScore() {
        return score;
    }

    public Long getStep() {
        return step;
    }
}
