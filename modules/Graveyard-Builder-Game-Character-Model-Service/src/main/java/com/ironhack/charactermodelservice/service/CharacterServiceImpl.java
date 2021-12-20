package com.ironhack.charactermodelservice.service;

import com.ironhack.charactermodelservice.dao.Character;
import com.ironhack.charactermodelservice.dto.CharacterDTO;
import com.ironhack.charactermodelservice.dto.LevelUpDTO;
import com.ironhack.charactermodelservice.dto.NewCharacterDTO;
import com.ironhack.charactermodelservice.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.ironhack.charactermodelservice.util.InstantConverter.convertStringToInstant;
import static com.ironhack.charactermodelservice.util.constants.CharacterStatsConstants.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;


    // -------------------- Get methods --------------------
    public List<CharacterDTO> getAllCharacters() {
        log.info("Getting all characters");
        var storedCharacters = characterRepository.findAll();
        return storedCharacters.stream().
                map(CharacterDTO::new).
                collect(toList());
    }

    public CharacterDTO getCharacterById(Long id) {
        log.info("Getting character with id: {}", id);
        var storedCharacter = characterRepository.findById(id);
        return storedCharacter
                .map(CharacterDTO::new)
                .orElse(null);
    }

    public List<CharacterDTO> getAliveCharactersByUserUsername(String username) {
        log.info("Getting all alive characters by user with username: {}", username);
        var storedCharacters = characterRepository.findAllByUserUsernameAndIsAlive(username, true);
        return storedCharacters.stream().
                map(CharacterDTO::new).
                collect(toList());
    }

    public List<CharacterDTO> getDeadCharactersByUserUsername(String username) {
        log.info("Getting all dead characters by user with username: {}", username);
        var storedCharacters = characterRepository.findAllByUserUsernameAndIsAlive(username, false);
        return storedCharacters.stream().
                map(CharacterDTO::new).
                collect(toList());
    }


    // -------------------- Create methods --------------------
    public CharacterDTO createCharacter(NewCharacterDTO newCharacterDTO) {
        log.info("Creating character with name: {}", newCharacterDTO.getName());
        var savedCharacter = characterRepository.save(new Character(newCharacterDTO));
        log.info("Character saved with id: {}", savedCharacter.getId());
        return new CharacterDTO(savedCharacter);
    }


    // -------------------- Update methods --------------------
    public CharacterDTO updateCharacter(CharacterDTO updateCharacterDTO) {
        log.info("Updating character with id: {}", updateCharacterDTO.getId());

        var storedCharacter = characterRepository.findById(updateCharacterDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));

        if (updateCharacterDTO.getIsAlive() != null) {
            storedCharacter.setIsAlive(updateCharacterDTO.getIsAlive());
            if (updateCharacterDTO.getIsAlive()) storedCharacter.setDeathTime(null);
        }
        if (updateCharacterDTO.getDeathTime() != null)
            storedCharacter.setDeathTime(convertStringToInstant(updateCharacterDTO.getDeathTime()));
        if (updateCharacterDTO.getName() != null)
            storedCharacter.setName(updateCharacterDTO.getName());
        if (updateCharacterDTO.getPictureURL() != null)
            storedCharacter.setPictureURL(updateCharacterDTO.getPictureURL());
        if (updateCharacterDTO.getLevel() != null)
            storedCharacter.setLevel(updateCharacterDTO.getLevel());
        if (updateCharacterDTO.getExperience() != null)
            storedCharacter.setExperience(updateCharacterDTO.getExperience());
        // -------------------- Stats --------------------
        if (updateCharacterDTO.getMaxHealth() != null)
            storedCharacter.setMaxHealth(updateCharacterDTO.getMaxHealth());
        if (updateCharacterDTO.getCurrentHealth() != null)
            storedCharacter.setCurrentHealth(updateCharacterDTO.getCurrentHealth());
        if (updateCharacterDTO.getPassiveChance() != null)
            storedCharacter.setPassiveChance(updateCharacterDTO.getPassiveChance());
        // ---------- Warrior and Archer ----------
        if (updateCharacterDTO.getMaxStamina() != null)
            storedCharacter.setMaxStamina(updateCharacterDTO.getMaxStamina());
        if (updateCharacterDTO.getCurrentStamina() != null)
            storedCharacter.setCurrentStamina(updateCharacterDTO.getCurrentStamina());
        if (updateCharacterDTO.getStrength() != null)
            storedCharacter.setStrength(updateCharacterDTO.getStrength());
        if (updateCharacterDTO.getAccuracy() != null)
            storedCharacter.setAccuracy(updateCharacterDTO.getAccuracy());
        // ---------- Mage ----------
        if (updateCharacterDTO.getMaxMana() != null)
            storedCharacter.setMaxMana(updateCharacterDTO.getMaxMana());
        if (updateCharacterDTO.getCurrentMana() != null)
            storedCharacter.setCurrentMana(updateCharacterDTO.getCurrentMana());
        if (updateCharacterDTO.getIntelligence() != null)
            storedCharacter.setIntelligence(updateCharacterDTO.getIntelligence());

        log.info("Character updated");
        return new CharacterDTO(characterRepository.save(storedCharacter));
    }

    public CharacterDTO levelUpCharacter(LevelUpDTO levelUpDTO) {
        log.info("Leveling up character with id: {}", levelUpDTO.getId());

        var storedCharacter = characterRepository.findById(levelUpDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));

        storedCharacter.setLevel(storedCharacter.getLevel() + 1);
        storedCharacter.setExperience(0L);

        switch (storedCharacter.getType()) {
            case WARRIOR -> {
                storedCharacter.setMaxHealth(storedCharacter.getMaxHealth() +
                        (INCREASE_VALUE_WARRIOR_HEALTH * levelUpDTO.getHealthPoints()));
                storedCharacter.setMaxStamina(storedCharacter.getMaxStamina() +
                        (INCREASE_VALUE_WARRIOR_STAMINA * levelUpDTO.getEnergyPoints()));
                storedCharacter.setStrength(storedCharacter.getStrength() +
                        (INCREASE_VALUE_WARRIOR_STRENGTH * levelUpDTO.getAttackPoints()));
            }
            case ARCHER -> {
                storedCharacter.setMaxHealth(storedCharacter.getMaxHealth() +
                        (INCREASE_VALUE_ARCHER_HEALTH * levelUpDTO.getHealthPoints()));
                storedCharacter.setMaxStamina(storedCharacter.getMaxStamina() +
                        (INCREASE_VALUE_ARCHER_STAMINA * levelUpDTO.getEnergyPoints()));
                storedCharacter.setAccuracy(storedCharacter.getAccuracy() +
                        (INCREASE_VALUE_ARCHER_ACCURACY * levelUpDTO.getAttackPoints()));
            }
            case MAGE -> {
                storedCharacter.setMaxHealth(storedCharacter.getMaxHealth() +
                        (INCREASE_VALUE_MAGE_HEALTH * levelUpDTO.getHealthPoints()));
                storedCharacter.setMaxMana(storedCharacter.getMaxMana() +
                        (INCREASE_VALUE_MAGE_MANA * levelUpDTO.getEnergyPoints()));
                storedCharacter.setIntelligence(storedCharacter.getIntelligence() +
                        (INCREASE_VALUE_MAGE_INTELLIGENCE * levelUpDTO.getAttackPoints()));
            }
        }
        storedCharacter.setCurrentHealth(storedCharacter.getMaxHealth());
        storedCharacter.setCurrentStamina(storedCharacter.getMaxStamina());
        storedCharacter.setCurrentMana(storedCharacter.getMaxMana());

        log.info("Character leveled up");
        return new CharacterDTO(characterRepository.save(storedCharacter));
    }

    // -------------------- Delete methods --------------------
    public void deleteCharacter(Long id) {
        log.info("Deleting character with id: {}", id);
        var storedCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));
        characterRepository.delete(storedCharacter);
        log.info("Character deleted");
    }

}
