package com.ssafy.tripinfo.web.dto.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@ToString(callSuper = true)
public class LeisureDetailDto extends CommonDetailDto {
    private String parkingleports;   // 레포츠 (28)
    private String openperiod;       // 개장기간 (28)
    private String usetimeleports;
    private String infocenterleports;
}
