package com.ironhack.partymanagerservice.proxy;

import com.ironhack.partymanagerservice.dto.CharacterDTO;
import com.ironhack.partymanagerservice.dto.LevelUpDTO;
import com.ironhack.partymanagerservice.dto.NewCharacterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "character-model-service", path = "/api/v1/characters")
public interface CharacterModelProxy {

    // -------------------- GET --------------------
    @GetMapping("/id/{id}")
    CharacterDTO getCharacterById(@PathVariable Long id);

    @GetMapping("/party/{username}")
    List<CharacterDTO> getCharactersByUserUsernameActive(@PathVariable String username);

    @GetMapping("/graveyard/{username}")
    List<CharacterDTO> getCharactersByUserUsernameGraveyard(@PathVariable String username);

    // -------------------- POST --------------------
    @PostMapping("/create")
    CharacterDTO createCharacter(@RequestBody @Valid NewCharacterDTO newCharacterDTO);

    // -------------------- PUT --------------------
    @PutMapping("/update")
    CharacterDTO updateCharacter(@RequestBody CharacterDTO updateCharacterDTO);

    @PutMapping("/update/levelUp")
    CharacterDTO levelUpCharacter(@RequestBody LevelUpDTO levelUpDTO);

    // -------------------- DELETE --------------------
    @DeleteMapping("/delete/{id}")
    void deleteCharacterById(@PathVariable Long id);

}
