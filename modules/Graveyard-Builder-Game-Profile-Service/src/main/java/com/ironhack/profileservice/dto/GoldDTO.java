package com.ironhack.profileservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GoldDTO {

    @NotNull
    @PositiveOrZero
    private Long gold;

}
