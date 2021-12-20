package com.ironhack.profileservice.controller;

import com.ironhack.profileservice.dto.ExperienceDTO;
import com.ironhack.profileservice.dto.GoldDTO;
import com.ironhack.profileservice.dto.PasswordChangeDTO;
import com.ironhack.profileservice.dto.UserDTO;
import com.ironhack.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@Slf4j
public class ProfileControllerImpl implements ProfileController {
    private final ProfileService profileService;


    @GetMapping("/user")
    @ResponseStatus(OK)
    public UserDTO getUserProfile(@RequestHeader("username") String username) {
        log.info("Getting user profile");
        return profileService.getUserProfile(username);
    }

    @GetMapping("/experience")
    @ResponseStatus(OK)
    public ExperienceDTO getUserExperience(@RequestHeader("username") String username) {
        log.info("Getting user experience");
        return profileService.getUserExperience(username);
    }

    @GetMapping("/gold")
    @ResponseStatus(OK)
    public GoldDTO getUserGold(@RequestHeader("username") String username) {
        log.info("Getting user gold");
        return profileService.getUserGold(username);
    }

    @PostMapping("/update/password")
    @ResponseStatus(OK)
    public void changeUserPassword(@RequestHeader("username") String username,
                                   @RequestBody @Valid PasswordChangeDTO PasswordChangeDTO) {
        log.info("Changing user password");
        try {
            profileService.changeUserPassword(username, PasswordChangeDTO);
        } catch (IllegalArgumentException e) {
            log.error("Error changing user password -> {}", e.getMessage());
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/update/all")
    @ResponseStatus(OK)
    public UserDTO changeUserDetails(@RequestHeader("username") String username,
                                     @RequestBody UserDTO userDTO) {
        log.info("Changing user details");
        return profileService.changeUserDetails(username, userDTO);
    }

}
