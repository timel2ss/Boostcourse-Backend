package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private Date create_date;
    private Date modify_date;
}
