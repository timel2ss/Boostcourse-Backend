package com.example.guestbook.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class Log {
    private long id;
    private String ip;
    private String method;
    private Date regdate;
}
