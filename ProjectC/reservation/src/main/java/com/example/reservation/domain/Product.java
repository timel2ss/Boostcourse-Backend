package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class Product {
    private long id;
    private long category_id;
    private String description;
    private String content;
    private String event;
    private Date create_date;
    private Date modify_date;
}
