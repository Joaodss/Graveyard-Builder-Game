package com.ironhack.charactermodelservice.util;

import com.ironhack.charactermodelservice.enums.Type;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class TypeEnumConverter {

    public static Type convertStringToType(String stringValue) {
        log.info("Converting string {} to type", stringValue);
        for (Type type : Type.values()) {
            if (type.name().equalsIgnoreCase(stringValue))
                return type;
        }
        log.error("Type not found");
        throw new IllegalArgumentException("Invalid type");
    }

}
