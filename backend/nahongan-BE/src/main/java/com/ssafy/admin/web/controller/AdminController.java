package com.ssafy.admin.web.controller;
import com.ssafy.admin.domain.AdminService;
import com.ssafy.admin.dto.TableDataRequest;
import com.ssafy.admin.dto.TableDto;
import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.tripinfo.persistent.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/tables")
    public ResponseEntity<List<Map<String, Object>>> getTables() {
        List<Map<String, Object>> tables = adminService.getTables();
        return ResponseEntity.ok(tables);
    }

    /**
     * 특정 테이블의 컬럼 정보를 가져옵니다.
     */
    @GetMapping("/tables/{tableName}/columns")
    public ResponseEntity<List<Map<String, Object>>> getColumns(@PathVariable String tableName) {
        List<Map<String, Object>> columns = adminService.getColumns(tableName);
        return ResponseEntity.ok(columns);
    }

    /**
     * 특정 테이블의 제약 조건을 가져옵니다.
     */
    @GetMapping("/tables/{tableName}/constraints")
    public ResponseEntity<List<Map<String, Object>>> getConstraints(@PathVariable String tableName) {
        List<Map<String, Object>> constraints = adminService.getConstraints(tableName);
        return ResponseEntity.ok(constraints);
    }

    /**
     * 특정 테이블의 모든 데이터를 가져옵니다.
     */
    @PostMapping("/tables")
    public ResponseEntity<TableDto> selectAll(@RequestBody TableDataRequest request) {

        TableDto data = adminService.selectAll(request);
        return ResponseEntity.ok(data);
    }

    /**
     * 특정 테이블의 ID로 데이터를 가져옵니다.
     */
    @GetMapping("/tables/{tableName}/{id}")
    public ResponseEntity<Map<String, Object>> selectById(@PathVariable String tableName, @PathVariable int id) {
        Map<String, Object> data = adminService.selectById(tableName, id);
        return ResponseEntity.ok(data);
    }

    /**
     * 특정 테이블에 데이터를 삽입합니다.
     */
    @PostMapping("/tables/{tableName}")
    public ResponseEntity<Integer> insert(@PathVariable String tableName, @RequestBody Map<String, Object> data) {
        int insertedId = adminService.insert(tableName, data);
        return ResponseEntity.ok(insertedId);
    }

    /**
     * 특정 테이블의 데이터를 업데이트합니다.
     */
    @PutMapping("/tables/{tableName}")
    public ResponseEntity<Integer> update(@PathVariable String tableName, @RequestBody Map<String, Object> data) {
        int updatedCount = adminService.update(tableName, data);
        return ResponseEntity.ok(updatedCount);
    }

    /**
     * 특정 테이블에서 ID로 데이터를 삭제합니다.
     */
    @DeleteMapping("/tables/{tableName}/{id}")
    public ResponseEntity<Integer> delete(@PathVariable String tableName, @PathVariable int id) {
        int deletedCount = adminService.delete(tableName, id);
        return ResponseEntity.ok(deletedCount);
    }
}



