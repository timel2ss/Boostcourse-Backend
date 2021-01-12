package com.example.guestbook.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class Guestbook {
    private long id;
    private String name;
    private String content;
    private Date regdate;
}
