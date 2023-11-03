package com.t1.parser.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDto {
    private Map<String, Integer> response;
}
