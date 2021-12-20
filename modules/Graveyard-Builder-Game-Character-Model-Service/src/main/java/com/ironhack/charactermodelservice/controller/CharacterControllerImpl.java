package com.ironhack.charactermodelservice.controller;

import com.ironhack.charactermodelservice.dto.CharacterDTO;
import com.ironhack.charactermodelservice.dto.LevelUpDTO;
import com.ironhack.charactermodelservice.dto.NewCharacterDTO;
import com.ironhack.charactermodelservice.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/characters")
@RequiredArgsConstructor
@Slf4j
public class CharacterControllerImpl implements CharacterController {
    private final CharacterService characterService;


    // -------------------- GET --------------------
    @GetMapping("/all")
    @ResponseStatus(OK)
    public List<CharacterDTO> getAllCharacters() {
        log.info("Getting all characters");
        return characterService.getAllCharacters();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(OK)
    public CharacterDTO getCharacterById(@PathVariable Long id) {
        log.info("Getting character by id {}", id);
        var character = characterService.getCharacterById(id);
        if (character == null) throw new ResponseStatusException(NOT_FOUND, "Character not found");
        return character;
    }

    @GetMapping("/party/{username}")
    @ResponseStatus(OK)
    public List<CharacterDTO> getCharactersByUserUsernameActive(@PathVariable String username) {
        log.info("Getting party by user username {}", username);
        return characterService.getAliveCharactersByUserUsername(username);
    }

    @GetMapping("/graveyard/{username}")
    @ResponseStatus(OK)
    public List<CharacterDTO> getCharactersByUserUsernameGraveyard(@PathVariable String username) {
        log.info("Getting graveyard by user username {}", username);
        return characterService.getDeadCharactersByUserUsername(username);
    }


    // -------------------- POST --------------------
    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public CharacterDTO createCharacter(@RequestBody @Valid NewCharacterDTO newCharacterDTO) {
        log.info("Creating character {} of type {}", newCharacterDTO.getName(), newCharacterDTO.getType());
        return characterService.createCharacter(newCharacterDTO);
    }


    // -------------------- PUT --------------------
    @PutMapping("/update")
    @ResponseStatus(OK)
    public CharacterDTO updateCharacter(@RequestBody CharacterDTO updateCharacterDTO) {
        log.info("Updating character {}", updateCharacterDTO.getId());
        try {
            return characterService.updateCharacter(updateCharacterDTO);
        } catch (EntityNotFoundException e1) {
            throw new ResponseStatusException(BAD_REQUEST, "Character not found");
        }
    }

    @PutMapping("/update/levelUp")
    @ResponseStatus(OK)
    public CharacterDTO levelUpCharacter(@RequestBody LevelUpDTO levelUpDTO) {
        log.info("Leveling up character {}", levelUpDTO.getId());
        try {
            return characterService.levelUpCharacter(levelUpDTO);
        } catch (EntityNotFoundException e1) {
            throw new ResponseStatusException(BAD_REQUEST, "Character not found");
        }
    }


    // -------------------- DELETE --------------------
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(OK)
    public void deleteCharacterById(@PathVariable Long id) {
        log.info("Deleting character {}", id);
        try {
            characterService.deleteCharacter(id);
        } catch (EntityNotFoundException e1) {
            throw new ResponseStatusException(BAD_REQUEST, "Character not found");
        }
    }

}
