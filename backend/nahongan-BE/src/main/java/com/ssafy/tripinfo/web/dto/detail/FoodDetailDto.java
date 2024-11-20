package com.ssafy.tripinfo.web.dto.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@ToString(callSuper = true)
public class FoodDetailDto extends CommonDetailDto {
    private String parkingfood;      // 음식점 (39)
    private String opentimefood;     // 영업시간 (39)
    private String restdatefood;
}
