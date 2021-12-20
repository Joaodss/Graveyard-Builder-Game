package com.ironhack.usermodelservice.dto;

import com.ironhack.usermodelservice.dao.Role;
import com.ironhack.usermodelservice.dao.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Slf4j
public class UserDTO {

    private String username;
    private String email;
    private Set<String> roles;
    private String profilePictureUrl;
    private Long experience;
    private Long gold;
    private Integer partyLevel;


    // -------------------- Custom Constructor --------------------
    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(toSet());
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.experience = user.getExperience();
        this.gold = user.getGold();
        this.partyLevel = user.getPartyLevel();
        log.info("UserDTO created for returning details");
    }

}
