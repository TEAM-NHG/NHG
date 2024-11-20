package com.ssafy.tripinfo.web.dto.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonDetailDto {
    private int contentid;
    private int contenttypeid;
    private String title;
    private String homepage;
    private String tel;
    private String overview;
    private String addr1;
    private String addr2;
}
