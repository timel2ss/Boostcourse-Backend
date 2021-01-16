package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class ProductPrice {
    private int id;
    private int product_id;
    private String price_type_name;
    private int price;
    private int discount_rate;
    private Date create_date;
    private Date modify_date;
}
