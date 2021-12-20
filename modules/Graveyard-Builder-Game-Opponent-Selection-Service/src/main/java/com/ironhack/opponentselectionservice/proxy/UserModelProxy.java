package com.ironhack.opponentselectionservice.proxy;

import com.ironhack.opponentselectionservice.dto.RegisterUserDTO;
import com.ironhack.opponentselectionservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-model-service", path = "/api/v1/users", decode404 = true)
public interface UserModelProxy {

    @GetMapping("/username/{username}")
    ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username);

    @GetMapping("/partyLevel")
    List<String> getAllUsersUsernamesByPartyLevelBetween(@RequestParam Integer min, @RequestParam Integer max);

    @PostMapping("/register")
    UserDTO registerUser(@RequestBody RegisterUserDTO registerUserDTO);

    @PutMapping("/update/{username}")
    UserDTO updateUser(@PathVariable String username, @RequestBody UserDTO user);

}