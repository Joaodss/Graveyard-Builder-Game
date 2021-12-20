package com.ironhack.charactermodelservice.util;

import org.junit.jupiter.api.Test;

import static com.ironhack.charactermodelservice.enums.Type.WARRIOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TypeEnumConverterTest {

    // Test Convert String to TypeEnum - valid Enum
    @Test
    void convertStringToTypeEnum_validEnum() {
        var stringValue = "Warrior";
        var typeEnum = TypeEnumConverter.convertStringToType(stringValue);
        assertEquals(WARRIOR, typeEnum);
    }

    // Test Convert String to TypeEnum - invalid Enum
    @Test
    void convertStringToTypeEnum_invalidEnum() {
        var stringValue = "Wizard";
        assertThrows(IllegalArgumentException.class, () -> TypeEnumConverter.convertStringToType(stringValue));
    }

}
