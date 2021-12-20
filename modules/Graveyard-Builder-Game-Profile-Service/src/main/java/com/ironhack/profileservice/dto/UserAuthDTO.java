package com.ironhack.profileservice.dto;

import lombok.*;

import java.util.Set;

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

}
