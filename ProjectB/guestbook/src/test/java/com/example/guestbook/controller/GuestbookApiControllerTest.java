package com.example.guestbook.controller;

import com.example.guestbook.domain.Guestbook;
import com.example.guestbook.dto.GuestbookDto;
import com.example.guestbook.service.GuestbookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GuestbookApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GuestbookService guestbookService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void 목록_조회하기() throws Exception {
        // given
        int start = 0;
        List<Guestbook> list = guestbookService.getList(start);
        int count = guestbookService.getCount();
        int page = (start + 1) / 5;

        // when
        when(guestbookService.getList(start)).thenReturn(list);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/guestbook/list")
                                                                .param("start", Integer.toString(start))
                                                                .contentType(MediaType.APPLICATION_JSON);
        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(page)))
                .andExpect(jsonPath("$.list", is(list)))
                .andExpect(jsonPath("$.total_count", is(count)))
                .andDo(print());

    }


    // TODO solve empty response body problem
//    @Test
//    public void 글_작성하기() throws Exception {
//        // given
//        String name = "name";
//        String content = "content";
//        GuestbookDto.Create createForm = new GuestbookDto.Create(name, content);
//        String requestContent = objectMapper.writeValueAsString(createForm);
//
//        Guestbook guestbook = new Guestbook();
//        guestbook.setName(name);
//        guestbook.setContent(content);
//
//        // when
//        when(guestbookService.write(guestbook, "127.0.0.1")).thenReturn(guestbook);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/guestbook/write")
//                                                                .content(requestContent)
//                                                                .contentType(MediaType.APPLICATION_JSON)
//                                                                .accept(MediaType.APPLICATION_JSON);
//
//        // then
//        mockMvc.perform(requestBuilder)
//                .andExpect(jsonPath("$.name", is(name)))
//                .andExpect(jsonPath("$.content", is(content)))
//                .andDo(print()).andReturn();
//    }

    @Test
    public void 글_삭제하기() throws Exception {
        // given
        Guestbook guestbook = new Guestbook();
        guestbook.setId(0);
        guestbook.setName("name");
        guestbook.setContent("content");
        guestbook.setRegdate(new Date());
        guestbookService.write(guestbook, "127.0.0.1");

        // when
        long id = 0L;
        when(guestbookService.delete(id, "127.0.0.1")).thenReturn(1);

        // then
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/guestbook/delete/" + id).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andDo(print());

        verify(guestbookService).delete(id, "127.0.0.1");
    }
}