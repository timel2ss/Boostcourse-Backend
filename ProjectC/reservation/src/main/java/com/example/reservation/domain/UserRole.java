package com.example.reservation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserRole {
    private int id;
    private int user_id;
    private String role_name;
}
