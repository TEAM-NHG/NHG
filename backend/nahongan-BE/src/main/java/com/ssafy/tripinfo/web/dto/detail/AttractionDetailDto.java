package com.ssafy.tripinfo.web.dto.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true)
public class AttractionDetailDto extends CommonDetailDto {
    private String parking;
    private String infocenter;
    private String usetime;
}
