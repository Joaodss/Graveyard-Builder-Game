package com.ironhack.charactermodelservice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class NewCharacterDTO {

    @NotBlank
    private String userUsername;
    @NotBlank
    private String type;
    @NotBlank
    private String name;
    private String pictureURL;

}
