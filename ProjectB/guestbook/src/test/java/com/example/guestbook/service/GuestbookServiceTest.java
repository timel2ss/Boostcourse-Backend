package com.example.guestbook.service;

import com.example.guestbook.domain.Guestbook;
import com.example.guestbook.repository.GuestbookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class GuestbookServiceTest {
    @Autowired GuestbookService guestbookService;
    @Autowired GuestbookRepository guestbookRepository;

    @Test
    public void 글_작성하기() {
        // given
        Guestbook guestbook = new Guestbook();
        guestbook.setName("name");
        guestbook.setContent("content");
        int beforeCount = guestbookService.getCount();

        // when
        guestbookService.write(guestbook, "127.0.0.1");
        int afterCount = guestbookService.getCount();

        // then
        Guestbook findOne = guestbookService.getList(0).stream().filter(guestbook1 -> guestbook1.getId() == guestbook.getId()).findAny().get();
        assertThat(findOne.getName()).isEqualTo(guestbook.getName());
        assertThat(findOne.getContent()).isEqualTo(guestbook.getContent());
        assertThat(afterCount).isEqualTo(beforeCount + 1);
    }

    @Test
    public void 글_삭제하기() {
        // given
        Guestbook guestbook = new Guestbook();
        guestbook.setName("name");
        guestbook.setContent("content");
        Guestbook saveOne = guestbookService.write(guestbook, "127.0.0.1");
        long saveId = saveOne.getId();
        int beforeCount = guestbookService.getCount();

        // when
        int deleteNum = guestbookService.delete(saveId, "127.0.0.1");

        // then
        int afterCount = guestbookService.getCount();
        assertThat(afterCount).isEqualTo(beforeCount - deleteNum);
    }
}