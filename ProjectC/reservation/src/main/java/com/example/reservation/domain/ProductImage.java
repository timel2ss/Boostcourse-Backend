package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProductImage {
    private int id;
    private int product_id;
    private String type;
    private int file_id;
}
