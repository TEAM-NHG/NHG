package com.ssafy.member.persistent.repository;

import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.web.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Map;

@Mapper
@Repository
public interface MemberRepository {
    Boolean existsById(@Param("id") String id) throws SQLException;
    void create(Member member) throws SQLException;
    Member findByIdAndPassword(@Param("id") String id, @Param("password") String password) throws SQLException;

    Member findById(String id);

    String findIdByEmail(@Param("email") String email);
    
    int modify(MemberDto member) throws SQLException;
    
	int delete(String id);
	
	boolean existsByIdAndEmail(@Param("id") String id, @Param("email") String email);
	
	boolean modifyPassword(@Param("id") String id, @Param("password") String password);
}
