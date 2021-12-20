package com.ironhack.partymanagerservice.service;

import com.ironhack.partymanagerservice.dto.CharacterDTO;
import com.ironhack.partymanagerservice.dto.LevelUpDTO;
import com.ironhack.partymanagerservice.dto.NewCharacterDTO;

import java.util.List;

public interface PartyManagerService {

    List<CharacterDTO> getParty(String username);

    List<CharacterDTO> getGraveyard(String username);

    CharacterDTO getCharacterById(String username, Long id);

    CharacterDTO createCharacter(String username, NewCharacterDTO characterDTO);

    CharacterDTO levelUpCharacter(String username, LevelUpDTO levelUpDTO);

    CharacterDTO healCharacter(String username, Long id, Integer healAmount);

    CharacterDTO reviveCharacter(String username, Long id);

    void removeCharacter(String username, Long id);

    void updatePartyLevel(String username);

}
