package com.example.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

public class DisplayInfoImageDto {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final long id;
        private final long displayInfoId;
        private final long fileId;
        private final String fileName;
        private final String saveFileName;
        private final String contentType;
        private final boolean deleteFlag;
        private final Date createDate;
        private final Date modifyDate;
    }
}
