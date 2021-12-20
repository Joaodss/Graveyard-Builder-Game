package com.ironhack.profileservice.service;

import com.ironhack.profileservice.dto.*;
import com.ironhack.profileservice.proxy.UserModelProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final PasswordEncoder passwordEncoder;
    private final UserModelProxy userModelProxy;


    public UserDTO getUserProfile(String username) {
        log.info("Getting user profile for user: {}", username);
        var userDetails = userModelProxy.getUserByUsername(username).getBody();
        if (userDetails == null) throw new IllegalArgumentException("User not found");
        return userDetails;
    }

    public ExperienceDTO getUserExperience(String username) {
        log.info("Getting user experience for user: {}", username);
        var retrievedUser = getUserProfile(username);
        return new ExperienceDTO(
                retrievedUser != null ?
                        retrievedUser.getExperience() :
                        null
        );
    }

    public GoldDTO getUserGold(String username) {
        log.info("Getting user gold for user: {}", username);
        var retrievedUser = getUserProfile(username);
        return new GoldDTO(
                retrievedUser != null ?
                        retrievedUser.getGold() :
                        null
        );
    }

    public void changeUserPassword(String username, PasswordChangeDTO passwordChangeDTO) {
        log.info("Changing user password for user: {}", username);
        var userAuthDetails = userModelProxy.getUserAuth(username).getBody();
        if (userAuthDetails == null) throw new IllegalArgumentException("User not found");

        if (passwordEncoder.matches(passwordChangeDTO.getOldPassword(), userAuthDetails.getPassword())) {
            var encodedNewPassword = passwordEncoder.encode(passwordChangeDTO.getNewPassword());
            userModelProxy.changePassword(username, new NewPasswordDTO(encodedNewPassword));
        } else {
            throw new IllegalArgumentException("Wrong password");
        }
    }

    public UserDTO changeUserDetails(String username, UserDTO userDTO) {
        log.info("Changing user details for user: {}", username);
        getUserProfile(username);
        return userModelProxy.updateUser(username, userDTO);
    }

}
