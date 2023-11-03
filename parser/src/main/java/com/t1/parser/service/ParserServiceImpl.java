package com.t1.parser.service;

import com.t1.parser.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class ParserServiceImpl implements ParserService {
    @Override
    public ResponseDto parse(String text) {
        if (isNull(text) || text.isEmpty()) {
            log.warn("empty text: {}", text);
            return ResponseDto.builder()
                    .response(new HashMap<>())
                    .build();
        }

        Map<String, Integer> response = new LinkedHashMap<>();

        /* Пробелы не будут учитываться */
        String textWithoutSpaces = text.replaceAll("\\s+", "");

        /* Добавляем в мапу значения */
        for (int i = 0; i < textWithoutSpaces.length(); i++) {
            String key = String.valueOf(textWithoutSpaces.charAt(i));
            response.put(key, response.getOrDefault(key, 0) + 1);
        }

        /* сортируем */
        Map<String, Integer> sortedResponse = response.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        log.info("response: {}", sortedResponse);
        return ResponseDto.builder()
                .response(sortedResponse)
                .build();
    }
}
