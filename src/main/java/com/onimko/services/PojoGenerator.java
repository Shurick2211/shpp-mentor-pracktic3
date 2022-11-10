package com.onimko.services;

import com.onimko.pojo.PojoMessage;

import java.time.LocalDateTime;
import java.util.Random;

public class PojoGenerator {
    private static int count = 1;
    private static final int MIN_SIZE = 1;
    private static final int DELTA_NAME_SIZE = 15;
    private static final int DELTA_ALPHABET = 26;
    private static final int START_ALPHABET = 'a';
    private static final Random random = new Random();

    private PojoGenerator() {
    }

    public static PojoMessage getPojo(){
        return new PojoMessage(genName(), genCount(), genDataTime());
    }

    private static LocalDateTime genDataTime() {
        final int year = 2050;
        final int month = 12;
        final int day = 28;
        final int hour = 24;
        final int minutes = 60;
        
        return LocalDateTime.of(random.nextInt(year),
                MIN_SIZE + random.nextInt(month - MIN_SIZE),
                MIN_SIZE + random.nextInt(day - MIN_SIZE),
                MIN_SIZE + random.nextInt(hour - MIN_SIZE),
                MIN_SIZE + random.nextInt(minutes - MIN_SIZE));
    }

    private static int genCount() {
        return count++;
    }

    private static String genName() {
        StringBuilder name = new StringBuilder("");
        int size = MIN_SIZE + random.nextInt(DELTA_NAME_SIZE);
        for (int i = 0; i < size; i++) {
            char ch = (char) (START_ALPHABET +random.nextInt(DELTA_ALPHABET));
            name.append(random.nextBoolean() ? ch : String.valueOf(ch).toUpperCase());
        }
        return name.toString();
    }
}
