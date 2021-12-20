package com.ironhack.signinservice.service;

import com.ironhack.signinservice.dto.RegisterUserDTO;
import com.ironhack.signinservice.dto.UserDTO;

public interface RegisterService {

    UserDTO register(RegisterUserDTO registerUserDTO);

    boolean isValidUserUsername(String username);

    boolean isValidUserEmail(String email);

}
