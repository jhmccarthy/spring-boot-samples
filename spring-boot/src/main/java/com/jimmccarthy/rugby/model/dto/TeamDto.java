package com.jimmccarthy.rugby.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 3712000774196795371L;
    private long id;
    private String name;
    private String stadium;
    private List<PlayerDto> players = new ArrayList<>();

    public TeamDto() {
    }

    public TeamDto(long id, String name, String stadium) {
        this.id = id;
        this.name = name;
        this.stadium = stadium;
    }
}
