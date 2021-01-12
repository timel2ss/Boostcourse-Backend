package com.example.guestbook.controller;

import com.example.guestbook.domain.Guestbook;
import com.example.guestbook.dto.GuestbookDto;
import com.example.guestbook.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class GuestbookController {
    private final GuestbookService guestbookService;

    @Autowired
    public GuestbookController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    @GetMapping("guestbook")
    public String guestbook(Model model) {
        return "redirect:list";
    }

    @GetMapping("guestbook/list")
    public String list(@RequestParam(name="start", required = false, defaultValue = "0") int start, Model model) {
        int count = guestbookService.getCount();
        int pageCount = count / GuestbookService.LIMIT;
        if(count % GuestbookService.LIMIT > 0) {
            pageCount++;
        }

        List<Integer> pageStartList = new ArrayList<>();
        for(int i = 0; i < pageCount; i++) {
            pageStartList.add(i * GuestbookService.LIMIT);
        }

        model.addAttribute("count", count);
        model.addAttribute("pages", pageStartList);
        model.addAttribute("list", guestbookService.getList(start));
        return "list";
    }

    @PostMapping("guestbook/write")
    public String write(GuestbookDto.Create guestbookDto, HttpServletRequest httpServletRequest) {
        Guestbook guestbook = new Guestbook();
        guestbook.setName(guestbookDto.getName());
        guestbook.setContent(guestbookDto.getContent());
        guestbook.setRegdate(new Date());

        String clientIp = httpServletRequest.getHeader("X-FORWARDED-FOR") != null ? httpServletRequest.getHeader("X-FORWARDED-FOR") : httpServletRequest.getRemoteAddr();
        guestbookService.write(guestbook, clientIp);
        return "redirect:list";
    }

    @PostMapping("guestbook/delete")
    public String delete(@RequestParam(name = "id") Long id, HttpServletRequest httpServletRequest) {
        String clientIp = httpServletRequest.getHeader("X-FORWARDED-FOR") != null ? httpServletRequest.getHeader("X-FORWARDED-FOR") : httpServletRequest.getRemoteAddr();
        guestbookService.delete(id, clientIp);
        return "redirect:list";
    }
}
