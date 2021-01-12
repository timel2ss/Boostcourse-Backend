package com.example.guestbook.service;

import com.example.guestbook.domain.Guestbook;
import com.example.guestbook.domain.Log;
import com.example.guestbook.repository.GuestbookRepository;
import com.example.guestbook.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;
    private final LogRepository logRepository;
    public static final int LIMIT = 5;

    @Autowired
    public GuestbookService(GuestbookRepository guestbookRepository, LogRepository logRepository) {
        this.guestbookRepository = guestbookRepository;
        this.logRepository = logRepository;
    }

    @Transactional(readOnly = false)
    public long write(Guestbook guestbook, String ip) {
        guestbookRepository.write(guestbook);

        Log log = new Log();
        log.setIp(ip);
        log.setMethod("INSERT");
        log.setRegdate(new Date());
        logRepository.insert(log);
        return guestbook.getId();
    }

    @Transactional(readOnly = false)
    public int delete(long id, String ip) {
        int deleteNum = guestbookRepository.delete(id);

        Log log = new Log();
        log.setIp(ip);
        log.setMethod("DELETE");
        log.setRegdate(new Date());
        logRepository.insert(log);
        return deleteNum;
    }

    @Transactional
    public List<Guestbook> getList(int start) {
        return guestbookRepository.getList(start, start + LIMIT);
    }

    public int getCount() {
        return guestbookRepository.getCount();
    }
}
