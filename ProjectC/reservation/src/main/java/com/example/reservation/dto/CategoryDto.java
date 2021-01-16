package com.example.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CategoryDto {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final long id;
        private final String name;
        private final long count;
    }
}
