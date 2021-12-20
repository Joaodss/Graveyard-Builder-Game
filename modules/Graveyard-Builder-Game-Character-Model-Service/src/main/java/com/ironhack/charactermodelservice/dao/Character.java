package com.ironhack.charactermodelservice.dao;

import com.ironhack.charactermodelservice.dto.NewCharacterDTO;
import com.ironhack.charactermodelservice.enums.Type;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

import static com.ironhack.charactermodelservice.util.TypeEnumConverter.convertStringToType;
import static com.ironhack.charactermodelservice.util.constants.CharacterStatsConstants.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "characters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
public class Character {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_id")
    private String userUsername;

    @Column(name = "type")
    private Type type;

    @Column(name = "is_alive")
    private Boolean isAlive;

    @Column(name = "death_time")
    private Instant deathTime;

    @Column(name = "name")
    private String name;

    @Column(name = "picture_url")
    private String pictureURL;

    @Column(name = "level")
    private Integer level;

    @Column(name = "experience")
    private Long experience;

    // -------------------- Stats --------------------
    @Column(name = "max_health")
    private Integer maxHealth;

    @Column(name = "current_health")
    private Integer currentHealth;

    @Column(name = "passive_chance")
    private Double passiveChance;

    // ---------- Warrior and Archer ----------
    @Column(name = "max_stamina")
    private Integer maxStamina;

