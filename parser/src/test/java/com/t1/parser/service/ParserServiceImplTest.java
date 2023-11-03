package com.t1.parser.service;

import com.t1.parser.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class ParserServiceImplTest {

    private ParserServiceImpl parserService = new ParserServiceImpl();

    @Test
    public void testParser_whenHaveNormalText_thenReturnMap() {
        String text = "aaabbbbcc";

        ResponseDto responseDto = parserService.parse(text);

        Map<String, Integer> expectedResponse = new LinkedHashMap<>() {{
            put("b", 4);
            put("a", 3);
            put("c", 2);
        }};

        assertEquals(expectedResponse, responseDto.getResponse());
    }

    @Test
    public void testParser_whenTextIsEmpty_thenReturnEmptyMap() {
        String text = "";

        ResponseDto responseDto = parserService.parse(text);

        assertTrue(responseDto.getResponse().isEmpty());
    }

    @Test
    public void testParser_whenTextIsNull_thenReturnEmptyMap() {
        String text = null;

        ResponseDto responseDto = parserService.parse(text);

        assertTrue(responseDto.getResponse().isEmpty());
    }
}