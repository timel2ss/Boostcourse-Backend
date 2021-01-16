package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReservationUserCommentImage {
    private int id;
    private int reservation_info_id;
    private int reservation_user_comment_id;
    private int file_id;
}
