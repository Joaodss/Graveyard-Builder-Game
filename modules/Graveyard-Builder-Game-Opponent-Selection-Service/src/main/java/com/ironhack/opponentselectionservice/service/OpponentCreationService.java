package com.ironhack.opponentselectionservice.service;

import com.ironhack.opponentselectionservice.dto.CharacterDTO;
import com.ironhack.opponentselectionservice.dto.UserDTO;

import java.util.List;

public interface OpponentCreationService {

    // -------------------- Create Opponent --------------------
    List<CharacterDTO> generateOpponent(int opponentLevel);

    // -------------------- Create User Opponent --------------------
    UserDTO createUserOpponent();

    // -------------------- Create Party Opponent --------------------
    List<CharacterDTO> createOpponentParty(String userUsername);

    // -------------------- LevelUp Opponent --------------------
    List<CharacterDTO> levelUpOpponentParty(int level, String userUsername, List<CharacterDTO> party);

}
