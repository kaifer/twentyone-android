package com.kaifer.fasttwentyone.game;

import com.kaifer.fasttwentyone.utils.RandomUtil;

import java.util.Random;

/**
 * Created by kaifer on 2016. 7. 16..
 */
public class Dealer {
    public Dealer() {
        this.step = 1L;
        this.score = 0L;
        this.life = 5;
    }

    public GameStep getGameStep() {
        return this.gameStep;
    }

    public void generateGameStep() {
        this.gameStep = new GameStep(getGoal());
    }

    public boolean checkAnswer(int inp) {
        return inp == this.gameStep.getAnswer();
    }

    public boolean useLife() {
        this.life -= 1;
        return this.life > 0;
    }

    public void nextStep() {
        this.score += this.step * (5 + this.life);
        this.step += 1;
    }

    public int getTime() {
        if (step < 20) {
            return 5;
        } else if (step < 50) {
            return 4;
        } else if (step < 100) {
            return 3;
        } else if (step < 200) {
            return 2;
        } else {
            return 1;
        }
    }

    public Long getStep() {
        return step;
    }

    public Long getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public int getGoal() {
        if (step < 40) {
            return 21;
        } else if (step < 70) {
            return 42;
        } else if (step < 150) {
            return 84;
        } else if (step < 250) {
            return 168;
        } else {
            Random rand = new Random();
            return RandomUtil.randMinMax(rand, 168, 1000);
        }
    }

    private Long step;
    private Long score;
    private int life;
    private GameStep gameStep;
}
