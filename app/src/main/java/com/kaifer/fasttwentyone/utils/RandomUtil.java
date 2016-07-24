package com.kaifer.fasttwentyone.utils;

import java.util.Random;

/**
 * Created by kaifer on 2016. 7. 16..
 */
public class RandomUtil {
    static public int randMinMax(Random rand, int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
