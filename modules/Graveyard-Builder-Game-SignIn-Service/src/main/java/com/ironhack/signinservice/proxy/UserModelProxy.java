package com.ironhack.signinservice.proxy;

import com.ironhack.signinservice.dto.RegisterUserDTO;
import com.ironhack.signinservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "user-model-service", path = "/api/v1/users", decode404 = true)
public interface UserModelProxy {

    @GetMapping("/username/{username}")
    ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username);

    @GetMapping("/email/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email);

    @PostMapping("/register")
    UserDTO registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO);

}
