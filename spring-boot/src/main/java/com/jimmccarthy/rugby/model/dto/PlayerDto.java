package com.jimmccarthy.rugby.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class PlayerDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6144254787205588704L;
    private Long id;
    private String name;
    private String position;
    private int jerseyNumber;
}
