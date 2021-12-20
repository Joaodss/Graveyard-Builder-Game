package com.ironhack.profileservice.proxy;

import com.ironhack.profileservice.dto.NewPasswordDTO;
import com.ironhack.profileservice.dto.UserAuthDTO;
import com.ironhack.profileservice.dto.UserDTO;
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

    @GetMapping("/auth/{username}")
    ResponseEntity<UserAuthDTO> getUserAuth(@PathVariable String username);

    @PutMapping("/update/{username}")
    UserDTO updateUser(@PathVariable String username, @RequestBody UserDTO user);

    @PutMapping("/update/{username}/password")
    void changePassword(@PathVariable String username, @RequestBody NewPasswordDTO passwordDTO);

}
