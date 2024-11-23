package com.ssafy.member.persistent.repository;

import com.ssafy.member.persistent.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Mapper
@Repository
public interface MemberRepository {
    Boolean existsById(@Param("id") String id) throws SQLException;
    void create(Member member) throws SQLException;
    Member findByIdAndPassword(@Param("id") String id, @Param("password") String password) throws SQLException;

    Member findById(@Param("id") String id);

    String findIdByEmail(@Param("email") String email);
    
    int modify(Member member) throws SQLException;
    
	int delete(String id);
	
	boolean existsByIdAndEmail(@Param("id") String id, @Param("email") String email);
	
	void modifyPassword(@Param("id") String id, @Param("password") String password);

    //-------------admin------------------

    List<Member> findAllMember();
    int countMember();

}

