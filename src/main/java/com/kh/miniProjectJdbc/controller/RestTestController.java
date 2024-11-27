package com.kh.miniProjectJdbc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/get-api")
public class RestTestController {
    @GetMapping("/hello")
    public String getHello() {
        return "안녕하세요. Rest API의 기본 동작입니다.";
    }
    // URL 경로에 값을 포함하여 전송하는 방식
    @GetMapping("/board/{number}")
    public String getPathVariable(@PathVariable String number) {
        return "요청 받은 게시글 번호 : " + number;
    }
}
