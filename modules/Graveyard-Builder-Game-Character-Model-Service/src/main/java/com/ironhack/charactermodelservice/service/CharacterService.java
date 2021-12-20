package com.ironhack.charactermodelservice.service;

import com.ironhack.charactermodelservice.dto.CharacterDTO;
import com.ironhack.charactermodelservice.dto.LevelUpDTO;
import com.ironhack.charactermodelservice.dto.NewCharacterDTO;

import java.util.List;

public interface CharacterService {

    List<CharacterDTO> getAllCharacters();

    CharacterDTO getCharacterById(Long id);

    List<CharacterDTO> getAliveCharactersByUserUsername(String username);

    List<CharacterDTO> getDeadCharactersByUserUsername(String username);

    CharacterDTO createCharacter(NewCharacterDTO newCharacterDTO);

    CharacterDTO updateCharacter(CharacterDTO updateCharacterDTO);

    CharacterDTO levelUpCharacter(LevelUpDTO levelUpDTO);

    void deleteCharacter(Long id);

}
