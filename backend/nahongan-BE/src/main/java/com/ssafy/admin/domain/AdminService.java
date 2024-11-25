package com.ssafy.admin.domain;

import com.ssafy.admin.dto.TableDataRequest;
import com.ssafy.admin.dto.TableDto;
import com.ssafy.admin.persistent.repository.AdminRepository;
import com.ssafy.companion_board.persistent.entity.CompanionBoard;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.tripinfo.persistent.entity.*;
import com.ssafy.companion_board.persistent.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    int LIST_SIZE = 10;

    public List<Map<String, Object>> getTables() {
        return adminRepository.getTables();
    }

    public List<Map<String, Object>> getColumns(String tableName) {
        return adminRepository.getColumns(tableName);
    }

    public List<Map<String, Object>> getConstraints(String tableName) {
        return adminRepository.getConstraints(tableName);
    }

    public TableDto selectAll(TableDataRequest request) {
        int offset = (request.getPage() - 1) * LIST_SIZE;

        Map<String, Object> params = new HashMap<>();
        params.put("tableName", request.getTableName());
        params.put("filters", request.getFilters());
        params.put("sortConfig", request.getSortConfig());
        params.put("offset", offset);
        params.put("size", LIST_SIZE);
        List<Map<String, Object>> resultMap = adminRepository.selectAll(params);
        int count = adminRepository.getCount(params);
        return TableDto.builder().table(resultMap).currentPage(request.getPage()).lastPage(count/LIST_SIZE+1).build();
    }

    public Map<String, Object> selectById(String tableName, int id) {
        return adminRepository.selectById(tableName, id);
    }

    public int insert(String tableName, Map<String, Object> data) {
        // 데이터 변환: Map<String, Object> -> List<Map<String, Object>>
        List<Map<String, Object>> dataList = data.entrySet().stream()
                .map(entry -> Map.of("key", entry.getKey(), "value", entry.getValue()))
                .toList();

        // 변환된 데이터 확인
        System.out.println(dataList);

        // MyBatis 호출
        return adminRepository.insert(tableName, dataList);
    }

    public int update(String tableName, Map<String, Object> data) {// 데이터 변환: Map<String, Object> -> List<Map<String, Object>>
        // idColumn과 idValue 추출
        String idColumn = data.keySet().iterator().next(); // 첫 번째 키가 idColumn
        Object idValue = data.get(idColumn); // idColumn에 해당하는 값

        // idColumn과 idValue를 제외한 나머지 항목들로 dataList 구성
        List<Map<String, Object>> dataList = data.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(idColumn))  // idColumn을 제외
                .map(entry -> Map.of("key", entry.getKey(), "value", entry.getValue()))  // 나머지 항목들을 Map으로 변환
                .toList();
        // MyBatis 쿼리 호출 (예: adminRepository.update(tableName, data))
        return adminRepository.update(tableName, dataList, idColumn, idValue);
    }

    public int delete(String tableName, int id) {
        return adminRepository.delete(tableName, id);
    }

}


