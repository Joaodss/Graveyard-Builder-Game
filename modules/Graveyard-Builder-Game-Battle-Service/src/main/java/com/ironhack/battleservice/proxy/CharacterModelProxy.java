package com.ironhack.battleservice.proxy;

import com.ironhack.battleservice.dto.CharacterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "character-model-service", path = "/api/v1/characters")
public interface CharacterModelProxy {

    @GetMapping("/id/{id}")
    CharacterDTO getCharacterById(@PathVariable Long id);

    @PutMapping("/update")
    CharacterDTO updateCharacter(@RequestBody CharacterDTO updateCharacterDTO);

    @GetMapping("/party/{username}")
    List<CharacterDTO> getCharactersByUserUsernameActive(@PathVariable String username);

}
