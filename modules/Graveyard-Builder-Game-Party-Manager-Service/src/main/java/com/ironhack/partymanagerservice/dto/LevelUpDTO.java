package com.ironhack.partymanagerservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LevelUpDTO {

    @NotNull
    @Positive
    private Long id;
    private Integer healthPoints;
    private Integer energyPoints;
    private Integer attackPoints;

}
