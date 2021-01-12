package com.example.guestbook.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class GuestbookDtoTest {

    @Test
    public void create_Dto_테스트() {
        String name = "name";
        String content = "content";
        GuestbookDto.Create guestbookDto = new GuestbookDto.Create(name, content);

        assertThat(guestbookDto.getName()).isEqualTo(name);
        assertThat(guestbookDto.getContent()).isEqualTo(content);
    }
}