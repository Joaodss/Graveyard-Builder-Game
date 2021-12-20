package com.ironhack.usermodelservice.service;

import com.ironhack.usermodelservice.dao.Role;
import com.ironhack.usermodelservice.dao.User;
import com.ironhack.usermodelservice.dto.RegisterUserDTO;
import com.ironhack.usermodelservice.dto.UserAuthDTO;
import com.ironhack.usermodelservice.dto.UserDTO;
import com.ironhack.usermodelservice.repository.RoleRepository;
import com.ironhack.usermodelservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    // -------------------- Get methods --------------------
    public List<UserDTO> getAllUsers() {
        log.info("Getting all users");
        var storedUsers = userRepository.findAll();
        return storedUsers.stream()
                .map(UserDTO::new)
                .collect(toList());
    }

    public UserAuthDTO getUserAuthByUsername(String username) {
        log.info("Getting user auth by username: {}", username);
        var storedUser = userRepository.findByUsername(username);
        return storedUser
                .map(UserAuthDTO::new)
                .orElse(null);
    }

    public UserDTO getUserById(long id) {
        log.info("Getting user by id: {}", id);
        var storedUser = userRepository.findById(id);
        return storedUser
                .map(UserDTO::new)
                .orElse(null);
    }

    public UserDTO getUserByUsername(String username) {
        log.info("Getting user by username: {}", username);
        var storedUser = userRepository.findByUsername(username);
        return storedUser
                .map(UserDTO::new)
                .orElse(null);
    }

    public UserDTO getUserByEmail(String email) {
        log.info("Getting user by email: {}", email);
        var storedUser = userRepository.findByEmail(email);
        return storedUser
                .map(UserDTO::new)
                .orElse(null);
    }

    public List<String> getAllUsersUsernamesByPartyLevelBetween(int min, int max) {
        log.info("Getting all users ids by party level between: {} and {}", min, max);
        var storedUsers = userRepository.findAllByPartyLevelBetween(min, max);
        return storedUsers.stream()
                .map(User::getUsername)
                .collect(toList());
    }


    // -------------------- Register methods --------------------
    public UserDTO registerUser(RegisterUserDTO registerUserDTO) {
        log.info("Registering user: {}", registerUserDTO.getUsername());
        var savedUser = userRepository.save(new User(registerUserDTO));
        addRoleToUser(savedUser.getUsername(), "USER");
        log.info("User registered");
        return new UserDTO(savedUser);
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role: {}, to user: {}", roleName, username);
        var storedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        var storedRole = roleRepository.findByName(roleName);
        storedUser.getRoles().add(
                storedRole.orElseGet(() -> roleRepository.save(new Role(roleName)))
        );
        userRepository.save(storedUser);
        log.info("Role added");
    }


    // -------------------- Update methods --------------------
    public UserDTO updateUser(String username, UserDTO userDTO) {
        log.info("Updating user: {}", username);
        var storedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (userDTO.getUsername() != null) storedUser.setUsername(userDTO.getUsername());
        if (userDTO.getEmail() != null) storedUser.setEmail(userDTO.getEmail());
        if (userDTO.getProfilePictureUrl() != null) storedUser.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        if (userDTO.getExperience() != null) storedUser.setExperience(userDTO.getExperience());
        if (userDTO.getGold() != null) storedUser.setGold(userDTO.getGold());
        if (userDTO.getPartyLevel() != null) storedUser.setPartyLevel(userDTO.getPartyLevel());
        log.info("User updated");
        return new UserDTO(userRepository.save(storedUser));
    }

    public void changePassword(String username, String password) {
        log.info("Changing password for user: {}", username);
        var storedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        storedUser.setPassword(password);
        userRepository.save(storedUser);
        log.info("Password changed");
    }


    // -------------------- Delete methods --------------------
    public void deleteUser(String username) {
        log.info("Deleting user: {}", username);
        var storedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(storedUser.getId());
        log.info("User deleted");
    }

}
