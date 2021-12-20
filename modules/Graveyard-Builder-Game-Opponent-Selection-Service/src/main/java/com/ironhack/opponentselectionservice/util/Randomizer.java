package com.ironhack.opponentselectionservice.util;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Randomizer {
    private static final Faker faker = new Faker();

    public static <T> T getRandom(T[] array) {
        return array[faker.random().nextInt(array.length)];
    }

    public static <T> T getRandom(List<T> list) {
        return list.get(faker.random().nextInt(list.size()));
    }

    public static int getRandom(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

}
