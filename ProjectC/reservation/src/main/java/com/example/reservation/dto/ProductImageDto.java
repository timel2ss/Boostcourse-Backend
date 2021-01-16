package com.example.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

public class ProductImageDto {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final long productId;
        private final long productImageId;
        private final String type;
        private final long fileInfoId;
        private final String fileName;
        private final String saveFileName;
        private final String contentType;
        private final boolean deleteFlag;
        private final Date createDate;
        private final Date modifyDate;
    }
}
