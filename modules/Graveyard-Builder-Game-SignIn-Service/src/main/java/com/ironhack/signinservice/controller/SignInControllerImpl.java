package com.ironhack.signinservice.controller;

import com.ironhack.signinservice.dto.EmailDTO;
import com.ironhack.signinservice.dto.RegisterUserDTO;
import com.ironhack.signinservice.dto.UserDTO;
import com.ironhack.signinservice.dto.UsernameDTO;
import com.ironhack.signinservice.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/signIn")
@RequiredArgsConstructor
@Slf4j
public class SignInControllerImpl implements SignInController {
    private final RegisterService registerService;


    @PostMapping("/register")
    @ResponseStatus(OK)
    public UserDTO registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        log.info("Registering new user {}", registerUserDTO.getUsername());
        return registerService.register(registerUserDTO);
    }

    @PostMapping("/validate/username")
    @ResponseStatus(OK)
    public boolean checkValidUsername(@RequestBody @Valid UsernameDTO usernameDTO) {
        log.info("Checking if username is valid");
        log.info("Username: " + usernameDTO.getUsername());
        return registerService.isValidUserUsername(usernameDTO.getUsername());
    }

    @PostMapping("/validate/email")
    @ResponseStatus(OK)
    public boolean checkValidEmail(@RequestBody @Valid EmailDTO emailDTO) {
        log.info("Checking if email is valid");
        log.info("Email: " + emailDTO.getEmail());
        return registerService.isValidUserEmail(emailDTO.getEmail());
    }

}
