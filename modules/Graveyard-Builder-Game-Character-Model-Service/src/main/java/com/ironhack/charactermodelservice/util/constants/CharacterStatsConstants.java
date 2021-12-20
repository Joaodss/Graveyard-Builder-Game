package com.ironhack.charactermodelservice.util.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CharacterStatsConstants {

    // Base Character stats
    public static final double BASE_PASSIVE_CHANCE = 0.25;
    public static final long BASE_EXPERIENCE = 0;
    public static final int BASE_LEVEL = 0;

    // Base Warrior stats
    public static final int BASE_WARRIOR_HEALTH = 120;
    public static final int BASE_WARRIOR_STAMINA = 60;
    public static final int BASE_WARRIOR_STRENGTH = 5;
    public static final int INCREASE_VALUE_WARRIOR_HEALTH = 12;
    public static final int INCREASE_VALUE_WARRIOR_STAMINA = 10;
    public static final int INCREASE_VALUE_WARRIOR_STRENGTH = 2;

    // Base Archer stats
    public static final int BASE_ARCHER_HEALTH = 80;
    public static final int BASE_ARCHER_STAMINA = 40;
    public static final int BASE_ARCHER_ACCURACY = 5;
    public static final int INCREASE_VALUE_ARCHER_HEALTH = 8;
    public static final int INCREASE_VALUE_ARCHER_STAMINA = 8;
    public static final int INCREASE_VALUE_ARCHER_ACCURACY = 2;

    // Base Mage stats
    public static final int BASE_MAGE_HEALTH = 80;
    public static final int BASE_MAGE_MANA = 10;
    public static final int BASE_MAGE_INTELLIGENCE = 5;
    public static final int INCREASE_VALUE_MAGE_HEALTH = 7;
    public static final int INCREASE_VALUE_MAGE_MANA = 5;
    public static final int INCREASE_VALUE_MAGE_INTELLIGENCE = 2;


}
