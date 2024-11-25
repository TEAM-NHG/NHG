package com.ssafy.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class TableDataRequest {
    private String tableName;
    private int page;
    private List<Filter> filters;
    private SortConfig sortConfig;// Getters and setters
}
@Builder
@Getter
@Setter
class Filter {
    private String column;
    private String value;

    // Getters and setters
}
@Builder
@Getter
@Setter
class SortConfig {
    private String column;
    private int direction; // 1 for ASC, 2 for DESC
    // Getters and setters
}
