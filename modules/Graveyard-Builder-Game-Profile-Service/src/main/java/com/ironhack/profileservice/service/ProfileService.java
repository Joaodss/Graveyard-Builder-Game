package com.ironhack.profileservice.service;

import com.ironhack.profileservice.dto.ExperienceDTO;
import com.ironhack.profileservice.dto.GoldDTO;
import com.ironhack.profileservice.dto.PasswordChangeDTO;
import com.ironhack.profileservice.dto.UserDTO;

public interface ProfileService {

    UserDTO getUserProfile(String username);

    ExperienceDTO getUserExperience(String username);

    GoldDTO getUserGold(String username);

    void changeUserPassword(String Username, PasswordChangeDTO PasswordChangeDTO);

    UserDTO changeUserDetails(String Username, UserDTO userDTO);

}
