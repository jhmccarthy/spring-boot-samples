package com.jimmccarthy.rugby.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestApiResponse<T> {
    private T payload;
}
