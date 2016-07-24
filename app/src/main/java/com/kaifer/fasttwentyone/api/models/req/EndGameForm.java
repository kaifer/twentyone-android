package com.kaifer.fasttwentyone.api.models.req;

/**
 * Created by kaifer on 2016. 7. 17..
 */
public class EndGameForm {
    private Long score;
    private Long step;

    public EndGameForm() {

    }

    public EndGameForm(Long score, Long step) {
        this.score = score;
        this.step = step;
    }
}
