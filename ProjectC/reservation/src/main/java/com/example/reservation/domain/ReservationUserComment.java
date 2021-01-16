package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class ReservationUserComment {
    private int id;
    private int product_id;
    private int reservation_info_id;
    private int user_id;
    private int score;
    private String comment;
    private Date create_date;
    private Date modify_date;
}
