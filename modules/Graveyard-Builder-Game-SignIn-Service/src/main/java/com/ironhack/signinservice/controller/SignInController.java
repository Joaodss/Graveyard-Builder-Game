package com.ironhack.signinservice.controller;

import com.ironhack.signinservice.dto.*;

public interface SignInController {

    UserDTO registerUser(RegisterUserDTO registerUserDTO);

    boolean checkValidUsername(UsernameDTO username);

    boolean checkValidEmail(EmailDTO email);

}


