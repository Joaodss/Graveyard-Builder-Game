package com.ironhack.opponentselectionservice.controller;

import com.ironhack.opponentselectionservice.dto.CharacterDTO;

import java.util.List;

public interface OpponentSelectionController {

    List<CharacterDTO> getOpponents(Integer partyLevel);

}
