package com.ssafy.admin.persistent.repository;

import com.ssafy.admin.dto.TableDataRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AdminRepository {
    // db전체 관련 - 해당 테이블의 제약조건, 컬럼 등의 정보, 전체 테이블명 호출
    List<Map<String, Object>> getTables();
    List<Map<String, Object>> getColumns(@Param("tableName") String tableName);
    List<Map<String, Object>> getConstraints(@Param("tableName") String tableName);

    List<Map<String, Object>> selectAll(Map<String, Object> request);

    int getCount(Map<String, Object> request);

    Map<String, Object> selectById(String tableName, int id);

    int insert(String tableName, List<Map<String, Object>> data);

    int update(String tableName, List<Map<String, Object>> data, String idColumn, Object idValue);

    int delete(String tableName, int id);
}