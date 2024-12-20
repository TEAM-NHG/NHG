package com.ssafy.tripinfo.web.dto.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@ToString(callSuper = true)
public class LodgingDetailDto extends CommonDetailDto {
    private String parkinglodging;
    private String infocenterlodging;
}
