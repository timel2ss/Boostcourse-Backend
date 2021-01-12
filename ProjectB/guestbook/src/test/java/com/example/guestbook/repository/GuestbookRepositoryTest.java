package com.example.guestbook.repository;

import com.example.guestbook.domain.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class GuestbookRepositoryTest {
    @Autowired GuestbookRepository guestbookRepository;

    @Test
    public void 글_작성하기() {
        // given
        Guestbook guestbook = new Guestbook();
        guestbook.setName("name");
        guestbook.setContent("content");

        // when
        guestbookRepository.write(guestbook);

        // then
        List<Guestbook> list = guestbookRepository.getList(0, 5);
        Guestbook findOne = list.stream().filter(guestbook2 -> guestbook2.getId() == guestbook.getId()).findAny().get();

        assertThat(findOne.getName()).isEqualTo(guestbook.getName());
        assertThat(findOne.getContent()).isEqualTo(guestbook.getContent());
    }
    
    @Test
    public void 글_삭제하기() {
        // given
        Guestbook guestbook = new Guestbook();
        guestbook.setName("name");
        guestbook.setContent("content");
        long saveId = guestbookRepository.write(guestbook).getId();
        int beforeCount = guestbookRepository.getCount();

        // when
        int deleteNum = guestbookRepository.delete(saveId);

        // then
        int afterCount = guestbookRepository.getCount();
        assertThat(afterCount).isEqualTo(beforeCount - deleteNum);
    }

    @Test
    public void count_테스트() {
        // given
        int beforeCount = guestbookRepository.getCount();

        Guestbook guestbook1 = new Guestbook();
        guestbook1.setName("name1");
        guestbook1.setContent("content1");

        Guestbook guestbook2 = new Guestbook();
        guestbook2.setName("name2");
        guestbook2.setContent("content2");

        // when
        guestbookRepository.write(guestbook1);
        guestbookRepository.write(guestbook2);

        // then
        int afterCount = guestbookRepository.getCount();
        assertThat(afterCount).isEqualTo(beforeCount + 2);
    }
}