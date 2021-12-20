package com.ironhack.charactermodelservice.dto;

import com.ironhack.charactermodelservice.dao.Character;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static com.ironhack.charactermodelservice.util.InstantConverter.convertInstantToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Slf4j
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


    // -------------------- Custom Constructor --------------------
    public CharacterDTO(Character character) {
        this.id = character.getId();
        this.userUsername = character.getUserUsername();
        this.type = character.getType().name();
        this.isAlive = character.getIsAlive();
        this.deathTime = convertInstantToString(character.getDeathTime());
        this.name = character.getName();
        this.pictureURL = character.getPictureURL();
        this.level = character.getLevel();
        this.experience = character.getExperience();
        this.maxHealth = character.getMaxHealth();
        this.currentHealth = character.getCurrentHealth();
        this.passiveChance = character.getPassiveChance();
        this.maxStamina = character.getMaxStamina();
        this.currentStamina = character.getCurrentStamina();
        this.strength = character.getStrength();
        this.accuracy = character.getAccuracy();
        this.maxMana = character.getMaxMana();
        this.currentMana = character.getCurrentMana();
        this.intelligence = character.getIntelligence();
        log.info("CharacterDTO created for returning details");
    }

}
