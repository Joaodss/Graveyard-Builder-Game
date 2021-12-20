package com.ironhack.battleservice.controller;

import com.ironhack.battleservice.dto.CharacterDTO;
import com.ironhack.battleservice.dto.OpponentDTO;
import com.ironhack.battleservice.dto.UserDTO;

import java.util.List;

public interface BattleController {

    OpponentDTO getOpponents(String username);

    CharacterDTO updateHealth(String username, Long id, Integer health);

    CharacterDTO addExperience(String username, Long id, Long experience);

    UserDTO addUserExperienceAndGold(String username, Long experience, Long gold);

}
