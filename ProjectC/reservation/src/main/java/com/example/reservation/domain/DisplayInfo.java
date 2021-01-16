package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class DisplayInfo {
    private long id;
    private long product_id;
    private String opening_hours;
    private String place_name;
    private String place_lot;
    private String place_street;
    private String tel;
    private String homepage;
    private String email;
    private Date create_date;
    private Date modify_date;
}
