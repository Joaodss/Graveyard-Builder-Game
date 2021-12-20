package com.ironhack.opponentselectionservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CharacterDTO {

    private Long id;
    private String userUsername;
    private String type;
    private Boolean isAlive;
    private String deathTime;
    private String name;
    private String pictureURL;
    private Integer level;
    private Long experience;

    // -------------------- Stats --------------------
    private Integer maxHealth;
    private Integer currentHealth;
    private Double passiveChance;

    // ---------- Warrior and Archer ----------
    private Integer maxStamina;
    private Integer currentStamina;
    private Integer strength;
    private Integer accuracy;

    // ---------- Mage ----------
    private Integer maxMana;
    private Integer currentMana;
    private Integer intelligence;

}
