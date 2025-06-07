package com.jimmccarthy.rugby.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class PlayerTeamDto extends PlayerDto {
    @Serial
    private static final long serialVersionUID = 2053858643973217352L;
    private String teamName;
}
