package com.ironhack.signinservice.service;

import com.ironhack.signinservice.dto.RegisterUserDTO;
import com.ironhack.signinservice.dto.UserDTO;
import com.ironhack.signinservice.proxy.UserModelProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    private final UserModelProxy userModelProxy;
    private final PasswordEncoder passwordEncoder;


    // -------------------- User Registration --------------------
    public UserDTO register(RegisterUserDTO registerUserDTO) {
        log.info("Registering user: " + registerUserDTO.getUsername());
        boolean isValidUsername = isValidUserUsername(registerUserDTO.getUsername());
        boolean isValidEmail = isValidUserEmail(registerUserDTO.getEmail());
        if (isValidUsername && isValidEmail) {
            registerUserDTO.setPassword(encodeString(registerUserDTO.getPassword()));
            return userModelProxy.registerUser(registerUserDTO);
        }
        return null;
    }


    // -------------------- User Registration --------------------
    public boolean isValidUserUsername(String username) {
        log.info("Validating username: " + username);
        var userResponse = userModelProxy.getUserByUsername(username);
        return userResponse.getStatusCodeValue() == 404 &&
                Objects.equals(userResponse.getHeaders().getFirst("error"), "User not found");

    }

    public boolean isValidUserEmail(String email) {
        log.info("Validating email: " + email);
        var userResponse = userModelProxy.getUserByEmail(email);
        return userResponse.getStatusCodeValue() == 404 &&
                Objects.equals(userResponse.getHeaders().getFirst("error"), "User not found");
    }


    // -------------------- Aux Methods --------------------
    private String encodeString(String value) {
        return passwordEncoder.encode(value);
    }

}
