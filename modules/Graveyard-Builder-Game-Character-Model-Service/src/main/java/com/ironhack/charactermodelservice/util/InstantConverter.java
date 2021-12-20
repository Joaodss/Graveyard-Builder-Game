package com.ironhack.charactermodelservice.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class InstantConverter {

    public static String convertInstantToString(Instant instant) {
        if (instant == null) return null;
        log.info("Converting Instant to String");
        return instant.toString();
    }

    public static Instant convertStringToInstant(String string) {
        if (string == null) return null;
        log.info("Converting String to Instant");
        return Instant.parse(string).atZone(ZoneId.systemDefault()).toInstant();
    }

}
