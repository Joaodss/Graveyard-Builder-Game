package com.ironhack.battleservice.controller;

import com.ironhack.battleservice.dto.CharacterDTO;
import com.ironhack.battleservice.dto.OpponentDTO;
import com.ironhack.battleservice.dto.UserDTO;
import com.ironhack.battleservice.service.BattleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/battle")
@RequiredArgsConstructor
@Slf4j
public class BattleControllerImpl implements BattleController {
    private final BattleService battleService;


    @GetMapping("/opponents")
    @ResponseStatus(OK)
    public OpponentDTO getOpponents(@RequestHeader("username") String username) {
        log.info("Getting opponents for user: {}", username);
        return battleService.getOpponentsByUserLevel(username);
    }

    @PutMapping("/updateHealth/{id}")
    @ResponseStatus(OK)
    public CharacterDTO updateHealth(@RequestHeader("username") String username,
                                     @PathVariable Long id,
                                     @RequestParam Integer health) {
        log.info("Updating health of character with id: {}", id);
        try {
            return battleService.updateHealth(username, id, health);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/addExperience/{id}")
    @ResponseStatus(OK)
    public CharacterDTO addExperience(@RequestHeader("username") String username,
                                      @PathVariable Long id,
                                      @RequestParam Long experience) {
        log.info("Adding experience to character with id: {}", id);
        try {
            return battleService.addExperience(username, id, experience);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/addUserExperienceAndGold")
    @ResponseStatus(OK)
    public UserDTO addUserExperienceAndGold(@RequestHeader("username") String username,
                                            @RequestParam(required = false) Long experience,
                                            @RequestParam(required = false) Long gold) {
        log.info("Adding experience and gold to user: {}", username);
        return battleService.addUserExperienceAndGold(username, experience, gold);
    }

}
