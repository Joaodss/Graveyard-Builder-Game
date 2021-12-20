package com.ironhack.opponentselectionservice.controller;

import com.ironhack.opponentselectionservice.dto.CharacterDTO;
import com.ironhack.opponentselectionservice.service.OpponentSelectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/opponent")
@RequiredArgsConstructor
@Slf4j
public class OpponentSelectionControllerImpl implements OpponentSelectionController {
    private final OpponentSelectionService opponentSelectionService;


    @GetMapping("random/{level}")
    @ResponseStatus(OK)
    public List<CharacterDTO> getOpponents(@PathVariable(name = "level") Integer partyLevel) {
        log.info("Getting opponents for level {}", partyLevel);
        return opponentSelectionService.getOpponentCharacters(partyLevel);
    }

}
