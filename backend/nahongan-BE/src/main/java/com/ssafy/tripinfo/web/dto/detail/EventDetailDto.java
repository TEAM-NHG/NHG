package com.ssafy.tripinfo.web.dto.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@ToString(callSuper = true)
public class EventDetailDto extends CommonDetailDto {
    private String eventenddate;     // 행사 종료일 (15)
    private String usetimefestival;
    private String sponsor1tel;
}
