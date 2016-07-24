package com.kaifer.fasttwentyone.game;

import com.kaifer.fasttwentyone.utils.RandomUtil;

import java.util.Random;

/**
 * Created by kaifer on 2016. 7. 16..
 */
public class GameStep {
    public GameStep(int goal) {
        this.goal = goal;
        this.opens = new int[2];
        this.choices = new int[3];

        Random rand = new Random();
        int rand1 = RandomUtil.randMinMax(rand, 1, goal);
        int rand2 = RandomUtil.randMinMax(rand, 1, goal);

        while (rand1 == rand2) {
            rand2 = RandomUtil.randMinMax(rand, 1, goal);
        }

        int left, right;
        if(rand1 < rand2) {
            left = rand1;
            right = rand2 - rand1;
            this.answer = goal - rand2;
        } else {
            left = rand2;
            right = rand1 - rand2;
            this.answer = goal - rand1;
        }

        if(rand.nextBoolean()){
            this.opens[0] = left;
            this.opens[1] = right;
        } else {
            this.opens[0] = right;
            this.opens[1] = left;
        }

        int answerIdx = rand.nextInt(3);
        int beforeChoice = -1;
        int delta = goal / 6;
        int min = this.answer - rand.nextInt(delta);
        int max = this.answer + rand.nextInt(delta);

        if(min <= 0) min = 0;
        if(max - min < 2) max = min + 2;

        this.choices[answerIdx] = this.answer;

        for (int i = 0; i < 3; i++) {
            if(answerIdx != i) {
                int choice = RandomUtil.randMinMax(rand, min, max);

                while (choice == beforeChoice || choice == this.answer) {
                    choice = RandomUtil.randMinMax(rand, min, max);
                }
                beforeChoice = choice;
                this.choices[i] = choice;
            }
        }
    }

    public int getAnswer() {
        return answer;
    }

    public int getGoal() {
        return goal;
    }

    public int[] getOpens() {
        return opens;
    }

    public int[] getChoices() {
        return choices;
    }

    private int goal;

    private int[] opens;
    private int[] choices;

    private int answer;
}
