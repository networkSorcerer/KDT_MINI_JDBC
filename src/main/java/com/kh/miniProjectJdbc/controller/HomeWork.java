package com.kh.miniProjectJdbc.controller;

import com.kh.miniProjectJdbc.dao.MemberDAO;
import com.kh.miniProjectJdbc.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeWork {
    private final MemberDAO memberDAO;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> resultMap = new HashMap<>();
        List<MemberVO>list = memberDAO.memberList();
        resultMap.put("list",list);
        return resultMap;
    }
    @GetMapping("/list/{email}")
    public Map<String, Object>detail(@PathVariable("email") String email) {
        Map<String,Object> resultMap = new HashMap<>();
        List<MemberVO>detail = memberDAO.memberDetail(email);
        resultMap.put("detail", detail);
        return resultMap;
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody Map<String, Object> param) {
        // 객체로 받을 때는 @RequestBody ,
        // 단일값 혹은 키-값의 쌍으로 이루어진 data는 @RequestParam
        boolean isSuccess = memberDAO.update(param);
        return ResponseEntity.ok(isSuccess);
    }


//    @PostMapping("/login")
//    public ResponseEntity<Boolean> login(@RequestBody MemberVO vo){
//        log.info("이메일 {}", vo.getEmail());
//        log.info("패스워드 {}", vo.getPassword());
//        boolean isSuccess = memberDAO.login(vo.getEmail(), vo.getPassword());
//        return ResponseEntity.ok(isSuccess);
//    }

}
