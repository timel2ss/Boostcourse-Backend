package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReservationUserCommentImage {
    private long id;
    private long reservation_info_id;
    private long reservation_user_comment_id;
    private long file_id;
}
