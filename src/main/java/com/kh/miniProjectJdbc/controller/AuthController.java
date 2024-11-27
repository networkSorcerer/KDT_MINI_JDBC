package com.kh.miniProjectJdbc.controller;

import com.kh.miniProjectJdbc.dao.MemberDAO;
import com.kh.miniProjectJdbc.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberDAO memberDAO;
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody MemberVO vo){
        log.info("이메일 {}", vo.getEmail());
        log.info("패스워드 {}", vo.getPassword());
        boolean isSuccess = memberDAO.login(vo.getEmail(), vo.getPassword());
        return ResponseEntity.ok(isSuccess);
    }
    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody MemberVO vo) {
        log.info("가입  {}", vo);
        boolean isSuccess = memberDAO.signup(vo);
        return ResponseEntity.ok(isSuccess);
    }
    // 가입 여부 확인
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> exists(@PathVariable String email) {
        log.error("email : {}", email);
        boolean isExist = memberDAO.isEmailExist(email);
        return ResponseEntity.ok(!isExist);
    }
}
