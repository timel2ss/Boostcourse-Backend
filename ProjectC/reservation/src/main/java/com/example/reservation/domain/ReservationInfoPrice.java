package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReservationInfoPrice {
    private int id;
    private int reservation_info_id;
    private int product_price_id;
    private int count;
}
