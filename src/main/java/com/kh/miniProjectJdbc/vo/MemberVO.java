package com.kh.miniProjectJdbc.vo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberVO {
    private String email;
    private String password;
    private String name;
    private Date date;


}
