package com.example.reservation.dto;

import com.example.reservation.domain.ReservationUserCommentImage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

public class ReservationUserCommentDto {
    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final long id;
        private final long productId;
        private final long reservationInfoId;
        private final double score;
        private final String reservationEmail;
        private final String comment;
        private final Date createDate;
        private final Date modifyDate;
        private final List<ReservationUserCommentImage> reservationUserCommentImages;
    }
}
