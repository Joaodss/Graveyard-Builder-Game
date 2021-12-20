package com.ironhack.usermodelservice.service;

import com.ironhack.usermodelservice.dto.RegisterUserDTO;
import com.ironhack.usermodelservice.dto.UserAuthDTO;
import com.ironhack.usermodelservice.dto.UserDTO;

import java.util.List;

public interface UserService {

    // -------------------- Get methods --------------------
    List<UserDTO> getAllUsers();

    UserDTO getUserById(long id);

    UserAuthDTO getUserAuthByUsername(String username);

    UserDTO getUserByUsername(String username);

    UserDTO getUserByEmail(String email);

    List<String> getAllUsersUsernamesByPartyLevelBetween(int min, int max);

    // -------------------- Register methods --------------------
    UserDTO registerUser(RegisterUserDTO registerUserDTO);

    void addRoleToUser(String username, String roleName);

    // -------------------- Update methods --------------------
    UserDTO updateUser(String username, UserDTO user);

    void changePassword(String username, String password);

    // -------------------- Delete methods --------------------
    void deleteUser(String username);

}
