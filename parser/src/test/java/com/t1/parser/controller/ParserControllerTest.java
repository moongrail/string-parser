package com.t1.parser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t1.parser.dto.ResponseDto;
import com.t1.parser.service.ParserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = ParserController.class)
class ParserControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ParserService parserService;

    @Test
    @SneakyThrows
    void parse_whenEmptyText_thenStatus400() {
        mockMvc.perform(get("/parse?text=")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @SneakyThrows
    void parse_whenTextBlank_thenStatus400() {
        mockMvc.perform(get("/parse")
                        .param("text", " ")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @SneakyThrows
    void parse_whenTextSize129_thenStatus400() {
        mockMvc.perform(get("/parse")
                        .param("text", "sing limb, floating limbs, (mutated hands and fingers:1.4)," +
                                " disconnected limbs, mutation, mutated, disgusting, blurry, amputation")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @SneakyThrows
    void parse_whenTextSize128_thenStatus200() {
        mockMvc.perform(get("/parse")
                        .param("text", "ing limb, floating limbs, (mutated hands and fingers:1.4)," +
                                " disconnected limbs, mutation, mutated, disgusting, blurry, amputation")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void parse_whenTextNormal_thenStatus200() {
        ResponseDto responseDto = ResponseDto.builder()
                .response(new LinkedHashMap<>() {{
                    put("a", 5);
                    put("c", 4);
                    put("b", 1);
                }})
                .build();

        when(parserService.parse("aaaaabcccc")).thenReturn(responseDto);
        mockMvc.perform(get("/parse")
                        .param("text", "aaaaabcccc")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)))
                .andDo(print());
    }
}