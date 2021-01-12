package com.example.guestbook.controller;

import com.example.guestbook.domain.Guestbook;
import com.example.guestbook.dto.GuestbookDto;
import com.example.guestbook.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class GuestbookApiController {
    private final GuestbookService guestbookService;

    @Autowired
    public GuestbookApiController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping("api/guestbook/list")
    public Map<String, Object> list(@RequestParam(name="start", required = false, defaultValue = "0") int start) {
        int count = guestbookService.getCount();
        List<Guestbook> list = guestbookService.getList(start);

        Map<String, Object> map = new HashMap<>();
        map.put("total_count", count);
        map.put("page", (start + 1) / 5);
        map.put("list", list);
        return map;
    }

    @PostMapping("api/guestbook/write")
    public Guestbook write(@RequestBody GuestbookDto.Create guestbookDto, HttpServletRequest httpServletRequest) {
        Guestbook guestbook = new Guestbook();
        guestbook.setName(guestbookDto.getName());
        guestbook.setContent(guestbookDto.getContent());
        guestbook.setRegdate(new Date());

        String clientIp = httpServletRequest.getHeader("X-FORWARDED-FOR") != null ? httpServletRequest.getHeader("X-FORWARDED-FOR") : httpServletRequest.getRemoteAddr();
        return guestbookService.write(guestbook, clientIp);
    }

    @DeleteMapping("api/guestbook/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id, HttpServletRequest httpServletRequest) {
        String clientIp = httpServletRequest.getHeader("X-FORWARDED-FOR") != null ? httpServletRequest.getHeader("X-FORWARDED-FOR") : httpServletRequest.getRemoteAddr();
        guestbookService.delete(id, clientIp);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
