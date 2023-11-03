package com.t1.parser.service;

import com.t1.parser.dto.ResponseDto;

public interface ParserService {
    ResponseDto parse(String text);
}
