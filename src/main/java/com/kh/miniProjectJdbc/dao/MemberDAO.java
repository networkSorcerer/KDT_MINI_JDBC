package com.kh.miniProjectJdbc.dao;

import com.kh.miniProjectJdbc.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL_MEMBERS = "SELECT email,password, name, reg_date FROM mini_member";
    private static final String LOGIN_QUERY = "SELECT COUNT(*) FROM mini_member WHERE email = ? AND password =? ";
    private static final String SIGNUP_QUERY = "INSERT INTO mini_member (email, password, name) VALUES (? ,?, ?)";
    private static final String CHECK_EMAIL = "SELECT COUNT(*) FROM mini_member WHERE email = ?";
    private static final String DETAIL_MEMBER ="SELECT * FROM mini_member WHERE email = ?";
    private static final String UPDATE_MEMBER = "UPDATE mini_member SET name = ? , password = ? WHERE email = ?";
    // 회원 조회
    public List<MemberVO> memberList() {
        try{
            return jdbcTemplate.query(SELECT_ALL_MEMBERS, new MemberRowMapper() {
            });
        }catch (DataAccessException e) {
            log.error("회원 목록 조회 중 예외 발생", e);
            throw e;
        }
    }
    // 로그인
    public boolean login(String email, String password){
        try {
            int count = jdbcTemplate.queryForObject(LOGIN_QUERY, new Object[]{email, password}, Integer.class);
            return count > 0; // 사용자가 존재하면 true 반환
        } catch (DataAccessException e) {
            log.error("로그인 조회 실패"); // 데이터베이스 접근 중 오류가 발생하면 로그 출력
            return false;
        }
    }


    // 회원 가입
    public boolean signup(MemberVO vo) {
        try {
            int result = jdbcTemplate.update(SIGNUP_QUERY, vo.getEmail(), vo.getPassword(), vo.getName());
            return result >0;
        } catch (DataAccessException e) {
            log.error("회원 가입 중 예외 발생", e);
            return false;
        }
    }
    
    // 회원 가입 여부 확인
    public boolean isEmailExist(String email){
        try {
            int count = jdbcTemplate.queryForObject(CHECK_EMAIL, new Object[]{email},Integer.class);
            return count > 0;
        } catch (DataAccessException e) {
            log.error("이메일 존재 여부 확인 중 에러",e);
            return false;
        }
    }
    public boolean update(Map<String, Object> param) {
        System.out.println(param.get("name"));
        System.out.println(param.get("password"));

        try {
            int count = jdbcTemplate.update(UPDATE_MEMBER, param.get("name"), param.get("password"),param.get("email"));
            return count > 0;
        } catch (DataAccessException e) {
            log.error("회원 정보 업데이트 중 에러 발생", e);
            return false;
        }
    }



    public List<MemberVO> memberDetail(String email) {
        try{
            return jdbcTemplate.query(DETAIL_MEMBER, new MemberRowMapper(), email);
        } catch (DataAccessException e) {
            log.error("상세 정보 조회 실패");
            throw e;
        }
    }


    private static class MemberRowMapper implements RowMapper<MemberVO> {
        @Override
        public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MemberVO(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getDate("reg_date")
            );
        }
    }
}
