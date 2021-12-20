package com.ironhack.opponentselectionservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LevelUpDTO {

    private Long id;
    private Integer healthPoints;
    private Integer energyPoints;
    private Integer attackPoints;

}
