package com.ironhack.opponentselectionservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class NewCharacterDTO {

    private String userUsername;
    private String type;
    private String name;
    private String pictureURL;

}
