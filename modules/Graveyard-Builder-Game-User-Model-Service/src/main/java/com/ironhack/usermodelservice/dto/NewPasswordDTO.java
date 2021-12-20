package com.ironhack.usermodelservice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class NewPasswordDTO {

    @NotBlank(message = "Password cannot be empty")
    private String newPassword;

}
