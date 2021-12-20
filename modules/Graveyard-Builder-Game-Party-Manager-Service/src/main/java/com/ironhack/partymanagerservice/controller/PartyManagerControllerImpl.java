package com.ironhack.partymanagerservice.controller;

import com.ironhack.partymanagerservice.dto.CharacterDTO;
import com.ironhack.partymanagerservice.dto.LevelUpDTO;
import com.ironhack.partymanagerservice.dto.NewCharacterDTO;
import com.ironhack.partymanagerservice.service.PartyManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/party-manager")
@RequiredArgsConstructor
@Slf4j
public class PartyManagerControllerImpl implements PartyManagerController {
    private final PartyManagerService partyManagerService;


    @GetMapping("/party")
    @ResponseStatus(OK)
    public List<CharacterDTO> getParty(@RequestHeader(value = "username") String username) {
        return partyManagerService.getParty(username);
    }

    @GetMapping("/graveyard")
    @ResponseStatus(OK)
    public List<CharacterDTO> getGraveyard(@RequestHeader(value = "username") String username) {
        return partyManagerService.getGraveyard(username);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(OK)
    public CharacterDTO getCharacterById(@RequestHeader(value = "username") String username,
                                         @PathVariable Long id) {
        try {
            return partyManagerService.getCharacterById(username, id);
        } catch (IllegalArgumentException e1) {
            throw new ResponseStatusException(FORBIDDEN, e1.getMessage());
        }
    }


    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public CharacterDTO createCharacter(@RequestHeader(value = "username") String username,
                                        @RequestBody @Valid NewCharacterDTO newCharacterDTO) {
        try {
            return partyManagerService.createCharacter(username, newCharacterDTO);
        } catch (IllegalArgumentException e1) {
            throw new ResponseStatusException(FORBIDDEN, e1.getMessage());
        }
    }

    @PutMapping("/level-up")
    @ResponseStatus(OK)
    public CharacterDTO levelUpCharacter(@RequestHeader(value = "username") String username,
                                         @RequestBody @Valid LevelUpDTO levelUpDTO) {
        try {
            return partyManagerService.levelUpCharacter(username, levelUpDTO);
        } catch (IllegalArgumentException e1) {
            throw new ResponseStatusException(FORBIDDEN, e1.getMessage());
        }
    }

    @PutMapping("/heal/{id}")
    @ResponseStatus(OK)
    public CharacterDTO healCharacter(@RequestHeader(value = "username") String username,
                                      @PathVariable Long id,
                                      @RequestParam Integer healAmount) {
        try {
            return partyManagerService.healCharacter(username, id, healAmount);
        } catch (IllegalArgumentException e1) {
            throw new ResponseStatusException(FORBIDDEN, e1.getMessage());
        }
    }

    @PutMapping("/revive/{id}")
    @ResponseStatus(OK)
    public CharacterDTO reviveCharacter(@RequestHeader(value = "username") String username,
                                        @PathVariable Long id) {
        try {
            return partyManagerService.reviveCharacter(username, id);
        } catch (IllegalArgumentException e1) {
            throw new ResponseStatusException(FORBIDDEN, e1.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(OK)
    public void removeCharacter(@RequestHeader(value = "username") String username,
                                @PathVariable Long id) {
        try {
            partyManagerService.removeCharacter(username, id);
        } catch (IllegalArgumentException e1) {
            throw new ResponseStatusException(FORBIDDEN, e1.getMessage());
        }
    }

}
