package com.kaifer.fasttwentyone.api.models.res;

import java.util.List;

/**
 * Created by kaifer on 2016. 7. 21..
 */
public class RankingRes {
    private int draw;
    private List<ScoreRes> data;

    public RankingRes(List<ScoreRes> data, int draw) {
        this.data = data;
        this.draw = draw;
    }

    public List<ScoreRes> getData() {
        return data;
    }

    public void setData(List<ScoreRes> data) {
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
