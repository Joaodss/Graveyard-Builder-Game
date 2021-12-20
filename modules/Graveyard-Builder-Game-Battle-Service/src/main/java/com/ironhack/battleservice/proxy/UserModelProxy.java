package com.ironhack.battleservice.proxy;

import com.ironhack.battleservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-model-service", path = "/api/v1/users")
public interface UserModelProxy {

    @GetMapping("/username/{username}")
    ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username);

    @PutMapping("/update/{username}")
    UserDTO updateUser(@PathVariable String username, @RequestBody UserDTO user);

}
