package com.example.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

public class DisplayInfoDto {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final long id;
        private final long categoryId;
        private final long displayInfoId;
        private final String name;
        private final String description;
        private final String content;
        private final String event;
        private final String openingHours;
        private final String placeName;
        private final String placeLot;
        private final String placeStreet;
        private final String tel;
        private final String homepage;
        private final String email;
        private final Date createDate;
        private final Date modifyDate;
        private final long fileId;
    }
}
