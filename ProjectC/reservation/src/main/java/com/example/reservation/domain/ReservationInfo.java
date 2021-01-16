package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class ReservationInfo {
    private int id;
    private int product_id;
    private int display_info_id;
    private int user_id;
    private Date reservation_date;
    private boolean cancel_flag;
    private Date create_date;
    private Date modify_date;
}
