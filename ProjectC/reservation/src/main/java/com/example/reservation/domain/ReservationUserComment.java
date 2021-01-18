package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class ReservationUserComment {
    private long id;
    private long product_id;
    private long reservation_info_id;
    private long user_id;
    private double score;
    private String comment;
    private Date create_date;
    private Date modify_date;
}
