package com.ironhack.opponentselectionservice.service;

import com.ironhack.opponentselectionservice.dto.CharacterDTO;

import java.util.List;

public interface OpponentSelectionService {

    List<CharacterDTO> getOpponentCharacters(int partyLevel);


}
