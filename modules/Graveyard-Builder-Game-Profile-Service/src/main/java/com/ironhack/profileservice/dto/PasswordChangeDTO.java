package com.ironhack.profileservice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PasswordChangeDTO {

    @NotBlank(message = "Password cannot be empty")
    private String oldPassword;

    @NotBlank(message = "Password cannot be empty")
    private String newPassword;

}
