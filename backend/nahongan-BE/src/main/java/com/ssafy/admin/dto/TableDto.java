package com.ssafy.admin.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class TableDto {
    List<Map<String, Object>> table;
    int currentPage;
    int lastPage;
}
