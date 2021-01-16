package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProductImage {
    private long id;
    private long product_id;
    private String type;
    private long file_id;
}