    @Column(name = "current_stamina")
    private Integer currentStamina;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "accuracy")
    private Integer accuracy;

    // ---------- Mage ----------
    @Column(name = "max_mana")
    private Integer maxMana;

    @Column(name = "current_mana")
    private Integer currentMana;

    @Column(name = "intelligence")
    private Integer intelligence;


    // -------------------- Custom Constructor --------------------
    public Character(String userUsername,
                     Type type,
                     Boolean isAlive,
                     Instant deathTime,
                     String name,
                     String pictureURL,
                     Integer level,
                     Long experience,
                     Integer maxHealth,
                     Integer currentHealth,
                     Double passiveChance,
                     Integer maxStamina,
                     Integer currentStamina,
                     Integer strength,
                     Integer accuracy,
                     Integer maxMana,
                     Integer currentMana,
                     Integer intelligence
    ) {
        this.userUsername = userUsername;
        this.type = type;
        this.isAlive = isAlive;
        this.deathTime = deathTime;
        this.name = name;
        this.pictureURL = pictureURL;
        this.level = level;
        this.experience = experience;
        this.passiveChance = passiveChance;
        switch (type) {
            case WARRIOR -> {
                this.maxHealth = maxHealth;
                this.currentHealth = currentHealth;
                this.maxStamina = maxStamina;
                this.currentStamina = currentStamina;
                this.strength = strength;
            }
            case ARCHER -> {
                this.maxHealth = maxHealth;
                this.currentHealth = currentHealth;
                this.maxStamina = maxStamina;
                this.currentStamina = currentStamina;
                this.accuracy = accuracy;
            }
            case MAGE -> {
                this.maxHealth = maxHealth;
                this.currentHealth = currentHealth;
                this.maxMana = maxMana;
                this.currentMana = currentMana;
                this.intelligence = intelligence;
            }
        }
        log.info("New Character created -> {}", this);
    }

    public Character(String userUsername, Type type, String name, String pictureURL) {
        this.userUsername = userUsername;
        this.type = type;
        this.isAlive = true;
        this.deathTime = null;
        this.name = name;
        this.pictureURL = pictureURL;
        this.level = BASE_LEVEL;
        this.experience = BASE_EXPERIENCE;
        this.passiveChance = BASE_PASSIVE_CHANCE;
        switch (type) {
            case WARRIOR -> {
                this.maxHealth = BASE_WARRIOR_HEALTH;
                this.currentHealth = BASE_WARRIOR_HEALTH;
                this.maxStamina = BASE_WARRIOR_STAMINA;
                this.currentStamina = BASE_WARRIOR_STAMINA;
                this.strength = BASE_WARRIOR_STRENGTH;
            }
            case ARCHER -> {
                this.maxHealth = BASE_ARCHER_HEALTH;
                this.currentHealth = BASE_ARCHER_HEALTH;
                this.maxStamina = BASE_ARCHER_STAMINA;
                this.currentStamina = BASE_ARCHER_STAMINA;
                this.accuracy = BASE_ARCHER_ACCURACY;
            }
            case MAGE -> {
                this.maxHealth = BASE_MAGE_HEALTH;
                this.currentHealth = BASE_MAGE_HEALTH;
                this.maxMana = BASE_MAGE_MANA;
                this.currentMana = BASE_MAGE_MANA;
                this.intelligence = BASE_MAGE_INTELLIGENCE;
            }
        }
        log.info("New Character created -> {}", this);
    }

    public Character(NewCharacterDTO newCharacterDTO) {
        this.userUsername = newCharacterDTO.getUserUsername();
        this.type = convertStringToType(newCharacterDTO.getType());
        this.isAlive = true;
        this.deathTime = null;
        this.name = newCharacterDTO.getName();
        this.pictureURL = newCharacterDTO.getPictureURL() != null ?
                newCharacterDTO.getPictureURL() :
                "";
        this.level = BASE_LEVEL;
        this.experience = BASE_EXPERIENCE;
        this.passiveChance = BASE_PASSIVE_CHANCE;
        switch (type) {
            case WARRIOR -> {
                this.maxHealth = BASE_WARRIOR_HEALTH;
                this.currentHealth = BASE_WARRIOR_HEALTH;
                this.maxStamina = BASE_WARRIOR_STAMINA;
                this.currentStamina = BASE_WARRIOR_STAMINA;
                this.strength = BASE_WARRIOR_STRENGTH;
            }
            case ARCHER -> {
                this.maxHealth = BASE_ARCHER_HEALTH;
                this.currentHealth = BASE_ARCHER_HEALTH;
                this.maxStamina = BASE_ARCHER_STAMINA;
                this.currentStamina = BASE_ARCHER_STAMINA;
                this.accuracy = BASE_ARCHER_ACCURACY;
            }
            case MAGE -> {
                this.maxHealth = BASE_MAGE_HEALTH;
                this.currentHealth = BASE_MAGE_HEALTH;
                this.maxMana = BASE_MAGE_MANA;
                this.currentMana = BASE_MAGE_MANA;
                this.intelligence = BASE_MAGE_INTELLIGENCE;
            }
        }
        log.info("New Character created -> {}", this);
    }


    // -------------------- Equals and HashCode --------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(id, character.id) &&
                Objects.equals(userUsername, character.userUsername) &&
                type == character.type &&
                Objects.equals(isAlive, character.isAlive) &&
                Objects.equals(deathTime, character.deathTime) &&
                Objects.equals(name, character.name) &&
                Objects.equals(pictureURL, character.pictureURL) &&
                Objects.equals(level, character.level) &&
                Objects.equals(experience, character.experience) &&
                Objects.equals(maxHealth, character.maxHealth) &&
                Objects.equals(currentHealth, character.currentHealth) &&
                Objects.equals(passiveChance, character.passiveChance) &&
                Objects.equals(maxStamina, character.maxStamina) &&
                Objects.equals(currentStamina, character.currentStamina) &&
                Objects.equals(strength, character.strength) &&
                Objects.equals(accuracy, character.accuracy) &&
                Objects.equals(maxMana, character.maxMana) &&
                Objects.equals(currentMana, character.currentMana) &&
                Objects.equals(intelligence, character.intelligence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                userUsername,
                type,
                isAlive,
                deathTime,
                name,
                pictureURL,
                level,
                experience,
                maxHealth,
                currentHealth,
                passiveChance,
                maxStamina,
                currentStamina,
                strength,
                accuracy,
                maxMana,
                currentMana,
                intelligence
        );
    }

}
