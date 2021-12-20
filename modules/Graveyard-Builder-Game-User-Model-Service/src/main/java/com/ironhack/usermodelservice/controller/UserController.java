package com.ironhack.usermodelservice.controller;

import com.ironhack.usermodelservice.dto.NewPasswordDTO;
import com.ironhack.usermodelservice.dto.RegisterUserDTO;
import com.ironhack.usermodelservice.dto.UserAuthDTO;
import com.ironhack.usermodelservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserController {

    // -------------------- GET REQUESTS --------------------
    List<UserDTO> getAllUsers();

    ResponseEntity<UserDTO> getUserById(Long id);

    ResponseEntity<UserAuthDTO> getUserAuth(String username);

    ResponseEntity<UserDTO> getUserByUsername(String username);

    ResponseEntity<UserDTO> getUserByEmail(String email);

    List<String> getAllUsersUsernamesByPartyLevelBetween(Integer min, Integer max);


    // -------------------- POST REQUESTS --------------------
    UserDTO registerUser(RegisterUserDTO registerUserDTO);


    // -------------------- PUT REQUESTS --------------------
    UserDTO updateUser(String username, UserDTO user);

    void changePassword(String username, NewPasswordDTO password);


    // -------------------- DELETE REQUESTS --------------------
    void deleteUser(String username);

}
