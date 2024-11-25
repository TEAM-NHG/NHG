package com.ssafy.tripinfo.web.dto.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@ToString(callSuper = true)
public class ShoppingDetailDto extends CommonDetailDto {
    private String parkingshopping;  // 쇼핑 (38)
    private String opentime;
    private String infocentershopping;
}
