package com.ironhack.battleservice.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OpponentDTO {
    private String username;
    private String profilePictureUrl;
    private List<CharacterDTO> opponents;
}
