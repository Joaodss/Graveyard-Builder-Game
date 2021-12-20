package com.ironhack.battleservice.proxy;

import com.ironhack.battleservice.dto.CharacterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "opponent-selection-service", path = "/api/v1/opponent")
public interface OpponentSelectionProxy {

    @GetMapping("random/{level}")
    List<CharacterDTO> getOpponents(@PathVariable(name = "level") Integer partyLevel);

}
