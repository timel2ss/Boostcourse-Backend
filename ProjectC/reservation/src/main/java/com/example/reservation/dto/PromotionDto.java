package com.example.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PromotionDto {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final long id;
        private final long productId;
        private final long categoryId;
        private final String categoryName;
        private final String description;
        private final long fileId;
    }
}
