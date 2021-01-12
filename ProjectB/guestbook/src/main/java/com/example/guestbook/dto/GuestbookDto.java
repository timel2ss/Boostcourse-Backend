package com.example.guestbook.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class GuestbookDto {

    @Getter
    @RequiredArgsConstructor
    public static class Create {
        private final String name;
        private final String content;
    }
}
