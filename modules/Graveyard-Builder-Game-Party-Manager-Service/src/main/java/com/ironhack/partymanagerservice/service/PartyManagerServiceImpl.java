package com.ironhack.partymanagerservice.service;

import com.ironhack.partymanagerservice.dto.CharacterDTO;
import com.ironhack.partymanagerservice.dto.LevelUpDTO;
import com.ironhack.partymanagerservice.dto.NewCharacterDTO;
import com.ironhack.partymanagerservice.dto.UserDTO;
import com.ironhack.partymanagerservice.proxy.CharacterModelProxy;
import com.ironhack.partymanagerservice.proxy.UserModelProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyManagerServiceImpl implements PartyManagerService {
    private final UserModelProxy userModelProxy;
    private final CharacterModelProxy characterModelProxy;


    public List<CharacterDTO> getParty(String username) {
        log.info("Getting party of user: {}", username);
        return characterModelProxy.getCharactersByUserUsernameActive(username);
    }

    public List<CharacterDTO> getGraveyard(String username) {
        log.info("Getting graveyard of user: {}", username);
        return characterModelProxy.getCharactersByUserUsernameGraveyard(username);
    }

    public CharacterDTO getCharacterById(String username, Long id) {
        log.info("Getting character with id: {}", id);
        var storedCharacter = characterModelProxy.getCharacterById(id);
        if (storedCharacter.getUserUsername().equals(username)) return storedCharacter;
        throw new IllegalArgumentException("Dont have access to this character");
    }

    public CharacterDTO createCharacter(String username, NewCharacterDTO newCharacterDTO) {
        log.info("Creating character with name: {}", newCharacterDTO.getName());
        if (username.equals(newCharacterDTO.getUserUsername())) {
            return characterModelProxy.createCharacter(newCharacterDTO);
        }
        throw new IllegalArgumentException("Dont have access to create this character");
    }

    public CharacterDTO levelUpCharacter(String username, LevelUpDTO levelUpDTO) {
        log.info("Leveling up character with id: {}", levelUpDTO.getId());
        var storedCharacter = characterModelProxy.getCharacterById(levelUpDTO.getId());
        if (storedCharacter.getUserUsername().equals(username)) {
            var updatedCharacter = characterModelProxy.levelUpCharacter(levelUpDTO);
            updatePartyLevel(username);
            return updatedCharacter;
        }
        throw new IllegalArgumentException("Dont have access to this character");
    }

    public CharacterDTO healCharacter(String username, Long id, Integer healAmount) {
        log.info("Healing character with id: {}", id);
        var storedCharacter = characterModelProxy.getCharacterById(id);
        if (storedCharacter.getUserUsername().equals(username)) {
            var updateHealthDTO = new CharacterDTO();
            updateHealthDTO.setId(id);
            updateHealthDTO.setUserUsername(username);
            updateHealthDTO.setCurrentHealth(
                    storedCharacter.getCurrentHealth() + healAmount < storedCharacter.getMaxHealth() ?
                            storedCharacter.getCurrentHealth() + healAmount :
                            storedCharacter.getMaxHealth()
            );
            return characterModelProxy.updateCharacter(updateHealthDTO);
        }
        throw new IllegalArgumentException("Dont have access to this character");
    }

    public CharacterDTO reviveCharacter(String username, Long id) {
        log.info("Reviving character with id: {}", id);
        var storedCharacter = characterModelProxy.getCharacterById(id);
        if (storedCharacter.getUserUsername().equals(username)) {
            var reviveCharacterDTO = new CharacterDTO();
            reviveCharacterDTO.setId(id);
            reviveCharacterDTO.setUserUsername(username);
            reviveCharacterDTO.setCurrentHealth(storedCharacter.getMaxHealth());
            reviveCharacterDTO.setIsAlive(true);
            var updatedCharacter = characterModelProxy.updateCharacter(reviveCharacterDTO);
            updatePartyLevel(username);
            return updatedCharacter;
        }
        throw new IllegalArgumentException("Dont have access to this character");
    }

    public void removeCharacter(String username, Long id) {
        log.info("Removing character with id: {}", id);
        var storedCharacter = characterModelProxy.getCharacterById(id);
        if (storedCharacter.getUserUsername().equals(username)) {
            characterModelProxy.deleteCharacterById(id);
            updatePartyLevel(username);
        } else throw new IllegalArgumentException("Dont have access to this character");
    }


    public void updatePartyLevel(String username) {
        log.info("Updating party level");
        var party = getParty(username);
        var partyLevel = party.stream()
                .mapToInt(CharacterDTO::getLevel)
                .sum();
        var userUpdateDTO = new UserDTO();
        userUpdateDTO.setPartyLevel(partyLevel);
        userModelProxy.updateUser(username, userUpdateDTO);
    }

}
