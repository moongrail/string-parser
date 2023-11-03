package com.t1.parser.controller;

import com.t1.parser.dto.ResponseDto;
import com.t1.parser.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/parse")
public class ParserController {
    private final ParserService parserService;

    @GetMapping
    public ResponseDto parse(@RequestParam @Size(min = 1, max = 128)
                             @NotBlank @NotEmpty String text) {
        log.info("input text: {}", text);
        return parserService.parse(text);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException: {}", ex.getMessage());
        return ex.getMessage();
    }
}
