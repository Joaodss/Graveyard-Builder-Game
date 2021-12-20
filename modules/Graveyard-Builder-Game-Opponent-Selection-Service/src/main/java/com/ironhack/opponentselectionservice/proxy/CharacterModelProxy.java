package com.ironhack.opponentselectionservice.proxy;

import com.ironhack.opponentselectionservice.dto.CharacterDTO;
import com.ironhack.opponentselectionservice.dto.LevelUpDTO;
import com.ironhack.opponentselectionservice.dto.NewCharacterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "character-model-service", path = "/api/v1/characters")
public interface CharacterModelProxy {

    @GetMapping("/party/{username}")
    List<CharacterDTO> getCharactersByUserUsernameActive(@PathVariable String username);

    @PostMapping("/create")
    CharacterDTO createCharacter(@RequestBody NewCharacterDTO newCharacterDTO);

    @PutMapping("/update/levelUp")
    CharacterDTO levelUpCharacter(@RequestBody LevelUpDTO levelUpDTO);

}
