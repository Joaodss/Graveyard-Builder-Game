package com.ironhack.charactermodelservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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

    @NotNull
    @PositiveOrZero
    private Integer healthPoints;

    @NotNull
    @PositiveOrZero
    private Integer energyPoints;

    @NotNull
    @PositiveOrZero
    private Integer attackPoints;

}
