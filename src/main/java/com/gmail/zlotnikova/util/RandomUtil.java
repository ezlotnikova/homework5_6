package com.gmail.zlotnikova.util;

import java.util.Random;

public class RandomUtil {

    private static Random random = new Random();

    public static int getElement(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

}