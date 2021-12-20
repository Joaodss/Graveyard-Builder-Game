package com.ironhack.opponentselectionservice.dto;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO {

    private String username;
    private String email;
    private Set<String> roles;
    private String profilePictureUrl;
    private Long experience;
    private Long gold;
    private Integer partyLevel;

}
