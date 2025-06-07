package com.jimmccarthy.rugby.controller;

public class RestApiResponseBuilder<T> {
    private final T payload;

    private RestApiResponseBuilder(T payload) {
        this.payload = payload;
    }

    public static <T> RestApiResponseBuilder<T> createBuilder(T payload) {
        return new RestApiResponseBuilder<>(payload);
    }

    public RestApiResponse<T> getResponse() {
        RestApiResponse<T> response = new RestApiResponse<>();
        response.setPayload(payload);

        return response;
    }
}
