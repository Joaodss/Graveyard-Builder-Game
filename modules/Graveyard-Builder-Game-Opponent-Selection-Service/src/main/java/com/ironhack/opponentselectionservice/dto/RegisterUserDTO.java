package com.ironhack.opponentselectionservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RegisterUserDTO {

    private String username;
    private String email;
    private String password;

}
