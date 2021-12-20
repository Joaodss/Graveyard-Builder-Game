package com.ironhack.usermodelservice.dto;

import com.ironhack.usermodelservice.dao.Role;
import com.ironhack.usermodelservice.dao.User;
import lombok.*;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserAuthDTO {

    private String username;
    private String password;
    private Set<String> roles;

    public UserAuthDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(toSet());
    }

}
