package com.example.guestbook.repository;

import com.example.guestbook.domain.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LogRepositoryTest {
    @Autowired LogRepository logRepository;

    @Test
    public void log_insert_테스트() {
        Log log = new Log();
        log.setIp("127.0.0.1");
        log.setMethod("insert");

        Log insert = logRepository.insert(log);

        assertThat(insert.getIp()).isEqualTo(log.getIp());
        assertThat(insert.getMethod()).isEqualTo(log.getMethod());
    }
}