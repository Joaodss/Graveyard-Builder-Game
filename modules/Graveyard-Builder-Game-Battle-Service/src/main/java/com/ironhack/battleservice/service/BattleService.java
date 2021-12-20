package com.ironhack.battleservice.service;

import com.ironhack.battleservice.dto.CharacterDTO;
import com.ironhack.battleservice.dto.OpponentDTO;
import com.ironhack.battleservice.dto.UserDTO;

import java.util.List;

public interface BattleService {

    OpponentDTO getOpponentsByUserLevel(String username);

    CharacterDTO updateHealth(String username, Long id, Integer health);

    CharacterDTO addExperience(String username, Long id, Long experience);

    UserDTO addUserExperienceAndGold(String username, Long experience, Long gold);

}
